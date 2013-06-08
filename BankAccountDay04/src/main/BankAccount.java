/**
 * 
 */
package main;

/**
 * @author quynhlt
 * 
 */
public class BankAccount {
	private static BankAccountDAO mockDAO;

	public static void setBankAccountDAO(BankAccountDAO mockDAO) {
		BankAccount.mockDAO = mockDAO;
	}

	public static BankAccountDTO openAccount(String accountNumber) {
		return null;

	}

}
