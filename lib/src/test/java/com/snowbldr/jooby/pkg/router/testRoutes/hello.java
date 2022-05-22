package com.snowbldr.jooby.pkg.router.testRoutes;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class hello extends JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "hello";
    }
}
