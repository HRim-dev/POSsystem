package pos.domainlayer;

//상품의 id
// Money와 유사한 구성
public class ItemID {
	String id;

	public ItemID(String id) {	//생성자 Source ->uisng Field
		this.id=id;
	}
	
	public ItemID(int id) {	//생성자
		this.id=String.valueOf(id);
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {	//문자열로 변환
		return id;
	}
	
	
		

}
