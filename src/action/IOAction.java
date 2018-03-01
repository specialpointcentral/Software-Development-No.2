package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import classes.Card;

public class IOAction {
	/**
	 * use to return the file list
	 * 
	 * @param f
	 *            input the dir
	 * @return the list of all card file in dir
	 */
	public static ArrayList<File> fileList(File f) {
		if(!f.exists()) {
			f.mkdirs();
			return fileList(f);
		}
		File[] fileList = f.listFiles();
		ArrayList<File> wjList = new ArrayList<File>();// 文件集合
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {// 判断是否为文件，并且符合要求
				String fileName = fileList[i].getName();
				if(!fileName.matches(".")) continue;
				String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				if (fileTyle.equals("crd"))
					wjList.add(fileList[i]);
			}
		}

		return wjList;
	}

	/**
	 * use to read Card Object from the file
	 * 
	 * @param f
	 *            file dir
	 * @return
	 */
	public static Card readCardFile(File f) {
		Card C = null;

		try {
			FileInputStream fio = new FileInputStream(f);
			ObjectInputStream oin = new ObjectInputStream(fio);
			C = (Card) oin.readObject();
			fio.close();
			oin.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return C;
	}

	/**
	 * use to write the Card object to the file
	 * 
	 * @param f
	 *            file dir
	 *            @param C Card
	 * @return true -- success false -- do not success
	 */
	public static boolean writeCardFile(File f,Card C) {
		//先判断是否重名
		if(f.exists()) {
			return writeCardFile(new File(f.getPath()+"/"+rename(f.getName())),C);//重命名后递归
		}else {
			try {
				FileOutputStream fo = new FileOutputStream(f);
				ObjectOutputStream oot = new ObjectOutputStream(fo);
				oot.writeObject(C);
				fo.close();
				oot.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	/**
	 * use to rename the file
	 * @param name
	 */
	private static String rename(String name) {
		return name.substring(0,name.lastIndexOf("."))+"_re"+".crd";
	}

}
