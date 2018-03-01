package action;

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
				new String[] { "\u6761\u76EE", "\u8BB0\u5FC6\u6B21\u6570", "\u6A21\u7CCA\u6B21\u6570" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		T.setModel(Tmodel);
		if (C == null) {// бЁПе
			Tmodel.addRow(new String[] { "", "", "" });
			return;
		}
		ArrayList<Record> record = C.getRecordList();
		for (Iterator<Record> it = record.iterator(); it.hasNext();) {
			Record temp = it.next();
			Tmodel.addRow(new Object[] { temp.remember, temp.seeTimes, temp.forgetTimes });
		}
	}

}
