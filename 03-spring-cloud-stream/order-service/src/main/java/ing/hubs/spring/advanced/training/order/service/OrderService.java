package ing.hubs.spring.advanced.training.order.service;

import ing.hubs.spring.advanced.training.dto.order.OrderDTO;
import ing.hubs.spring.advanced.training.message.command.order.ChargeOrderCommand;
import ing.hubs.spring.advanced.training.message.command.order.ShipOrderCommand;
import ing.hubs.spring.advanced.training.message.command.order.CreateOrderCommand;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.customer.CustomerUpdatedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderCreatedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderNotChargedEvent;
import ing.hubs.spring.advanced.training.message.event.order.OrderShippedEvent;
import ing.hubs.spring.advanced.training.order.domain.model.Order;
import ing.hubs.spring.advanced.training.order.domain.model.OrderItem;
import ing.hubs.spring.advanced.training.order.domain.model.OrderStatus;
import ing.hubs.spring.advanced.training.order.inbound.port.MessagingInboundPort;
import ing.hubs.spring.advanced.training.order.inbound.port.RestInboundPort;
import ing.hubs.spring.advanced.training.order.outbound.port.MessagingOutboundPort;
import ing.hubs.spring.advanced.training.order.outbound.port.PersistenceOutboundPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService implements RestInboundPort, MessagingInboundPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private static final Random RANDOM = new Random(3000);

    private final ApplicationEventPublisher applicationEventPublisher;

    private final MessagingOutboundPort messagingOutboundPort;
    private final PersistenceOutboundPort persistenceOutboundPort;

    private final ThreadPoolTaskExecutor customThreadPool;

    @Autowired
    public OrderService(final ApplicationEventPublisher applicationEventPublisher,
                        final MessagingOutboundPort messagingOutboundPort,
                        final PersistenceOutboundPort persistenceOutboundPort,
                        final ThreadPoolTaskExecutor customThreadPool) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.messagingOutboundPort = messagingOutboundPort;
        this.persistenceOutboundPort = persistenceOutboundPort;
        this.customThreadPool = customThreadPool;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveInitialOrder() {
        Order order = new Order(1, RANDOM.nextInt(200));
        order.setStatus(OrderStatus.PAYED);

        OrderItem orderItem = new OrderItem();
        orderItem.setRestaurantId(RANDOM.nextInt(100));
        orderItem.setFoodId(RANDOM.nextLong(150));
        orderItem.setPrice(RANDOM.nextLong(200));
        orderItem.setName("Great pizza");
        orderItem.setDescription("A delicious pizza");

        order.setOrderItems(Set.of(orderItem));
        orderItem.setOrder(order);
        persistenceOutboundPort.save(order);
        LOGGER.info("The initial order was saved");
    }

    // creating an order received from a REST endpoint (UI, testing app etc)
    @Transactional
    public void createOrder(final OrderDTO orderDTO) {
        final long customerId = orderDTO.getCustomerId();
        final long orderId = saveOrder(convertDTOIntoOrder(orderDTO));

        LOGGER.info("Creating an order for the customer with the ID {}, for {} items...", customerId,
                orderDTO.getOrderItems().size());
        final double orderTotal = orderDTO.getOrderTotal();

        CompletableFuture.runAsync(() -> publishChargeOrder(customerId, orderId, orderTotal))
                         .thenRunAsync(() -> publishOrderCreatedEvent(customerId, orderId));
    }

    // creating an order received from a messaging endpoint (upstream system, 3rd party application etc)
    @Transactional
    public void createOrder(final CreateOrderCommand createOrderCommand) {
        final long customerId = createOrderCommand.getCustomerId();
        LOGGER.info("Creating an order for the product '{}', for the customer with the ID {}...",
                createOrderCommand.getProductName(), customerId);

        final long orderId = saveOrder(convertCommandIntoOrder(createOrderCommand));
        final double orderTotal = createOrderCommand.getOrderTotal();

        // will be published synchronously on the default Spring Boot / Tomcat thread pool
        publishChargeOrder(customerId, orderId, orderTotal);
        publishOrderCreatedEvent(customerId, orderId);

        if (true) return;

        CompletableFuture.runAsync(() -> publishChargeOrder(customerId, orderId, orderTotal), customThreadPool)
                         .thenRunAsync(() -> publishOrderCreatedEvent(customerId, orderId), customThreadPool)
                         .thenRunAsync(() -> LOGGER.info("The command and event were successfully published"));
    }

    @Transactional
    public void handleCustomerUpdated(final CustomerUpdatedEvent customerUpdatedEvent) {
        LOGGER.info("Updating the orders for the customer with the ID {}...", customerUpdatedEvent.getCustomerId());

        // TODO insert magic here
    }

    @Override
    public CustomerUpdatedEvent handleCustomerUpdated(CustomerCreatedEvent customerCreatedEvent) {
        return new CustomerUpdatedEvent(getNextMessageId(), getNextEventId(), customerCreatedEvent.getCustomerId());
    }

    @Transactional
    public void handleOrderCharged(final OrderChargedEvent orderChargedEvent) {
        final long customerId = orderChargedEvent.getCustomerId();
        final long orderId = orderChargedEvent.getOrderId();
        LOGGER.info("The order with the ID {} of the customer {} was successfully charged, updating it", orderId, customerId);

        // TODO insert magic here

        messagingOutboundPort.publishShipOrderCommand(new ShipOrderCommand(getNextMessageId(), customerId, orderId));
    }

    @Transactional
    public void handleOrderNotCharged(final OrderNotChargedEvent orderNotChargedEvent) {
        LOGGER.warn("The order with the ID {} of the customer {} could not be charged - reason: '{}'",
                orderNotChargedEvent.getOrderId(), orderNotChargedEvent.getCustomerId(), orderNotChargedEvent.getReason());

        // TODO insert magic here
    }

    @Transactional
    public void handleOrderShipped(final OrderShippedEvent orderShippedEvent) {
        final long customerId = orderShippedEvent.getCustomerId();
        final long orderId = orderShippedEvent.getOrderId();
        LOGGER.info("The order with the ID {} of the customer {} was successfully shipped!", orderId, customerId);

        //TODO update the order status in the database
    }

    private void publishChargeOrder(final long customerId, final long orderId, final double orderTotal) {
        messagingOutboundPort.publishChargeOrderCommand(
                new ChargeOrderCommand(getNextMessageId(), customerId, orderId, orderTotal,
                        ChargeOrderCommand.Currency.EUR));
    }

    private void publishOrderCreatedEvent(final long customerId, final long orderId) {
        // in-process / intra-JVM event
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));

        messagingOutboundPort.publishOrderCreatedEvent(
                new OrderCreatedEvent(getNextMessageId(), getNextEventId(), customerId, orderId));
    }

    private Order convertCommandIntoOrder(final CreateOrderCommand command) {
        return new Order(command.getCustomerId(), command.getOrderTotal());
    }

    private Order convertDTOIntoOrder(final OrderDTO orderDTO) {
        return new Order(orderDTO.getCustomerId(), orderDTO.getOrderTotal());
    }

    private long saveOrder(final Order order) {
        sleepALittle();
        return persistenceOutboundPort.save(order);
    }

    private long getNextMessageId() {
        return new Random(90000).nextLong();
    }

    private long getNextEventId() {
        // returned from the saved database event, before sending it (using transactional messaging)
        return new Random(900000).nextLong();
    }

    // simulate a long running operation
    private void sleepALittle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
