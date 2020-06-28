package pos.domainlayer.tax;

import java.util.ArrayList;
import java.util.List;

import pos.domainlayer.Sale;
import pos.domainlayer.TaxLineItem;

public class TaxMasterAdapter implements ITaxCalculatorAdapter{
	private TaxMaster master=new TaxMaster();
	
	@Override
	public List<TaxLineItem> getTaxes(Sale s) {
		List<TaxLineItem> taxes=new ArrayList<TaxLineItem>();
		taxes.add(new TaxLineItem(master.calTax(s)));
		return taxes;
	}
	
}
