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
	private Queue<Record> CardTest;// 测试的记忆的队列
	private ArrayList<Record> KnowList;// 知道的表
	private ArrayList<Record> donnotKonwList;// 不知道的表
	private int allList;// 测试数量
	private String type;// 测试方式

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
		setQueue();// 队列初始化

	}

	/**
	 * use to return next recite data
	 * 
	 * @return ReciteData
	 */
	public ReciteData getReciteData() {
		// 第一步 判断队列是否为空，不空则取出第一次记忆队列的元素
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
			// 队列空 返回空
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
		// 知道，复习次数-1，判断复习次数，如果复习次数为0，不加入队列，不为0，加入总队列
		R.seeTimes += 1;
		R.passTimes += 1;
		KnowList.add(R);// 加入知道队列
	}

	/**
	 * use to point the R is not known very clear
	 * 
	 * @param R
	 *            Record
	 */
	public void act_donotKnow(Record R) {
		// 不清楚，复习次数不变，加入总队列
		R.seeTimes += 1;
		R.forgetTimes += 1;
		R.thisForgetTimes += 1;
		donnotKonwList.add(R);
	}

	public List<String> getAswList(Record R) {
		List<String> L = new ArrayList<String>();
		/**
		 * L List 0 - getQuestion 回答内容, 1 - getNote 提示内容的补充（用于语句）
		 */
		if (C.type == 1) {
			// 单词类
			L.add(0, R.getRemenber());
			L.add(1, null);
		} else if (C.type == 2) {
			// 语句类
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
		// 先填总队列，按照模糊次数+不知道次数排序来
		LinkedList<Record> R = new LinkedList<Record>(C.getRecordList());
		sort(R, 0, R.size() - 1);// 快速排序
		// 随机抽取内容，抽满10个
		Random ren = new Random(System.nanoTime());
		int i = 0;
		while (true) {
			if (CardTest.size() > 10 || R.isEmpty()) {
				break;// 测试队列满或者待测队列空，退出循环
			}
			i = Math.abs(ren.nextInt()) % R.size();
			CardTest.offer(R.get(i));// 加入队列
			R.remove(i);// 移除队列
		}
		allList = CardTest.size();
		switch (C.type) {
		case 1:
			type = "单词拼写";
			break;
		case 2:
			type = "单词填空";
			break;
		case 3:
		default:
			type = "自测";
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
		// 固定的切分方式
		int key = getKey(R.get(start));
		Record temp = R.get(start);
		while (start < end) {
			while (getKey(R.get(end)) >= key && end > start) {// 从后半部分向前扫描
				end--;
			}
			R.set(start, R.get(end));
			while (getKey(R.get(start)) <= key && end > start) {// 从前半部分向后扫描
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
	 * @return 忘记次数和模糊次数之和
	 */
	public static int getKey(Record R) {
		return R.forgetTimes + R.donotKnowTimes;
	}

	public static String getRecite(String S) {
		String[] arr = S.replaceAll("[^a-zA-Z0-9]", "$0 ").split(" ");// 先把标点等前面加上空格，然后拆成数组
		// 首先先判断单词最大长度
		int len = 0;
		for (int i = 0; i < arr.length; i++) {
			len = (arr[i].trim().length() > len) ? arr.length : len;
		}
		int get = 0;
		Random ran = new Random(System.nanoTime());// 准备随机抽取
		while (true) {
			get = Math.abs(ran.nextInt()) % arr.length;// 取出随机数
			if (arr[get].contains("'"))
				continue;// 避免取出'
			if (len < 5 || arr[get].trim().length() > len - 4)
				break;// 取出随机数
		}
		return arr[get].trim().replaceAll("[\\pP]", "");// 去除空格以及标点
	}

}
