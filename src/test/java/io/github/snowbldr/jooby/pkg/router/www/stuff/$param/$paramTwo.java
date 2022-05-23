package io.github.snowbldr.jooby.pkg.router.www.stuff.$param;

import io.github.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class $paramTwo implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return ctx.path("param")+" and "+ctx.path("paramTwo").value();
    }
}
