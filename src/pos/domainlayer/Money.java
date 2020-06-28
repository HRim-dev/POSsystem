package pos.domainlayer;

//금액 관련 클래스
public class Money {
	//가져야할 속성: 금액
	int amount;	
	
	public Money() {	
		//this.amount = 0;
		this(0); //더 선호
	}

	public Money(int amount) {	//생성자 추가, 돈금액 설정
		this.amount = amount;
	}

	public int getAmount() {	//설정된 돈 금액 얻어오기
		return amount;
	}	
	
	public Money times(double d) {
		return new Money((int) (amount*d));		
	}
	
	public void add(Money m) { 
		amount+=m.getAmount();
	}
	public Money minus(Money m) {
		return new Money(amount-m.getAmount());
	}

	public boolean compare(Money m) {
		if(this.amount>m.amount) {
			return true;
		}else {
			return false;
		}
	}
	
	public Money min(Money m) {
		if(this.amount<m.amount) {
			return this;
		}else {
			return m;
		}
	}
	
	public Money max(Money m) {
		if(this.amount>m.amount) {
			return this;
		}else {
			return m;
		}
	}
	@Override
	public String toString() {	
		return String.valueOf(amount);
	}
	
}
