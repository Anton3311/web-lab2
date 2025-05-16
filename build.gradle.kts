plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.xerial:sqlite-jdbc:3.49.1.0")
    // https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly("javax.servlet:javax.servlet-api:3.0.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.war {
    webAppDirectory = file("src/main/webapp")
}