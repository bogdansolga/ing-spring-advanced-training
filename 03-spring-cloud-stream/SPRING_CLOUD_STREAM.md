# Spring Cloud Stream Integration Guide

This document explains how to use Spring Cloud Stream with both RabbitMQ and ActiveMQ binders in the training project.

## Overview

Spring Cloud Stream provides a framework for building message-driven microservices. It offers:

- **Abstraction Layer**: Unified programming model regardless of underlying message broker
- **Binder Support**: Pluggable binders for different message brokers (RabbitMQ, ActiveMQ, Kafka, etc.)
- **Functional Programming Model**: Uses Java 8+ functional interfaces for message processing
- **Profile-based Configuration**: Easy switching between different message brokers

## Project Structure

```
03-spring-cloud-stream/
├── simple-stream-publisher/     # Message producer application
├── simple-stream-subscriber/    # Message consumer application
├── billing-service/            # Complex microservice example (legacy)
├── order-service/              # Complex microservice example (legacy)
└── pom.xml                     # Module configuration
```

## Key Components

### Publisher Application (`SimpleStreamPublisher`)

- **StreamBridge**: Programmatic way to send messages to output channels
- **ApplicationRunner**: Sends a test message on startup
- **Non-web Application**: Runs as console application (WebApplicationType.NONE)

```java
@Bean
public ApplicationRunner runner(StreamBridge streamBridge) {
    return args -> {
        streamBridge.send("first-output", "Test message");
    };
}
```

### Subscriber Application (`SimpleStreamSubscriber`)

- **Functional Consumer**: Uses Java 8 Consumer functional interface
- **Automatic Binding**: Spring Cloud Stream automatically binds the function to input channels
- **Web Application**: Runs continuously to consume messages

```java
@Bean
public Consumer<String> firstConsumer() {
    return message -> {
        System.out.println("Received: " + message);
    };
}
```

## Configuration Profiles

### Default Configuration
- Defines binding destinations and content types
- Works with any configured binder

### RabbitMQ Profile (`rabbitmq`)
```yaml
spring:
  config:
    activate:
      on-profile: rabbitmq
  cloud:
    stream:
      binders:
        default:
          type: rabbit
```

### ActiveMQ Profile (`activemq`)
```yaml
spring:
  config:
    activate:
      on-profile: activemq
  cloud:
    stream:
      binders:
        default:
          type: jms
```

## Running the Applications

### Prerequisites
1. Start the appropriate message broker:
   ```bash
   # For RabbitMQ
   docker-compose -f docker-compose-rabbitmq.yml up -d
   
   # For ActiveMQ
   docker-compose -f docker-compose-activemq.yml up -d
   ```

### With RabbitMQ
```bash
# Start subscriber
mvn spring-boot:run -pl 03-spring-cloud-stream/simple-stream-subscriber -Dspring-boot.run.profiles=rabbitmq

# Start publisher (in another terminal)
mvn spring-boot:run -pl 03-spring-cloud-stream/simple-stream-publisher -Dspring-boot.run.profiles=rabbitmq
```

### With ActiveMQ
```bash
# Start subscriber
mvn spring-boot:run -pl 03-spring-cloud-stream/simple-stream-subscriber -Dspring-boot.run.profiles=activemq

# Start publisher (in another terminal)
mvn spring-boot:run -pl 03-spring-cloud-stream/simple-stream-publisher -Dspring-boot.run.profiles=activemq
```

## Channel Naming Convention

Spring Cloud Stream uses specific naming conventions for function-based bindings:

- **Output Channels**: `{functionName}` (e.g., `first-output`)
- **Input Channels**: `{functionName}-in-{index}` (e.g., `firstConsumer-in-0`)

## Key Benefits

1. **Broker Agnostic**: Same code works with different message brokers
2. **Profile-based Switching**: Easy environment-specific configuration
3. **Functional Programming**: Clean, declarative message processing
4. **Auto-configuration**: Minimal boilerplate configuration
5. **Testing Support**: Built-in test binder for unit testing

## Comparison with Direct Integration

| Aspect | Spring Cloud Stream | Direct Integration |
|--------|--------------------|--------------------|
| **Abstraction** | High-level, broker-agnostic | Low-level, broker-specific |
| **Configuration** | Declarative YAML | Programmatic beans |
| **Switching Brokers** | Profile change only | Code changes required |
| **Learning Curve** | Moderate | Steep (broker-specific APIs) |
| **Flexibility** | Good for common patterns | Full broker feature access |

## Best Practices

1. **Use Profiles**: Always define broker-specific profiles
2. **Error Handling**: Implement proper error handling in consumers
3. **Content Types**: Specify content types for proper serialization
4. **Consumer Groups**: Use consumer groups for load balancing
5. **Testing**: Use the test binder for unit tests