package coding.patterns.ddd.essence;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

public class ShipmentOrder implements IDomainModel, IAggregateRoot<Long> {
    private String orderNo;
    private Integer orderType;
    private List<ShipmentOrderLine> orderLines;

    private ShipmentOrder(Essence essence) {
        validate(essence);

        // 可以使用mapstruct简化映射逻辑
        this.orderNo = essence.orderNo;
        this.orderType = essence.orderType;
        this.orderLines = new ArrayList<ShipmentOrderLine>(essence.getItems().size());
    }

    public boolean isPresale() {
        return orderType.equals(3);
    }

    private void validate(Essence essence) {
        if (essence.getOrderNo() == null) {
            throw new RuntimeException();
        }
    }

    @Data
    public static class Essence {
        @Data
        public static class Item {
            private String sku;
            private BigDecimal qty;
        }

        private String orderNo;
        private Integer orderType;
        private List<Item> items;

        public ShipmentOrder createSo() {
            return new ShipmentOrder(this);
        }
    }

    private static void test() {
        ShipmentOrder.Essence essence = new Essence();
        essence.setOrderNo("so-12");
        essence.setOrderType(3);
        ShipmentOrder so = essence.createSo();
        so.isPresale();
    }

}
