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
		BankAccountDTO newAccount = createAccount(accountNumber);
		return mockDAO.save(newAccount);
	}

	private static BankAccountDTO createAccount(String accountNumber) {
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountNumber(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt("08/06/2013");
		return account;
	}

	public static BankAccountDTO deposit(String accountNumber, float amount, String description) {
		return null;
	}
}
