package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import classes.Card;
import main.Main;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class ModifyCard extends JFrame {
	private JTextField text_cardName;
	private JComboBox<String> box_type;
	private JComboBox<String> box_lang;
	private Card C;// Card
	private boolean modify = false;// 是否为修改模式

	public ModifyCard() {
		this.setTitle("填加卡片");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setSize(427, 255);
		getContentPane().setLayout(null);

		JLabel label = new JLabel("\u8BB0\u5FC6\u5185\u5BB9\u8BED\u8A00");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label.setBounds(14, 114, 96, 18);
		getContentPane().add(label);

		text_cardName = new JTextField();
		text_cardName.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		text_cardName.setBounds(128, 11, 254, 24);
		getContentPane().add(text_cardName);
		text_cardName.setColumns(10);

		JLabel label_1 = new JLabel("\u5361\u7247\u540D\u79F0");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(14, 14, 72, 18);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("\u5361\u7247\u7C7B\u578B");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_2.setBounds(14, 64, 72, 18);
		getContentPane().add(label_2);

		box_type = new JComboBox<String>();
		box_type.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		box_type.setModel(new DefaultComboBoxModel<String>(new String[] {
				"\u5355\u8BCD\u7C7B\uFF08\u53EF\u4EE5\u8FDB\u884C\u5355\u8BCD\u62FC\u5199\u6D4B\u8BD5\uFF09",
				"\u8BED\u53E5\u7C7B\uFF08\u53EF\u4EE5\u8FDB\u884C\u53E5\u5B50\u586B\u7A7A\u6D4B\u8BD5\uFF09",
				"\u5176\u4ED6\u7C7B\uFF08\u81EA\u884C\u6D4B\u8BD5\uFF09" }));
		box_type.setBounds(128, 62, 254, 24);
		getContentPane().add(box_type);

		box_lang = new JComboBox<String>();
		box_lang.setModel(new DefaultComboBoxModel<String>(new String[] { "\u4E2D\u6587\uFF08zh-cn\uFF09",
				"\u82F1\u8BED\uFF08en-us\uFF09", "\u5176\u4ED6\u8BED\u7CFB" }));
		box_lang.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		box_lang.setBounds(128, 112, 254, 24);
		getContentPane().add(box_lang);

		JButton btn_confirm = new JButton("\u786E\u8BA4");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 保存信息
				// 先进行边界检测

				if (text_cardName.getText().trim().equals("")) {
					// 卡片名称为空值
					JOptionPane.showMessageDialog(null, "卡片名称不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if (box_type.getSelectedIndex() < 2) {
					if (modify) {
						C.card = text_cardName.getText().trim();
						C.lang = (String) box_lang.getSelectedItem();
						C.type = box_type.getSelectedIndex() + 1;
						JOptionPane.showMessageDialog(null, "成功保存！", "提示", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						C = new Card(box_type.getSelectedIndex() + 1, (String) box_lang.getSelectedItem(),
								text_cardName.getText().trim());
						if(Main.Clist.addCard(C)) {
							JOptionPane.showMessageDialog(null, "成功保存！", "提示", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}else JOptionPane.showMessageDialog(null, "保存失败！", "提示", JOptionPane.WARNING_MESSAGE);
						
					}
				}

			}
		});
		btn_confirm.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		btn_confirm.setBounds(269, 168, 113, 39);
		getContentPane().add(btn_confirm);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				//关闭窗口前提示
				if (JOptionPane.showConfirmDialog(null, "是否关闭对话框，关闭后内容将不会保存？", "警告", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
					dispose();
				else return;
			}
			
		});
	}

	/**
	 * use to set the value of the UI, this is the modify mode
	 * 
	 * @param C
	 *            Card
	 */
	public void setValue(Card C) {
		this.C = C;
		modify = true;
		this.setTitle("修改卡片");
		this.text_cardName.setText(C.card);
		this.box_lang.setSelectedItem(C.lang);
		switch (C.type) {
		case 1:
		case 2:
		case 3:
			this.box_type.setSelectedIndex(C.type - 1);
			break;
		default:
			this.box_type.setSelectedIndex(0);
			break;

		}
	}
}
