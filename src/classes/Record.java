package classes;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable  {
	private static final long serialVersionUID = 1L;
	public String remember;// ��������
	public String note;// ��ʾ
	public int seeTimes;// �Ѽ������
	public int forgetTimes;// ģ������
	public int donotKnowTimes;// ��֪������
	public int passTimes;// ͨ������
	public int thisTimes;// ������Ҫ��ϰ����
	public int thisForgetTimes;// ����ģ������
	public int reviewTimes;// ��Ҫ��ϰ����
	public Date nextTime;// ��һ�θ�ϰʱ��
	public boolean isReciteOver;// �Ѿ�ȫ����ϰ��ɣ������ϣ�

	public Record(String remember, String note) {
		this.remember = remember;
		this.note = note;
		seeTimes = 0;
		forgetTimes = 0;
		donotKnowTimes = 0;
		passTimes = 0;
		thisTimes = 0;
		thisForgetTimes = 0;
		reviewTimes = 4;
		nextTime = null;
		isReciteOver = false;
	}

	public String getRemenber() {
		return remember;
	}

	public String getNote() {
		return note;
	}

	/**
	 * rewrite toString make it more considerable
	 */
	public String toString() {
		String remembers;
		if (remember.length() <= 40)
			remembers = remember;
		else
			remembers = remember.substring(0, 39) + "..";
		return remembers;
	}
}
