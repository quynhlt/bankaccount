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

	public static void main(String[] args) {
		Account account = new Account();
		account.setAccountNumber("");
		account.setBalance(0);
		account.setOpenTimestampt("06/06/2013");
		openAccount(account);
	}

	public static int openAccount(Account account) {
		int balance = 0;
		if (account.getAccountNumber().isEmpty()) {
			balance = -1;
		}
		return balance;
	}
}
