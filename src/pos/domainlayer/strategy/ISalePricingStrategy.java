package pos.domainlayer.strategy;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public interface ISalePricingStrategy {
	public Money getTotal(Sale s);
}
