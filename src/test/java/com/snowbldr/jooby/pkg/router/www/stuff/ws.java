package com.snowbldr.jooby.pkg.router.www.stuff;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;
import io.jooby.WebSocketConfigurer;

import javax.annotation.Nonnull;

public class ws implements JoobyRoute {
    @Override
    public Object ws(@Nonnull Context ctx, @Nonnull WebSocketConfigurer configurer) {
        configurer.onMessage((ws, message) -> {
            ws.send("Got " + message.value());
        });
        return null;
    }
}
