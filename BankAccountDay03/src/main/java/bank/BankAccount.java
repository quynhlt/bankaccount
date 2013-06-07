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

	
	public static int openAccount(Account account) {
		int balance = 0;
		if (account.getAccountNumber().isEmpty()) {
			balance = -1;
		} 
		return balance;
	}

}
