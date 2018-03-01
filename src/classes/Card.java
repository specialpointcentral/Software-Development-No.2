package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	public int type;// ���� 1-���� 2-��� 3-����
	public String lang; // ����
	public String card;// ��Ƭ��
	public boolean start;// ��ʼ���䣿
	public Collection<Record> records;// ��Ƭ���еļ�¼

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
			types = "������";
			break;
		case 2:
			types = "�����";
			break;
		default:
			types = "������";
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
	 * use to find records all start
	 * 
	 * @return true all records are start, false some records are not start
	 */
	public boolean getAllisStart() {
		boolean startAll = true;
		// �ж��´θ�ϰʱ������null
		for (Iterator<Record> it = records.iterator(); it.hasNext();) {
			Record R = it.next();
			if (R != null && !R.isReciteOver && R.nextTime == null) {
				startAll = false;
				break;
			}
		}
		return startAll;
	}
	/**
	 * use to identify all the records are studied
	 * @return
	 */
	public boolean getAllisOver() {
		boolean overAll = true;
		// �ж��´θ�ϰʱ������null
		for (Iterator<Record> it = records.iterator(); it.hasNext();) {
			Record R = it.next();
			if (R != null && (!R.isReciteOver || R.nextTime != null)) {
				overAll = false;
				break;
			}
		}
		return overAll;
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
