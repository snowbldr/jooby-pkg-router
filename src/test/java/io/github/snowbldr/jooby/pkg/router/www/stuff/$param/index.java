package io.github.snowbldr.jooby.pkg.router.www.stuff.$param;

import io.github.snowbldr.jooby.pkg.router.JoobyRoute;
import io.jooby.Context;

import javax.annotation.Nonnull;

public class index implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object head(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object post(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object put(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object patch(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object delete(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object options(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }

    @Override
    public Object trace(@Nonnull Context ctx) {
        return "found stuff "+ctx.path("param").value();
    }
}
