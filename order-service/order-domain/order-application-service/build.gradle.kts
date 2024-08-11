dependencies {
    implementation(project(":common:common-domain"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")

    implementation("org.springframework.boot:spring-boot-starter-validation")
}