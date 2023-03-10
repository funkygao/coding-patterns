package coding.patterns.ddd.specification;

import coding.patterns.ddd.essence.ShipmentOrder;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class BarSpec implements ISpecification<ShipmentOrder> {
    private Date date;


    @Override
    public boolean satisfiedBy(ShipmentOrder so) {
        return so.isPresale();
    }
}
