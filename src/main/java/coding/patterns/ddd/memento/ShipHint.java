package coding.patterns.ddd.memento;

import coding.patterns.ddd.essence.ShipmentOrder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShipHint implements IDirtyHint {
    private ShipmentOrder shipmentOrder;

}
