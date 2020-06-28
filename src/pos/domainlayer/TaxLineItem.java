package pos.domainlayer;

public class TaxLineItem {
	
	private Money amount;
	private double percentage;
	private ProductDescription description;
	
	public TaxLineItem(Money m)
	{
		this.amount=m;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public ProductDescription getDescription() {
		return description;
	}

	public void setDescription(ProductDescription description) {
		this.description = description;
	}
	
}
