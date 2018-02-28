package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import classes.Card;
import classes.Record;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ModifyRecord extends JFrame {
	private JTextArea text_content;
	private JTextArea text_note;
	private Card C;

	public ModifyRecord() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setSize(466, 388);
		getContentPane().setLayout(null);
		setTitle("添加记忆内容");
		JLabel label_1 = new JLabel("\u8BB0\u5FC6\u5185\u5BB9");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setBounds(14, 14, 72, 18);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("\u8BB0\u5FC6\u63D0\u793A");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_2.setBounds(14, 97, 72, 18);
		getContentPane().add(label_2);

		JButton btn_confirm = new JButton("\u786E\u8BA4");
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 边界检测
				if (text_content.getText().trim().equals("") || text_note.getText().trim().equals("")) {
					// 结果空，不通过
					JOptionPane.showMessageDialog(null, "记忆内容和提示内容不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					C.addRecord(new Record(text_content.getText().trim(), text_note.getText().trim()));
					JOptionPane.showMessageDialog(null, "插入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});
		btn_confirm.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		btn_confirm.setBounds(321, 300, 113, 39);
		getContentPane().add(btn_confirm);

		text_content = new JTextArea();
		text_content.setBounds(100, 13, 334, 73);
		getContentPane().add(text_content);

		text_note = new JTextArea();
		text_note.setBounds(100, 95, 334, 73);
		getContentPane().add(text_note);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(200, 200, 200)), " \u6CE8\u610F\u4E8B\u9879 ",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), new Color(0, 0, 0)));
		panel.setBounds(14, 188, 420, 99);
		getContentPane().add(panel);
		panel.setLayout(null);

		JTextArea text_info = new JTextArea();
		text_info.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		text_info.setBackground(SystemColor.control);
		text_info.setEditable(false);
		text_info.setText(
				"\u8F93\u5165\u7684\u5185\u5BB9\u5E94\u8BE5\u4E0E\u5361\u7247\u8BBE\u7F6E\u5BF9\u5E94\u4E00\u81F4\u3002\r\n\u6240\u9009\u5361\u7247\u4E3A \u5355\u8BCD\u7C7B \u82F1\u8BED\u3002\r\n\u5982\u679C\u4E0D\u4E00\u81F4\u8BF7\u91CD\u65B0\u521B\u5EFA\u5361\u7247\u6216\u8005\u6539\u53D8\u5361\u7247\u7684\u5C5E\u6027\u3002");
		text_info.setBounds(14, 28, 392, 63);
		panel.add(text_info);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 关闭窗口前提示
				if (JOptionPane.showConfirmDialog(null, "是否关闭对话框，关闭后内容将不会保存？", "警告", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
					dispose();
				else
					return;
			}

		});
	}

	public void setValue(Card C) {
		this.C = C;
	}
}
