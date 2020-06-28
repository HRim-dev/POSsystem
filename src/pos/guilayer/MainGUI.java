package pos.guilayer;
import pos.domainlayer.DBConnect;
import pos.domainlayer.Register;
import pos.domainlayer.Store;
import pos.presentationlayer.ProcessSaleJFrame;

public class MainGUI {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String databaseURL=args[0];
		
		DBConnect db=new DBConnect(databaseURL);
		
		Store store=new Store(db);
		Register register=store.getRegister();
		
		new ProcessSaleJFrame(register);
		
	}

}
