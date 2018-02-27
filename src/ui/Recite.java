package ui;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Recite extends JFrame {
	private JTextArea text_input;
	public Recite() {
		setSize(1150,750);
		getContentPane().setLayout(null);
		
		JTextArea text_info = new JTextArea();
		text_info.setBackground(SystemColor.control);
		text_info.setEditable(false);
		text_info.setText("\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9");
		text_info.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 56));
		text_info.setBounds(14, 46, 1104, 166);
		text_info.setLineWrap(true);
		getContentPane().add(text_info);
		
		text_input = new JTextArea();
		text_input.setBackground(SystemColor.control);
		text_input.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 58));
		text_input.setText("\u5185\u5BB9");
		text_input.setBounds(203, 355, 721, 97);
		text_input.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.GRAY));
		text_input.setLineWrap(true);
		getContentPane().add(text_input);

		
		JButton btn_maybe = new JButton("\u6A21\u7CCA");
		btn_maybe.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 45));
		btn_maybe.setBounds(64, 581, 231, 72);
		getContentPane().add(btn_maybe);
		
		JButton btn_donnotKnow = new JButton("\u4E0D\u77E5\u9053");
		btn_donnotKnow.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 45));
		btn_donnotKnow.setBounds(824, 581, 231, 72);
		getContentPane().add(btn_donnotKnow);
		
		JButton btn_know = new JButton("\u77E5\u9053");
		btn_know.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 45));
		btn_know.setBounds(448, 581, 231, 72);
		getContentPane().add(btn_know);
		
		JLabel label_info = new JLabel("\u8BF7\u5728\u4E0B\u9762\u586B\u5199\u4E0A\u8FF0\u7B54\u6848\uFF1A");
		label_info.setForeground(Color.GRAY);
		label_info.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 18));
		label_info.setBounds(39, 309, 212, 24);
		getContentPane().add(label_info);

	}
}
