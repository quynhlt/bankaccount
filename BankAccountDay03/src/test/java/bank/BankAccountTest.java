/**
 * 
 */
package test.java.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import main.java.bank.BankAccount;
import main.java.bank.BankAccountDAO;
import main.java.bank.entity.Account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author quynhlt
 * 
 */
public class BankAccountTest {
	BankAccountDAO mockDAO = mock(BankAccountDAO.class);

	@Before
	public void setUp() {
		reset(mockDAO);
		BankAccount.setBankAccountDAO(mockDAO);
	}

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

	@Test
	public void testOpenAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "1234567890";
		Account account = createAccount(accountNumber);
		BankAccount.open(account);
		ArgumentCaptor<Account> captorSaveAccount = ArgumentCaptor.forClass(Account.class);
		verify(mockDAO).save(captorSaveAccount.capture());
		assertEquals(captorSaveAccount.getValue().getBalance(), 0.0, 0.01);
		assertEquals((captorSaveAccount.getValue()).getAccountNumber(), accountNumber);
	}
}
