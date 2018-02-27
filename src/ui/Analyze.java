package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;

public class Analyze extends JPanel {
	private JTable table_all;
	public Analyze() {
		setSize(950,550);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 240, 524);
		panel.add(scrollPane);
		
		JList list_card = new JList();
		scrollPane.setViewportView(list_card);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(268, 13, 357, 524);
		panel.add(scrollPane_1);
		
		table_all = new JTable();
		table_all.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", null, null},
				{"2", null, null},
			},
			new String[] {
				"\u6761\u76EE", "\u8BB0\u5FC6\u6B21\u6570", "\u6A21\u7CCA\u6B21\u6570"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(table_all);
		
		JTextArea text_detail = new JTextArea();
		text_detail.setBounds(639, 13, 297, 524);
		panel.add(text_detail);
	}
}
