plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)

    // Apply the JMH plugin to add support for benchmarking.
    id("me.champeau.jmh") version "0.7.3"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("one.util:streamex:0.8.2")
    implementation("com.codepoetics:protonpack:1.16")
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("org.jooq:jool:0.9.15")
    implementation("io.vavr:vavr:0.10.4")
    implementation("com.pivovarit:parallel-collectors:3.0.0")
    implementation("org.eclipse.collections:eclipse-collections-api:11.1.0")
    implementation("org.eclipse.collections:eclipse-collections:11.1.0")

    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation(libs.junit.jupiter.engine)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Configure JMH to not include test classes by default
jmh {
    includeTests = false
}

// Make JMH classes available to test source set
sourceSets {
    test {
        compileClasspath += sourceSets["jmh"].output
        runtimeClasspath += sourceSets["jmh"].output
    }
}

configurations {
    testImplementation {
        extendsFrom(configurations["jmhImplementation"])
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
