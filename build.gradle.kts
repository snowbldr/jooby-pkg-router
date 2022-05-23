group = "io.github.snowbldr.jooby"
description = "Package based routing for Jooby"
version = "1.0.0"

plugins {
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

dependencies {
    api("io.jooby:jooby:2.14.2")
    api("org.reflections:reflections:0.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.jooby:jooby-utow:2.14.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "jooby-pkg-router"
            from(components["java"])
            versionMapping {
                usage("java-api"){
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Jooby Package Router")
                description.set("Package based routing for Jooby")
                url.set("https://github.com/snowbldr/jooby-pkg-router")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://github.com/snowbldr/jooby-pkg-router/blob/main/LICENSE.txt")
                    }
                }
                developers {
                    developer {
                        id.set("snowbldr")
                        name.set("Snow Builder")
                        email.set("r@kmtn.me")
                        organization.set("snowbldr")
                        organizationUrl.set("https://github.com/snowbldr/")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/snowbldr/jooby-pkg-router.git")
                    developerConnection.set("scm:git:ssh://github.com:snowbldr/jooby-pkg-router.git")
                    url.set("https://github.com/snowbldr/jooby-pkg-router")
                }
            }
        }
    }
    repositories {
        maven {
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = properties["sonatypeUsername"].toString()
                password = properties["sonatypePassword"].toString()
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if(JavaVersion.current().isJava9Compatible){
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}