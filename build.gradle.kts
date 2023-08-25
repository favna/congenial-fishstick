plugins {
    id("java")
    id("org.springframework.boot") version "3.1.2"
    id("org.liquibase.gradle") version "2.0.4"
}

group = "com.example"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
                "changelogFile" to "src/test/resources/db/changelog/db.changelog-master.yaml",
                "classpath" to "src/test/resources",
                "url" to "jdbc:postgresql://localhost:5432/test",
                "username" to "gpadmin",
                "password" to "gpadmin",
                "driver" to "org.postgresql.Driver",
        )
    }
    runList = "main"
}

configurations.configureEach {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-json")
    exclude(group = "com.sun.xml.bind")
}

repositories {
    mavenLocal()
    mavenCentral()
}

val springBootVersion = "3.1.2"
val springCloudVersion = "2022.0.3"
val springCloudStarterVersion = "4.0.3"
val pivotalSpringCloudServicesVersion = "4.0.3"
val springCloudContractVersion = "4.0.3"
val springdocVersion = "2.1.0"
val restAssuredVersion = "5.3.1"
val rdf4jVersion = "3.7.7"
val lombokVersion = "1.18.28"

dependencies {
    // Spring boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-webflux:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-security:${springBootVersion}")

    // Spring Cloud dependencies
    implementation("io.pivotal.spring.cloud:spring-cloud-services-starter-config-client:${pivotalSpringCloudServicesVersion}")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:${springCloudStarterVersion}")

    // Database / service related dependencies
    implementation("org.apache.velocity:velocity-engine-core:2.3")
    implementation("org.postgresql:r2dbc-postgresql:1.0.2.RELEASE")
    runtimeOnly("org.postgresql:postgresql:42.6.0")

    // Other dependencies
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:${springdocVersion}")
    implementation("com.google.guava:guava:32.1.1-jre")
    implementation("org.eclipse.rdf4j:rdf4j-model:${rdf4jVersion}")

    // Lombok
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    // Test Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}")
    testImplementation("org.springframework.security:spring-security-test:6.1.1")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:${springCloudContractVersion}")
    testImplementation("io.rest-assured:spring-web-test-client:${restAssuredVersion}")
    testImplementation("io.rest-assured:rest-assured-common:${restAssuredVersion}")
    testImplementation("io.projectreactor:reactor-test:3.5.8")

    // Dependencies required for running liquibase for tests
    liquibaseRuntime("org.liquibase:liquibase-core:4.23.0")
    liquibaseRuntime("info.picocli:picocli:4.7.4")
    liquibaseRuntime("org.liquibase.ext:liquibase-hibernate5:4.22.0")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
