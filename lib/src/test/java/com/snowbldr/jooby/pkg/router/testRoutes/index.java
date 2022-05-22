package com.snowbldr.jooby.pkg.router.testRoutes;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class index extends JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "You found the root!";
    }
}
