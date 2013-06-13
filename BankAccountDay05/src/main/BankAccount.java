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

	public static void setBankAccountDAO(BankAccountDAO mockBankAccountDAO) {
		BankAccount.bankAccountDAO = mockBankAccountDAO;
	}

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = createAccount(accountNumber);
		bankAccountDAO.save(accountDTO);
		return accountDTO;
	}

	private static BankAccountDTO createAccount(String accountNumber) {
		BankAccountDTO account = new BankAccountDTO(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt(System.currentTimeMillis());
		return account;
	}

	public static BankAccountDTO getAccount(String accountNumber) {
		return bankAccountDAO.getAccount(accountNumber);
	}

	public static void deposit(String accountNumber, float amount, String description) {
		BankAccountDTO bankAccountDTO = bankAccountDAO.getAccount(accountNumber);
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() + amount);
		bankAccountDAO.save(bankAccountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.doTransaction(accountNumber, timestamp, amount, description);
	}

	public static BankAccountDTO withdraw(String accountNumber, float amount, String description) {
		BankAccountDTO bankAccountDTO = bankAccountDAO.getAccount(accountNumber);
		bankAccountDTO.setBalance(bankAccountDTO.getBalance() - amount);
		bankAccountDAO.save(bankAccountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.doTransaction(accountNumber, timestamp, -amount, description);
		return bankAccountDTO;
	}

}
