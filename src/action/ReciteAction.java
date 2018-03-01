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
	private Queue<Record> firstRecite;// ��һ�μ���Ķ���
	private Queue<Record> review;// ��ϰ���У�8�����ң�
	private Queue<Record> remanent;// ��ʣ�����

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
		setQueue();// ���г�ʼ��

	}

	/**
	 * use to return next recite data
	 * 
	 * @return ReciteData
	 */
	public ReciteData getReciteData() {
		// ���жϸ�ϰ���У������Ƚ��и�ϰ
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
		// ��һ�� �жϵ�һ�μ�������Ƿ�Ϊ�գ�������ȡ����һ�μ�����е�Ԫ��
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
		// �����һ�μ�����пգ���鸴ϰ���У�������գ���ȡ��ϰ����
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
		// ��ϰ����Ϊ�գ����ܶ��м��븴ϰ���У�Ȼ���ȡ��ϰ����
		else if (!remanent.isEmpty()) {
			if (addReviewQueue())// �ɹ��ٽ���
				return getReciteData();// �ݹ����
			else
				return null;// ���ɹ����ؿ�
		} else
			return null;// ˵���Ѿ�����

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
		R.thisTimes -= 1;
		// �жϸ�ϰ����
		if (R.thisTimes <= 0) {
			// ��ɸ�ϰ
			R.reviewTimes -= 1;
			if (R.reviewTimes <= 0) {
				// ���ȫ����ϰ����
				R.nextTime = null;
				R.isReciteOver = true;
			} else {
				// ֲ���´θ�ϰʱ��
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
			remanent.offer(R);// �������
		}
	}

	/**
	 * use to point the R is not known
	 * 
	 * @param R
	 *            Record
	 */
	public void act_donotKnow(Record R) {
		// ��֪��������ֵ��λ�����¸�ϰ�������ܶ���
		R.seeTimes += 1;
		R.donotKnowTimes += 1;
		R.thisTimes = 3;
		remanent.offer(R);// �������
	}

	/**
	 * use to point the R is not known very clear
	 * 
	 * @param R
	 *            Record
	 */
	public void act_maybe(Record R) {
		// ���������ϰ�������䣬�����ܶ���
		R.seeTimes += 1;
		R.forgetTimes += 1;
		R.thisForgetTimes += 1;
		remanent.offer(R);// �������
	}

	/**
	 * use to add element to the review queue
	 * 
	 * @return true add success, false add is not correct
	 */
	public boolean addReviewQueue() {
		// �߽��� �ж϶����Ƿ��� 8��Ϊ��
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
			// ������
			return R.getRemenber();
		} else if (C.type == 2) {
			// �����
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
			// �����
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
		calendar.setTime(D); // ��Ҫ��date����ת�Ƶ�Calendar�����в���
		calendar.add(Calendar.DATE, day);// ��������������n��.����������,������ǰ�ƶ�
		return calendar.getTime(); // ���ʱ���������������һ��Ľ��
	}

	public void setQueue() {
		// �����һ�μ������
		LinkedList<Record> R = new LinkedList<Record>(C.getRecordList());
		for (Iterator<Record> it = R.iterator(); it.hasNext();) {
			Record temp = it.next();
			if (temp.seeTimes == 0 || temp.nextTime == null) {
				// ��һ�μ����
				temp.thisTimes = 3;
				firstRecite.offer(temp);
			}
		}
		// �����ܶ��У�����ģ������+��֪������������
		sort(R, 0, R.size() - 1);// ��������
		SimpleDateFormat Fmt = new SimpleDateFormat("yyyy/MM/dd");// ����String��
		for (Iterator<Record> it = R.iterator(); it.hasNext();) {
			Record temp = it.next();
			if (temp.nextTime != null && Fmt.format(temp.nextTime).equals(Fmt.format(new Date()))) {
				// ���츴ϰ
				temp.thisTimes = 3;
				remanent.offer(temp);
			}
		}
		R = null;// �ڴ����
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

}
