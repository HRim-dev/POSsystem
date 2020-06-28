package pos.domainlayer;

public class Payment {
	private Money amount;	

	public Payment(Money cachTendered) {
		amount = cachTendered;
	}

	public Money getAmount() {//잔돈 얼마인지 알려줌
		return amount;
	}
}
