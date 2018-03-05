package ui;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Point;

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
import javax.swing.JLabel;
import java.awt.Color;

public class Insert extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table_content;
	private JList<Card> list_card;
	private Insert thisPanel;// 本窗体

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
					// 未选择卡片
					JOptionPane.showMessageDialog(null, "未选择要插入的卡片！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					ModifyRecord fr = new ModifyRecord(thisPanel, Main.f);
					fr.setValue(list_card.getSelectedValue());
					fr.setVisible(true);
				}
			}
		});
		button_inscontent.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		button_inscontent.setBounds(798, 503, 138, 34);
		mainPanel.add(button_inscontent);

		JButton button_inscard = new JButton("\u63D2\u5165\u5361\u7247");
		button_inscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyCard fr = new ModifyCard(thisPanel, Main.f);
				fr.setVisible(true);
			}
		});
		button_inscard.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		button_inscard.setBounds(653, 503, 113, 34);
		mainPanel.add(button_inscard);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 273, 477);
		mainPanel.add(scrollPane);

		list_card = new JList<Card>();
		list_card.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		scrollPane.setViewportView(list_card);
		// 弹出菜单
		JPopupMenu jPopupMenu = new JPopupMenu("选项");
		JMenuItem modify = new JMenuItem("修改");
		JMenuItem delete = new JMenuItem("删除");
		jPopupMenu.add(modify);
		jPopupMenu.add(delete);
		modify.addActionListener(new ActionListener() {
			// 修改选择
			@Override
			public void actionPerformed(ActionEvent e) {
				ModifyCard fr = new ModifyCard(thisPanel, Main.f);
				fr.setValue(list_card.getSelectedValue());
				fr.setVisible(true);
			}
		});
		delete.addActionListener(new ActionListener() {
			// 删除选择
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(thisPanel, "是否删除这个词条，随同的文件同样也会被删除且不能恢复？", "警告", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					if (Main.Clist.deleteCard(list_card.getSelectedValue()))
						refreshUI();
					else System.err.println("出错");
				}
			}
		});

		list_card.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (list_card.getSelectedValue() != null) {
					// 有选中
					if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
						// 左键双击
						ModifyCard fr = new ModifyCard(thisPanel, Main.f);
						fr.setValue(list_card.getSelectedValue());
						fr.setVisible(true);
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						// 右键
						Point p = e.getPoint();
						jPopupMenu.show(e.getComponent(), p.x, p.y);

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
		scrollPane_1.setBounds(301, 13, 635, 477);
		mainPanel.add(scrollPane_1);

		table_content = new JTable();
		table_content.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		table_content.setRowHeight(25);
		table_content.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_content.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u8BB0\u5FC6\u5185\u5BB9", "\u63D0\u793A\u5185\u5BB9" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table_content.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				InsertAction.setBeforeText((String) table_content.getValueAt(table_content.getSelectedRow(), table_content.getSelectedColumn()));
			}
		});

		scrollPane_1.setViewportView(table_content);
		new InsertBeginUI(list_card);

		JLabel lblNewLabel = new JLabel(
				"\u901A\u8FC7\u53CC\u51FB\u6216\u53F3\u51FB\u76F8\u5E94\u7684\u5185\u5BB9\u66F4\u6539\u5361\u7247\u4FE1\u606F");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(14, 503, 273, 18);
		mainPanel.add(lblNewLabel);
	}

	/**
	 * use to refresh UI
	 */
	public void refreshUI() {
		list_card.clearSelection();
		list_card.updateUI();
		table_content.updateUI();
		DetailAction.showCardList(list_card);
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
