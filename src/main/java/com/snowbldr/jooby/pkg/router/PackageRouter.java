package com.snowbldr.jooby.pkg.router;

import io.jooby.*;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static io.jooby.Router.METHODS;
import static org.reflections.scanners.Scanners.SubTypes;

public class PackageRouter implements Extension {
    private final String routePackage;

    public PackageRouter(String routePackage) {
        this.routePackage = routePackage;
    }

    @Override
    public void install(@Nonnull Jooby app) {
        Reflections reflections = new Reflections(routePackage);
        Set<Class<?>> routes = reflections.get(SubTypes.of(JoobyRoute.class).asClass());
        try {
            for (Class<?> route : routes) {
                Object routeInstance = constructHandler(route, app);
                String routePath = toRoutePath(route.getCanonicalName());
                for (String method : METHODS) {
                    Method handler = route.getMethod(method.toLowerCase(), Context.class);
                    if (isOverriden(handler)) {
                        app.route(method, routePath, ctx -> handler.invoke(routeInstance, ctx));
                    }
                }
                Method wsHandler = route.getMethod("ws", Context.class, WebSocketConfigurer.class);
                if (isOverriden(wsHandler)) {
                    app.ws(routePath, (ctx, configurer) -> {
                        try {
                            wsHandler.invoke(routeInstance, ctx, configurer);
                        } catch (ReflectiveOperationException e) {
                            throw new RuntimeException("Failed to call ws handler on route " + route.getCanonicalName(), e);
                        }
                    });
                }
                Method sseHandler = route.getMethod("sse", ServerSentEmitter.class);
                if (isOverriden(sseHandler)) {
                    app.sse(routePath, sse -> sseHandler.invoke(routeInstance, sse));
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create routes", e);
        }
    }

    private boolean isOverriden(Method method) {
        return !method.getDeclaringClass().equals(JoobyRoute.class);
    }

    private Object constructHandler(Class<?> route, Jooby app) {
        try {
            return route.getConstructor(Jooby.class).newInstance(app);
        } catch (ReflectiveOperationException ignored) {
        }
        try {
            return route.getConstructor().newInstance();
        } catch (ReflectiveOperationException ignored) {
        }
        throw new RuntimeException("Cannot construct new instance of " + route.getCanonicalName() + ", no public constructor available. Must either provide a noArgs constructor, or a constructor that receives only an instance of io.jooby.Jooby");
    }

    private String toRoutePath(String routeClass) {
        String path = routeClass.substring(routePackage.length() + 1).replaceAll("\\.", "/");

        path = "/" + Arrays.stream(path.split("/"))
                .map(part -> part.startsWith("$") ? "{" + part.substring(1) + "}" : part)
                .map(part -> part.endsWith("_") ? part.substring(0, part.length() - 1) + "*path" : part)
                .collect(Collectors.joining("/"));

        if (path.endsWith("index")) {
            path = path.substring(0, path.length() - "/index".length());
        }
        return path;
    }
}
