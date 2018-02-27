package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Insert extends JPanel {
	private JTable table_content;
	public Insert() {
		setSize(950,550);
		setLayout(new BorderLayout(0, 0));
		
		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		
		JButton button_inscontent = new JButton("\u63D2\u5165\u8BB0\u5FC6\u5185\u5BB9");
		button_inscontent.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		button_inscontent.setBounds(798, 503, 138, 34);
		mainPanel.add(button_inscontent);
		
		JButton button_inscard = new JButton("\u63D2\u5165\u5361\u7247");
		button_inscard.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		button_inscard.setBounds(653, 503, 113, 34);
		mainPanel.add(button_inscard);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 23, 273, 467);
		mainPanel.add(scrollPane);
		
		JList list_card = new JList();
		scrollPane.setViewportView(list_card);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(301, 23, 635, 467);
		mainPanel.add(scrollPane_1);
		
		table_content = new JTable();
		table_content.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"\u8BB0\u5FC6\u5185\u5BB9", "\u63D0\u793A\u5185\u5BB9"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(table_content);
	}

}
