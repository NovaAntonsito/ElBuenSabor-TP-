plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    tasks.named("compileJava") {
        inputs.files(tasks.named("processResources"))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mercadopago:sdk-java:2.1.11")
    implementation("jakarta.mail:jakarta.mail-api:2.1.2")
    implementation("commons-fileupload:commons-fileupload:1.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("com.mashape.unirest:unirest-java:1.4.9")
    implementation ("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.security:spring-security-core:6.0.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("com.cloudinary:cloudinary-http44:1.24.0")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
