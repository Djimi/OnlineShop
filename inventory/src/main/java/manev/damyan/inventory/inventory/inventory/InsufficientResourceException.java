package manev.damyan.inventory.inventory.inventory;

import lombok.Data;

@Data
public class InsufficientResourceException extends RuntimeException {

    private final long itemId;

    private final int amountNeeded;

    private final int availableAmount;

    public InsufficientResourceException(String message, Throwable cause, long itemId, int amountNeeded,
            int availableAmount) {
        super(message, cause);
        this.itemId = itemId;
        this.amountNeeded = amountNeeded;
        this.availableAmount = availableAmount;
    }
}
