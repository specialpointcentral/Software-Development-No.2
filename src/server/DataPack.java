package server;

import java.io.Serializable;

public class DataPack implements Serializable{
	public int type;//1-read 2-write
	public String fileName;

}
