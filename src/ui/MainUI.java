package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import action.IOAction;
import action.LastWorks;
import main.Main;

import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


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
				// refresh the UI
				Object source=tabbedPane.getSelectedComponent();
				if(source instanceof Detail) {
					((Detail) source).refreshUI();
				}else if(source instanceof Insert) {
					((Insert) source).refreshUI();
				}else if(source instanceof Analyze) {
					((Analyze) source).refreshUI();
				}
				
			}
		});
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				//�رմ���ǰ��������
				Main.Clist.saveCard();
			}
			
		});
		
	}
}
