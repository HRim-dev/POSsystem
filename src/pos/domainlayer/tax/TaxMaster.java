package pos.domainlayer.tax;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class TaxMaster {
	public Money calTax(Sale s) {
		//s.total에 세율 10%를 부과한 합계 반환
		Money m=s.getTotal().times(1.1);
		return m;
	}
}
