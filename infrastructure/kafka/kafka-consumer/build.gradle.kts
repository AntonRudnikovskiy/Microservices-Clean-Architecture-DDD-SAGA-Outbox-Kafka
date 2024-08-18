dependencies {
    implementation(project(":infrastructure:kafka:kafka-config-data"))
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.apache.kafka:kafka-clients")
    implementation("org.springframework.boot:spring-boot-starter-web")
}