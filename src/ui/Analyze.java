package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import action.AnalyzeAction;
import action.DetailAction;
import action.InsertAction;
import classes.Card;

import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Analyze extends JPanel {
	private JTable table_all;
	private JList<Card> list_card;

	public Analyze() {
		setSize(950, 550);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 240, 524);
		panel.add(scrollPane);

		list_card = new JList<Card>();
		scrollPane.setViewportView(list_card);
		list_card.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (!e.getValueIsAdjusting()) {
					AnalyzeAction.setAnalyzeTable(list_card.getSelectedValue(), table_all);
				}

			}
		});

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(268, 13, 357, 524);
		panel.add(scrollPane_1);

		table_all = new JTable();
		table_all.setRowHeight(25);
		table_all.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u6761\u76EE", "\u8BB0\u5FC6\u6B21\u6570", "\u6A21\u7CCA\u6B21\u6570" }));
		scrollPane_1.setViewportView(table_all);

		JTextArea text_detail = new JTextArea();
		text_detail.setBounds(639, 13, 297, 524);
		panel.add(text_detail);
		DetailAction.showCardList(list_card);

	}

	/**
	 * use to refresh UI
	 */
	public void refreshUI() {
		list_card.updateUI();
		table_all.updateUI();
		list_card.clearSelection();
	}
}
