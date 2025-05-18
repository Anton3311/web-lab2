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
    implementation("log4j:log4j:1.2.16")
    implementation("org.postgresql:postgresql:42.7.5")
    // https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly("javax.servlet:javax.servlet-api:3.0.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.war {
    webAppDirectory = file("src/main/webapp")
}

var catalinaHome: String? = System.getenv("CATALINA_HOME")
var tomcatWebappsDirectory = file("${catalinaHome}/webapps/")

tasks.register<Copy>("deployToTomcat") {
    dependsOn(tasks.named("war"))

    val warTask = tasks.named<War>("war")
    from(warTask.map { it.archiveFile.get().asFile })

    into(tomcatWebappsDirectory)
    rename { "Lab2Cinema.war" }
}