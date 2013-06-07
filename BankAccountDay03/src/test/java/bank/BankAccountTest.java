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
		assertEquals(-1, BankAccount.open(account));
	}

	private Account createAccount(String accountNumber) {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt("07/06/2013");
		return account;
	}

	@Test
	public void allowLengthOfAccountNumberIs10() {
		Account account = createAccount("123456789");
		assertEquals(-1, BankAccount.open(account));
	}

	@Test
	public void valueOfAccountNumberIsNumberValue() {
		try {
			Account account = createAccount("123456789A");
			assertEquals(0, BankAccount.open(account));
			fail();
		} catch (RuntimeException e) {
			assertEquals(BankAccount.ERROR_FORMAT_NUMBER, e.getMessage());
		}
	}
}
