package classes;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import action.IOAction;
import action.LastWorks;

public class CardList {
	private ArrayList<Card> Clist;
	private ArrayList<File> flist;
	public String path="D:/test/";

	public CardList() {
		// read list
		flist=new ArrayList<File>();
		Clist = _getList();
	}

	/**
	 * get card list
	 * 
	 * @return ArrayList<Card>
	 */
	private ArrayList<Card> _getList() {
		// 扫描文件夹下面的所有文件，找到拓展名相同的文件
		// 每一个文件就是一个卡片，全部读取
		ArrayList<File> Flist = IOAction.fileList(new File(path));
		ArrayList<Card> Clist = new ArrayList<Card>();
		Card C = null;
		for (int i = 0; i < Flist.size(); i++) {
			C = IOAction.readCardFile(Flist.get(i));
			if (C == null)
				continue;
			else {
				//把卡片和卡片路径存储
				Clist.add(C);
				_addFilePath(Flist.get(i));
			}
		}
		return Clist;

//		 TODO only a test
//		 ArrayList<Card> test = new ArrayList<Card>();
//		 Card C = new Card(1, "中文(zh-cn)", "测试");
//		 C.addRecord(new Record("Remember", "Remember"));
//		 C.start=true;
//		 Record record=new Record("remember2", "remember2");
//		 record.nextTime=new Date();
//		 C.addRecord(record);
//		 Record record2=new Record("remember3", "remember3");
//		 record2.nextTime=new Date();
//		 C.addRecord(record2);
//		 test.add(C);
//		 Card C1 = new Card(3, "中文(zh-cn)", "测试");
//		 C1.addRecord(new Record("Remember", "note"));
//		 C1.start=true;
//		 Record record3=new Record("remember2", "note");
//		 record3.nextTime=new Date();
//		 C1.addRecord(record3);
//		 Record record4=new Record("remember3", "note");
//		 record4.nextTime=new Date();
//		 C1.addRecord(record4);
//		 test.add(C1);
//
//		 return test;
		
	}

	/**
	 * use to return saved list
	 * 
	 * @return ArrayList<Card>
	 */
	public ArrayList<Card> getList() {
		return Clist;
	}

	/**
	 * use to create new card
	 * 
	 * @param C
	 *            Card
	 * @return true -- create success, false -- create do not success
	 */
	public boolean addCard(Card C) {
		File f=IOAction.writeNewCardFile(new File(path+C.card+".crd"), C);
		if(f==null) return false;
		else {
			Clist.add(C);
			_addFilePath(f);
			return true;
		}
	}
	/**
	 * delete the card then delete the file
	 * @param C Card
	 * @return true - success, false - do not success
	 */
	public boolean deleteCard(Card C) {
		//取出位置
		int position=Clist.indexOf(C);
		//清空文件
		if(flist.get(position).exists()&&flist.get(position).delete()) {//文件存在且删除正确
			//删除数据
			Clist.remove(position);
		//退出序列
			flist.remove(position);
			return true;
		}else return false;

	}

	/**
	 * use to save all cards
	 */
	public void saveCard() {
		for(int i=0;i<Clist.size();i++) {
			IOAction.writeCardFile(flist.get(i), Clist.get(i));
		}
		Thread th=new LastWorks(IOAction.exec);
		try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * use to add file path
	 * 
	 * @param f
	 */
	private void _addFilePath(File f) {
		flist.add(f);
	}
}
