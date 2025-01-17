plugins {
    id("application")
    id("org.gradlex.extra-java-module-info").version("1.3")
}

dependencies {
    implementation(project(":com.mechanitis.demo.sense.flow"))
    implementation(project(":com.mechanitis.demo.sense.service"))
    implementation("io.projectreactor:reactor-core:3.5.2")

    testImplementation(project(":com.mechanitis.demo.sense.service.test"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testImplementation("org.mockito:mockito-core:5.1.1")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.mechanitis.demo.sense.mood.MoodService")
}

extraJavaModuleInfo {
    // this I seem to need at runtime
    automaticModule("javax.websocket-api-1.1.jar", "javax.websocket.api")
    // these I don"t need explicitly, but the build fails without them
    automaticModule("javax.websocket-client-api-1.0.jar", "org.eclipse.jetty.websocket.client")
    automaticModule("javax.servlet-api-3.1.0.jar", "javax.servlet.api")
}
