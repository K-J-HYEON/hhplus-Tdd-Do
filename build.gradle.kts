plugins {
	java
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	id("jacoco")
}

allprojects {
	group = property("app.group").toString()
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.dependencies.get().toString())
	}
}

group = "io.hhplus"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

with(extensions.getByType(JacocoPluginExtension::class.java)) {
	toolVersion = "0.8.7"
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation(libs.spring.boot.starter.web)
	annotationProcessor(libs.spring.boot.configuration.processor)
	testImplementation(libs.spring.boot.starter.test)
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.2")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	compileOnly(libs.lombok)
	annotationProcessor(libs.lombok)

	// JUnit5
	testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.3.50")

	// AssertJ
	testImplementation("org.assertj:assertj-core:3.13.2")

	// junit4, hamcrst 의존성 제거
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
		exclude(module = "hamcrest-core")
		exclude(module = "hamcrest-library")
	}
}

// bundling tasks
tasks.getByName("bootJar") {
	enabled = true
}
tasks.getByName("jar") {
	enabled = false
}
// test tasks
tasks.test {
	ignoreFailures = true
	useJUnitPlatform()
}


// JUnit5 - Test를 위한 Task 추가
tasks.withType<Test> {
	useJUnitPlatform()
}


