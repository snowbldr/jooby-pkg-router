package com.snowbldr.jooby.pkg.router;

import io.jooby.Context;
import io.jooby.ServerSentEmitter;
import io.jooby.WebSocketConfigurer;

import javax.annotation.Nonnull;

public abstract class JoobyRoute {

    public Object get(@Nonnull Context ctx) {
        return null;
    }

    public Object post(@Nonnull Context ctx) {
        return null;
    }

    public Object put(@Nonnull Context ctx) {
        return null;
    }

    public Object patch(@Nonnull Context ctx) {
        return null;
    }

    public Object head(@Nonnull Context ctx) {
        return null;
    }

    public Object options(@Nonnull Context ctx) {
        return null;
    }

    public Object delete(@Nonnull Context ctx) {
        return null;
    }

    public Object trace(@Nonnull Context ctx) {
        return null;
    }

    public Object ws(@Nonnull Context ctx, @Nonnull WebSocketConfigurer configurer) {
        return null;
    }

    public Object sse(@Nonnull ServerSentEmitter sse) {
        return null;
    }
}
