package action;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import classes.*;
/**
 * this part is used to help the Insert UI, all function is in here
 */
public class InsertAction {
	/**
	 * use to show the table_content
	 * 
	 * @param C
	 *            Card
	 * @param T
	 *            JTable
	 */
	public static void showTable(Card C, JTable T) {
		DefaultTableModel Tmodel=new DefaultTableModel(new Object[][] {},new String[] {
				"\u8BB0\u5FC6\u5185\u5BB9", "\u63D0\u793A\u5185\u5BB9"
			});
		T.setModel(Tmodel);
		ArrayList<Record> record = C.getRecordList();
		for (Iterator<Record> it = record.iterator(); it.hasNext();) {
			Record temp=it.next();
			Tmodel.addRow(new String[] {temp.remember,temp.note});
		}
	}
	/**
	 * use to open the card modify dialog
	 * @param Clist JList<Card> update the UI
	 */
	public static void act_insertCard(JList<Card> Clist) {
		//TODO
		
	}
	/**
	 * use to open the record modify dialog
	 * @param Clist JList<Card> update the UI
	 */
	public static void act_insertRecord(JList<Card> Clist) {
		//TODO
		
	}
	/**
	 * use to modify the card list
	 * @param C Card which need modify
	 * @param Clist JList<Card> update the UI
	 */
	public static void act_modify(Card C,JList<Card> Clist) {
		//TODO
		
	}
	
}
