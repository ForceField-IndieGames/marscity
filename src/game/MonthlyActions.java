package game;

import gui.GuiProgressbar;

import java.awt.Color;
import java.util.TimerTask;

import objects.Building;

/**
 * Recurring actions (e.g. taxes) are managed here
 * @author Benedikt Ringlein
 */

public class MonthlyActions {
	
	public static int[] TransactionList = new int[TransactionCategory.values().length];
	public static int[] PopulationStatistics = new int[10];

	public static TimerTask ExecuteTransactions = new TimerTask(){
		@Override
		public void run() {
			if(!Game.isPaused()&&Main.gameState==Main.STATE_GAME){
				for(Building b:ResourceManager.objects){
					b.MonthlyTransaction();
				}
				float max=0;
				for(int i=0;i<TransactionList.length;i++){
					if(Math.abs(TransactionList[i])>max)max=Math.abs(TransactionList[i]);
				}
				int total=0;
				for(TransactionCategory t:TransactionCategory.values())
				{
					((GuiProgressbar)Main.gui.moneycategories.elements.get(t.ordinal())).setText(t.getName()+": "+TransactionList[t.ordinal()]+"$");
					((GuiProgressbar)Main.gui.moneycategories.elements.get(t.ordinal())).setBarColor((TransactionList[t.ordinal()]<0)?new Color(255,150,150):new Color(150,255,150));
					((GuiProgressbar)Main.gui.moneycategories.elements.get(t.ordinal())).setValue((max!=0)?Math.abs(TransactionList[t.ordinal()]/max):0);
					((GuiProgressbar)Main.gui.moneycategories.elements.get(t.ordinal())).setRightaligned((TransactionList[t.ordinal()]<0)?true:false);
					Main.money+=TransactionList[t.ordinal()];
					total+=TransactionList[t.ordinal()];
					TransactionList[t.ordinal()]=0;
				}
				for(byte i=0;i<TransactionList.length;i++)
				{
					Main.money+=TransactionList[i];
					total+=TransactionList[i];
					TransactionList[i]=0;
				}
				if(total>0){
					Main.gui.infoMonthly.setText("+"+total+"$");
					Main.gui.infoMoney.setColor(new Color(150,250,150));
				}else if(total<0){
					Main.gui.infoMonthly.setText("-"+total+"$");
					Main.gui.infoMoney.setColor(new Color(250,150,150));
				}else{
					Main.gui.infoMonthly.setText(total+"$");
					Main.gui.infoMoney.setColor(Color.white);
				}
				
				//update population statistics
				for(int i=0; i<PopulationStatistics.length-1;i++)
				{
					PopulationStatistics[i]=PopulationStatistics[i+1];
				}
				PopulationStatistics[9] = Main.citizens;
				Main.gui.populationGraph.setPoints(PopulationStatistics);
				Main.gui.max.setText(""+(int)Main.gui.populationGraph.getMax());
			}
		}
	};
	
	public static void addTransaction(int value, TransactionCategory category)
	{
		try {
			TransactionList[category.ordinal()]+=value;
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Transactioncategory "+category+" does not exist.");
			e.printStackTrace();
		}
	}
	
}
