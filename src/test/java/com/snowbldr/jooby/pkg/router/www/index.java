package com.snowbldr.jooby.pkg.router.www;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;
import io.jooby.Jooby;

import javax.annotation.Nonnull;

public class index implements JoobyRoute {
    private final Jooby app;
    public index(Jooby app){
        this.app = app;
    }
    @Override
    public String get(@Nonnull Context ctx) {
        return "You found the root! "+app.getName();
    }
}
