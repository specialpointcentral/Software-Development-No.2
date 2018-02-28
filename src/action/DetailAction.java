package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import classes.Card;
import classes.Record;
import main.Main;
import ui.Recite;

/**
 * this part is used to help the Detail UI, all function is in here
 */
public class DetailAction {

	/**
	 * use to show card list to the list
	 * 
	 * @param L
	 *            JList<Card>
	 */
	public static void showCardList(JList<Card> C) {
		ArrayList<Card> cards = getCardList();
		DefaultListModel<Card> Lmodel = new DefaultListModel<Card>();
		C.setModel(Lmodel);
		for (Iterator<Card> it = cards.iterator(); it.hasNext();) {
			Lmodel.addElement(it.next());
		}
	}

	/**
	 * use to show record list to the list
	 * 
	 * @param R
	 *            JList<Record>
	 * @param C
	 *            Card
	 */
	public static void showRecord(JList<Record> R, Card C) {
		DefaultListModel<Record> Lmodel = new DefaultListModel<Record>();
		R.setModel(Lmodel);
		if (C == null) {
			Lmodel.clear();
		} else {
			Lmodel.clear();
			ArrayList<Record> cards = C.getRecordList();
			for (Iterator<Record> it = cards.iterator(); it.hasNext();) {
				Lmodel.addElement(it.next());
			}
		}
		R.updateUI();
	}

	/**
	 * use to return Card list array
	 * 
	 * @return ArrayList<Card>
	 */
	public static ArrayList<Card> getCardList() {
		return Main.Clist.getList();
	}

	/**
	 * use to return the Card information
	 * 
	 * @param C
	 *            Card
	 * @return String the Card information
	 */
	public static String getDetailInfo(Card C) {
		String temp = null;
		ArrayList<Record> recordList = C.getRecordList();
		temp = "此卡片的详细数据：\n";
		temp += "总词条数：" + recordList.size() + "\n";
		temp += "卡片类型：" + C.getType() + "\n";
		temp += "语言：" + C.getlang() + "\n";
		temp += "是否开始学习：" + (C.getStartorNot() ? "是" : "否") + "\n";
		if (!C.getStartorNot())
			return temp;// 未开始学习，不考虑复习
		temp += "下次复习时间：" + getNextReviewInfo(recordList).get(0) + "\n";
		temp += "下次复习词条数：" + getNextReviewInfo(recordList).get(1) + "\n";
		return temp;
	}

	/**
	 * use to get next review time
	 * 
	 * @param R
	 *            ArrayList<Record>
	 * @return the next review time and the record number,index 0 is Time(String) format is yyyy/MM/dd,
	 *         index 1 is number(int)
	 */
	public static ArrayList<Object> getNextReviewInfo(ArrayList<Record> R) {
		// TODO
		R.sort(new sortRule());
		SimpleDateFormat Fmt = new SimpleDateFormat("yyyy/MM/dd");
		ArrayList<Object> L = new ArrayList<Object>();
		if (R.get(0).nextTime == null) {
			//没有复习时间，即没开始学习
			L.add(0, "未定义");// 复习时间
			L.add(1, 0);// 复习条数
			return L;
		} else
			L.add(0, Fmt.format(R.get(0).nextTime));// 复习时间
		int num = 0;
		for (int i = 0; i < R.size(); i++) {
			if (R.get(i).nextTime!=null&&Fmt.format(R.get(0).nextTime).equals(Fmt.format(R.get(i).nextTime)))
				num++;
			else
				break;
		}
		L.add(1, num);// 复习条数
		return L;
	}

	/**
	 * use to unset the card study
	 * 
	 * @param C
	 *            Card
	 */
	public static boolean act_reset(Card C) {
		// set card is not study
		C.start = false;
		// then set all record
		ArrayList<Record> recordList = C.getRecordList();
		for (Iterator<Record> it = recordList.iterator(); it.hasNext();) {
			Record temp = it.next();
			temp.donotKnowTimes = 0;
			temp.forgetTimes = 0;
			temp.nextTime = null;
			temp.passTimes = 0;
			temp.reviewTimes = 4;
			temp.seeTimes = 0;
			temp.thisForgetTimes = 0;
			temp.thisTimes = 3;
		}
		return true;
	}

	/**
	 * use to start the study
	 * 
	 * @param C
	 *            Card
	 */
	public static void act_start(Card C) {
		// TODO
		Recite fr=new Recite(C);
		fr.setVisible(true);
		fr.setLocationRelativeTo(null);

	}

	/**
	 * use to start the review
	 * 
	 * @param C
	 *            Card
	 */
	public static void act_review(Card C) {
		// TODO
		Recite fr=new Recite(C);
		fr.setVisible(true);
		fr.setLocationRelativeTo(null);

	}

}

class sortRule implements Comparator<Record> {
	public int compare(Record r1, Record r2) {
		//判断null 防止有些单词没有学习，将null放在后面
		if(r1.nextTime!=null&&r2.nextTime==null) return -1;
		if(r1.nextTime==null&&r2.nextTime!=null) return 1;
		if(r1.nextTime==null&&r2.nextTime==null) return 0;
		if(r1.nextTime.before(r2.nextTime)) return -1;
		else return 1;
	}
}
