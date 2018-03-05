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
		// ɨ���ļ�������������ļ����ҵ���չ����ͬ���ļ�
		// ÿһ���ļ�����һ����Ƭ��ȫ����ȡ
		ArrayList<File> Flist = IOAction.fileList(new File(path));
		ArrayList<Card> Clist = new ArrayList<Card>();
		Card C = null;
		for (int i = 0; i < Flist.size(); i++) {
			C = IOAction.readCardFile(Flist.get(i));
			if (C == null)
				continue;
			else {
				//�ѿ�Ƭ�Ϳ�Ƭ·���洢
				Clist.add(C);
				_addFilePath(Flist.get(i));
			}
		}
		return Clist;

//		 TODO only a test
//		 ArrayList<Card> test = new ArrayList<Card>();
//		 Card C = new Card(1, "����(zh-cn)", "����");
//		 C.addRecord(new Record("Remember", "Remember"));
//		 C.start=true;
//		 Record record=new Record("remember2", "remember2");
//		 record.nextTime=new Date();
//		 C.addRecord(record);
//		 Record record2=new Record("remember3", "remember3");
//		 record2.nextTime=new Date();
//		 C.addRecord(record2);
//		 test.add(C);
//		 Card C1 = new Card(3, "����(zh-cn)", "����");
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
		//ȡ��λ��
		int position=Clist.indexOf(C);
		//����ļ�
		if(flist.get(position).exists()&&flist.get(position).delete()) {//�ļ�������ɾ����ȷ
			//ɾ������
			Clist.remove(position);
		//�˳�����
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
