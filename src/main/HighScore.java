package main;

import java.io.RandomAccessFile;

public class HighScore {
	public static final int STRING_SIZE = 3;
	public static final int INT_SIZE = 4;
	public static final int FILE_SIZE = (STRING_SIZE*2) + INT_SIZE;
	
	private char[] name;
	private int score;
	
	public HighScore(){
		name = new char[STRING_SIZE];
		name[0] = 'a';
		name[1] = 'a';
		name[2] = 'a';
		score = 1;
	}
	
	public HighScore(char[] name, int score) throws CharException{
		if(name.length != STRING_SIZE)
			throw new CharException("Impropper char array length");
		this.name = name;
		this.score = score;
	}
	
	public class CharException extends Exception{
		private static final long serialVersionUID = 7233311605465327623L;

		public CharException(String s){
			super(s);
		}
	}
	
	public char[] getName(){
		return name;
	}
	
	public String getNameString(){
		String r = "";
		for(char t : name){
			r += t;
		}
		return r;
	}
	
	public void setName(char[] name) throws Exception{
		if(name.length != STRING_SIZE)
			throw new Exception("Impropper char array length");
		this.name = name;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public static void write(HighScore hs, int n, String file){
		try{
			RandomAccessFile f = new RandomAccessFile(file, "rw");
			char[] ch = hs.getName();
			f.seek(FILE_SIZE * n);
			for(int i = 0; i < STRING_SIZE; i++){
				f.writeChar(ch[i]);
			}
			f.writeInt(hs.getScore());
			f.close();
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static HighScore read(int n, String file){
		HighScore r = null;
		try{
			RandomAccessFile f = new RandomAccessFile(file, "r");
			f.seek(n * FILE_SIZE);
			char[] ch = new char[STRING_SIZE];
			for(int i = 0; i<STRING_SIZE; i++){
				ch[i] = f.readChar();
			}
			int s = f.readInt();
			r = new HighScore(ch, s);
			f.close();
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return r;
	}
}
