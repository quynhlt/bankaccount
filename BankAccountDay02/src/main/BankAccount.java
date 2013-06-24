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
	private static BankAccountDAO bankAccountDAO;
	private static Calendar calendar;

	public static void setCalendar(Calendar calendar) {
		BankAccount.calendar = calendar;
	}

	public static void setBankAccountDAO(BankAccountDAO mockDAO) {
		BankAccount.bankAccountDAO = mockDAO;
	}

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO newAccount = createAccount(accountNumber);
		bankAccountDAO.save(newAccount);
		return newAccount;
	}

	public static BankAccountDTO get(String accountNumber) {
		return bankAccountDAO.get(accountNumber);
	}

	private static BankAccountDTO createAccount(String accountNumber) {
		BankAccountDTO account = new BankAccountDTO(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt(System.currentTimeMillis());
		return account;
	}

	public static void deposit(String accountNumber, float amount, String description) {
		BankAccountDTO bankAccountDTO = bankAccountDAO.get(accountNumber);
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() + amount);
		bankAccountDTO.setDescription(description);
		bankAccountDAO.save(bankAccountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.doTransaction(accountNumber, timestamp, amount, description);
	}

	public static void withdraw(String accountNumber, float amount, String description) {
		BankAccountDTO bankAccountDTO = bankAccountDAO.get(accountNumber);
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() - amount);
		bankAccountDTO.setDescription(description);
		bankAccountDAO.save(bankAccountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.doTransaction(accountNumber, timestamp, -amount, description);
	}

}
