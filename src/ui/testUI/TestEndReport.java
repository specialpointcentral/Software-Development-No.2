package ui.testUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import classes.Record;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class TestEndReport extends JPanel {
	private JTable tbl_list;
	private JLabel text_info;
	private JLabel text_score;
	private JLabel label;
	private JLabel label_1;

	public TestEndReport(JFrame Jf) {
		setSize(1150, 750);
		setLayout(null);

		text_info = new JLabel(
				"\u5361\u7247\u7C7B\u578B\u4E3A__\uFF0C\u603B\u5171\u6D4B\u8BD5__\u4E2A\uFF0C\u4EE5\u4E0B\u4E3A\u8BE6\u7EC6\u7684\u8BB0\u5FC6\u60C5\u51B5\uFF1A");
		text_info.setFont(new Font("微软雅黑", Font.BOLD, 26));
		text_info.setBounds(100, 76, 861, 45);
		add(text_info);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 160, 970, 433);
		add(scrollPane);

		tbl_list = new JTable();
		tbl_list.setRowHeight(25);
		tbl_list.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u8BB0\u5FC6\u5185\u5BB9", "\u63D0\u793A\u4FE1\u606F", "\u6D4B\u8BD5\u7ED3\u679C" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		tbl_list.getColumnModel().getColumn(2).setMaxWidth(200);
		tbl_list.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		scrollPane.setViewportView(tbl_list);

		text_score = new JLabel("100");
		text_score.setHorizontalAlignment(SwingConstants.LEFT);
		text_score.setForeground(Color.RED);
		text_score.setFont(new Font("Bradley Hand ITC", Font.BOLD, 80));
		text_score.setBounds(975, 35, 143, 113);
		add(text_score);

		label = new JLabel("\u53C2\u8003\u5206\u6570\uFF1A");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label.setBounds(907, 35, 96, 28);
		add(label);

		JButton button = new JButton("\u4E86\u89E3");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Jf.dispose();
			}
		});
		button.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		button.setBounds(907, 606, 163, 58);
		add(button);
		
		label_1 = new JLabel("\u6211\u4EEC\u5DF2\u7ECF\u8BB0\u5F55\u4E0B\u9519\u8BEF\u7684\u8BCD\u6761\uFF0C\u4EE5\u540E\u5C06\u4F1A\u52A0\u5F3A\u590D\u4E60");
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		label_1.setBounds(100, 595, 390, 28);
		add(label_1);
	}

	/**
	 * renew the UI to show the report
	 * 
	 * @param DataList
	 * 
	 *            <pre>
	 *            list(0)(int) - number of the test; 
	 *            list(1)(String) - the type of the test; 
	 *            list(2)(ArrayList<Record>) - KnowList;
	 *            list(3)(ArrayList<Record>) - donnotKonwList.
	 *            </pre>
	 */
	public void actionUI(List DataList) {
		// 设置提示信息
		text_info.setText("卡片类型为" + DataList.get(1) + "，总共测试" + DataList.get(0) + "个，以下为详细的记忆情况：");
		// 设置分数
		int score = 0;
		ArrayList<Record> KnowList = (ArrayList<Record>) DataList.get(2);
		ArrayList<Record> donnotKonwList = (ArrayList<Record>) DataList.get(3);
		score = (int) (100 * KnowList.size() / (KnowList.size() + donnotKonwList.size()));
		text_score.setText(String.valueOf(score));
		DefaultTableModel Tmodel = (DefaultTableModel) tbl_list.getModel();
		// 先添加知道的
		Record temp = null;
		for (Iterator<Record> it = KnowList.iterator(); it.hasNext();) {
			temp = it.next();
			Tmodel.addRow(new String[] { temp.remember, temp.note, "√" });
		}
		// 后添加不知道的
		for (Iterator<Record> it = donnotKonwList.iterator(); it.hasNext();) {
			temp = it.next();
			Tmodel.addRow(new String[] { temp.remember, temp.note, "×" });
		}

	}
}
