package game;

import java.awt.Color;
import java.util.TimerTask;

import objects.Building;

public class MonthlyTransactions {
	
	public final static byte CATEGORY_TAXES = 0;
	
	public static int[] TransactionList = new int[5];

	public static TimerTask ExecuteTransactions = new TimerTask(){
		@Override
		public void run() {
			if(!Game.isPaused()&&Main.gameState==Main.STATE_GAME){
				for(Building b:ResourceManager.objects){
					b.MonthlyTransaction();
				}
				int total=0;
				for(byte i=0;i<TransactionList.length;i++)
				{
					Main.money+=TransactionList[i];
					total+=TransactionList[i];
					TransactionList[i]=0;
				}
				if(total>0){
					Main.gui.infoMonthly.setText("+"+total+"$");
					Main.gui.infoMoney.setColor(new Color(0,100,0));
				}else if(total<0){
					Main.gui.infoMonthly.setText("-"+total+"$");
					Main.gui.infoMoney.setColor(new Color(100,0,0));
				}else{
					Main.gui.infoMonthly.setText(total+"$");
					Main.gui.infoMoney.setColor(Color.white);
				}
			}
		}
	};
	
	public static void addTransaction(int value, byte category)
	{
		try {
			TransactionList[category]+=value;
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Transactioncategory "+category+" does not exist.");
			e.printStackTrace();
		}
	}
	
}
