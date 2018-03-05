package ui;

import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import action.DetailAction;
import classes.Card;
import classes.Record;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Detail extends JPanel {
	private JList<Card> list_card;
	private JButton btn_review;
	private JButton btn_study;
	private JLabel label_review;
	private JLabel label_study;
	private JList<Record> list_content;
	private JTextArea txt_info;

	public Detail() {
		setSize(950, 550);
		setLayout(new BorderLayout(0, 0));

		JPanel mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(200, 200, 200)), " \u8BB0\u5FC6\u4E0E\u590D\u4E60 ",
				TitledBorder.LEADING, TitledBorder.TOP, new Font("微软雅黑", Font.PLAIN, 15), new Color(0, 0, 0)));
		panel.setBounds(665, 13, 271, 252);
		mainPanel.add(panel);
		panel.setLayout(null);

		label_study = new JLabel("\u6B64\u5361\u7247\u8FD8\u672A\u8FDB\u884C\u5B66\u4E60/\u91CD\u7F6E");
		label_study.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_study.setBounds(14, 36, 243, 29);
		panel.add(label_study);

		btn_study = new JButton("\u73B0\u5728\u5F00\u59CB\u5B66\u4E60");
		btn_study.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Card C = list_card.getSelectedValue();
				if (C == null) {
					JOptionPane.showMessageDialog(null, "未选中记忆卡片！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if (C.getStartorNot()) {
					// 已开始学习，动作为重置
					if (JOptionPane.showConfirmDialog(null, "是否重置卡片，所有学习内容将会被初始化？", "警告", JOptionPane.WARNING_MESSAGE,
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
						if (DetailAction.act_reset(C)) {// 成功重置
							list_card.clearSelection();// 清除列表选择
							JOptionPane.showMessageDialog(null, "卡片重置成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
						}
				} else {
					// 未开始学习，动作为开始学习
					refreshUI();
					DetailAction.act_start(C);
				}

			}
		});
		btn_study.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btn_study.setBounds(109, 78, 148, 38);
		panel.add(btn_study);

		label_review = new JLabel("\u4E0B\u6B21\u590D\u4E60\u65F6\u95F4");
		label_review.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		label_review.setBounds(14, 146, 243, 29);
		panel.add(label_review);

		btn_review = new JButton("\u6682\u65F6\u4E0D\u7528\u590D\u4E60");
		btn_review.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card C = list_card.getSelectedValue();
				if (C == null)
					JOptionPane.showMessageDialog(null, "未选中记忆卡片！", "提示", JOptionPane.INFORMATION_MESSAGE);
				else {
					refreshUI();//设置未选中列表状态
					DetailAction.act_review(C);//启动复习
				}
					
			}
		});
		btn_review.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btn_review.setBounds(109, 188, 148, 38);
		panel.add(btn_review);

		txt_info = new JTextArea();
		txt_info.setEditable(false);
		txt_info.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		txt_info.setText(
				"\u6B64\u5361\u7247\u7684\u8BE6\u7EC6\u6570\u636E\uFF1A\r\n\u603B\u8BCD\u6761\u6570\uFF1A\r\n\u5361\u7247\u7C7B\u578B\uFF1A\r\n\u8BED\u8A00\uFF1A\r\n\u662F\u5426\u5F00\u59CB\u5B66\u4E60\uFF1A\r\n\u4E0B\u6B21\u590D\u4E60\u65F6\u95F4\uFF1A\r\n\u4E0B\u6B21\u590D\u4E60\u8BCD\u6761\u6570\uFF1A");
		txt_info.setBounds(665, 278, 271, 259);
		txt_info.setBorder(new LineBorder(Color.LIGHT_GRAY));
		mainPanel.add(txt_info);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(277, 13, 374, 524);
		mainPanel.add(scrollPane);

		list_content = new JList<Record>();
		list_content.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		scrollPane.setViewportView(list_content);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(14, 13, 249, 524);
		mainPanel.add(scrollPane_1);

		list_card = new JList<Card>();
		list_card.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		list_card.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_card.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && list_card.getSelectedValue() != null) {// 确保不是空值并且值已经改变，减少读取次数
					DetailAction.showRecord(list_content, list_card.getSelectedValue());
					txt_info.setText(DetailAction.getDetailInfo(list_card.getSelectedValue()));
					//先判断卡片内容是否为空
					if(list_card.getSelectedValue().records.size()==0) {
						//空，按钮发生相应变化
						label_study.setText("此卡片还没有任何记忆内容");
						btn_study.setText("现在开始学习");
						btn_study.setEnabled(false);
					}else
					// 开始学习了吗？
					if (list_card.getSelectedValue().getStartorNot() && !list_card.getSelectedValue().getAllisOver()) {
						// 开始，并且没有学完
						label_study.setText("将此卡片内容重置");
						btn_study.setText("重置此卡片");
						btn_study.setEnabled(true);
					} else if (list_card.getSelectedValue().getStartorNot()
							&& list_card.getSelectedValue().getAllisOver()) {
						// 学完了
						label_study.setText("此卡片已完成学习，你可以：");
						btn_study.setText("重置此卡片");
						btn_study.setEnabled(true);
					} else {
						// 未开始
						label_study.setText("此卡片现在还未开始学习");
						btn_study.setText("现在开始学习");
						btn_study.setEnabled(true);
					}
					// 是否有复习
					ArrayList<Object> list = DetailAction
							.getNextReviewInfo((ArrayList<Record>) list_card.getSelectedValue().getRecordList());
					SimpleDateFormat Fmt = new SimpleDateFormat("yyyy/MM/dd");
					if (list.get(0).equals(Fmt.format(new Date()))) {
						// 今天有复习任务
						btn_review.setEnabled(true);
						btn_review.setText("开始复习");
						label_review.setText("今天有复习任务");
					} else if (list_card.getSelectedValue() != null && list_card.getSelectedValue().start
							&& !list_card.getSelectedValue().getAllisStart()) {
						// 有未学习的，判断已经开始学习的卡片有无出现下次复习时间为null的
						btn_review.setEnabled(true);
						btn_review.setText("记忆未学习条目");
						label_review.setText("此卡片今天没有复习任务");
					} else {
						btn_review.setEnabled(false);
						btn_review.setText("没有复习任务");
						label_review.setText("此卡片今天没有复习任务");
					}
				} else if (list_card.getSelectedValue() == null) {// 空值
					DetailAction.showRecord(list_content, null);// 展示数据
					txt_info.setText("此卡片的详细数据：\n您还未选中卡片！");
					label_study.setText("现在还未开始学习");
					btn_review.setEnabled(false);
					label_review.setText("现在没有复习计划");
					btn_study.setEnabled(false);
				}
			}
		});
		scrollPane_1.setViewportView(list_card);
		// 初开页面进行初始化
		label_study.setText("现在还未开始学习");
		btn_review.setEnabled(false);
		btn_study.setEnabled(false);
		new DetailBeginUI(list_card, txt_info);
	}

	/**
	 * refreshUI
	 */
	public void refreshUI() {
		list_card.updateUI();
		list_content.updateUI();
		list_card.clearSelection();
		new DetailBeginUI(list_card, txt_info);
	}
}

/**
 * this class is used to show the UI
 * 
 * @author huqi1
 *
 */
class DetailBeginUI extends Thread {
	JList<Card> C;
	JTextArea txt_info;

	public DetailBeginUI(JList<Card> C, JTextArea txt_info) {
		this.C = C;
		this.txt_info = txt_info;
		this.start();
	}

	public void run() {
		txt_info.setText("此卡片的详细数据：\n您还未选中卡片！");

		DetailAction.showCardList(C);
	}
}