package coding.patterns.ddd.dci;

import coding.patterns.ddd.essence.ShipmentOrder;
import coding.patterns.ddd.specification.PresaleSoCanBeShippedSpec;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;

/**
 * 这个例子用来说明：聚合根的场景化建模：角色对象，特点场景(业务上下文)下才具有的数据和行为.
 * 通过角色对象，降低聚合根认知负荷，聚合根膨胀.
 *
 * 但这个例子又不恰当：它混淆了聚合根的状态与场景：
 * 状态，在聚合根的生命周期内，可变，时间维度上不确定；
 * 场景，不可变，是确定的。
 *
 * anyway，这里只演示，先忘掉其不合理性：假设它是合理的，因为我手头上没有更合适例子.
 */
@AllArgsConstructor
public class PresaleSo {
    @Delegate
    private final ShipmentOrder so;

    public boolean canBeShipped(IShipmentOrderGateway shipmentOrderGateway, ILabelPrintGateway labelPrintGateway) {
        // 由于预售状态下用户可以修改收货地址，因此如果最近一次打印的面单没有反映最新地址，则不能出库
        // 即强制重新按照最新地址打印面单
        PresaleSoCanBeShippedSpec spec = new PresaleSoCanBeShippedSpec(shipmentOrderGateway, labelPrintGateway);
        return spec.satisfiedBy(this);
    }

    private static void test() {
        ShipmentOrder.Essence essence = new ShipmentOrder.Essence();
        ShipmentOrder so = essence.createSo();
        PresaleSo presaleSo = so.ofPresaleSo();
        if (presaleSo != null) {
            // 是预售出库
            if (!presaleSo.canBeShipped(gw1, gw2)) {
                throw new RuntimeException();
            }
        }
    }

}
