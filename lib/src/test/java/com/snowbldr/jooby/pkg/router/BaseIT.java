package com.snowbldr.jooby.pkg.router;

import org.junit.jupiter.api.BeforeAll;
import io.jooby.Jooby;

abstract class BaseIT {
    private static final Object lock = new Object();
    private static boolean appStarted = false;

    @BeforeAll
    static void startApp(){
        synchronized (lock){
            if(!appStarted){
                runTestApp();
                appStarted = true;
            }
        }
    }

    static void runTestApp(){
        Jooby.runApp(new String[]{}, app -> {
            app.install(new PackageRouter("me.kmtn.jooby.pkg.router.testRoutes"));
        });
    }
}
