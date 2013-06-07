/**
 * 
 */
package test.java.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import main.java.bank.BankAccount;
import main.java.bank.entity.Account;

import org.junit.Test;

/**
 * @author quynhlt
 * 
 */
public class BankAccountTest {

	@Test
	public void testOpenAccountWithEmptyAccountNumber() {
		Account account = createAccount("");
		assertEquals(-1, BankAccount.openAccount(account));
	}

	private Account createAccount(String accountNumber) {
		
		return null;
	}
}
