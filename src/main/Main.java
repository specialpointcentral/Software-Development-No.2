package main;
import classes.CardList;
import ui.MainUI;
public class Main {
	public static CardList Clist;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clist=new CardList();
		new MainUI().setVisible(true);
		

	}

}
