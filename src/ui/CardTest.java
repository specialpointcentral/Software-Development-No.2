package ui;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.border.MatteBorder;

import action.CardTestAction;
import classes.Card;
import classes.ReciteData;
import ui.testUI.TestBeginning;
import ui.testUI.TestEndReport;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.CardLayout;

public class CardTest extends JFrame {
	private JTextPane text_input;
	private CardTestAction actionClass;
	private JTextPane text_info;
	private JLabel label_info;
	private JButton btn_know;
	private JButton btn_donnotKnow;
	private JFrame thisFrame;// ��ǰ����
	private boolean actEnter = false;// input��Ӧ�س�
	private JPanel Jp;// ��¼��ǰPanel
	private CardLayout cardLayout = new CardLayout();

	private ReciteData data;// ��ǰ���������
	private TestBeginning beginShow;
	private TestEndReport endReport;

	public CardTest(Card C) {
		setResizable(false);
		Jp = new JPanel();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		thisFrame = this;
		actionClass = new CardTestAction(C);
		setSize(1150, 750);
		getContentPane().setLayout(cardLayout);
		beginShow = new TestBeginning();
		// TODO
		String note = new String();
		// ____���Բ⡢������ա�����ƴд��
		note = "����ѡȡ" + actionClass.getTestNum() + "��������Ŀ��ȫ������" + actionClass.getType() + "��ʽ���С�\r\n" + "\r\n"
				+ "���Խ�������������ϸ�Ĳ��Խ��";
		beginShow.setUIs(this, note);
		getContentPane().add(beginShow);

		getContentPane().add(Jp);
		Jp.setLayout(null);
		setTitle("���ڲ���-��Ƭ " + C.card);
		text_info = new JTextPane();
		text_info.setBackground(SystemColor.control);
		text_info.setEditable(false);
		text_info.setText("\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9");
		text_info.setFont(new Font("΢���ź�", Font.PLAIN, 56));
		text_info.setBounds(14, 46, 1104, 166);
		Jp.add(text_info);

		text_input = new JTextPane();
		text_input.setBackground(SystemColor.control);
		text_input.setFont(new Font("΢���ź�", Font.PLAIN, 58));
		text_input.setText("\u5185\u5BB9");
		text_input.setBounds(203, 355, 721, 97);
		text_input.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.GRAY));
		text_input.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// ���ε����ʾ��
				if (e.getClickCount() == 1) {
					// ���ε��
					if (!data.firstRecite && !data.needAsw) {
						// ��ϰ״̬�����Ҳ����ڽ��в���
						text_input.setText(data.record.remember);
					}
				}
			}
		});
		text_input.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (actEnter && e.getKeyCode() == KeyEvent.VK_ENTER && data.needAsw) {
					// ���»س���������Ҫ�ش����⣬�Ž�����Ӧ
					e.consume();// ��ִ��enter
					actEnter = false;// ����enter
					if (data.rightAsw.trim().equalsIgnoreCase(text_input.getText().trim())) {
						// �ش���ȷ
						actionClass.act_know(data.record);
						text_input.setForeground(Color.GREEN);
						text_input.setEditable(false);// ����򲻿ɱ༭
						// ���ð�ť
						btn_know.setEnabled(false);
						btn_donnotKnow.setEnabled(false);

						new _setUIClass(1000);
					} else {
						// �ش����
						actionClass.act_donotKnow(data.record);

						label_info.setText("��ȷ��Ӧ���ǣ�");
						text_input.setText(data.rightAsw);
						text_input.setEditable(false);// ����򲻿ɱ༭
						// ���ð�ť
						btn_know.setEnabled(false);
						btn_donnotKnow.setEnabled(false);

						new _setUIClass(3000);
					}
				}
			}
		});
		Jp.add(text_input);

		btn_donnotKnow = new JButton("\u4E0D\u77E5\u9053");
		btn_donnotKnow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_donotKnow(data.record);

				if (data.needAsw) {
					label_info.setText("��ȷ��Ӧ���ǣ�");
					// TODO ���ִ�С
					text_input.setText(data.rightAsw);
				} else {
					label_info.setText("�����������£�");
					// TODO ���ִ�С
					text_input.setText(data.record.remember);
				}
				// ���ð�ť
				text_input.setEditable(false);// ����򲻿ɱ༭
				btn_know.setEnabled(false);
				btn_donnotKnow.setEnabled(false);

				new _setUIClass(3000);
			}
		});
		btn_donnotKnow.setFont(new Font("΢���ź�", Font.PLAIN, 45));
		btn_donnotKnow.setBounds(693, 581, 231, 72);
		Jp.add(btn_donnotKnow);

		btn_know = new JButton("\u77E5\u9053");
		btn_know.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_know(data.record);

				_setUI();
			}
		});
		btn_know.setFont(new Font("΢���ź�", Font.PLAIN, 45));
		btn_know.setBounds(203, 581, 231, 72);
		Jp.add(btn_know);

		label_info = new JLabel("\u8BF7\u5728\u4E0B\u9762\u586B\u5199\u4E0A\u8FF0\u7B54\u6848\uFF1A");
		label_info.setForeground(Color.GRAY);
		label_info.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		label_info.setBounds(39, 309, 212, 24);
		Jp.add(label_info);
		// �����ر��¼�
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// �رմ���ǰ��ʾ
				if (JOptionPane.showConfirmDialog(thisFrame, "�˴β��Ի�δ��ɣ��Ƿ��˳���", "����", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					dispose();
				} else
					return;
			}

		});

		endReport = new TestEndReport(this);
		getContentPane().add(endReport);

		// �ʼ��ʾ��ʾ���棬���ǲ��ԣ����Ǳ���

		_setUI();// ��һ����ʾ

	}

	private void _setUI() {
		data = actionClass.getReciteData();
		// �ָ���ť�������״̬
		text_input.setForeground(Color.black);
		text_input.setEditable(true);
		btn_know.setEnabled(true);
		btn_donnotKnow.setEnabled(true);

		if (data == null) {
			// ���ѧϰ
			// TODO ����ͳ�ƽ���
			// JOptionPane.showMessageDialog(null, "�������");
			endReport.actionUI(actionClass.getList());//������Ϣ
			nextCard();
			return;
		}

		// ���в���
		text_info.setText(data.record.note);
		// TODO ���ִ�С
		text_input.setVisible(true);
		text_input.setText("");// ����ı���׼���ش�������߸����
		if (data.needAsw) {
			// Ҫ�ش�����
			actEnter = true;// ����enter
			btn_know.setVisible(false);// ֪����ť����
			label_info.setText("��������д���𰸣�");
			text_input.setEditable(true);// �����ɱ༭
			// ��������
			if (data.testNote != null) {// ��Ҫ��ʾ����
				// TODO ���ִ�С
				text_info.setText(text_info.getText() + "\n" + data.testNote);
			}
		} else {
			// ���ûش��Բ�
			btn_know.setVisible(true);
			label_info.setText("���������ִ𰸣�");
			// TODO ���ִ�С
			text_input.setEditable(false);// ����򲻿ɱ༭
		}

	}

	private void setTextsize(JTextPane Jp, String showText) {
		// TODO

	}

	/**
	 * set the next card layout
	 */
	public void nextCard() {
		cardLayout.next(thisFrame.getContentPane());
	}

	class _setUIClass extends Thread {
		private int time;// wait time

		public _setUIClass(int time) {
			this.time = time;
			this.start();
		}

		public void run() {
			try {
				sleep(time);
				_setUI();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
