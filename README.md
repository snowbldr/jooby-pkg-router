# jooby-pkg-router
Package based routing for the jooby web framework

## What is it
This library provides the ability to have routes defined by their package and class names, much like they're defined in
the apache web server and other static file servers.

Using package (or directory) based routing makes finding and managing routes in a web application trivial. Look at the 
url being used, and follow the directory path down to the handler. 

This prevents hunting through a web app and trying to piece together bits of routes, or trying to match patterns to find
where the route is being handled.

## How to use it
First install the dependency
Gradle kotlin:
```kotlin
implementation("io.github.snowbldr.jooby:jooby-pkg-router:1.0.0")
```
Maven:
```xml
<dependency>
    <groupId>io.github.snowbldr.jooby</groupId>
    <artifactId>jooby-pkg-router</artifactId>
    <version>1.0.0</version>
</dependency>
```

Make a package to put your routes in, and add route classes. Each java route file must implement JoobyRoute.

- src/main/java
  - com.mycompany.myapp
    - www
      - stuff
        - index.java
        - secrets.java
      - index.java
      - hello.java

These routes map to the following paths:
<pre>
- index.java         -> /
- hello.java         -> /hello
- stuff/index.java   -> /stuff 
- stuff/secrets.java -> /stuff/secrets
</pre>

Here's an example of one of index the routes:
```java
import JoobyRoute;
import io.jooby.Context;
import javax.annotation.Nonnull;

public class index implements JoobyRoute {
    @Override
    public Object get(@Nonnull Context ctx) {
        return "This is an index!";
    }
}
```

Install the PackageRouter extension in your Jooby app, and pass the base package containing your routes.
```java
import PackageRouter;
import static io.jooby.Jooby.runApp;
public class MyApp {
    public static void main(String[] args) {
        runApp(args, app -> app.install(new PackageRouter("com.mycompany.myapp.www")));
    }
}
```

That's it!

See [Test Routes](https://github.com/snowbldr/jooby-pkg-router/tree/main/src/test/java/io/github/snowbldr/jooby/pkg/router/www), 
[Integration Tests](https://github.com/snowbldr/jooby-pkg-router/blob/main/src/test/java/io/github/snowbldr/jooby/pkg/router/AppIT.java),
and [Example app](https://github.com/snowbldr/jooby-pkg-router/blob/main/src/test/java/io/github/snowbldr/jooby/pkg/router/Example.java)
For more examples

## Jooby instance (app) access
To get access to the Jooby instance (the app) from a JoobyRoute, add a constructor with Jooby as a parameter and it will
be injected when the Route is instantiated.

```java
import JoobyRoute;
import io.jooby.Context;
import io.jooby.Jooby;
import javax.annotation.Nonnull;

public class index implements JoobyRoute {
    private final Jooby app;
    
    public index(Jooby app){
        this.app = app;
    }
    @Override
    public Object get(@Nonnull Context ctx) {
        return "Running in app " + app.getName();
    }
}
```

## Path Parameters
Single parameters are supported by prefixing the name of the class with a `$` (i.e. $userId.java)

- src/main/java
  - com.mycompany.myapp
    - www
      - users
        - $userId.java

`www.users.$userId.java` maps to the path `/users/{userId}` in your Jooby app

## CatchAll
Catchall paths are supported by suffixing the name of the class with a `_` (i.e. catch_.java)

- src/main/java
  - com.mycompany.myapp
    - www
      - images_.java

`www.images_.java` maps to the path `/images*path` in your Jooby app.

The remainder of the path is always set to the variable name `restPath` and is accessible via `ctx.path("restPath")` in the handler

## How it works
When the jooby application starts up, the PackageRouter scans the provided base package for all classes implementing the
JoobyRoute interface.

The package of each class is converted to a valid jooby route path string, and then added to the app.

Only the methods that are implemented by the class are added to the app.