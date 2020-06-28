package pos.domainlayer.strategy;

import java.util.Iterator;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public class CompositeBestForStorePricingStrategy extends CompositePricingStrategy{
//	protected List<ISalePricingStrategy> strategies=new ArrayList<ISalePricingStrategy>();
	
	@Override
	public Money getTotal(Sale s) {
		Money highestTotal=new Money(Integer.MIN_VALUE);
		for(int i=0;i<strategies.size();i++) {
			System.out.println("전략:"+strategies.get(i));
		}

		Iterator<ISalePricingStrategy> i=strategies.iterator();
		while(i.hasNext()) {
			ISalePricingStrategy strategy=i.next();
			
			Money total=strategy.getTotal(s);
			
			highestTotal=total.max(highestTotal);
		}
		System.out.println("for Store"+highestTotal);
		return highestTotal;
	}

}
