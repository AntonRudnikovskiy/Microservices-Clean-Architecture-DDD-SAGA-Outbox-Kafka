rootProject.name = "food-ordering-system"
include("order-service")
include("order-service:order-application")
findProject(":order-service:order-application")?.name = "order-application"
include("order-service:order-container")
findProject(":order-service:order-container")?.name = "order-container"
include("order-service:order-dataaccess")
findProject(":order-service:order-dataaccess")?.name = "order-dataaccess"
include("order-service:order-domain")
findProject(":order-service:order-domain")?.name = "order-domain"
include("order-service:order-domain:order-damain-core")
findProject(":order-service:order-domain:order-damain-core")?.name = "order-damain-core"
include("order-service:order-domain:order-domain-service")
findProject(":order-service:order-domain:order-domain-service")?.name = "order-domain-service"
include("order-service:order-messaging")
findProject(":order-service:order-messaging")?.name = "order-messaging"
