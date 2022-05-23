package com.snowbldr.jooby.pkg.router.www.stuff;

import com.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class index implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "you found stuff";
    }

}
