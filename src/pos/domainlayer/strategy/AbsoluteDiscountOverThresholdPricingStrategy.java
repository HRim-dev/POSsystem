package pos.domainlayer.strategy;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class AbsoluteDiscountOverThresholdPricingStrategy implements ISalePricingStrategy {
	
	private Money discount;//할인가
	private Money threshold;//할인 조건
	@Override
	public Money getTotal(Sale s) {
		//s.total이 20000원 초과할 때만 20% 할인 가격 반환
		discount=s.getTotal();
		threshold=new Money(20000);
		
		if(discount.compare(threshold)) {
			discount=discount.times(0.8);
		}
		System.out.println("20000Discount="+discount.toString());
		return discount;
	}
	
}
