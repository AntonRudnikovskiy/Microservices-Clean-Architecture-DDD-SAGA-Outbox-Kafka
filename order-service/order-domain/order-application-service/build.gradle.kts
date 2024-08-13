dependencies {
    implementation(project(":common:common-domain"))
    implementation(project(":order-service:order-domain:order-domain-core"))
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework:spring-tx")

    implementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    implementation("org.assertj:assertj-core:3.24.2")
    implementation("org.mockito:mockito-junit-jupiter:5.4.0")
    implementation("org.mockito:mockito-core:5.4.0")
}