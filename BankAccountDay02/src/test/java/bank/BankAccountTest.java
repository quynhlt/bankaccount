/**
 * 
 */
package test.java.bank;

import static org.junit.Assert.assertEquals;
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
	@Test
	public void testLengthOfAccountNumberIs10() {
		Account account = createAccount("1234567890");
		assertEquals(-2, BankAccount.openAccount(account));
	}

	private Account createAccount(String accountNumber) {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt("06/06/2013");
		return account;
	}
}
