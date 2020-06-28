package pos.domainlayer.tax;

import java.util.ArrayList;
import java.util.List;

import pos.domainlayer.Sale;
import pos.domainlayer.TaxLineItem;

public class GoodAsGoldTaxProAdapter implements ITaxCalculatorAdapter{
	
	private GoodAsGoldTaxPro good=new GoodAsGoldTaxPro();
	
	@Override
	public List<TaxLineItem> getTaxes(Sale s) {
		List<TaxLineItem> taxes=new ArrayList<TaxLineItem>();
		taxes.add(new TaxLineItem(good.getTax(s)));
		return taxes;
	}

}
