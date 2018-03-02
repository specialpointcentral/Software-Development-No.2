package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import classes.Card;
import classes.Record;

public class AnalyzeAction {

	/**
	 * use to set the analyze table
	 * 
	 * @param C
	 *            Card
	 * @param T
	 *            JTable
	 */
	public static void setAnalyzeTable(Card C, JTable T) {
		DefaultTableModel Tmodel = new DefaultTableModel(new Object[][] {},
				new String[] { "\u6761\u76EE", "\u63D0\u793A", "\u8BB0\u5FC6\u6B21\u6570", "\u6A21\u7CCA\u6B21\u6570",
						"\u4E0D\u77E5\u9053\u6B21\u6570", "\u8BB0\u5FC6\u8F6E\u6570", "\u4E0B\u6B21\u590D\u4E60" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		T.setModel(Tmodel);
		if (C == null) {// 选空
			Tmodel.addRow(new String[] {});
			return;
		}
		ArrayList<Record> record = C.getRecordList();
		for (Iterator<Record> it = record.iterator(); it.hasNext();) {
			Record temp = it.next();
			SimpleDateFormat Fmt = new SimpleDateFormat("yyyy/MM/dd");// 日期String化
			String nextTime = (temp.nextTime == null) ? "未定义" : Fmt.format(temp.nextTime);
			Tmodel.addRow(new Object[] { temp.remember, temp.note, temp.seeTimes, temp.forgetTimes, temp.donotKnowTimes,
					"剩余" + temp.reviewTimes + "轮", nextTime });
		}
	}

}
