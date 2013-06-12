/**
 * 
 */
package main;

import java.util.Calendar;

/**
 * @author quynhlt
 * 
 */
public class BankAccount {
	private static BankAccountDAO mockBankAccountDAO;
	private static Calendar calendar;

	public static void setCalendar(Calendar calendar) {
		BankAccount.calendar = calendar;
	}

	public static void setBankAccountDAO(BankAccountDAO mockBankAccountDAO) {
		BankAccount.mockBankAccountDAO = mockBankAccountDAO;
	}

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = createAccount(accountNumber);
		mockBankAccountDAO.save(accountDTO);
		return accountDTO;
	}

	private static BankAccountDTO createAccount(String accountNumber) {
		BankAccountDTO account = new BankAccountDTO(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt(System.currentTimeMillis());
		return account;
	}

	public static BankAccountDTO getByAccountNumber(String accountNumber) {
		return mockBankAccountDAO.getByAccountNumber(accountNumber);
	}

	public static void deposit(String accountNumber, float amount, String description) {
		BankAccountDTO bankAccountDTO = mockBankAccountDAO.getByAccountNumber(accountNumber);
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() + amount);
		mockBankAccountDAO.save(bankAccountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.createTransaction(accountNumber, timestamp, amount, description);
	}

	public static void withdraw(String accountNumber, float amount, String description) {
		BankAccountDTO bankAccountDTO = mockBankAccountDAO.getByAccountNumber(accountNumber);
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() - amount);
		mockBankAccountDAO.save(bankAccountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.createTransaction(accountNumber, timestamp, -amount, description);
	}

}
