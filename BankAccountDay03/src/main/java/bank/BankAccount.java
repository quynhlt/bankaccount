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
	private static final int INVALID_BALANCE = -1;
	private static final int MAXLENGTH = 10;
	public static final String ERROR_FORMAT_NUMBER = "Account number contains number value";

	public static int open(Account account) {
		int balance = 0;
		if (account.getAccountNumber().isEmpty()) {
			balance = INVALID_BALANCE;
		} else {
			if (account.getAccountNumber().length() == MAXLENGTH) {
				if (!isNumber(account.getAccountNumber())) {
					throw new RuntimeException("Account number contains number value");
				} else {

				}
			} else {
				balance = INVALID_BALANCE;
			}
		}
		return balance;
	}

	private static boolean isNumber(String accountNumber) {
		try {
			Long.parseLong(accountNumber);
			return true;
		} catch (RuntimeException exception) {
			return false;
		}
	}

}
