package d9;

public class Benchmark {
	private static int TARGETBLPLAYER = 5;
	private static int NORMALPLAYER = 3;
	private static int TOTALGAME = 2000;

	public static void main (String[]  args) {
		System.out.println("run Benchmark");
		Player target;
		Player normal;
		int winbl = 0;
		int losebl = 0;
		int drawbl = 0;
		int winwh = 0;
		int losewh = 0;
		int drawwh = 0;
		target = Control.playerSet(TARGETBLPLAYER);
		normal = Control.playerSet(NORMALPLAYER);
		long t1 = System.currentTimeMillis();
		for (int i = 0;i < TOTALGAME / 2;i++) {
			Game game = new InvisibleGame();
			int score = game.playSet(target,normal);
			if (score == 999) {
				System.out.println("エラー");
				return;
			}else if(score > 0) {
				winbl++;
			}else if(score == 0) {
				drawbl++;
			}else {
				losebl++;
			}
		}
		for (int i = TOTALGAME / 2;i < TOTALGAME;i++) {
			Game game = new InvisibleGame();
			int score = game.playSet(normal,target);
			if (score == 999) {
				System.out.println("エラー");
				return;
			}else if(score < 0) {
				winwh++;
			}else if(score == 0) {
				drawwh++;
			}else {
				losewh++;
			}
		}
		t1 = System.currentTimeMillis() - t1;
		System.out.println("黒 : "+winbl + " 勝, "+losebl+" 負, "+drawbl+"分 /"+ TOTALGAME /2 +"試合");
		System.out.println("白 : "+winwh + " 勝, "+losewh+" 負, "+drawwh+"分 /"+ (TOTALGAME - TOTALGAME/2) +"試合");
		System.out.println(t1 + "[ms]");
	}
}
