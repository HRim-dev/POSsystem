package pos.domainlayer;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String databaseURL=args[0];		
		DBConnect db=new DBConnect(databaseURL);
		
		Store store=new Store(db);
		Register register=store.getRegister();
		Sale sale=register.makeNewSale();//새 판매 시작
		
		register.enterItem(new ItemID(100), 3);
		register.enterItem(new ItemID(200), 2);
		
		register.endSale();
		
		System.out.println("Total= "+sale.getTotal());	//Money
		
		register.calculateTax();
		System.out.println("Tax= "+sale.getTotal());
		register.makePayment(new Money(10000));
		
		System.out.println("Balance= "+sale.getBalance());
	}

}
