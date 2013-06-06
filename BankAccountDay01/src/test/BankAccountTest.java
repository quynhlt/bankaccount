/**
 * 
 */
package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import main.java.BankAccount;
import main.java.entity.Account;

import org.junit.Test;

/**
 * @author quynhlt
 * 
 */
public class BankAccountTest {

	@Test
	public void testOpenAccountWithEmptyAccountNumber() {
		Account account = createAccount("");
		assertEquals("-1", BankAccount.openAccount(account));
	}

	private Account createAccount(String accountNumber) {
		return null;
	}
}
