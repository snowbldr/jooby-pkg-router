package com.snowbldr.jooby.pkg.router;

import io.jooby.Jooby;
import io.jooby.ServerOptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static io.jooby.Router.HEAD;
import static io.jooby.Router.METHODS;
import static org.assertj.core.api.Assertions.assertThat;

class AppIT {
    private static final int port = 45420;

    private static final String appUrl = "http://localhost:" + port;
    private static HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void startApp() throws InterruptedException {
        new Thread(() -> Jooby.runApp(
                new String[]{},
                app -> {
                    app.setServerOptions(new ServerOptions().setPort(port));
                    app.install(new PackageRouter("com.snowbldr.jooby.pkg.router.www"));
                }
        )).start();
        String response = getString(appUrl + "/health");
        long start = System.currentTimeMillis();
        while (response == null || !response.equals("OK")) {
            if (System.currentTimeMillis() - start > 30_000) {
                throw new RuntimeException("Timed out waiting for app to start");
            }
            Thread.sleep(50);
            response = getString(appUrl + "/health");
        }
        System.out.println("App Started!");
    }

    protected static String getString(String url) {
        try {
            return client.send(HttpRequest.newBuilder().uri(URI.create(url)).GET().build(), HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            return "";
        }
    }

    protected static HttpResponse<String> retrieveString(String method, String url) {
        try {
            return client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .method(method, HttpRequest.BodyPublishers.noBody())
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void checkHealthy() {
        assertThat(getString(appUrl + "/health")).isEqualTo("OK");
    }

    @Test
    public void indexResolves() {
        assertThat(getString(appUrl)).isEqualTo("You found the root! jooby");
    }

    @Test
    public void helloResolves() {
        assertThat(getString(appUrl + "/hello?name=world")).isEqualTo("hello world!");
    }

    @Test
    public void stuffIndexResolves() {
        assertThat(getString(appUrl + "/stuff")).isEqualTo("you found stuff");
    }

    @Test
    public void stuffParamResolves() {
        assertThat(getString(appUrl + "/stuff/foo")).isEqualTo("found stuff foo");
    }

    @Test
    public void allMethodsWork() {
        for (String method : METHODS) {
            HttpResponse<String> response = retrieveString(method, appUrl + "/stuff/foo");
            assertThat(response).isNotNull();
            if (method.equals(HEAD)) {
                assertThat(response.statusCode()).isEqualTo(200);
            } else {
                assertThat(response.body()).isEqualTo("found stuff foo");
            }
        }
    }

    @Test
    public void websocketWorks() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        final String[] response = {null};
        WebSocket ws = client.newWebSocketBuilder()
                .buildAsync(
                        URI.create("ws://localhost:" + port + "/stuff/ws"),
                        new WebSocket.Listener() {
                            @Override
                            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                                response[0] = data.toString();
                                latch.countDown();
                                return null;
                            }
                        }
                ).join();
        ws.sendText("hello", true).join();
        latch.await(5, TimeUnit.SECONDS);
        assertThat(response[0]).isEqualTo("Got hello");
    }

    @Test
    public void sseWorks() throws IOException, InterruptedException {
        HttpResponse<Stream<String>> response = client.send(HttpRequest.newBuilder(URI.create(appUrl + "/stuff/sse")).build(), HttpResponse.BodyHandlers.ofLines());
        assertThat(response.statusCode()).isEqualTo(200);
        Optional<String> event = response.body().findFirst();
        assertThat(event.isPresent()).isTrue();
        assertThat(event.get()).isEqualTo("data:hello");
    }

}
