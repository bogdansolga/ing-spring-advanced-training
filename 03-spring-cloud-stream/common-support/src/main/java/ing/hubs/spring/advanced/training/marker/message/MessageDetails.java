package ing.hubs.spring.advanced.training.marker.message;

public @interface MessageDetails {
    Service publisher();

    Service[] subscribers();

    Channel channel();
}
