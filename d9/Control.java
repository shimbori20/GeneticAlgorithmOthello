package d9;

import java.util.Scanner;

public class Control {
	public static void main (String[]  args) {
	    @SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
	    System.out.print("黒番を入力してください Human:0 Com:1 R:2 : ");
	    int i = scan.nextInt();
	    System.out.print("白番を入力してください Human:0 Com:1 R:2 : ");
	    int j = scan.nextInt();
	    Player bl;
	    Player wh;
	    bl = playerSet(i);
	    wh = playerSet(j);
	    Game game = new Game();
	    int score;
		long t1 = System.currentTimeMillis();
		score = game.playSet(bl,wh);
		t1 = System.currentTimeMillis() - t1;
	    System.out.println("スコアは" + score);
	    System.out.println(t1 + "[ms]");
	}
	static Player playerSet(int i) {
		Player a;
	    switch (i){
	    case 0:
	    	a = new HumanPlayer();
	    	break;
	    case 1:
	    	a = new ComPlayer();
	    	break;
	    case 2:
	    	a = new RandomComPlayer();
	    	break;
	    case 3:
	    	a = new InvisibleRandomComPlayer();
	    	break;
	    case 4:
	    	a = new GeneticPlayer(GeneticContorol.st());
	    	break;
	    case 5:
	    	a = new InvisibleGeneticPlayer(GeneticContorol.st());
	    	break;
	    case 6:
	    	a = new GeneticPlayer(GeneticContorol.st("C:\\Users\\shinbori kazuki\\Documents\\makegen.dat"));
	    	break;
	    default:
	    	System.out.println("default");
	    	a = new Player();
	    }
	    return a;
	}
}
