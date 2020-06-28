/**
 * 
 */
package pos.domainlayer;

import java.util.ArrayList;
import java.util.List;

import pos.domainlayer.strategy.AbsoluteDiscountOverThresholdPricingStrategy;
import pos.domainlayer.strategy.CompositeBestForCustomerStrategy;
import pos.domainlayer.strategy.CompositeBestForStorePricingStrategy;
import pos.domainlayer.strategy.CompositePricingStrategy;
import pos.domainlayer.strategy.ISalePricingStrategy;
import pos.domainlayer.strategy.PercentDiscountPricingStrategy;

/**
 * @author DS
 *
 */
public class Sale {
	
	private List<SalesLineItem> lineItems =  new ArrayList<SalesLineItem>();
	private List<TaxLineItem> taxes =  new ArrayList<TaxLineItem>();
	
	private ISalePricingStrategy pricingStrategy1;
	private ISalePricingStrategy pricingStrategy2;
	private CompositePricingStrategy composite_pricingStrategy;
	
	private boolean isComplete = false;
	private Payment payment;
	
	public Money getBalance()	{	//잔액 계산
		return payment.getAmount().minus(getDiscount());
	}
	
	public void becomeComplete(){ 
		isComplete = true; 
	}
	
	public boolean isComplete() {  
		return isComplete; 
	}
	
	public void makeLineItem(ProductDescription desc, int quantity)	{
		lineItems.add( new SalesLineItem(desc, quantity));	//상품 추가
	}
	
	
	public void resetTaxes() {
		this.taxes=new ArrayList<TaxLineItem>();;
	}
	public List<TaxLineItem> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<TaxLineItem> taxes) {
		this.taxes = taxes;
	}

	//할인 정책
	public void makeSalePricingStrategy(int mode) {
		pricingStrategy1=new PercentDiscountPricingStrategy();
		pricingStrategy2=new AbsoluteDiscountOverThresholdPricingStrategy();
		
		if(mode==1) {
			composite_pricingStrategy=new CompositeBestForCustomerStrategy();
			composite_pricingStrategy.add(pricingStrategy1);
			composite_pricingStrategy.add(pricingStrategy2);			
		}else if(mode==2) {
			composite_pricingStrategy=new CompositeBestForStorePricingStrategy();
			composite_pricingStrategy.add(pricingStrategy1);
			composite_pricingStrategy.add(pricingStrategy2);	
		}
	}
	public Money getTotal() {	//합계
		Money total = new Money();	
		Money subtotal = null;
		
		if(taxes.isEmpty()) {
			for(SalesLineItem lineItem : lineItems)
			{
				subtotal = lineItem.getSubtotal();
				total.add(subtotal);	//total의 누적합
			}
		}else {
			total=taxes.get(0).getAmount();
		}
		return total;
	}
	
	public Money getDiscount() {
		System.out.println("total "+composite_pricingStrategy.getTotal(this));	
		return composite_pricingStrategy.getTotal(this);	
	}
	
	public void makePayment(Money cashTendered) {
		payment = new Payment(cashTendered);
	}
}