package game;

import java.util.Locale;

/**
 * This enum contians the categories of monthly transactions.
 * They can automatically return their names from the language file.
 * @author Benedikt Ringlein
 */

public enum TransactionCategory {
	
	Taxes,Health,Energy,Garbage;
	
	
	public String getName()
	{
		return ResourceManager.getString("TRANSACTIONCATEGORY_"+this.toString().toUpperCase(Locale.ENGLISH));
	}
}
