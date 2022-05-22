/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.snowbldr.jooby.pkg.router;

import io.jooby.Context;
import io.jooby.Extension;
import io.jooby.Jooby;
import org.reflections.Reflections;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static io.jooby.Router.METHODS;
import static org.reflections.scanners.Scanners.SubTypes;

public class PackageRouter implements Extension {
    private final String routePackage;

    public PackageRouter(String routePackage) {
        this.routePackage = routePackage;
    }

    @Override
    public void install(@Nonnull Jooby application) {
        Reflections reflections = new Reflections(routePackage);
        Set<Class<?>> routes = reflections.get(SubTypes.of(JoobyRoute.class).asClass());
        try {
            for (Class<?> route : routes) {
                Object routeInstance = route.getConstructor().newInstance();
                for (String method : METHODS) {
                    Method handler = route.getMethod(method.toLowerCase(), Context.class);
                    if (!handler.getDeclaringClass().equals(JoobyRoute.class)) {
                        application.route(method, toRoutePath(route.getCanonicalName()), ctx -> handler.invoke(routeInstance, ctx));
                    }
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create routes", e);
        }
    }

    private String toRoutePath(String routeClass) {
        String path = routeClass.substring(routePackage.length() + 1).replaceAll("\\.", "/");

        path = "/" + Arrays.stream(path.split("/"))
                .map(part -> part.startsWith("$") ? "{" + part.substring(1) + "}" : part)
                .map(part -> part.endsWith("_") ? part.substring(0, part.length() - 1) + "*path" : part)
                .collect(Collectors.joining("/"));

        if (path.endsWith("index")) {
            path = path.substring(0, path.length() - "/index".length());
        }
        return path;
    }
}