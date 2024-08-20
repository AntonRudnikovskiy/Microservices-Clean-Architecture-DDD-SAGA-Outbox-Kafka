dependencies {
    implementation(project(":order-service:order-domain:order-application-service"))
    implementation(project(":common:common-domain"))
    implementation(project(":order-service:order-domain:order-domain-core"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}