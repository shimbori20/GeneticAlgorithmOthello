package d9;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class AssistTool {
	public static void main (String[]  args) {
		makeGen0_1_0();
		//show0_1_0();
		//boardTest();
	}
	private static void show0_1_0() {
		GeneticField GenFi[] = GeneticContorol.in(false);
		try {
			for(int k = 0;k < GenFi.length;k++) {
			System.out.println("\n"+k+"----------------");
				for(int i = 0;i < 8;i++) {
					for(int j = 0;j < 8;j++) {
						System.out.print(GenFi[k].getEva(i,j) + " ,");
					}
					System.out.println();
				}
			}
		}catch(Exception e){
			System.out.println("エラー　AssistTool");
		}
	}
	@SuppressWarnings("resource")
	private static void makeGen0_1_0() {
		try {
			File file = new File("C:\\Users\\shinbori kazuki\\Documents\\makegen.dat");
			System.out.println("mekeGen: ver0.1.0");
			FileWriter filewriter = new FileWriter(file);
			Scanner scan = new Scanner(System.in);
			int ver;
			ver = 0;
			filewriter.write(ver);
			ver = 1;
			filewriter.write(ver);
			ver = 0;
			filewriter.write(ver);
			System.out.print("\ngen:");
			filewriter.write(scan.nextInt());
			System.out.print("\nmenOfGen:");
			filewriter.write(scan.nextInt());
			System.out.print("\nleave:");
			filewriter.write(scan.nextInt());
			System.out.print("\nmutation:");
			filewriter.write(scan.nextInt());
			System.out.print("\nnewMenber:");
			int newMenber = scan.nextInt();
			filewriter.write(newMenber);
			for(int k = 0;k < newMenber;k++) {
				for(int i = 0;i < 8;i++) {
					for(int j = 0;j < 8;j++) {
						System.out.print("\n[" + k +"]" + "eva "+ i + " ," + j +" : ");
						//filewriter.write(scan.nextInt());

						filewriter.write(mekeGen0_1_0_test1(i,j));
					}
				}
			}
			filewriter.close();
		}catch(Exception e){
			System.out.println("mekeGen: ver0.1.0 Error");
			System.out.println(e);
		}
	}
	private static int mekeGen0_1_0_test1(int i,int j) {
		int [][]a = {{68,-12,53,-8},{-12,-62,-33,-7},{53,-33,26,8},{-8,-7,8,18}};
		int k = testPos(i);
		int l = testPos(j);
		return  a[k][l] * 100 + 10000;
	}
	private static int testPos(int i) {
		switch(i){
		case 0:
		case 7:
			return 0;
		case 1:
		case 6:
			return 1;
		case 2:
		case 5:
			return 2;
		case 3:
		case 4:
			return 3;
		}
		return -1;
	}
	private static void boardTest() {
		Board b = new Board();
		b.boardreset();
		b.show();
		b.setState() ;
		System.out.println(b.sTurn(3, 2));
	}
}
