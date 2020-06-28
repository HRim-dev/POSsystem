package pos.domainlayer;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCatalog {
	//컬렉션 객체 유지
	// 유연성 확보를 위해 MAP = HashMap 사용
	private Map<String, ProductDescription> descriptions = new HashMap<String, ProductDescription>();	
	
	//결과 테이블 저장
	private ResultSet myResultSet;
	
	private Statement myStatement;
	
	private List<String> ids;
	
	private String id;
	private String description;
	private int price;
		
	public ProductCatalog(DBConnect db){
		ids=new ArrayList<String>();
		//DB연결
		this.myStatement=db.getMyStatement();
		
		try {
			myResultSet=myStatement.executeQuery("select * from ProductDescriptions");
			while(myResultSet.next()) {
				id=myResultSet.getString("itemId");
				ids.add(id);
				System.out.println("itemID: "+id);
				
				description=myResultSet.getString("description");
				System.out.println("desc: "+description);
				
				price=myResultSet.getInt("price");
				System.out.println("price: "+price);
				
				ItemID id1=new ItemID(id);
				Money price1=new Money(price);
				String description1=description;
				
				ProductDescription desc;
				//상품 정보 저장(DB데이터)
				desc=new ProductDescription(id1, price1,description1);
				descriptions.put(id1.toString(), desc);
				
			}
			myResultSet.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> getIds() {
		return ids;
	}

	public ProductDescription getProductDescription(ItemID id){	//상품정보 전달
		//id를 키값으로 상품 찾기
		return descriptions.get(id.toString());	
	}
	
	public String getDesc(ItemID id) {
		return descriptions.get(id.toString()).getDescription();	
	}
	
	public String getPrice(ItemID id) {
		return descriptions.get(id.toString()).getPrice().toString();	
	}
	
}
