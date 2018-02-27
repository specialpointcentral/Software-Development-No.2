package classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Card {
	public int type;// 类型
	public String lang; // 语言
	public String card;// 卡片名
	public boolean start;// 开始记忆？
	public Collection<Record> records;// 卡片含有的记录

	public Card(int type, String lang, String card) {
		this.type = type;
		this.lang = lang;
		this.card = card;
		records = new LinkedList<Record>();
		start = false;
	}

	public void insertRecord(Record r) throws Exception {
		records.add(r);
	}

	public void deleteRecord(Record r) throws Exception {
		records.remove(r);
	}

	public String getType() {
		String types;
		switch (type) {
		case 1:
			types = "单词类";
			break;
		case 2:
			types = "语句类";
			break;
		default:
			types = "其他类";
			break;
		}
		return types;
	}

	public String getlang() {
		return lang;
	}

	public String getCardName() {
		return card;
	}

	public boolean getStartorNot() {
		return start;
	}

	/**
	 * use to return record list which in the card
	 * 
	 * @param C
	 *            Card
	 * @return ArrayList<Record>
	 */
	public ArrayList<Record> getRecordList() {

		return new ArrayList<Record>(records);
	}

	/**
	 * rewrite toString make it more considerable
	 */
	public String toString() {
		return card + "(" + getType() + ") - " + records.size();
	}

	/**
	 * add record to the card
	 * 
	 * @param R
	 *            Record
	 */
	public void addRecord(Record R) {
		records.add(R);
	}
}
