package pos.domainlayer.tax;


import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class GoodAsGoldTaxPro {
	public Money getTax(Sale s) {
		//s.total에 세율 20%를 부과한 합계 반환
		Money m=s.getTotal().times(1.2);
		return m;
	}
}
