package ing.hubs.spring.advanced.training.order.inbound.adapter;

import ing.hubs.spring.advanced.training.dto.order.OrderDTO;
import ing.hubs.spring.advanced.training.marker.adapter.InboundAdapter;
import ing.hubs.spring.advanced.training.order.inbound.port.RestInboundPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class RestInboundAdapter implements InboundAdapter {

    private final RestInboundPort restInboundPort;

    @Autowired
    public RestInboundAdapter(final RestInboundPort restInboundPort) {
        this.restInboundPort = restInboundPort;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody final OrderDTO orderDTO) {
        restInboundPort.createOrder(orderDTO);

        return ResponseEntity.ok("The order was successfully created");
    }
}
