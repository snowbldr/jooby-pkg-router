package com.snowbldr.jooby.pkg.router.www.stuff.$param;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class catchAll_ implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return ctx.path("param").value() +" at "+ctx.path("restPath");
    }
}
