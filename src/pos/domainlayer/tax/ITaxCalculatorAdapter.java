package pos.domainlayer.tax;

import java.util.List;

import pos.domainlayer.Sale;
import pos.domainlayer.TaxLineItem;

public interface ITaxCalculatorAdapter {
	
	public List<TaxLineItem> getTaxes(Sale s); 

}
