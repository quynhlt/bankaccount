/**
 * 
 */
package main;

/**
 * @author quynhlt
 * 
 */
public class BankAccount {
	private static BankAccountDAO mockBankAccountDAO;

	public static void setBankAccountDAO(BankAccountDAO mockBankAccountDAO) {
		BankAccount.mockBankAccountDAO = mockBankAccountDAO;
	}

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = createAccount(accountNumber);
		mockBankAccountDAO.save(accountDTO);
		return accountDTO;
	}

	private static BankAccountDTO createAccount(String accountNumber) {
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountNumber(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt("08/06/2013");
		return account;
	}

	public static void dotransaction(BankAccountDTO accountDTO, float amount, String description) {
		// TODO Auto-generated method stub
		
	}

}
