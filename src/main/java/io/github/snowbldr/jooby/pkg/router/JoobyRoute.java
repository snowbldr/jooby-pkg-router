package io.github.snowbldr.jooby.pkg.router;

import io.jooby.Context;
import io.jooby.ServerSentEmitter;
import io.jooby.WebSocketConfigurer;

import javax.annotation.Nonnull;

/**
 * A Jooby application route
 * The implementation's canonical name becomes the path to the route
 */
public interface JoobyRoute {

    /**
     * Handler for a get request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object get(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a post request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object post(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a get request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object put(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a patch request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object patch(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a head request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object head(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a options request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object options(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a delete request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object delete(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a trace request to a Jooby Application
     * @param ctx The request context
     * @return A response body, if any
     */
    default Object trace(@Nonnull Context ctx) {
        return null;
    }

    /**
     * Handler for a get request to a Jooby Application
     * @param ctx The request context
     * @param configurer The WebSocketConfigurer used to initialize and configure each websocket
     * @return A response body, if any
     */
    default Object ws(@Nonnull Context ctx, @Nonnull WebSocketConfigurer configurer) {
        return null;
    }

    /**
     * Handler for a get request to a Jooby Application
     * @param sse A Server Sent Event emitter for emitting events
     * @return A response body, if any
     */
    default Object sse(@Nonnull ServerSentEmitter sse) {
        return null;
    }
}
