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
	private JFrame thisFrame;// 当前窗口

	private ReciteData data;// 当前记忆的数据

	public Recite(Card C) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		thisFrame = this;
		actionClass = new ReciteAction(C);
		setSize(1150, 750);
		getContentPane().setLayout(null);
		setTitle("正在记忆-" + C.card);
		text_info = new JTextPane();
		text_info.setBackground(SystemColor.control);
		text_info.setEditable(false);
		text_info.setText(
				"\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9");
		text_info.setFont(new Font("微软雅黑", Font.PLAIN, 56));
		text_info.setBounds(14, 46, 1104, 166);
		// text_info.setLineWrap(true);
		getContentPane().add(text_info);

		text_input = new JTextPane();
		text_input.setBackground(SystemColor.control);
		text_input.setFont(new Font("微软雅黑", Font.PLAIN, 58));
		text_input.setText("\u5185\u5BB9");
		text_input.setBounds(203, 355, 721, 97);
		text_input.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.GRAY));
		// text_input.setLineWrap(true);
		text_input.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 单次点击显示答案
				if (e.getClickCount() == 1) {
					// 单次点击
					if (!data.firstRecite && !data.needAsw) {
						// 复习状态，并且不是在进行测试
						text_input.setText(data.record.remember);
					}
				}
			}
		});
		text_input.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && data.needAsw) {
					// 按下回车，并且需要回答问题，才进行相应
					e.consume();// 不执行enter
					if (data.rightAsw.trim().equals(text_input.getText().trim())) {
						// 回答正确
						actionClass.act_know(data.record);
						text_input.setForeground(Color.GREEN);
						// 禁用按钮
						btn_know.setEnabled(false);
						btn_donnotKnow.setEnabled(false);
						btn_maybe.setEnabled(false);

						new _setUI(1000);
					} else {
						// 回答错误
						actionClass.act_donotKnow(data.record);

						label_info.setText("正确答案应该是：");
						text_input.setText(data.rightAsw);
						// 禁用按钮
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
					label_info.setText("正确答案应该是：");
					text_input.setText(data.rightAsw);
				} else {
					label_info.setText("记忆内容如下：");
					text_input.setText(data.record.remember);
				}
				// 禁用按钮
				btn_know.setEnabled(false);
				btn_donnotKnow.setEnabled(false);
				btn_maybe.setEnabled(false);

				new _setUI(3000);
			}
		});
		btn_maybe.setFont(new Font("微软雅黑", Font.PLAIN, 45));
		btn_maybe.setBounds(64, 581, 231, 72);
		getContentPane().add(btn_maybe);

		btn_donnotKnow = new JButton("\u4E0D\u77E5\u9053");
		btn_donnotKnow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_donotKnow(data.record);

				if (data.needAsw) {
					label_info.setText("正确答案应该是：");
					text_input.setText(data.rightAsw);
				} else {
					label_info.setText("记忆内容如下：");
					text_input.setText(data.record.remember);
				}
				// 禁用按钮
				btn_know.setEnabled(false);
				btn_donnotKnow.setEnabled(false);
				btn_maybe.setEnabled(false);

				new _setUI(3000);
			}
		});
		btn_donnotKnow.setFont(new Font("微软雅黑", Font.PLAIN, 45));
		btn_donnotKnow.setBounds(824, 581, 231, 72);
		getContentPane().add(btn_donnotKnow);

		btn_know = new JButton("\u77E5\u9053");
		btn_know.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_know(data.record);

				_setUI();
			}
		});
		btn_know.setFont(new Font("微软雅黑", Font.PLAIN, 45));
		btn_know.setBounds(448, 581, 231, 72);
		getContentPane().add(btn_know);

		label_info = new JLabel("\u8BF7\u5728\u4E0B\u9762\u586B\u5199\u4E0A\u8FF0\u7B54\u6848\uFF1A");
		label_info.setForeground(Color.GRAY);
		label_info.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label_info.setBounds(39, 309, 212, 24);
		getContentPane().add(label_info);
		// 监听关闭事件
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 关闭窗口前提示
				if (JOptionPane.showConfirmDialog(thisFrame, "此次学习还未完成，是否退出？", "警告", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					dispose();
				} else
					return;
			}

		});

		_setUI();// 第一次显示

	}

	private void _setUI() {
		// TODO
		data = actionClass.getReciteData();
		// 恢复按钮和输入框状态
		text_input.setForeground(Color.black);
		btn_know.setEnabled(true);
		btn_donnotKnow.setEnabled(true);
		btn_maybe.setEnabled(true);

		if (data == null) {
			// 完成学习
			// TODO
			JOptionPane.showMessageDialog(null, "复习完成");
			dispose();
			return;
		}
		if (data.firstRecite) {
			// 第一次记忆
			text_info.setText(data.record.note);
			text_input.setVisible(true);
			text_input.setText(data.record.remember);
			label_info.setText("以下是记忆内容：");
			text_input.setEditable(false);// 输入框不可编辑

		} else {
			// 复习记忆
			text_info.setText(data.record.note);
			text_input.setVisible(true);
			text_input.setText("");// 清空文本域，准备回答问题或者给予答案
			if (data.needAsw) {
				// 要回答问题
				btn_know.setVisible(false);// 知道按钮隐藏
				label_info.setText("请在下面写出答案：");
				text_input.setEditable(true);// 输入框可编辑
			} else {
				// 不用回答，自测
				btn_know.setVisible(true);
				label_info.setText("点击下面出现答案：");
				text_input.setEditable(false);// 输入框不可编辑
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
