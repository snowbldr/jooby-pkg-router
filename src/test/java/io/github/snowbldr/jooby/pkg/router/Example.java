package io.github.snowbldr.jooby.pkg.router;

import static io.jooby.Jooby.runApp;

public class Example {
    public static void main(String[] args) {
        runApp(args, app -> app.install(new PackageRouter("io.github.snowbldr.jooby.pkg.router.www")));
    }
}
