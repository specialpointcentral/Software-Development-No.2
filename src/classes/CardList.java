package classes;

import java.util.ArrayList;
import java.util.Date;

public class CardList {
	private ArrayList<Card> Clist;

	public CardList() {
		// read list
		Clist = _getList();
	}

	/**
	 * get card list
	 * 
	 * @return ArrayList<Card>
	 */
	private  ArrayList<Card> _getList() {
		//ɨ���ļ�������������ļ����ҵ���չ����ͬ���ļ�
		//ÿһ���ļ�����һ����Ƭ��ȫ����ȡ
		//TODO only a test
		ArrayList<Card> test = new ArrayList<Card>();
		Card C = new Card(1, "����(zh-cn)", "����");
		C.addRecord(new Record("Remember", "note"));
		C.start=true;
		Record record=new Record("remember2", "note");
		record.nextTime=new Date();
		C.addRecord(record);
		Record record2=new Record("remember3", "note");
		record2.nextTime=new Date();
		C.addRecord(record2);
		test.add(C);

		return test;
	}
	/**
	 * use to return saved list
	 * @return ArrayList<Card>
	 */
	public ArrayList<Card> getList(){	
		return Clist;
	}
	/**
	 * use to create new card
	 * @param C Card
	 * @return true -- create success, false -- create do not success
	 */
	public boolean addCard(Card C) {
		//TODO
		return true;
	}
	/**
	 * use to save all cards
	 */
	public void saveCard() {
		//TODO
		
	}
}
