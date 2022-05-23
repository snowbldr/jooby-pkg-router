plugins {
    `java-library`
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

tasks.named<Test>("test") {
    useJUnitPlatform()
}
