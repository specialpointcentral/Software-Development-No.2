package classes;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable  {
	private static final long serialVersionUID = 1L;
	public String remember;// 记忆内容
	public String note;// 提示
	public int seeTimes;// 已记忆次数
	public int forgetTimes;// 模糊次数
	public int donotKnowTimes;// 不知道次数
	public int passTimes;// 通过次数
	public int thisTimes;// 本次需要复习次数
	public int thisForgetTimes;// 本次模糊次数
	public int reviewTimes;// 需要复习轮数
	public Date nextTime;// 下一次复习时间
	public boolean isReciteOver;// 已经全部复习完成（轮数上）

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
