package pos.domainlayer;

//하나의 상품 상세 정보
public class ProductDescription {

	private ItemID id;	//상품 id
	private Money price;	//상품 금액 
	private String description;	//상품 설명

	
	public ProductDescription(ItemID id, Money price, String description) {
		this.id = id;
		this.price = price;
		this.description = description;
	}

	public ItemID getItemID() {	
		return id;
	}

	public Money getPrice() {
		return price;
	}
	

	public String getDescription() {
		return description;
	}
}

