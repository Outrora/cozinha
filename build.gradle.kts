plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.allopen") version "2.1.20"
    java
    id("io.quarkus")
    jacoco
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-opentelemetry")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("io.quarkus:quarkus-jdbc-h2")

}

group = "br.com.fiap"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    systemProperty("quarkus.test.profile", "test")
    useJUnitPlatform()

}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    extensions.configure<JacocoTaskExtension> {
        excludes = listOf(
            "**/io/quarkus/**",
            "**/*PanacheRepository*",
            "**/*PanacheQuery*",
            "**/*\$Companion.*",
            "**/*\$DefaultImpls.*",
            "**/*_*",
            "**/Generated*",
            "**/META-INF/**"
        )
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }
    // Usa a API moderna do Gradle para diretórios
    classDirectories.setFrom(
        layout.buildDirectory.dir("classes/kotlin/main").map {
            fileTree(it) {
                exclude(

                    "**/io/quarkus/**",
                    "**/*PanacheRepository*",
                    "**/*PanacheQuery*",
                    "**/*\$Companion.*",
                    "**/*\$DefaultImpls.*",
                    "**/*_*",
                    "**/Generated*",
                    "**/META-INF/**"
                )
            }
        }.get()
    )
    sourceDirectories.setFrom(files("src/main/kotlin"))
    executionData.setFrom(files("${layout.buildDirectory.get()}/jacoco/test.exec"))
}

jacoco {
    toolVersion = "0.8.12"
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("io.quarkus.hibernate.orm.panache.kotlin.Generated")
    annotation("javax.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

