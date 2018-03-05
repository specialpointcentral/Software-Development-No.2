package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerMain extends JFrame {
	public JTextArea textArea;

	public ServerMain() {
		setTitle("\u670D\u52A1\u5668-\u8BB0\u5FC6\u5361\u7247");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 362, 232);
		setResizable(false);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 328, 171);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		setVisible(true);
		textArea.append("开始运行\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 9999;
		// 定义一个ServerSocket监听在端口9999上
		ServerMain m = new ServerMain();
		try {
			ServerSocket server = new ServerSocket(port);
			while (true) {
				// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
				Socket socket = server.accept();
				// 每接收到一个Socket就建立一个新的线程来处理它
				m.textArea.append("建立连接\n");
				new Thread(new Task(socket, m.textArea)).start();
			}
		} catch (Exception e) {
			m.textArea.append("发生错误\n");
			m.textArea.append(e.toString());
			e.printStackTrace();
		}
	}
}

/**
 * using to exchange the information
 * 
 * @author huqi1
 *
 */
class Task implements Runnable {
	private Socket socket;
	private JTextArea textArea;

	public Task(Socket socket, JTextArea textArea) {
		this.socket = socket;
		this.textArea = textArea;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DataPack data = new DataPack();
		textArea.append("获取读写操作\n");
		try {// first read info about file
			ObjectInputStream obin = new ObjectInputStream(socket.getInputStream());
			data = (DataPack) obin.readObject();
			System.out.println(data.fileName);
			System.out.println(data.type);
			if (data.type == 1) {
				textArea.append("读取操作\n");
				readFile(data.fileName);
			} else {
				textArea.append("写入操作\n");
				writeFile(data.fileName);
			}
			obin.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void writeFile(String name) throws Exception {
		// client to server
		// 创建网络接受流接受服务器文件数据
		InputStream netIn = socket.getInputStream();
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(netIn));
		textArea.append("文件" + name + "\n");
		File file = new File(name);
		if (!file.exists())
			file.createNewFile();// 文件不存在 创建
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(in.readObject());
		out.flush();
		
		//close all stream
		netIn.close();
		in.close();
		out.close();
		textArea.append("文件" + name + "操作完成\n");
	}

	public void readFile(String name) throws Exception {
		// server to client
		// maybe cannot find file
		OutputStream netOut = socket.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(netOut));
		textArea.append("文件" + name + "\n");
		File file = new File(name);
		if (!file.exists()) {// file not exit
			out.writeBoolean(false);// return false that make client use local file
			out.flush();
			textArea.append("文件不存在，返回false\n");
		} else {
			out.writeBoolean(true);
			out.flush();
			FileInputStream fos = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(fos));
			out.writeObject(in.readObject());
			out.flush();

			in.close();
			fos.close();
		}
		// close all stream
		netOut.close();
		out.close();
		textArea.append("文件" + name + "操作完成\n");
	}
}