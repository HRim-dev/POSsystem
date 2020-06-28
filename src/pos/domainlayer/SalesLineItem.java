package pos.domainlayer;
//여러개 구매하면 하나의 줄로 표현 
//ex) 콜라 5개 구매하면 1줄로 '콜라 5개' 표현
public class SalesLineItem { 

	private int quantity;
	private ProductDescription description;	
	
	public SalesLineItem(ProductDescription desc, int quantity)
	{
		this.description = desc;
		this.quantity= quantity;
	}
	
	public String getDescriptionToString() {
		return description.toString();
	}

	public Money getSubtotal()	//부분합(Money 타입으로 반환)
	{
		return description.getPrice().times(quantity);	
	}
}
