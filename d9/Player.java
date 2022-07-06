package d9;
import java.util.Random;
import java.util.Scanner;

class Player{
	void playerPut() {
		System.out.println("------------エラー-----------");
	}
	int []playerPut(Board b) {
		System.out.println("------------エラー-----------");
		int  i[] = {-1,-1};
		return i;
	}
	int illegalPut() {
		System.out.println("------------エラー-----------");
		return 0;
	}
}

class HumanPlayer extends Player{
	@SuppressWarnings("resource")
	Scanner scan = new Scanner(System.in);
	void playerPut() {
		System.out.println("置くことができません パスします");
		System.out.print("何か数字を入力してください");
		int i = scan.nextInt();
	    System.out.println("");
	}
	int []playerPut(Board b) {
		b.show();
		return think(b);
	}
	private  int []think(Board b) {
		System.out.print("rを入力してください : ");
		int r = scan.nextInt();
		System.out.print("cを入力してください : ");
		int c = scan.nextInt();
		int i []= {r,c};
		return i;
	}
	int illegalPut() {
		System.out.println("そこに置くことができません");
		return 0;
	}
}

class ComPlayer extends Player{
	//*最も多く返せるところに置く
	void playerPut() {
		System.out.println("------------パス-----------------");
	}
	int []playerPut(Board b) {
		b.show();
		return think(b);
	}
	private  int []think(Board b) {
		int sum;
		int max = -2147483648;
		int r,c;
		//念のため初期化
		r = 0;
		c = 0;
		for(int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				if(b.emptySpace(i,j) && b.sTurn(i,j) != 0) {
					sum = evaluation(b,i,j);
					if (sum > max) {
						max = sum;
						r = i;
						c = j;
					}if(sum == max) {
						Random random = new Random();
						if(random.nextInt(2) == 0) {
							max = sum;
							r = i;
							c = j;
						}
					}
				}
			}
		}
		int i []= {r,c};
		return i;
	}
	int illegalPut() {
		System.out.println("エラーが発生しました");
		System.out.println("エラー:Comの非合法着手");
		return 1;
	}
	int evaluation(Board b,int i,int j) {
		return b.sTurn(i,j);
	}
}
class RandomComPlayer extends ComPlayer{
	int evaluation(Board b,int i,int j) {
		Random random = new Random();
		return random.nextInt(20000);
	}
}
class InvisibleRandomComPlayer extends RandomComPlayer{
	//******************************************************//
	//********ベンチマーク時のパス出力を防ぐ****************//
	void playerPut() {
	}
}
class GeneticPlayer extends ComPlayer{
	private GeneticField GenFi;
	private final int DEPTHMAX = 3;
	private final int EVAMIN = -2147483648;
	private GeneticPlayer() {

	}
	GeneticPlayer(GeneticField GenFi){
		this.GenFi = GenFi;
	}
	int evaluation(Board b,int i,int j) {
		return evaluation(b,i,j,0,0);
	}
	int evaluation(Board b,int i,int j,int depth,int end) {
		Board a = new Board();
		b.copy(a);
		a.put(i,j);
		a.turnend();
		depth++;
		if (end == 2) {
			int sum = 0;
			for(int k = 0;k < 8;k++) {
				for(int l = 0;l < 8;l++) {
					sum = (a.getBoard(k,l) * GenFi.getEva(i,j)) / 100 + sum;
				}
			}
			return sum;
		}
		if(a.allSTurn() == 0) {
			a.turnend();
			end++;
			return -evaluation(a,i,j,depth,end);
		}else {
			end = 0;
		}
		if (depth >= DEPTHMAX) {
			int sum = 0;
			for(int k = 0;k < 8;k++) {
				for(int l = 0;l < 8;l++) {
					sum = a.getBoard(k,l) *(GenFi.getEva(i,j) - 10000) / 100 + sum;
				}
			}
			return sum;
		}
		int max = EVAMIN;
		int thisPutScore;
		for(int k = 0;k < 8;k++) {
			for(int l = 0;l < 8;l++) {
				if(a.emptySpace(k,l) && a.sTurn(k,l) != 0) {
					thisPutScore = -evaluation(a,k,l,depth,end);
					if(thisPutScore > max){
						max = thisPutScore;
					}
				}
			}
		}
		return max;
	}
}

class InvisibleGeneticPlayer extends GeneticPlayer{
	//******************************************************//
	//********学習時のパス出力を防ぐ************************//
	void playerPut() {
	}
	InvisibleGeneticPlayer(GeneticField GenFi){
		super(GenFi);
	}
}