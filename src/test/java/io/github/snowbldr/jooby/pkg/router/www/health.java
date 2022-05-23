package io.github.snowbldr.jooby.pkg.router.www;

import io.github.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class health implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "OK";
    }

    @Override
    public Object head(@Nonnull Context ctx) {
        return "";
    }
}
