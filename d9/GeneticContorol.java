package d9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GeneticContorol {
	private static final int MENOFGEN = 20;
	private static final int LEAVE = 0;
	private static final int MUTATION = 2;//(%)
	private static final int NEWMENBER = 2;
	private static final boolean CROSSOVER = true;
	private static final boolean MODE = false;
	private static final int GENERATION = 100;
	private static final int CANCROSSOVERA = 5;
	private static final int CANCROSSOVERB = 20;
	private static final int VER1 = 0;
	private static final int VER2 = 1;
	private static final int VER3 = 0;
	private static final boolean TAKEOVER = false;//true時、前の実験条件を引き継ぐ
	private static final String OUTFILEDIR = "ファイル名";
	private static final String OUTFILENAME = "ファイル接頭辞";
	private static final String OUTEXTENSION  = "拡張子";
	private static final String FILENAME  = "インポートしたいファイル名";
	private static int gene;
	private static int ver1;
	private static int ver2;
	private static int ver3;
	private static int menOfGen;
	private static int leave;
	private static int mutation;//(%)
	private static int newMenber;
	private static boolean crossover = true;
	public static void main(String[] args) {
		File file = new File(FILENAME);
		long t1 = System.currentTimeMillis();
		GeneticField []GenFi = new GeneticField1[MENOFGEN];
		if(MODE) {
			in(GenFi,TAKEOVER,FILENAME);
			if(crossover) {
				gene++;
				GenFi = crossover(GenFi,gene);
			}
		}else {
			ver1 = VER1;
			ver2 = VER2;
			ver3 = VER3;
			menOfGen = MENOFGEN;
			leave = LEAVE;
			newMenber = NEWMENBER;
			mutation = MUTATION;
			gene = 0;
			for (int i = 0;i < MENOFGEN;i++) {
				GenFi[i] = new GeneticField1(gene,i);
				GenFi[i].rand(i);
			}
		}
		long t2 = System.currentTimeMillis();
		for(int i = 0;i < GENERATION;i++) {
			GenFi =play(GenFi,menOfGen);
			GenFi = crossover(GenFi,i);
			if(i % 100 == 0) {
				out(GenFi,menOfGen,OUTFILEDIR + OUTFILENAME + i + OUTEXTENSION);
				long t3 = System.currentTimeMillis();
				t2 = t3 - t2;
			    System.out.println("Generation :" + i + ", " + t2 + "[ms]");
			    t2 = t3;
			}
		}
		out(GenFi,menOfGen,OUTFILEDIR + OUTFILENAME + GENERATION + "FINAL" + OUTEXTENSION);
		t1 = System.currentTimeMillis() - t1;
	    System.out.println(t1 + "[ms]");
	}

	private static GeneticField [] play (GeneticField []GenFi,int menOfGen) {
		int thisScore;
		for (int i = 0;i < menOfGen;i++) {
			GenFi[i].setScore(0);
		}
		for (int i = 0;i < menOfGen;i++) {
			for (int j = 0;j < menOfGen;j++) {
				if(i != j) {
					Player bl = new InvisibleGeneticPlayer(GenFi[i]);
					Player wh = new InvisibleGeneticPlayer(GenFi[j]);
					Game game = new InvisibleGame();
					thisScore = game.playSet(bl,wh);
					if (thisScore == 999) {
					}else if(thisScore > 0) {
						GenFi[i].changeScore(1);
						GenFi[j].changeScore(-1);
					}else if(thisScore < 0) {
						GenFi[i].changeScore(-1);
						GenFi[j].changeScore(1);
					}
				}
			}
		}
		Arrays.sort(GenFi,0,menOfGen,Comparator.reverseOrder());
		return GenFi;
	}


	static int in(GeneticField GenFi[],boolean takeOver,String fileName) {
		try{
			File file = new File(fileName);
			@SuppressWarnings("resource")
			FileReader filereader = new FileReader(file);
			int ch;
			ch = filereader.read();
			ver1 = ch;
			if(ver1 == 0) {
				ch = filereader.read();
				ver2 = ch;
				if(ver2 == 1) {
					ch = filereader.read();
					ver3 = ch;
					if(ver3 == 0) {
						fileDecoder0_1_0(filereader,takeOver,GenFi);
					}
				}else {
					fileDecoder0_1_0(filereader,takeOver,GenFi);
				}
			}else{

			}
			return 0;
		}catch(FileNotFoundException e){
			System.out.println(e);
			return -1;
		}catch(IOException e){
			System.out.println(e);
			return -1;
		}
	}

	static GeneticField [] in(Boolean takeOver) {
		return in(takeOver,FILENAME);
	}
	static GeneticField [] in(Boolean takeOver,String fileName) {
		try{
			File file = new File(fileName);
			@SuppressWarnings("resource")
			FileReader filereader = new FileReader(file);
			int ch;
			GeneticField []GenFi = null;
			ch = filereader.read();
			ver1 = ch;
			if(ver1 == 0) {
				ch = filereader.read();
				ver2 = ch;
				if(ver2 == 1) {
					ch = filereader.read();
					ver3 = ch;
					if(ver3 == 0) {
						return fileDecoder0_1_0(filereader,takeOver,GenFi);
					}
				}else {
					return fileDecoder0_0(filereader,takeOver,GenFi);
				}
				return null;
			}else{
				return null;
			}
		}catch(FileNotFoundException e){
			System.out.println(e);
			return null;
		}catch(IOException e){
			System.out.println(e);
			return null;
		}
	}
	static GeneticField [] fileDecoder0_0(FileReader filereader,boolean takeOver,GeneticField [] GenFi) {
		int ch;
		try {
			ch = filereader.read();
			gene = ch;
			menOfGen = MENOFGEN;
			leave = LEAVE;
			mutation = MUTATION;
			newMenber = NEWMENBER;
			GenFi = new GeneticField[menOfGen];
			for(int k = 0;k < menOfGen;k++){
				GenFi[k] = new GeneticField(gene,k);
				for(int i = 0;i < 8;i++){
					for(int j = 0;j < 8;j++){
						ch = filereader.read();
						if(ch == -1) {
							break;
						}
						GenFi[k].setEva(i,j,ch);
					}
				}
			}
		}catch(FileNotFoundException e){
			System.out.println(e);
			return null;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
		return GenFi;
	}
	static GeneticField [] fileDecoder0_1_0(FileReader filereader,boolean takeOver,GeneticField [] GenFi) {
		int ch;
		try {
			ch = filereader.read();
			gene = ch;
			if(takeOver) {
				ch = filereader.read();
				menOfGen = MENOFGEN;
				ch = filereader.read();
				leave = LEAVE;
				ch = filereader.read();
				mutation = MUTATION;
				ch = filereader.read();
				newMenber = NEWMENBER;
			}else {
				ch = filereader.read();
				menOfGen = ch;
				ch = filereader.read();
				leave = ch;
				ch = filereader.read();
				mutation = ch;
				ch = filereader.read();
				newMenber = ch;
			}
			GenFi = new GeneticField1[menOfGen];
			for(int k = 0;k < menOfGen;k++){
				GenFi[k] = new GeneticField1(gene,k);
				for(int i = 0;i < 8;i++){
					for(int j = 0;j < 8;j++){
						ch = filereader.read();
						if(ch == -1) {
							break;
						}
						GenFi[k].setEva(i,j,ch);
					}
				}
			}
			return GenFi;
		}catch(FileNotFoundException e){
			System.out.println(e);
			return null;
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
	}
	static GeneticField [] crossover(GeneticField []GenFi,int gene) {
		GeneticField []GenFiCh = new GeneticField[menOfGen];
		for (int i = 0;i < leave;i++) {
			GenFiCh[i] = GenFi[i];
		}
		Random random = new Random();
		for (int i = leave;i < menOfGen - newMenber;i++) {
			int j = random.nextInt(CANCROSSOVERA);
			int k = j;
			while(k == j) {
				k = random.nextInt(CANCROSSOVERB);
			}
			GenFiCh[i] = new GeneticField(GenFi[j],GenFi[k],mutation,gene,i);
		}
		for (int i =  menOfGen - newMenber;i < menOfGen;i++) {
			GenFiCh[i] = new GeneticField(gene,i);
			GenFiCh[i].rand(i);
		}
		return GenFiCh;
	}

	private static int out(GeneticField GenFi[],int menOfGen,String fileName) {
		try{
			File file = new File(fileName);
			FileWriter filewriter = new FileWriter(file);
			filewriter.write(ver1);
			if(ver1 == 0) {
				if(ver2 == 1) {
					filewriter.write(ver2);
					if(ver3 == 0) {
						filewriter.write(ver3);
						filewriter.write(gene);
						filewriter.write(menOfGen);
						filewriter.write(leave);
						filewriter.write(mutation);
						filewriter.write(newMenber);
						for(int k = 0;k < menOfGen;k++){
							for(int i = 0;i < 8;i++){
								for(int j = 0;j < 8;j++){
									filewriter.write(GenFi[k].getEva(i,j));
								}
							}
						}
					}
				}else {
					filewriter.write(gene);
					for(int k = 0;k < menOfGen;k++){
						for(int i = 0;i < 8;i++){
							for(int j = 0;j < 8;j++){
								filewriter.write(GenFi[k].getEva(i,j));
							}
						}
					}

				}

				filewriter.close();
				return 0;
			}else{
				System.out.println("ファイルに書き込めません");
				return -1;
			}
		}catch(IOException e){
			System.out.println(e);
			return -1;
		}

	}
	static private  boolean checkBeforeWritefile(File file){
		if (file.exists()){
			if (file.isFile() && file.canWrite()){
				return true;
			}
		}
		return false;
	}
	static GeneticField st(String fileName){
		GeneticField GenFi[] = in(false,fileName);
		return GenFi[0];
	}
	static GeneticField st(){
		return st(FILENAME);
	}
}

