package pos.domainlayer.strategy;

import java.util.Iterator;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class CompositeBestForCustomerStrategy extends CompositePricingStrategy{
//	protected List<ISalePricingStrategy> strategies=new ArrayList<ISalePricingStrategy>();
	
	@Override
	public Money getTotal(Sale s) {
		Money lowestTotal=new Money(Integer.MAX_VALUE);
		
		Iterator<ISalePricingStrategy> i=strategies.iterator();
		while(i.hasNext()) {
			ISalePricingStrategy strategy=i.next();
			
			Money total=strategy.getTotal(s);
			
			lowestTotal=total.min(lowestTotal);
		}
		System.out.println("for customer: "+lowestTotal);
		return lowestTotal;
	}

}
