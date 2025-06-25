# ing-spring-advanced-training
The integrations and examples presented in the ING Hubs 'Spring Advanced' training

## Installation

### Prerequisites
- Java 21
- Maven 3.6+
- Docker and Docker Compose

### Setup Instructions

1. **Start the Docker Containers**
   
   Start ActiveMQ:
   ```bash
   docker compose -f docker-compose-activemq.yml up -d
   ```
   
   Start RabbitMQ:
   ```bash
   docker compose -f docker-compose-rabbitmq.yml up -d
   ```

2. **Build the Project**
   
   Download the Maven dependencies and compile all the modules:
   ```bash
   mvn clean compile
   ```
