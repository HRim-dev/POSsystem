package pos.domainlayer;

import java.util.List;

import pos.domainlayer.tax.GoodAsGoldTaxProAdapter;
import pos.domainlayer.tax.ITaxCalculatorAdapter;
import pos.domainlayer.tax.TaxMasterAdapter;

public class Register {	//컨트롤러 역할
	
	private ProductCatalog catalog;
	private Sale currentSale;
	private ITaxCalculatorAdapter taxAdapter;
	
	public Register(ProductCatalog catalog){
		this.catalog = catalog;	//속성 가시성으로 유지	
	}
	public void initialize(int mode) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		System.out.println("어댑터 생성");
		if(mode==1) {
			System.setProperty("taxcalculator.class.name", "pos.domainlayer.tax.TaxMasterAdapter");
			taxAdapter=ServiceFactory.getInstance().getTaxCalculatorAdapter();
		}else if(mode==2) {
			System.setProperty("taxcalculator.class.name", "pos.domainlayer.tax.GoodAsGoldTaxProAdapter");
			taxAdapter=ServiceFactory.getInstance().getTaxCalculatorAdapter();
		}
		
	}
	public void endSale()
	{
		currentSale.becomeComplete();
	}
	
	public void enterItem(ItemID id, int quantity)
	{
		ProductDescription desc = catalog.getProductDescription(id);
		currentSale.makeLineItem(desc, quantity);
	}
	
	
	public Sale makeNewSale()	//새 판매 시작
	{
		currentSale = new Sale();	//Sale객체 생성
		//어댑터 해제
		ServiceFactory.getInstance().reset();
		currentSale.resetTaxes();
		return currentSale; 		//Sale객체 반환, main 클래스에서 활용
	}
	
	public void makePayment(Money cashTendered)
	{
		currentSale.makePayment(cashTendered);
	}
	
	public String getSubTotal() {
		return currentSale.getTotal().toString();
	}
	
	//세금적용
	public Money calculateTax() {
		Money taxm=new Money();
		
			currentSale.setTaxes(taxAdapter.getTaxes(currentSale));
			taxm=currentSale.getTotal();
		return taxm;
	}
	
	//할인 적용
	public Money applyDiscount() {
		return currentSale.getDiscount();
	}
	//ID 리스트
	public List<String> getIDList(){
		return catalog.getIds();
	}
	
	//상품 정보
	public String getdescription(String strid) {
		ItemID id=new ItemID(strid);
		return catalog.getDesc(id);
	}
	public String getPrice(String strid) {
		ItemID id=new ItemID(strid);
		return catalog.getPrice(id);
	}

	public ITaxCalculatorAdapter getTaxAdapter() {
		return taxAdapter;
	}

	public void setTaxAdapter(ITaxCalculatorAdapter taxAdapter) {
		this.taxAdapter = taxAdapter;
	}
}
