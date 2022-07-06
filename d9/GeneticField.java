package d9;
import java.util.Random;
public class GeneticField implements Comparable<GeneticField>{
	private int eva[][] = new int[8][8];
	private int generation;
	private int no;
	private int score;
	@SuppressWarnings("unused")
	private GeneticField () {

	}
	GeneticField (int gene,int no) {
		setGeneration(gene);
		this.setNo(no);
	}
	GeneticField(GeneticField a,GeneticField b,int per,int gene,int no) {
		Random random = new Random();
		for(int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				int k = random.nextInt(100 + per);
				if(k >= 100){
					setEva(i,j,random.nextInt(20000));
				}else if(k % 2 == 0) {
					setEva(i,j,a.getEva(i,j));
				}else {
					setEva(i,j,b.getEva(i,j));
				}
			}
		}
		setGeneration(gene);
		this.setNo(no);
	}
	void rand(int no) {
		for(int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				Random random = new Random();
				setEva(i,j, random.nextInt(20000));
				setGeneration(0);
				this.setNo(no);
			}
		}
	}
	public int compareTo(GeneticField a) {
		if(score > a.score) {
			return 1;
		}else if(score == a.score) {
			return 0;
		}else {
			return -1;
		}
	}
	void set(int [][]a) {
		for(int i = 0;i < 8;i++){
			for(int j = 0;j < 8;j++){
				setEva(i,j,a[i][j]);
			}
		}
	}
	void setEva(int i,int j,int k) {
		eva[i][j] = k;
	}
	int getEva(int i,int j) {
		return eva[i][j];
	}

	public int getGeneration() {
		return generation;
	}
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void changeScore(int c) {
		score = score + c;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
}
class GeneticField1 extends GeneticField{
	GeneticField1 (int gene,int no) {
		super(gene,no);
	}
	GeneticField1(GeneticField a, GeneticField b, int per, int gene, int no) {
		super(a, b, per, gene, no);
	}
	int getEva(int i,int j) {
		int re[] = evaDecoder1(i,j);
		return super.getEva(re[0],re[1]);
	}
	private int[] evaDecoder1(int i,int j) {
		i = evaDecoder2(i);
		j = evaDecoder2(j);
		if(i > j) {
			int swap = i;
			i = j;
			j = swap;
		}
		int[] re = {i,j};
		return re;
	}
	private int evaDecoder2(int i) {
		switch (i){
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
		default:
			return -1;
		}

	}
}
