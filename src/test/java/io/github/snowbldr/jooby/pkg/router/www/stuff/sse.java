package io.github.snowbldr.jooby.pkg.router.www.stuff;

import io.github.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.ServerSentEmitter;

import javax.annotation.Nonnull;

public class sse implements JoobyRoute {

    @Override
    public Object sse(@Nonnull ServerSentEmitter sse) {
        sse.send("hello");
        return null;
    }
}
