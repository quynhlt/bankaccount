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

	public static void setBankAccountDAO(BankAccountDAO bankAccountDAO) {
		BankAccount.bankAccountDAO = bankAccountDAO;
	}

	public static void setCalendar(Calendar calendar) {
		BankAccount.calendar = calendar;
	}

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO(accountNumber);
		bankAccountDAO.save(bankAccountDTO);
		return bankAccountDTO;
	}

	public static BankAccountDTO get(String accountNumber) {
		return bankAccountDAO.get(accountNumber);
	}

	public static void deposit(String accountNumber, int amount, String description) {
		BankAccountDTO accountDTO = bankAccountDAO.get(accountNumber);
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		accountDTO.setDescription(description);
		bankAccountDAO.save(accountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.doTransaction(accountNumber, timestamp, amount, description);
	}

	public static void withdraw(String accountNumber, int amount, String description) {
		BankAccountDTO accountDTO = bankAccountDAO.get(accountNumber);
		accountDTO.setBalance(accountDTO.getBalance() - amount);
		accountDTO.setDescription(description);
		bankAccountDAO.save(accountDTO);
		Long timestamp = calendar.getTimeInMillis();
		Transaction.doTransaction(accountNumber, timestamp, -amount, description);
	}

}
