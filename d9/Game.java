package d9;

public class Game {
	public  Board b = new Board();


	public int playSet(Player bl,Player wh) {
		b = new Board();
		return play(bl,wh);
	}
	public int play(Player bl,Player wh) {
		int end;// 連続パス数2で試合終了
		// 初期設定
		b.boardreset();
		b.setState();
		// 指し手保存用

		//ゲーム開始
		end = 0;//パス回数0
		boolean pass;//パス警告するか
		while (end == 0 ||end == 1) {
			pass = false;
			if (b.allSTurn() == 0) {
				pass = true;
			}
			if(b.getState()) {
				if (pass) {
					end = passte(bl,b,end);
				}else {
					end = te(bl,b);
				}
			}else {
				if(pass) {
					end = passte(wh,b,end);
				}else {
					end = te(wh,b);
				}
			}
		}
		System.out.println("------------------------");
		System.out.println("--------試合終了--------");
		System.out.println("------------------------");
		b.show();
		//終了処理
		if(end == 2) {
			return b.score();
		}else {
			return 999;
		}
	}
	//プレーヤーへの問い合わせ処理
	int te(Player a,Board b) {
		int i = 0;
		int rc[] = new int [2];//着手保存用
		while(i  < 1) {
			rc = a.playerPut(b);
			i = b.put(rc[0],rc[1]);
			if(i < 1) {
				//illegalputが1のときcomのバグ
				if(a.illegalPut() == 1) {
					//これで試合終了にさせる　特別な数字だがplayでは何もしてない
					return 999;
				}
			}
		}
		b.turnend();
		return 0;
	}
	int passte(Player a,Board b,int end) {
		a.playerPut();
		b.turnend();
		return ++end;
	}
}
class InvisibleGame extends Game{
	public int playSet(Player bl,Player wh) {
		b = new InvisibleShowBoard();
		return play(bl,wh);
	}

	public int play(Player bl,Player wh) {
		int end;// 連続パス数2で試合終了
		// 初期設定
		b.boardreset();
		b.setState();
		// 指し手保存用

		//ゲーム開始
		end = 0;//パス回数0
		boolean pass;//パス警告するか
		while (end == 0 ||end == 1) {
			pass = false;
			if (b.allSTurn() == 0) {
				pass = true;
			}
			if(b.getState()) {
				if (pass) {
					end = passte(bl,b,end);
				}else {
					end = te(bl,b);
				}
			}else {
				if(pass) {
					end = passte(wh,b,end);
				}else {
					end = te(wh,b);
				}
			}
		}
		b.show();
		//終了処理
		if(end == 2) {
			return b.score();
		}else {
			return 999;
		}
	}
}