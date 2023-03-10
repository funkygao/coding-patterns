package coding.patterns.ddd.essence;

import coding.patterns.ddd.dci.PresaleSo;
import coding.patterns.ddd.memento.Memento;
import coding.patterns.ddd.memento.ShipHint;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class ShipmentOrder implements IDomainModel, IAggregateRoot<Long> {
    @Getter
    private final Memento memento = new Memento();

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

    private boolean isPresale() {
        return orderType.equals(3);
    }

    // 工厂方法
    public PresaleSo ofPresaleSo() {
        if (!isPresale()) {
            return null;
        }

        return new PresaleSo(this);
    }

    public int totalWeight() {
        int r = 0;
        for (ShipmentOrderLine orderLine : orderLines) {
            r += orderLine.totalWeight();
        }
        return r;
    }

    public int totalVolume() {
        int r = 0;
        for (ShipmentOrderLine orderLine : orderLines) {
            r += orderLine.totalVolume();
        }
        return r;
    }

    public void ship() {
        memento.register(new ShipHint(this));
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
        so.ship(); // 发货：引起状态变化
    }

}
