package pos.presentationlayer;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pos.domainlayer.ItemID;
import pos.domainlayer.Money;
import pos.domainlayer.Register;
import pos.domainlayer.Sale;

@SuppressWarnings("serial")
public class ProcessSaleJFrame extends JFrame {
	
	Register register;
	Sale sale;
	
	List<String> ids;
	JButton b_makeNewSale;
	JButton b_enterItem;
	JButton b_endSale;
	
	JButton b_calculateTax;
	JButton b_applyDiscount;
	JButton b_makePayment;
	
	JTextField t_quantity;
	JTextField t_desc;
	JTextField t_currentTotal;
	JTextField t_total;
	JTextField t_amount;
	JTextField t_balance;
	JTextField t_discountTotal;
	
	JTextArea messageArea;
	
	JRadioButton[] rb_tax=new JRadioButton[2];
	JRadioButton[] rb_discount=new JRadioButton[2];
	
	JComboBox<String> combo_itemID;//itemId
	
	//상품 정보
	String id="";
	String desc="";
	String price="";
	
	//장바구니에 담은 상품
	List<String> items;
	
	//라디오 버튼 모드
	int mode=0;
	
	//판매 시작을 위한 버튼 하나
	//어떤 아이템을 몇개 입력받을 수 있는 입력창으로 구성
	public ProcessSaleJFrame(Register register){	//frame 클래스의 생성자
		super("POS System (학번:20161047  이름:유혜림)");
		
		this.register=register;
		buildGUI();	//화면을 구성할 수 있는 함수
		registerEventHandler();	//이벤트 등록
		
		this.pack();
		this.setSize(600, 600);
		this.setVisible(true);	//화면 가시성
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	/*	
		for(int i=0;i<ids.size();i++) {
			System.out.println(ids.get(i));
		}*/
	}
	
	private void buildGUI() {	
		
		//화면 구성		
		Container cp=this.getContentPane();	
		cp.setLayout(new FlowLayout());
		cp.add(new GuiPane());
		
		b_enterItem.setEnabled(false);
		b_endSale.setEnabled(false);
		b_calculateTax.setEnabled(false);
		b_makePayment.setEnabled(false);
		
		combo_itemID.setEnabled(false);
		rb_tax[0].setEnabled(false);
		rb_tax[1].setEnabled(false);
		rb_discount[0].setEnabled(false);
		rb_discount[1].setEnabled(false);
		b_applyDiscount.setEnabled(false);
		t_discountTotal.setEditable(false);
		
		t_desc.setEnabled(false);
		t_quantity.setEnabled(false);
		t_currentTotal.setEnabled(false);
		t_total.setEnabled(false);
		t_amount.setEnabled(false);
		t_balance.setEnabled(false);
		
	}	
	
	//버튼 리스너 생성
	private void registerEventHandler() {
		b_makeNewSale.addActionListener(makeNewSaleHandler);
		combo_itemID.addActionListener(selectIDHandler);
		b_enterItem.addActionListener(enterItemHandler);
		b_endSale.addActionListener(endSaleHandler);
		b_calculateTax.addActionListener(calculateTaxHandler);
		b_applyDiscount.addActionListener(applyDiscountHandler);
		b_makePayment.addActionListener(makePaymentHandler);
		
		for(int i=0;i<rb_tax.length;i++) {
			rb_tax[i].addItemListener(new MyItemListener());
			rb_discount[i].addItemListener(new MyItemListener2());
		}
	}
	
