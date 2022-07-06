package d9;

public class Board {
	private int [][]p = new int [8][8];
	private int [][]o = new int [8][8];
	private boolean state;


	Board(){
		state = true;
	}
	// searchturn系 自分の手番1,相手の手番-1 [s]
	// 与えられたボード反転可能数 [s]
	int allSTurn() {
		int turnsum = 0;
		int k;//反転可能かの一時保存用
		for (int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				if(emptySpace(i,j)) {
					k = sTurn(i,j);
					turnsum = k + turnsum;
				}
			}
		}
		return turnsum;
	}
	// 与えられた手場所が返せるかどうか [s] ver.d7よりcom用にprivateからアクセス修飾子なしに変更
	int sTurn(int putr,int putc) {
		int turnsum = 0;
		if (o[putr][putc] != 0 && p[putr][putc] != 0) {
			return 0;
		}
		for (int i = -1;i <= 1;i++) {
			for (int j = -1;j <= 1;j++) {/*
				//turnsum = dirsturn(putr,putc,i,j) + turnsum;
				if (dirSTurn(putr,putc,i,j) != 0) {
					turnsum = 1;
				}*/
				turnsum = dirSTurn(putr,putc,i,j) + turnsum;
			}
		}
		return turnsum;
	}
	// 方向に対して返せる数 [s][t]
	private int dirSTurn(int putr,int putc,int r,int c) {
		if (r == 0 && c == 0) {
			return 0;
		}
		if (o[putr][putc] != 0 && p[putr][putc] != 0) {
				return 0;
		}
		int turnsum = 0;
		int sr = putr + r;
		int sc = putc + c;
		if (state) {
			while(sr < 8 && sr >= 0&& sc <8 && sc >= 0) {
				if(p[sr][sc] == 1) {
					return turnsum;
				}else if(o[sr][sc] == 1) {
					turnsum++;
				}else {
					return 0;
				}
				sr = sr + r;
				sc = sc + c;
			}
		}else{
			while(sr < 8 && sr >= 0&& sc <8 && sc >= 0) {
				if(o[sr][sc] == 1) {
					return turnsum;
				}else if(p[sr][sc] == 1) {
					turnsum++;
				}else {
					return 0;
				}
				sr = sr + r;
				sc = sc + c;
			}
		}
		return 0;
	}

	//*-------------------------ここまで反転判定-------------------------------------------------------------*//
	//*------------------------------------------------------------------------------------------------------*//
	//*-------------------------ここから反転処理-------------------------------------------------------------*//
	// 方向別反転
	private void dTurn(int putr,int putc,int r,int c,int sum) {
		if (r == 0 && c == 0 || sum == 0) {
		}else {
			int sr = putr + r;
			int sc = putc + c;
			if (state) {
				while(sr < 8 && sr >= 0&& sc <8 && sc >= 0 && sum != 0) {
					if (o[sr][sc] == 1) {
						p[sr][sc] = 1;
						o[sr][sc] = 0;
					}else {
						sum = 0;
					}
					sr = sr + r;
					sc = sc + c;
				}
			}else {
				while(sr < 8 && sr >= 0&& sc <8 && sc >= 0 && sum != 0) {
					if (p[sr][sc] == 1) {
						o[sr][sc] = 1;
						p[sr][sc] = 0;
					}else {
						sum = 0;
					}
					sr = sr + r;
					sc = sc + c;
				}
			}
		}
	}

	// 反転処理用
	private int turn(int putr,int putc) {
		int k,l = 0;//一時保存
		k = 0;
		for (int i = -1;i <= 1;i++) {
			for (int j = -1;j <= 1;j++) {
				k = dirSTurn(putr,putc,i,j);
				dTurn(putr,putc,i,j,k);
				l = l + k;
			}
		}
		return l;
	}
	/*
	private int turn(int putr,int putc,boolean teban) {
		int k,l = 0;//一時保存
		k = 0;
		for (int i = 0;i < 8;i++) {
			for (int j = 0;j < 8;j++) {
				k = dirSTurn(putr,putc,i,j,teban);
				dTurn(putr,putc,i,j,k,teban);
				l = l + k;
			}
		}
		return l;
	}
	*/
	//置く処理
	int put(int r,int c) {
		int i;//一時保存
		if (p[r][c] == 0 && o[r][c] == 0) {
			i = turn(r,c);
			if (i != 0) {
				if(state) {
					p[r][c] = 1;
				}else {
					o[r][c] = 1;
				}
			}
			return i;
		}else {
			return -1;
		}
	}
	//*------------------------------------------------------------------------------------------------------------*//
	//*--------------------------その他----------------------------------------------------------------------------*//
	// 8×8初期化
	void boardreset() {
		for (int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				o[i][j] = 0;
				p[i][j] = 0;
			}
		}
		p[3][4] = 1;
		p[4][3] = 1;
		o[3][3] = 1;
		o[4][4] = 1;
	}
	// aをbにコピー
	void copy(Board b) {
		for (int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				b.p[i][j] = this.p[i][j];
				b.o[i][j] = this.o[i][j];
			}
		}
		b.state = this.state;
	}
	//ボード表示
	void show() {
		System.out.println("------------------------");
		if(state) {
			System.out.print("●,");
		}else {
			System.out.print("〇,");
		}
		for (int i = 0;i < 8;i++) {
			System.out.print(i + " ,");
		}
		System.out.println();
		for (int i = 0;i < 8;i++) {
			System.out.print(i + ",");
			for(int j = 0;j < 8;j++) {
				if(p[i][j] == 0 && o[i][j] == 0) {
					System.out.print("  ,");
				}else if(p[i][j] == 0 && o[i][j] == 1) {
					System.out.print("〇,");
				}else if(p[i][j] == 1 && o[i][j] == 0) {
					System.out.print("●,");
				}else if(p[i][j] == 1 && o[i][j] == 1) {
					System.out.print("ER,");
				}
			}
			System.out.println("");
		}
	}
	int score() {
		int sum = 0;
		for(int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				if(p[i][j] == 1) {
					sum++;
				}else if (o[i][j] == 1) {
					sum--;
				}
			}
		}
		return sum;
	}

	void turnend() {
		state = !state;
	}
	void setState() {
		state = true;
	}
	boolean getState() {
		return state;
	}
	boolean emptySpace(int i,int j) {
		if(p[i][j] == 0&&o[i][j] == 0) {
			return true;
		}else {
			return false;
		}
	}
	int getBoard(int r,int c) {
		if((state && p[r][c]== 1)||(o[r][c] == 1 && state)) {
			return 1;//自分の石
		}else if((state && p[r][c]== 1)||(o[r][c] == 1 && state)) {
			return -1;
		}
		return 0;
	}
}

class InvisibleShowBoard extends Board{
	InvisibleShowBoard(){
		super();
	}
	void show() {

	}
}
