package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class ModifyCard extends JFrame {
	private JTextField text_cardName;
	public ModifyCard() {
		setSize(427,255);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u8BB0\u5FC6\u5185\u5BB9\u8BED\u8A00");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		label.setBounds(14, 114, 96, 18);
		getContentPane().add(label);
		
		text_cardName = new JTextField();
		text_cardName.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		text_cardName.setBounds(128, 11, 254, 24);
		getContentPane().add(text_cardName);
		text_cardName.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5361\u7247\u540D\u79F0");
		label_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		label_1.setBounds(14, 14, 72, 18);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5361\u7247\u7C7B\u578B");
		label_2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 16));
		label_2.setBounds(14, 64, 72, 18);
		getContentPane().add(label_2);
		
		JComboBox box_type = new JComboBox();
		box_type.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		box_type.setModel(new DefaultComboBoxModel(new String[] {"\u5355\u8BCD\u7C7B\uFF08\u53EF\u4EE5\u8FDB\u884C\u5355\u8BCD\u62FC\u5199\u6D4B\u8BD5\uFF09", "\u8BED\u53E5\u7C7B\uFF08\u53EF\u4EE5\u8FDB\u884C\u53E5\u5B50\u586B\u7A7A\u6D4B\u8BD5\uFF09", "\u5176\u4ED6\u7C7B\uFF08\u81EA\u884C\u6D4B\u8BD5\uFF09"}));
		box_type.setBounds(128, 62, 254, 24);
		getContentPane().add(box_type);
		
		JComboBox box_lang = new JComboBox();
		box_lang.setModel(new DefaultComboBoxModel(new String[] {"\u4E2D\u6587\uFF08zh-cn\uFF09", "\u82F1\u8BED\uFF08en-us\uFF09", "\u5176\u4ED6\u8BED\u7CFB"}));
		box_lang.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 15));
		box_lang.setBounds(128, 112, 254, 24);
		getContentPane().add(box_lang);
		
		JButton btn_confirm = new JButton("\u786E\u8BA4");
		btn_confirm.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 17));
		btn_confirm.setBounds(269, 168, 113, 27);
		getContentPane().add(btn_confirm);
	}
}
