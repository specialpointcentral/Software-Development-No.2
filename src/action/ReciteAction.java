package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import classes.*;

/**
 * this part is used to help the Recite UI, all function is in here
 */
public class ReciteAction {
	private Card C;
	private Queue<Record> firstRecite;// 第一次记忆的队列
	private Queue<Record> review;// 复习队列（8个左右）
	private Queue<Record> remanent;// 总剩余队列

	/**
	 * Every time start the recite, the class need point the recite card
	 * 
	 * @param C
	 *            Card
	 */
	public ReciteAction(Card C) {
		this.C = C;
		firstRecite = new LinkedList<Record>();
		review = new LinkedList<Record>();
		remanent = new LinkedList<Record>();
		// TODO
		setQueue();// 队列初始化

	}

	/**
	 * use to return next recite data
	 * 
	 * @return ReciteData
	 */
	public ReciteData getReciteData() {
		// 先判断复习队列，满则先进行复习
		if (reviewQueueFull()) {
			Record temp = review.poll();
			ReciteData data = new ReciteData();
			data.firstRecite = false;
			data.needAsw = (C.type == 1 || C.type == 2) ? true : false;
			data.record = temp;
			data.rightAsw = getQuestion(temp);
			data.testNote = getNote(temp);

			return data;
		}
		// 第一步 判断第一次记忆队列是否为空，不空则取出第一次记忆队列的元素
		if (!firstRecite.isEmpty()) {
			Record temp = firstRecite.poll();
			ReciteData data = new ReciteData();
			data.firstRecite = true;
			data.needAsw = false;
			data.record = temp;
			data.rightAsw = null;
			data.testNote = null;

			return data;
		}
		// 如果第一次记忆队列空，检查复习队列，如果不空，抽取复习队列
		if (!review.isEmpty()) {
			Record temp = review.poll();
			ReciteData data = new ReciteData();
			data.firstRecite = false;
			data.needAsw = (C.type == 1 || C.type == 2) ? true : false;
			data.record = temp;
			data.rightAsw = getQuestion(temp);
			data.testNote = getNote(temp);

			return data;
		}
		// 复习队列为空，从总队列加入复习队列，然后抽取复习队列
		else if (!remanent.isEmpty()) {
			if (addReviewQueue())// 成功再进行
				return getReciteData();// 递归调用
			else
				return null;// 不成功返回空
		} else
			return null;// 说明已经结束

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
		R.thisTimes -= 1;
		// 判断复习次数
		if (R.thisTimes <= 0) {
			// 完成复习
			R.reviewTimes -= 1;
			if (R.reviewTimes <= 0) {
				// 完成全部复习轮数
				R.nextTime = null;
				R.isReciteOver = true;
			} else {
				// 植入下次复习时间
				switch (R.reviewTimes) {
				case 1:
					R.nextTime = setDate(new Date(), 14);
					break;
				case 2:
					R.nextTime = setDate(new Date(), 6);
					break;
				case 3:
					R.nextTime = setDate(new Date(), 3);
					break;
				case 4:
					R.nextTime = setDate(new Date(), 1);
					break;
				default:
					R.nextTime = setDate(new Date(), 1);
					break;
				}
			}
		} else {
			remanent.offer(R);// 加入队列
		}
	}

	/**
	 * use to point the R is not known
	 * 
	 * @param R
	 *            Record
	 */
	public void act_donotKnow(Record R) {
		// 不知道，所有值归位，重新复习，加入总队列
		R.seeTimes += 1;
		R.donotKnowTimes += 1;
		R.thisTimes = 3;
		remanent.offer(R);// 加入队列
	}

	/**
	 * use to point the R is not known very clear
	 * 
	 * @param R
	 *            Record
	 */
	public void act_maybe(Record R) {
		// 不清楚，复习次数不变，加入总队列
		R.seeTimes += 1;
		R.forgetTimes += 1;
		R.thisForgetTimes += 1;
		remanent.offer(R);// 加入队列
	}

	/**
	 * use to add element to the review queue
	 * 
	 * @return true add success, false add is not correct
	 */
	public boolean addReviewQueue() {
		// 边界检测 判断队列是否满 8个为满
		if (reviewQueueFull()) {
			return false;
		}
		while (!remanent.isEmpty() && !reviewQueueFull()) {
			Record temp = remanent.poll();
			if (temp != null)
				review.offer(temp);
			else
				continue;
		}
		return true;
	}

	/**
	 * use identify review queue is full
	 * 
	 * @return true full, false not full
	 */
	public boolean reviewQueueFull() {
		if (review.size() > 8)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param R
	 *            Record
	 * @return the question answer
	 */
	public String getQuestion(Record R) {
		if (C.type == 1) {
			// 单词类
			return R.getRemenber();
		} else if (C.type == 2) {
			// 语句类
			// TODO
			return null;
		} else
			return null;

	}

	/**
	 * 
	 * @param R
	 *            Record
	 * @return the question note, only for sentence, other return null
	 */
	public String getNote(Record R) {
		if (C.type == 2) {
			// 语句类
			// TODO
			return null;
		} else
			return null;
	}

	/**
	 * use to set time, let the date add day
	 * 
	 * @param D
	 *            Date
	 * @param day
	 * @return Date
	 */
	public static Date setDate(Date D, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(D); // 需要将date数据转移到Calendar对象中操作
		calendar.add(Calendar.DATE, day);// 把日期往后增加n天.正数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	public void setQueue() {
		// 先填第一次记忆队列
		LinkedList<Record> R = new LinkedList<Record>(C.getRecordList());
		for (Iterator<Record> it = R.iterator(); it.hasNext();) {
			Record temp = it.next();
			if (temp.seeTimes == 0 || temp.nextTime == null) {
				// 第一次记忆的
				temp.thisTimes = 3;
				firstRecite.offer(temp);
			}
		}
		// 再填总队列，按照模糊次数+不知道次数排序来
		sort(R, 0, R.size() - 1);// 快速排序
		SimpleDateFormat Fmt = new SimpleDateFormat("yyyy/MM/dd");// 日期String化
		for (Iterator<Record> it = R.iterator(); it.hasNext();) {
			Record temp = it.next();
			if (temp.nextTime != null && Fmt.format(temp.nextTime).equals(Fmt.format(new Date()))) {
				// 今天复习
				temp.thisTimes = 3;
				remanent.offer(temp);
			}
		}
		R = null;// 内存回收
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

}
