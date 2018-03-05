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
		textArea.append("��ʼ����\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 9999;
		// ����һ��ServerSocket�����ڶ˿�9999��
		ServerMain m = new ServerMain();
		try {
			ServerSocket server = new ServerSocket(port);
			while (true) {
				// server���Խ�������Socket����������server��accept����������ʽ��
				Socket socket = server.accept();
				// ÿ���յ�һ��Socket�ͽ���һ���µ��߳���������
				m.textArea.append("��������\n");
				new Thread(new Task(socket, m.textArea)).start();
			}
		} catch (Exception e) {
			m.textArea.append("��������\n");
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
		textArea.append("��ȡ��д����\n");
		try {// first read info about file
			ObjectInputStream obin = new ObjectInputStream(socket.getInputStream());
			data = (DataPack) obin.readObject();
			System.out.println(data.fileName);
			System.out.println(data.type);
			if (data.type == 1) {
				textArea.append("��ȡ����\n");
				readFile(data.fileName);
			} else {
				textArea.append("д�����\n");
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
		// ����������������ܷ������ļ�����
		InputStream netIn = socket.getInputStream();
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(netIn));
		textArea.append("�ļ�" + name + "\n");
		File file = new File(name);
		if (!file.exists())
			file.createNewFile();// �ļ������� ����
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(in.readObject());
		out.flush();
		
		//close all stream
		netIn.close();
		in.close();
		out.close();
		textArea.append("�ļ�" + name + "�������\n");
	}

	public void readFile(String name) throws Exception {
		// server to client
		// maybe cannot find file
		OutputStream netOut = socket.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(netOut));
		textArea.append("�ļ�" + name + "\n");
		File file = new File(name);
		if (!file.exists()) {// file not exit
			out.writeBoolean(false);// return false that make client use local file
			out.flush();
			textArea.append("�ļ������ڣ�����false\n");
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
		textArea.append("�ļ�" + name + "�������\n");
	}
}