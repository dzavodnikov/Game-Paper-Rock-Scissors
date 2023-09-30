plugins {
    application
}

group = "pro.zavodnikov.prs"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("pro.zavodnikov.prs.Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
