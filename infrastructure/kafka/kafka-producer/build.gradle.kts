dependencies {
    implementation(project(":infrastructure:kafka:kafka-config-data"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation(project(":infrastructure:kafka:kafka-model"))
    implementation(project(":common:common-domain"))
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.apache.kafka:kafka-clients")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
