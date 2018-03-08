package ui.testUI;


import javax.swing.JPanel;
import javax.swing.JTextPane;

import ui.CardTest;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TestBeginning extends JPanel {
	private CardTest Jf;
	private JTextPane text_info;
	public TestBeginning() {
		setSize(1150, 750);
		setLayout(null);
		
		JButton button = new JButton("\u5F00\u59CB\u6D4B\u8BD5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Jf.nextCard();//ÇÐ»»panel
			}
		});
		button.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 36));
		button.setBounds(419, 533, 320, 103);
		add(button);
		
		 text_info = new JTextPane();
		 text_info.setEditable(false);
		text_info.setText("\u672C\u6B21\u9009\u53D6__\u4E2A\u8BB0\u5FC6\u6761\u76EE\uFF0C\u5168\u90E8\u91C7\u7528____\uFF08\u81EA\u6D4B\u3001\u5355\u8BCD\u586B\u7A7A\u3001\u5355\u8BCD\u62FC\u5199\uFF09\u65B9\u5F0F\u8FDB\u884C\u3002\r\n\r\n\u6D4B\u8BD5\u7ED3\u675F\u540E\u4F1A\u7ED9\u51FA\u8F83\u8BE6\u7EC6\u7684\u6D4B\u8BD5\u7ED3\u679C\u3002");
		text_info.setBackground(SystemColor.control);
		text_info.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 40));
		text_info.setBounds(213, 142, 736, 328);
		add(text_info);
	}
	public void setUIs(CardTest Jf,String S) {
		this.Jf=Jf;
		this.text_info.setText(S);
	}
}