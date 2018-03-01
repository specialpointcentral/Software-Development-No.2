package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import action.DetailAction;
import action.InsertAction;
import classes.Card;
import main.Main;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.awt.event.ActionEvent;

public class Insert extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table_content;
	private JList<Card> list_card;
	private Insert thisPanel;// ±¾´°Ìå

	public Insert() {
		thisPanel = this;
		setSize(950, 550);
		setLayout(new BorderLayout(0, 0));

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		JButton button_inscontent = new JButton("\u63D2\u5165\u8BB0\u5FC6\u5185\u5BB9");
		button_inscontent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list_card.getSelectedValue() == null) {
					// Î´Ñ¡Ôñ¿¨Æ¬
					JOptionPane.showMessageDialog(null, "Î´Ñ¡ÔñÒª²åÈëµÄ¿¨Æ¬£¡", "ÌáÊ¾", JOptionPane.INFORMATION_MESSAGE);
				} else {
					ModifyRecord fr = new ModifyRecord(thisPanel, Main.f);
					fr.setValue(list_card.getSelectedValue());
					fr.setVisible(true);
				}
			}
		});
		button_inscontent.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		button_inscontent.setBounds(798, 503, 138, 34);
		mainPanel.add(button_inscontent);

		JButton button_inscard = new JButton("\u63D2\u5165\u5361\u7247");
		button_inscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyCard fr = new ModifyCard(thisPanel, Main.f);
				fr.setVisible(true);
			}
		});
		button_inscard.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		button_inscard.setBounds(653, 503, 113, 34);
		mainPanel.add(button_inscard);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 23, 273, 467);
		mainPanel.add(scrollPane);

		list_card = new JList<Card>();
		scrollPane.setViewportView(list_card);
		list_card.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (list_card.getSelectedValue() != null) {
					// ÓÐÑ¡ÖÐ
					if (e.getClickCount() == 2) {
						// Ë«»÷
						ModifyCard fr = new ModifyCard(thisPanel, Main.f);
						fr.setValue(list_card.getSelectedValue());
						fr.setVisible(true);
					}
				}
			}
		});
		list_card.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					InsertAction.showTable(list_card.getSelectedValue(), table_content);
				}
			}
		});

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(301, 23, 635, 467);
		mainPanel.add(scrollPane_1);

		table_content = new JTable();
		table_content.setRowHeight(25);
		table_content.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_content.setModel(new DefaultTableModel(new Object[][] { { null, null }, },
				new String[] { "\u8BB0\u5FC6\u5185\u5BB9", "\u63D0\u793A\u5185\u5BB9" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		scrollPane_1.setViewportView(table_content);
		new InsertBeginUI(list_card);
	}

	/**
	 * use to refresh UI
	 */
	public void refreshUI() {
		list_card.updateUI();
		table_content.updateUI();
		list_card.clearSelection();
	}

}

class InsertBeginUI extends Thread {
	JList<Card> C;

	public InsertBeginUI(JList<Card> C) {
		this.C = C;
		this.start();
	}

	public void run() {
		DetailAction.showCardList(C);
	}
}
