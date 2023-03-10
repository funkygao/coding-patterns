package coding.patterns.ddd.specification;

import coding.patterns.ddd.essence.ShipmentOrder;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class FooSpec implements ISpecification<ShipmentOrder> {
    private String egg;

    @Override
    public boolean satisfiedBy(ShipmentOrder so) {
        return so.isPresale();
    }

    private static void test() {
        ShipmentOrder.Essence essence = new ShipmentOrder.Essence();
        essence.setOrderNo("so-2");

        ShipmentOrder so = essence.createSo();
        FooSpec fooSpec = new FooSpec("xxx");
        ISpecification<ShipmentOrder> spec = new FooSpec("xx")
                .and(new BarSpec(new Date()));
        spec.satisfiedBy(so);
    }
}
