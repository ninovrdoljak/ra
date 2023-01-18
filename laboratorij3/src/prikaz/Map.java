package prikaz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Map {

	private int[][] mapa;
	private String putanja;

	public int maxStupacMape;
	public int maxRedakMape;

	public Map(String name) {
		super();
		this.putanja = name;
		this.mapa = loadajMapu(name);
	}

	public String readFromJARFile(String filename) throws IOException
	{
		InputStream is = getClass().getResourceAsStream(filename);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) 
		{
			sb.append(line);
			sb.append(" ");
		}
		br.close();
		isr.close();
		is.close();
		return sb.toString();
	}

	private int[][] loadajMapu(String name) {
		this.maxStupacMape = Integer.parseInt(name.substring(0, name.indexOf("x")));
		this.maxRedakMape = Integer.parseInt(name.substring(name.indexOf("x")+1, name.indexOf("_")));

		int[][] mapa = new int[maxStupacMape][maxRedakMape];
		try {
			String mapaStr = readFromJARFile(name);
			//System.out.println(mapaStr);
			String[] poljeStr = mapaStr.split("\\s+");
			//for (String s : poljeStr) System.out.println(s);
			
			for (int i = 0; i < maxRedakMape; i++) {
				for (int j = 0; j < maxStupacMape; j++) {
					
					mapa[i][j] = Integer.parseInt(poljeStr[maxStupacMape * j + i]);
					
				}
			}
			
			/*
			for (int i = 0; i < maxRedakMape; i++) {
				String tmp = "";
				for (int j = 0; j < maxStupacMape; j++) {
					tmp += mapa[i][j] + " ";
				}
				System.out.println(tmp);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapa;
	}

	public int[][] getMap() {
		return mapa;
	}

	public void setMap(int[][] mapa) {
		this.mapa = mapa;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}



}
