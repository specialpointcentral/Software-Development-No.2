package ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;
import java.awt.Font;


public class MainUI extends JFrame {
	public MainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("\u8BB0\u5FC6\u5361\u7247");
		setSize(1050,680);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		tabbedPane.setBounds(30, 25, 980, 575);
		getContentPane().add(tabbedPane);
		JPanel panel_detail = new Detail();
		tabbedPane.addTab("��ʼ����", null, panel_detail, null);
		
		JPanel panel_insert = new Insert();
		tabbedPane.addTab("�༭��Ƭ", null, panel_insert, null);
		
		JPanel panel_analyze = new Analyze();
		tabbedPane.addTab("ͳ�Ʒ���", null, panel_analyze, null);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO need rewrite
				
				
			}
		});
		
		
	}
}
