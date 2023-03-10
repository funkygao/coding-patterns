package coding.patterns.ddd.bag;

import coding.patterns.ddd.essence.ShipmentOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentOrderBag {
    private final List<ShipmentOrder> shipmentOrderList;

    private ShipmentOrderBag(List<ShipmentOrder> list) {
        shipmentOrderList = list;
    }

    public static ShipmentOrderBag of(List<ShipmentOrder> list) {
        return new ShipmentOrderBag(list);
    }

    public PresaleSoBag presaleBag() {
        List<ShipmentOrder> presaleSoList = shipmentOrderList.stream().filter(so -> so.ofPresaleSo() != null).collect(Collectors.toList());
        // 转换对象类型
        return new PresaleSoBag(presaleSoList);
    }

    public boolean isEmpty() {
        return shipmentOrderList.isEmpty();
    }

    public int totalWeight() {
        int r = 0;
        for (ShipmentOrder so : shipmentOrderList) {
            r += so.totalWeight();
        }
        return r;
    }

    public int totalVolume() {
        int r = 0;
        for (ShipmentOrder so : shipmentOrderList) {
            r += so.totalVolume();
        }
        return r;
    }

    private static void test() {
        List<ShipmentOrder> shipmentOrderList = new ArrayList<ShipmentOrder>();
        ShipmentOrder.Essence essence = new ShipmentOrder.Essence();
        //
        shipmentOrderList.add(essence.createSo());
        essence = new ShipmentOrder.Essence();
        shipmentOrderList.add(essence.createSo());
        ShipmentOrderBag bag = ShipmentOrderBag.of(shipmentOrderList);
        bag.totalVolume();

        PresaleSoBag presaleSoBag = bag.presaleBag();
    }

}
