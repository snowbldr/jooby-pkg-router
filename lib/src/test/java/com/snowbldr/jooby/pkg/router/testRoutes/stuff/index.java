package com.snowbldr.jooby.pkg.router.testRoutes.stuff;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class index extends JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "you found stuff";
    }

    @Override
    public Object post(@Nonnull Context ctx) {
        return "postin to stuff";
    }
}
