package pos.domainlayer;

public class Store {
	private DBConnect db;
	private ProductCatalog catalog;
	private Register register;	
	public Store(DBConnect db) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.db=db;
		this.catalog = new ProductCatalog(db);
		this.register = new Register(catalog);
	}
	
	
	public Register getRegister() { return register; }
}
