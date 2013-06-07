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

	public static int open(Account account) {
		int balance = 0;
		if (account.getAccountNumber().isEmpty()) {
			balance = INVALID_BALANCE;
		} else if (account.getAccountNumber().length() != MAXLENGTH) {
			balance = INVALID_BALANCE;
		}
		return balance;
	}

}
