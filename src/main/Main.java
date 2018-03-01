package main;
import javax.swing.JFrame;

import classes.CardList;
import ui.MainUI;
public class Main {
	public static CardList Clist;
	public static JFrame f;//Ö÷´°Ìå

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clist=new CardList();
		f=new MainUI();
		f.setVisible(true);
		f.setLocationRelativeTo(null);

	}

}
