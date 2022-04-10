import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.gger"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.graphql-java:graphql-java:11.0")
	implementation("com.graphql-java:graphql-java-spring-boot-starter-webmvc:1.0")
	implementation("com.google.guava:guava:26.0-jre")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.github.ben-manes.caffeine:caffeine")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
