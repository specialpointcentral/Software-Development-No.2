package ui;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import action.ReciteAction;
import classes.Card;
import classes.ReciteData;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Recite extends JFrame {
	private JTextPane text_input;
	private ReciteAction actionClass;
	private JTextPane text_info;
	private JLabel label_info;
	private JButton btn_know;
	private JButton btn_donnotKnow;
	private JButton btn_maybe;
	private JFrame thisFrame;// ��ǰ����

	private ReciteData data;// ��ǰ���������

	public Recite(Card C) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		thisFrame = this;
		actionClass = new ReciteAction(C);
		setSize(1150, 750);
		getContentPane().setLayout(null);
		setTitle("���ڼ���-" + C.card);
		text_info = new JTextPane();
		text_info.setBackground(SystemColor.control);
		text_info.setEditable(false);
		text_info.setText(
				"\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9");
		text_info.setFont(new Font("΢���ź�", Font.PLAIN, 56));
		text_info.setBounds(14, 46, 1104, 166);
		// text_info.setLineWrap(true);
		getContentPane().add(text_info);

		text_input = new JTextPane();
		text_input.setBackground(SystemColor.control);
		text_input.setFont(new Font("΢���ź�", Font.PLAIN, 58));
		text_input.setText("\u5185\u5BB9");
		text_input.setBounds(203, 355, 721, 97);
		text_input.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.GRAY));
		// text_input.setLineWrap(true);
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER && data.needAsw) {
					// ���»س���������Ҫ�ش����⣬�Ž�����Ӧ
					e.consume();// ��ִ��enter
					if (data.rightAsw.trim().equals(text_input.getText().trim())) {
						// �ش���ȷ
						actionClass.act_know(data.record);
						text_input.setForeground(Color.GREEN);
						// ���ð�ť
						btn_know.setEnabled(false);
						btn_donnotKnow.setEnabled(false);
						btn_maybe.setEnabled(false);

						new _setUI(1000);
					} else {
						// �ش����
						actionClass.act_donotKnow(data.record);

						label_info.setText("��ȷ��Ӧ���ǣ�");
						text_input.setText(data.rightAsw);
						// ���ð�ť
						btn_know.setEnabled(false);
						btn_donnotKnow.setEnabled(false);
						btn_maybe.setEnabled(false);

						new _setUI(3000);
					}
				}
			}
		});
		getContentPane().add(text_input);

		btn_maybe = new JButton("\u6A21\u7CCA");
		btn_maybe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionClass.act_maybe(data.record);
				if (data.needAsw) {
					label_info.setText("��ȷ��Ӧ���ǣ�");
					text_input.setText(data.rightAsw);
				} else {
					label_info.setText("�����������£�");
					text_input.setText(data.record.remember);
				}
				// ���ð�ť
				btn_know.setEnabled(false);
				btn_donnotKnow.setEnabled(false);
				btn_maybe.setEnabled(false);

				new _setUI(3000);
			}
		});
		btn_maybe.setFont(new Font("΢���ź�", Font.PLAIN, 45));
		btn_maybe.setBounds(64, 581, 231, 72);
		getContentPane().add(btn_maybe);

		btn_donnotKnow = new JButton("\u4E0D\u77E5\u9053");
		btn_donnotKnow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_donotKnow(data.record);

				if (data.needAsw) {
					label_info.setText("��ȷ��Ӧ���ǣ�");
					text_input.setText(data.rightAsw);
				} else {
					label_info.setText("�����������£�");
					text_input.setText(data.record.remember);
				}
				// ���ð�ť
				btn_know.setEnabled(false);
				btn_donnotKnow.setEnabled(false);
				btn_maybe.setEnabled(false);

				new _setUI(3000);
			}
		});
		btn_donnotKnow.setFont(new Font("΢���ź�", Font.PLAIN, 45));
		btn_donnotKnow.setBounds(824, 581, 231, 72);
		getContentPane().add(btn_donnotKnow);

		btn_know = new JButton("\u77E5\u9053");
		btn_know.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_know(data.record);

				_setUI();
			}
		});
		btn_know.setFont(new Font("΢���ź�", Font.PLAIN, 45));
		btn_know.setBounds(448, 581, 231, 72);
		getContentPane().add(btn_know);

		label_info = new JLabel("\u8BF7\u5728\u4E0B\u9762\u586B\u5199\u4E0A\u8FF0\u7B54\u6848\uFF1A");
		label_info.setForeground(Color.GRAY);
		label_info.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		label_info.setBounds(39, 309, 212, 24);
		getContentPane().add(label_info);
		// �����ر��¼�
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// �رմ���ǰ��ʾ
				if (JOptionPane.showConfirmDialog(thisFrame, "�˴�ѧϰ��δ��ɣ��Ƿ��˳���", "����", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					dispose();
				} else
					return;
			}

		});

		_setUI();// ��һ����ʾ

	}

	private void _setUI() {
		// TODO
		data = actionClass.getReciteData();
		// �ָ���ť�������״̬
		text_input.setForeground(Color.black);
		btn_know.setEnabled(true);
		btn_donnotKnow.setEnabled(true);
		btn_maybe.setEnabled(true);

		if (data == null) {
			// ���ѧϰ
			// TODO
			JOptionPane.showMessageDialog(null, "��ϰ���");
			dispose();
			return;
		}
		if (data.firstRecite) {
			// ��һ�μ���
			text_info.setText(data.record.note);
			text_input.setVisible(true);
			text_input.setText(data.record.remember);
			label_info.setText("�����Ǽ������ݣ�");
			text_input.setEditable(false);// ����򲻿ɱ༭

		} else {
			// ��ϰ����
			text_info.setText(data.record.note);
			text_input.setVisible(true);
			text_input.setText("");// ����ı���׼���ش�������߸����
			if (data.needAsw) {
				// Ҫ�ش�����
				btn_know.setVisible(false);// ֪����ť����
				label_info.setText("��������д���𰸣�");
				text_input.setEditable(true);// �����ɱ༭
			} else {
				// ���ûش��Բ�
				btn_know.setVisible(true);
				label_info.setText("���������ִ𰸣�");
				text_input.setEditable(false);// ����򲻿ɱ༭
			}

		}

	}

	class _setUI extends Thread {
		private int time;// wait time

		public _setUI(int time) {
			this.time = time;
			this.start();
		}

		public void run() {
			try {
				sleep(time);
				_setUI();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