	//makeNewSale버튼을 눌렀을때의 이벤트 핸들러
	ActionListener makeNewSaleHandler=new ActionListener() {//익명 클래스 정의

		@Override
		public void actionPerformed(ActionEvent e) {	
			sale=register.makeNewSale();
			
			//초기화
			items=new ArrayList<String>();
			combo_itemID.setSelectedIndex(0);
			t_total.setText("");
			t_amount.setText("");
			t_balance.setText("");
			t_currentTotal.setText("");
			t_currentTotal.setEnabled(false);
			t_desc.setEnabled(false);
			
			//버튼 활성화/비활성화 설정
			b_makeNewSale.setEnabled(false);
			combo_itemID.setEnabled(true);
			t_quantity.setEnabled(true);
			b_enterItem.setEnabled(true);
			
			b_calculateTax.setEnabled(false);
			rb_tax[0].setEnabled(false);
			rb_tax[0].setSelected(false);
			rb_tax[1].setEnabled(false);
			rb_tax[1].setSelected(false);
			
			rb_discount[0].setEnabled(false);
			rb_discount[0].setSelected(false);
			rb_discount[1].setEnabled(false);
			rb_discount[1].setSelected(false);
			b_applyDiscount.setEnabled(false);
			t_discountTotal.setEditable(false);
			t_discountTotal.setText("");
			
			messageArea.setText("Status: 새 판매가 시작되었습니다.\n");
			System.out.println("Status: Make New Sale 버튼이 눌러졌습니다.");
			
		} 		
	};
	

	
	//itemID콤보박스 이벤트 핸들러
	ActionListener selectIDHandler=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			id=combo_itemID.getItemAt(combo_itemID.getSelectedIndex()).toString();
			desc=register.getdescription(id);
			price=register.getPrice(id);
			t_desc.setText(desc);
			messageArea.append("선택된 상품: "+desc+" ("+price+"원)\n");
		}
		
	};
	
	
	
	//enterItem버튼을 눌렀을때의 이벤트 핸들러
	ActionListener enterItemHandler=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
			
			ItemID id=new ItemID(combo_itemID.getSelectedItem().toString());
			int q=Integer.parseInt(t_quantity.getText());
			
			if(items.isEmpty()) {
				messageArea.append("\n<장바구니 현황>\n");
				items.add(desc+"("+price+"원) "+q+"개\n");
				for(int i=0;i<items.size();i++) {
					messageArea.append((i+1)+") "+items.get(i)+"\n");
				}
			}else {
				messageArea.append("\n<장바구니 현황>\n");
				items.add(desc+"("+price+"원) "+q+"개\n");
				for(int i=0;i<items.size();i++) {
					messageArea.append((i+1)+") "+items.get(i));
				}
			}
			
			messageArea.append("Status: Enter Item 버튼이 눌러졌습니다.\n");
			System.out.println("Status: Enter Item 버튼이 눌러졌습니다.");
			register.enterItem(id,q);
			
			//부분합 출력
			String total=register.getSubTotal();
			messageArea.append("현재 금액: "+total+"원\n");
			t_currentTotal.setText(total);
			}catch(NumberFormatException ee) {
				//수량 입력에 대한 예외처리
				JOptionPane.showMessageDialog(null,"숫자가 아닙니다. 숫자만 입력해주세요.","Message",JOptionPane.ERROR_MESSAGE);
			}
			t_quantity.setText("");
			b_endSale.setEnabled(true);
			
			b_makeNewSale.setEnabled(false);
			combo_itemID.setEnabled(true);
			t_quantity.setEnabled(true);
			b_enterItem.setEnabled(true);
			t_desc.setEnabled(false);
			t_currentTotal.setEnabled(false);
			b_calculateTax.setEnabled(false);
			rb_tax[0].setEnabled(false);
			rb_tax[1].setEnabled(false);
			rb_discount[0].setEnabled(false);
			rb_discount[1].setEnabled(false);
			b_applyDiscount.setEnabled(false);
			t_discountTotal.setEditable(false);
			//System.out.println("id:"+id+" /quantity:"+q);
			
		}	
	};
	
	//endSale버튼을 눌렀을때의 이벤트 핸들러
	ActionListener endSaleHandler=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		
		try {
			register.endSale();
			String result="0";
			if(sale.isComplete()) {
				result=sale.getTotal().toString();
			}
				t_total.setText(result);
			
			b_enterItem.setEnabled(false);
			b_calculateTax.setEnabled(true);
			b_makePayment.setEnabled(false);
			
			combo_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			t_total.setEnabled(true);
			t_total.setEditable(false);
			t_amount.setEnabled(true);
			t_desc.setEnabled(false);
			t_currentTotal.setEnabled(false);
			
			rb_tax[0].setEnabled(true);
			rb_tax[1].setEnabled(true);
			
			rb_discount[0].setEnabled(false);
			rb_discount[1].setEnabled(false);
			b_applyDiscount.setEnabled(false);
			t_discountTotal.setEditable(false);
			
			messageArea.append("총 금액: "+result+"원\n");
			messageArea.append("Status: EndSale 버튼이 눌러졌습니다.\n");
			System.out.println("Status: EndSale 버튼이 눌러졌습니다.");
			
		}catch(NumberFormatException ee) {
			//수량 예외처리
			JOptionPane.showMessageDialog(null,"숫자가 아닙니다. 숫자만 입력해주세요.","Message",JOptionPane.ERROR_MESSAGE);
		}catch(NullPointerException nullE) {
			//itemID에 없는 ID값 입력했을 때 예외처리
			JOptionPane.showMessageDialog(null,"상품 리스트에 없는 ItemID입니다. 새롭게 다시 주문해주세요.(Make New Sale)","Message",JOptionPane.ERROR_MESSAGE);
			b_makeNewSale.setEnabled(true);//새롭게 주문하도록 해주는 버튼(b_makeNewSale) 활성화
			b_enterItem.setEnabled(false);
			b_endSale.setEnabled(false);
			combo_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			t_desc.setEnabled(false);
			t_currentTotal.setEnabled(false);
			rb_tax[0].setEnabled(false);
			rb_tax[1].setEnabled(false);
			b_calculateTax.setEnabled(true);
			rb_discount[0].setEnabled(false);
			rb_discount[1].setEnabled(false);
			b_applyDiscount.setEnabled(false);
			t_discountTotal.setEditable(false);
			
		}
			//t_amount.setEditable(true);

			
		}	
	};
	
	//라디오 버튼 item리스너 구현(세금)
	class MyItemListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			try {
				if(e.getStateChange()==ItemEvent.DESELECTED) {
					return;
				}if(rb_tax[0].isSelected()) {
					mode=1;
					register.initialize(1);
					messageArea.append("세금 10% 부과\n");
				}else if(rb_tax[1].isSelected()) {
					mode=2;
					register.initialize(2);
					messageArea.append("세금 20% 부과\n");
				}
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
	
	//calculateTax버튼 눌렀을 때의 이벤트 핸들러
	ActionListener calculateTaxHandler=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Money taxTotal=new Money(Integer.parseInt(t_total.getText()));
			messageArea.append("Status: calculateTax 버튼이 눌러졌습니다.\n");
			System.out.println("Status: calculateTax 버튼이 눌러졌습니다.");
			String result="";
			
			taxTotal=register.calculateTax();
			result=taxTotal.toString();
			t_total.setText(result);
			messageArea.append("세금 포함:"+result+"\n");				
			
			b_enterItem.setEnabled(false);
			b_endSale.setEnabled(false);
			b_makePayment.setEnabled(false);
			
			combo_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			t_total.setEnabled(true);
			t_total.setEditable(false);
			t_amount.setEnabled(false);
			t_desc.setEnabled(false);
			t_currentTotal.setEnabled(true);
			t_currentTotal.setEditable(false);
			
			rb_tax[0].setEnabled(false);
			rb_tax[1].setEnabled(false);
			
			rb_discount[0].setEnabled(true);
			rb_discount[1].setEnabled(true);
			b_applyDiscount.setEnabled(true);
			t_discountTotal.setEditable(false);
		}
		
	};
	
	//라디오 버튼 item리스너 구현(할인)
		class MyItemListener2 implements ItemListener{

			@Override
			public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.DESELECTED) {
						return;
					}if(rb_discount[0].isSelected()) {
						mode=1;
						sale.makeSalePricingStrategy(mode);
						messageArea.append("Best For Customer\n");
					}else if(rb_discount[1].isSelected()) {
						mode=2;
						sale.makeSalePricingStrategy(mode);
						messageArea.append("Best for Store\n");
					}				
			}
			
		}
		
		//applyDiscount버튼 눌렀을 때의 이벤트 핸들러
		ActionListener applyDiscountHandler=new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Money discountTotal=new Money(Integer.parseInt(t_total.getText()));
				messageArea.append("Status: applyDiscount 버튼이 눌러졌습니다.\n");
				System.out.println("Status: applyDiscount 버튼이 눌러졌습니다.");
				String result="";
				
				discountTotal=register.applyDiscount();
				result=discountTotal.toString();
				t_discountTotal.setText(result);
				messageArea.append("할인 적용:"+result+"\n");				
				
				b_enterItem.setEnabled(false);
				b_endSale.setEnabled(false);
				b_makePayment.setEnabled(true);
				
				combo_itemID.setEnabled(false);
				t_quantity.setEnabled(false);
				t_total.setEnabled(true);
				t_total.setEditable(false);
				t_amount.setEnabled(true);
				t_desc.setEnabled(false);
				t_currentTotal.setEnabled(true);
				t_currentTotal.setEditable(false);
				
				rb_tax[0].setEnabled(false);
				rb_tax[1].setEnabled(false);
				
				rb_discount[0].setEnabled(false);
				rb_discount[1].setEnabled(false);
				b_applyDiscount.setEnabled(false);
				t_discountTotal.setEditable(false);
			}
			
		};
	
	//makePayment버튼을 눌렀을때의 이벤트 핸들러
	ActionListener makePaymentHandler=new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String amount=t_amount.getText();
			String balance="0";
		try {	
			Money m=new Money(Integer.parseInt(amount));
			register.makePayment(m);
			
			balance=sale.getBalance().toString();
			t_balance.setText(balance);
		}catch(NumberFormatException ee) {
			//수량 예외처리
			JOptionPane.showMessageDialog(null,"숫자가 아닙니다. 숫자만 입력해주세요.","Message",JOptionPane.ERROR_MESSAGE);
			t_amount.setText(" ");
		}
			combo_itemID.setEnabled(false);
			t_quantity.setText("");
			b_endSale.setEnabled(false);
			t_balance.setEnabled(true);
			t_balance.setEditable(false);
			b_makeNewSale.setEnabled(true);
			t_desc.setEnabled(false);
			t_currentTotal.setEnabled(false);
			rb_discount[0].setEnabled(false);
			rb_discount[1].setEnabled(false);
			b_applyDiscount.setEnabled(false);
			t_discountTotal.setEditable(false);
			
			messageArea.append("지불 금액: "+amount);
			messageArea.append("\n거스름돈: "+balance);
			System.out.println("Status: Make Payment 버튼이 눌러졌습니다.");
			
		}	
	};
	
	private class GuiPane extends JPanel{
		
		GridBagLayout grid;
		GridBagConstraints gbc;
		
		private GuiPane() {
			//setLayout(new GridBagLayout());
			
			//1.
			b_makeNewSale=new JButton("1. Make New Sale()"); 
			b_makeNewSale.setAlignmentX(Component.CENTER_ALIGNMENT);
			//b_makeNewSale.setPreferredSize(new Dimension(115,30));
			
			JLabel l_itemID=new JLabel("Item ID:  ");
			
			//현재 상황 설명하는 메시치 출력영역
			messageArea=new JTextArea("",30,25);
			JScrollPane s_message=new JScrollPane(messageArea);
			
			
			//DB데이터가 들어가는 콤보박스
			combo_itemID=new JComboBox<String>();
			
			ids=new ArrayList<String>();
			ids=register.getIDList();
			for(int i=0;i<ids.size();i++) {
				combo_itemID.addItem(ids.get(i));
			}
			combo_itemID.setSelectedIndex(-1);
			
			Box idBox = new Box(BoxLayout.X_AXIS);//id가 들어있는 박스
			idBox.add(l_itemID);
			idBox.add(combo_itemID);
			
			JLabel l_quantity=new JLabel("Quantity:  ");
			t_quantity=new JTextField(10);
			
			Box quantityBox = new Box(BoxLayout.X_AXIS);//수량이 들어있는 박스
			quantityBox.add(l_quantity);
			quantityBox.add(t_quantity);
			
			JLabel l_desc=new JLabel("Description:  ");
			t_desc=new JTextField(10);
			
			Box descBox = new Box(BoxLayout.X_AXIS);//수량이 들어있는 박스
			descBox.add(l_desc);
			descBox.add(t_desc);
			
			b_enterItem=new JButton("2. Enter Item(반복)");	
			b_enterItem.setAlignmentX(Component.CENTER_ALIGNMENT);
			//b_enterItem.setPreferredSize(new Dimension(115,30));
			
			JLabel lbl_currenttotal=new JLabel("Current Total: ");
			t_currentTotal=new JTextField(10);
			
			Box currentTotalBox = new Box(BoxLayout.X_AXIS);//total이 들어있는 박스
			currentTotalBox.add(lbl_currenttotal);
			currentTotalBox.add(t_currentTotal);
			
			
			b_endSale=new JButton("3. End Sale()");	
			b_endSale.setAlignmentX(Component.CENTER_ALIGNMENT);
			//b_endSale.setPreferredSize(new Dimension(115,30));
			
			//세금 계산
			ButtonGroup g1=new ButtonGroup();
			
			rb_tax[0]=new JRadioButton("TaxMaster");
			rb_tax[1]=new JRadioButton("GoodAsGoldTaxPro");
			
			g1.add(rb_tax[0]);
			g1.add(rb_tax[1]);
			
			Box taxBox=new Box(BoxLayout.Y_AXIS);
			taxBox.add(rb_tax[0]);
			taxBox.add(rb_tax[1]);
			
			b_calculateTax=new JButton("4. calculateTax()");
			b_calculateTax.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			JLabel lbl_total=new JLabel("Total with Tax:  ");
			t_total=new JTextField(10);
			
			Box totalBox = new Box(BoxLayout.X_AXIS);//total이 들어있는 박스
			totalBox.add(lbl_total);
			totalBox.add(t_total);	
			
			//할인 전략
			ButtonGroup g2=new ButtonGroup();
			
			rb_discount[0]=new JRadioButton("BestForCustomer");
			rb_discount[1]=new JRadioButton("BestForStore");
			
			g2.add(rb_discount[0]);
			g2.add(rb_discount[1]);
			
			Box discountBox=new Box(BoxLayout.Y_AXIS);
			discountBox.add(rb_discount[0]);
			discountBox.add(rb_discount[1]);
			
			b_applyDiscount=new JButton("5. applyDiscount()");
			b_applyDiscount.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			JLabel lbl_afterdiscounttotal=new JLabel("Total after Discount:  ");
			t_discountTotal=new JTextField(10);
			
			Box discounttotalBox = new Box(BoxLayout.X_AXIS);//total이 들어있는 박스
			discounttotalBox.add(lbl_afterdiscounttotal);
			discounttotalBox.add(t_discountTotal);	
			
			JLabel lbl_amount=new JLabel("Amount  ");
			t_amount=new JTextField(10);
			Box amountBox = new Box(BoxLayout.X_AXIS);
			amountBox.add(lbl_amount);
			amountBox.add(t_amount);	
			
			b_makePayment=new JButton("6. Make Payment()");
			b_makePayment.setAlignmentX(Component.CENTER_ALIGNMENT);
			//b_makePayment.setPreferredSize(new Dimension(115,30));
			
			JLabel lbl_balance=new JLabel("Balance  ");
			t_balance=new JTextField(10); 
			Box balanceBox = new Box(BoxLayout.X_AXIS);
			balanceBox.add(lbl_balance);
			balanceBox.add(t_balance);	
			
			
			grid=new GridBagLayout();
			gbc = new GridBagConstraints();
			
			setLayout(grid);
			
			gbc.fill=GridBagConstraints.BOTH;
			gbc.weightx=1.0;
			gbc.weighty=1.0;
			
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth=2;
            gbc.gridheight=1;
            grid.setConstraints(b_makeNewSale, gbc);
            add(b_makeNewSale);
            
            addCom(s_message,2,0,1,16);
            
            addCom(idBox,0,1,2,1);
            addCom(quantityBox,0,2,2,1);
            addCom(descBox,0,3,2,1);
            addCom(b_enterItem,0,4,2,1);
            addCom(currentTotalBox,0,5,2,1);
            addCom(b_endSale,0,6,2,1);
            addCom(taxBox,0,7,2,1);
            addCom(b_calculateTax,0,8,2,1);
            addCom(totalBox,0,9,2,1);
            addCom(discountBox,0,10,2,1);
            addCom(b_applyDiscount,0,11,2,1);
            addCom(discounttotalBox,0,12,2,1);
            addCom(amountBox,0,13,2,1);
            addCom(b_makePayment,0,14,2,1);
            addCom(balanceBox,0,15,2,1);
          /*  // gbc.fill = GridBagConstraints.HORIZONTAL;
           // gbc.ipadx = 25;
           // gbc.ipady = 10;
            
			// gbc.gridy++;
           // add(b_makeNewSale,gbc);
			// gbc.gridy++;
            addCom(s_message,2,0,1,2);
			add(idBox,gbc);
			 gbc.gridy++;
			add(quantityBox,gbc);
			 gbc.gridy++;
			add(descBox,gbc);
			 gbc.gridy++;
			add(b_enterItem,gbc);
			 gbc.gridy++;
			 add(currentTotalBox,gbc);
			 gbc.gridy++;
			add(b_endSale,gbc);
			 gbc.gridy++;
			add(totalBox,gbc);
			 gbc.gridy++;
			add(amountBox,gbc);
			 gbc.gridy++;
			add(b_makePayment,gbc);
			 gbc.gridy++;
			add(balanceBox,gbc);
			 gbc.gridy++;*/
		}

		private void addCom(Component c, int i, int j, int k, int l) {
			gbc.gridx=i;
			gbc.gridy=j;
			gbc.gridwidth=k;
			gbc.gridheight=l;
			
			grid.setConstraints(c, gbc);
			add(c);
		}
	}
}
