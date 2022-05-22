package com.snowbldr.jooby.pkg.router.testRoutes.stuff.$param;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class $paramTwo extends JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return ctx.path("paramTwo")+" and "+ctx.path("paramTwo").value();
    }
}
