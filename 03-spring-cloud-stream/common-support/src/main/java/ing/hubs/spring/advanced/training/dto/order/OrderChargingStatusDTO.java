package ing.hubs.spring.advanced.training.dto.order;

import ing.hubs.spring.advanced.training.dto.AbstractDTO;

import java.util.Objects;
import java.util.Optional;

public class OrderChargingStatusDTO extends AbstractDTO {

    private final boolean successful;
    private final String failureReason;

    public OrderChargingStatusDTO(boolean successful, String failureReason) {
        this.successful = successful;
        this.failureReason = failureReason;
    }

    public OrderChargingStatusDTO() {
        this.successful = true;
        this.failureReason = null;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public Optional<String> getFailureReason() {
        return Optional.ofNullable(failureReason);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderChargingStatusDTO that = (OrderChargingStatusDTO) o;
        return successful == that.successful &&
                Objects.equals(failureReason, that.failureReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(successful, failureReason);
    }
}
