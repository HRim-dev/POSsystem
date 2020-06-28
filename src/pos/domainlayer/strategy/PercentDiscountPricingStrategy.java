package pos.domainlayer.strategy;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class PercentDiscountPricingStrategy implements ISalePricingStrategy {
	
	private double percentage=0;
	
	@Override
	public Money getTotal(Sale s) {
		//s.Total의 10% 할인 가격 반환
		percentage=0.9;
		Money m=s.getTotal().times(percentage);
		System.out.println("percentDiscount="+m.toString());
		return m;
	}

}
