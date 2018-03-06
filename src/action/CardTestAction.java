package action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import classes.*;

/**
 * this part is used to help the Recite UI, all function is in here
 */
public class CardTestAction {
	private Card C;
	private Queue<Record> CardTest;// ���Եļ���Ķ���
	private ArrayList<Record> KnowList;// ֪���ı�
	private ArrayList<Record> donnotKonwList;// ��֪���ı�
	private int allList;// ��������
	private String type;// ���Է�ʽ

	/**
	 * Every time start the recite, the class need point the recite card
	 * 
	 * @param C
	 *            Card
	 */
	public CardTestAction(Card C) {
		this.C = C;
		CardTest = new LinkedList<Record>();
		KnowList = new ArrayList<Record>();
		donnotKonwList = new ArrayList<Record>();
		setQueue();// ���г�ʼ��

	}

	/**
	 * use to return next recite data
	 * 
	 * @return ReciteData
	 */
	public ReciteData getReciteData() {
		// ��һ�� �ж϶����Ƿ�Ϊ�գ�������ȡ����һ�μ�����е�Ԫ��
		if (!CardTest.isEmpty()) {
			Record temp = CardTest.poll();
			ReciteData data = new ReciteData();
			data.firstRecite = false;
			data.needAsw = (C.type == 1 || C.type == 2) ? true : false;
			data.record = temp;
			// TODO
			List<String> L = getAswList(temp);
			data.rightAsw = L.get(0);
			data.testNote = L.get(1);
			return data;
		} else {
			// ���п� ���ؿ�
			return null;
		}

	}

	/**
	 * use to point the R is known
	 * 
	 * @param R
	 *            Record
	 */
	public void act_know(Record R) {
		// ֪������ϰ����-1���жϸ�ϰ�����������ϰ����Ϊ0����������У���Ϊ0�������ܶ���
		R.seeTimes += 1;
		R.passTimes += 1;
		KnowList.add(R);// ����֪������
	}

	/**
	 * use to point the R is not known very clear
	 * 
	 * @param R
	 *            Record
	 */
	public void act_donotKnow(Record R) {
		// ���������ϰ�������䣬�����ܶ���
		R.seeTimes += 1;
		R.forgetTimes += 1;
		R.thisForgetTimes += 1;
		donnotKonwList.add(R);
	}

	public List<String> getAswList(Record R) {
		List<String> L = new ArrayList<String>();
		/**
		 * L List 0 - getQuestion �ش�����, 1 - getNote ��ʾ���ݵĲ��䣨������䣩
		 */
		if (C.type == 1) {
			// ������
			L.add(0, R.getRemenber());
			L.add(1, null);
		} else if (C.type == 2) {
			// �����
			String s = getRecite(R.remember);
			System.out.println(s);
			String note = R.remember;
			L.add(0, s);
			// [^a-zA-Z]a[^a-zA-Z]
			L.add(1, note.replaceFirst("\\b" + s + "\\b", "______"));
		} else {
			L.add(null);
			L.add(null);
		}
		return L;
	}

	public void setQueue() {
		// �����ܶ��У�����ģ������+��֪������������
		LinkedList<Record> R = new LinkedList<Record>(C.getRecordList());
		sort(R, 0, R.size() - 1);// ��������
		// �����ȡ���ݣ�����10��
		Random ren = new Random(System.nanoTime());
		int i = 0;
		while (true) {
			if (CardTest.size() > 10 || R.isEmpty()) {
				break;// ���Զ��������ߴ�����пգ��˳�ѭ��
			}
			i = Math.abs(ren.nextInt()) % R.size();
			CardTest.offer(R.get(i));// �������
			R.remove(i);// �Ƴ�����
		}
		allList = CardTest.size();
		switch (C.type) {
		case 1:
			type = "����ƴд";
			break;
		case 2:
			type = "�������";
			break;
		case 3:
		default:
			type = "�Բ�";
			break;
		}
	}
	/**
	 * get test type
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * get test number
	 * @return
	 */
	public int getTestNum() {
		return allList;
	}
	
	public  List<Object> getList(){
		/**
		 * DataList
		 * 
		 * <pre>
		 *            list(0)(int) - number of the test; 
		 *            list(1)(String) - the type of the test; 
		 *            list(2)(ArrayList<Record>) - KnowList;
		 *            list(3)(ArrayList<Record>) - donnotKonwList.
		 * </pre>
		 */
		ArrayList<Object> L=new ArrayList<Object>();
		L.add(0, allList);
		L.add(1, C.getType());
		L.add(2, KnowList);
		L.add(3, donnotKonwList);
		return L;
	}

	public static void sort(LinkedList<Record> R, int start, int end) {
		if (start >= end) {
			return;
		}
		int index = partition(R, start, end);
		sort(R, start, index - 1);
		sort(R, index + 1, end);
	}

	public static int partition(LinkedList<Record> R, int start, int end) {
		// �̶����зַ�ʽ
		int key = getKey(R.get(start));
		Record temp = R.get(start);
		while (start < end) {
			while (getKey(R.get(end)) >= key && end > start) {// �Ӻ�벿����ǰɨ��
				end--;
			}
			R.set(start, R.get(end));
			while (getKey(R.get(start)) <= key && end > start) {// ��ǰ�벿�����ɨ��
				start++;
			}
			R.set(end, R.get(start));
		}
		R.set(end, temp);
		return end;
	}

	/**
	 * get key value about the record
	 * 
	 * @param R
	 * @return ���Ǵ�����ģ������֮��
	 */
	public static int getKey(Record R) {
		return R.forgetTimes + R.donotKnowTimes;
	}

	public static String getRecite(String S) {
		String[] arr = S.replaceAll("[^a-zA-Z0-9]", "$0 ").split(" ");// �Ȱѱ���ǰ����Ͽո�Ȼ��������
		// �������жϵ�����󳤶�
		int len = 0;
		for (int i = 0; i < arr.length; i++) {
			len = (arr[i].trim().length() > len) ? arr.length : len;
		}
		int get = 0;
		Random ran = new Random(System.nanoTime());// ׼�������ȡ
		while (true) {
			get = Math.abs(ran.nextInt()) % arr.length;// ȡ�������
			if (arr[get].contains("'"))
				continue;// ����ȡ��'
			if (len < 5 || arr[get].trim().length() > len - 4)
				break;// ȡ�������
		}
		return arr[get].trim().replaceAll("[\\pP]", "");// ȥ���ո��Լ����
	}

}
