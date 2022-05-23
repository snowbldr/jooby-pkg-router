package io.github.snowbldr.jooby.pkg.router;

import io.jooby.Context;
import io.jooby.ServerSentEmitter;
import io.jooby.WebSocketConfigurer;

import javax.annotation.Nonnull;

public interface JoobyRoute {

    default Object get(@Nonnull Context ctx) {
        return null;
    }

    default Object post(@Nonnull Context ctx) {
        return null;
    }

    default Object put(@Nonnull Context ctx) {
        return null;
    }

    default Object patch(@Nonnull Context ctx) {
        return null;
    }

    default Object head(@Nonnull Context ctx) {
        return null;
    }

    default Object options(@Nonnull Context ctx) {
        return null;
    }

    default Object delete(@Nonnull Context ctx) {
        return null;
    }

    default Object trace(@Nonnull Context ctx) {
        return null;
    }

    default Object ws(@Nonnull Context ctx, @Nonnull WebSocketConfigurer configurer) {
        return null;
    }

    default Object sse(@Nonnull ServerSentEmitter sse) {
        return null;
    }
}
