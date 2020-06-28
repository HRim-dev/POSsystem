package pos.domainlayer;

import pos.domainlayer.tax.ITaxCalculatorAdapter;

public class ServiceFactory {
	
	private static ServiceFactory instance;
	
	ITaxCalculatorAdapter taxCalculatorAdapter;

	private ServiceFactory(){

	}
	
	//Singleton 패턴(synchronized:스레드가 여러개인 경우 동시접속 막아줌)
	public static synchronized ServiceFactory getInstance(){
		//소극적 초기화
		if(instance==null)
			instance=new ServiceFactory();
		return instance;
	}
	public ITaxCalculatorAdapter getTaxCalculatorAdapter() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(taxCalculatorAdapter==null) {//아직 세금계산 Adapter가 생성되지 않았다면
			String className=System.getProperty("taxcalculator.class.name");
			taxCalculatorAdapter=(ITaxCalculatorAdapter)Class.forName(className).newInstance();//새로 생성
		}
		return taxCalculatorAdapter;
	}
	
	public void reset() {
		taxCalculatorAdapter=null;
	}
}
