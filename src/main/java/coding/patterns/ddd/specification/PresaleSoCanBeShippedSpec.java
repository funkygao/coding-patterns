package coding.patterns.ddd.specification;

import coding.patterns.ddd.dci.PresaleSo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PresaleSoCanBeShippedSpec implements ISpecification<PresaleSo> {
    private IShipmentOrderGateway shipmentOrderGateway; // 单据中心防腐层
    private ILabelPrintGateway labelPrintGateway; // 面单打印服务防腐层

    @Override
    public boolean satisfiedBy(PresaleSo so) {
        int version1 = labelPrintGateway.query(so.getOrderNo());
        int version2 = shipmentOrderGateway.query(so.getOrderNo());
        if (version1 != version2) {
            // 面单打印的地址不是最新的单据收货人地址
            return false;
        }

        return true;
    }
}
