package pos.domainlayer.strategy;

import java.util.ArrayList;
import java.util.List;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public abstract class CompositePricingStrategy implements ISalePricingStrategy {

	protected List<ISalePricingStrategy> strategies=new ArrayList<ISalePricingStrategy>();
	
	public void add(ISalePricingStrategy s) {
		//System.out.println("전략추가"+s.toString());
		strategies.add(s);
	}
	@Override
	public abstract Money getTotal(Sale s);
	
	public List<ISalePricingStrategy> getStrategies() {
		return strategies;
	}
	
	
}
