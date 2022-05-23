package com.snowbldr.jooby.pkg.router.www;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class hello implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "hello "+ctx.query("name").value()+"!";
    }
}
