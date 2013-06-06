/**
 * 
 */
package main.java.bank;

import main.java.bank.entity.Account;

/**
 * @author quynhlt
 * 
 */
public class BankAccount {

	public static final int ACC_NUMBER_EMPTY = -1;
	public static final int ACC_NUMBER_INVALID_LENGTH = -2;
	public static final String ERROR_MESSAGE = "Account number is number value";

	public static void main(String[] args) {
		Account account = new Account();
		account.setAccountNumber("s");
		account.setBalance(0);
		account.setOpenTimestampt("06/06/2013");
		openAccount(account);
	}

	public static int openAccount(Account account) {
		int balance = 0;
		if (account.getAccountNumber().isEmpty()) {
			balance = ACC_NUMBER_EMPTY;
		} else {
			if (account.getAccountNumber().length() != 10) {
				balance = ACC_NUMBER_INVALID_LENGTH;
			} else if (!isNumberValue(account.getAccountNumber())) {
				throw new RuntimeException(ERROR_MESSAGE);
			} else {

			}
		}
		return balance;
	}

	private static boolean isNumberValue(String accountNumber) {
		try {
			Long.parseLong(accountNumber);
			return true;
		} catch (RuntimeException exception) {
			return false;
		}
	}
}
