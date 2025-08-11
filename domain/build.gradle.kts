plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Koin
    implementation(libs.koin.core)

    // BCrypt
    implementation(libs.bcrypt)

    // JUnit
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)

    // MockK
    testImplementation(libs.mockk)
}

tasks.withType<Test> {
    useJUnitPlatform()
}