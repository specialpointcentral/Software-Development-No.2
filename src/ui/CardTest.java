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
	private JFrame thisFrame;// 当前窗口
	private boolean actEnter = false;// input相应回车
	private JPanel Jp;// 记录当前Panel
	private CardLayout cardLayout = new CardLayout();

	private ReciteData data;// 当前记忆的数据
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
		// ____（自测、单词填空、单词拼写）
		note = "本次选取" + actionClass.getTestNum() + "个记忆条目，全部采用" + actionClass.getType() + "方式进行。\r\n" + "\r\n"
				+ "测试结束后会给出较详细的测试结果";
		beginShow.setUIs(this, note);
		getContentPane().add(beginShow);

		getContentPane().add(Jp);
		Jp.setLayout(null);
		setTitle("正在测试-卡片 " + C.card);
		text_info = new JTextPane();
		text_info.setBackground(SystemColor.control);
		text_info.setEditable(false);
		text_info.setText("\u8FD9\u91CC\u662F\u63D0\u793A\u5185\u5BB9");
		text_info.setFont(new Font("微软雅黑", Font.PLAIN, 56));
		text_info.setBounds(14, 46, 1104, 166);
		Jp.add(text_info);

		text_input = new JTextPane();
		text_input.setBackground(SystemColor.control);
		text_input.setFont(new Font("微软雅黑", Font.PLAIN, 58));
		text_input.setText("\u5185\u5BB9");
		text_input.setBounds(203, 355, 721, 97);
		text_input.setBorder(new MatteBorder(0, 0, 2, 0, (Color) Color.GRAY));
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
				if (actEnter && e.getKeyCode() == KeyEvent.VK_ENTER && data.needAsw) {
					// 按下回车，并且需要回答问题，才进行相应
					e.consume();// 不执行enter
					actEnter = false;// 锁定enter
					if (data.rightAsw.trim().equalsIgnoreCase(text_input.getText().trim())) {
						// 回答正确
						actionClass.act_know(data.record);
						text_input.setForeground(Color.GREEN);
						text_input.setEditable(false);// 输入框不可编辑
						// 禁用按钮
						btn_know.setEnabled(false);
						btn_donnotKnow.setEnabled(false);

						new _setUIClass(1000);
					} else {
						// 回答错误
						actionClass.act_donotKnow(data.record);

						label_info.setText("正确答案应该是：");
						text_input.setText(data.rightAsw);
						text_input.setEditable(false);// 输入框不可编辑
						// 禁用按钮
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
					label_info.setText("正确答案应该是：");
					// TODO 文字大小
					text_input.setText(data.rightAsw);
				} else {
					label_info.setText("记忆内容如下：");
					// TODO 文字大小
					text_input.setText(data.record.remember);
				}
				// 禁用按钮
				text_input.setEditable(false);// 输入框不可编辑
				btn_know.setEnabled(false);
				btn_donnotKnow.setEnabled(false);

				new _setUIClass(3000);
			}
		});
		btn_donnotKnow.setFont(new Font("微软雅黑", Font.PLAIN, 45));
		btn_donnotKnow.setBounds(693, 581, 231, 72);
		Jp.add(btn_donnotKnow);

		btn_know = new JButton("\u77E5\u9053");
		btn_know.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClass.act_know(data.record);

				_setUI();
			}
		});
		btn_know.setFont(new Font("微软雅黑", Font.PLAIN, 45));
		btn_know.setBounds(203, 581, 231, 72);
		Jp.add(btn_know);

		label_info = new JLabel("\u8BF7\u5728\u4E0B\u9762\u586B\u5199\u4E0A\u8FF0\u7B54\u6848\uFF1A");
		label_info.setForeground(Color.GRAY);
		label_info.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label_info.setBounds(39, 309, 212, 24);
		Jp.add(label_info);
		// 监听关闭事件
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 关闭窗口前提示
				if (JOptionPane.showConfirmDialog(thisFrame, "此次测试还未完成，是否退出？", "警告", JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
					dispose();
				} else
					return;
			}

		});

		endReport = new TestEndReport(this);
		getContentPane().add(endReport);

		// 最开始显示提示界面，再是测试，再是报告

		_setUI();// 第一次显示

	}

	private void _setUI() {
		data = actionClass.getReciteData();
		// 恢复按钮和输入框状态
		text_input.setForeground(Color.black);
		text_input.setEditable(true);
		btn_know.setEnabled(true);
		btn_donnotKnow.setEnabled(true);

		if (data == null) {
			// 完成学习
			// TODO 加入统计界面
			// JOptionPane.showMessageDialog(null, "测试完成");
			endReport.actionUI(actionClass.getList());//加载信息
			nextCard();
			return;
		}

		// 进行测试
		text_info.setText(data.record.note);
		// TODO 文字大小
		text_input.setVisible(true);
		text_input.setText("");// 清空文本域，准备回答问题或者给予答案
		if (data.needAsw) {
			// 要回答问题
			actEnter = true;// 启动enter
			btn_know.setVisible(false);// 知道按钮隐藏
			label_info.setText("请在下面写出答案：");
			text_input.setEditable(true);// 输入框可编辑
			// 如果是语句
			if (data.testNote != null) {// 需要提示数据
				// TODO 文字大小
				text_info.setText(text_info.getText() + "\n" + data.testNote);
			}
		} else {
			// 不用回答，自测
			btn_know.setVisible(true);
			label_info.setText("点击下面出现答案：");
			// TODO 文字大小
			text_input.setEditable(false);// 输入框不可编辑
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
