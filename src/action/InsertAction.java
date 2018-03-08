package action;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import classes.*;

/**
 * this part is used to help the Insert UI, all function is in here
 */
public class InsertAction {
	private static ArrayList<Record> R = new ArrayList<Record>();// ���ڱ������ʾ��

	/**
	 * use to show the table_content
	 * 
	 * @param C
	 *            Card
	 * @param T
	 *            JTable
	 */
	static String beforeText = null;// ��ǰ�ı������

	/**
	 * set the beforeText
	 * 
	 * @param S
	 */
	public static void setBeforeText(String S) {
		beforeText = S;
		System.out.println(beforeText);
	}

	/**
	 * show the table
	 * @param C Card
	 * @param T JTable
	 */
	public static void showTable(Card C, JTable T) {
		DefaultTableModel Tmodel = new DefaultTableModel(new Object[][] {},
				new String[] { "\u8BB0\u5FC6\u5185\u5BB9", "\u63D0\u793A\u5185\u5BB9" });
		Tmodel.addTableModelListener(new TableModelListener() {

			//�û����ı�����ݣ�ϵͳʵʱ��¼
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int col = e.getColumn();
				if (row >= 0 && col >= 0) {
					// �����ж��ǲ���һ�ж�Ϊ��
					if (((String) T.getValueAt(row, 0)).trim().equals("")
							&& ((String) T.getValueAt(row, 0)).trim().equals("")) {
						C.records.remove(R.get(row));
						Tmodel.removeRow(row);// ɾ�����һ��
						return;
					}
					// ����---------------------------
					System.out.println(row + " " + col);
					System.out.println(T.getValueAt(row, col));
					// -------------------------------
					if (col == 0) {
						// ��������
						if (((String) T.getValueAt(row, col)).trim().equals("")) {
							// ��ֵ���û�����
							if (JOptionPane.showConfirmDialog(null, "ȷ�Ͻ������������գ�", "����", JOptionPane.WARNING_MESSAGE,
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
								R.get(row).remember = "";
							else {
								T.setValueAt(beforeText, row, col);
								R.get(row).remember = beforeText;
							}
						}
						R.get(row).remember = (String) T.getValueAt(row, col);
					} else if (col == 1) {
						// ��ʾ����
						if (((String) T.getValueAt(row, col)).trim().equals("")) {
							// ��ֵ���û�����
							if (JOptionPane.showConfirmDialog(null, "ȷ�Ͻ���ʾ�������գ�", "����", JOptionPane.WARNING_MESSAGE,
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
								R.get(row).note = "";
							else {
								T.setValueAt(beforeText, row, col);
								R.get(row).note = beforeText;
							}
						}
						R.get(row).note = (String) T.getValueAt(row, col);
					}
				}
			}
		});
		T.setModel(Tmodel);

		if (C == null) {
			Tmodel.addRow(new Object[] {});
			R.clear();
			return;
		}
		ArrayList<Record> record = C.getRecordList();
		for (Iterator<Record> it = record.iterator(); it.hasNext();) {
			Record temp = it.next();
			R.add(temp);
			Tmodel.addRow(new Object[] { temp.remember, temp.note });
		}
	}

}
