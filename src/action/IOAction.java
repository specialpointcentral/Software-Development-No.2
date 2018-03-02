package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import classes.Card;

public class IOAction {
	public static ExecutorService exec = Executors.newCachedThreadPool();

	/**
	 * use to return the file list
	 * 
	 * @param f
	 *            input the dir
	 * @return the list of all card file in dir
	 */
	public static ArrayList<File> fileList(File f) {
		if (!f.exists()) {
			f.mkdirs();
			return fileList(f);
		}
		File[] fileList = f.listFiles();
		ArrayList<File> wjList = new ArrayList<File>();// 文件集合
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()) {// 判断是否为文件，并且符合要求
				String fileName = fileList[i].getName();
				if (!fileName.contains("."))// 没有发现有拓展名的文件直接跳过
					continue;
				String fileTyle = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());// 取出文件类型
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
	 * use to write a new Card object to the file
	 * 
	 * @param f
	 *            file dir
	 * @param C
	 *            Card
	 * @return file -- success, null -- do not success
	 */
	public static File writeNewCardFile(File f, Card C) {
		// 先判断是否重名
		if (f.exists()) {
			return writeNewCardFile(new File(f.getParent() + "\\" + rename(f.getName())), C);// 重命名后递归
		} else {
			try {
				FileOutputStream fo = new FileOutputStream(f);
				ObjectOutputStream oot = new ObjectOutputStream(fo);
				oot.writeObject(C);
				fo.close();
				oot.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return f;
		}
	}

	/**
	 * use to rename the file
	 * 
	 * @param name
	 */
	private static String rename(String name) {
		return name.substring(0, name.lastIndexOf(".")) + "_re" + ".crd";
	}

	/**
	 * write the file
	 * 
	 * @param f
	 * @param C
	 * @return
	 */
	public static boolean writeCardFile(File f, Card C) {
		try {
			Thread th = new WriteData(f, C);
			exec.submit(th);
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}

class WriteData extends Thread {
	private File f;
	private Card C;

	public WriteData(File f, Card C) {
		this.f = f;
		this.C = C;
		this.setUncaughtExceptionHandler(new ExceptionHandler());// unchecked exception
	}

	public void run() {
		try {
			FileOutputStream fo = new FileOutputStream(f);
			ObjectOutputStream oot = new ObjectOutputStream(fo);
			oot.writeObject(C);
			fo.close();
			oot.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class ExceptionHandler implements UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			System.err.printf("An exception has been captured\n");
			System.err.printf("Thread: %s\n", t.getId());
			System.err.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
			System.err.printf("Stack Trace: \n");
			e.printStackTrace(System.err);
			System.err.printf("Thread status: %s\n", t.getState());
		}
	}
}
