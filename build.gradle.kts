plugins {
	id("java")
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "8.3.0"
	// id("com.adarshr.test-logger") version "4.0.0"
}

group = "com.leboncoin"
version = "1.0.0"
description = "Leboncoin take home assignment: FizzBuzz API"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


spotless {
    java {
        palantirJavaFormat("2.89.0")
    }
}

// tasks.withType<Test>().configureEach {
// 	testLogging {
//
// 	}jk
//
// }
tasks.named<Test>("test") {
    useJUnitPlatform()
}
