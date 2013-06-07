/**
 * 
 */
package test.java.bank;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import main.java.bank.BankAccount;
import main.java.bank.TransactionDAO;
import main.java.bank.entity.Account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

/**
 * @author quynhlt
 * 
 */
public class TransactionTest {
	TransactionDAO mockTransaction = mock(TransactionDAO.class);
	@Before
	public void setUp() {
		reset(mockTransaction);
		
	}
	@Test
	public void testLogTransactionWhenDeposit() {
		String accountNumber = "1234567890";
		Account account = createAccount(accountNumber);
		BankAccount.open(account);
		ArgumentCaptor<Account> logDepositTransaction = ArgumentCaptor.forClass(Account.class);
		verify(mockTransaction).save(logDepositTransaction.capture());
		assertEquals(logDepositTransaction.getValue().getBalance(), 0.0, 0.01);
		assertEquals((logDepositTransaction.getValue()).getAccountNumber(), accountNumber);
	}

	private Account createAccount(String accountNumber) {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setBalance(0);
		account.setOpenTimestampt("07/06/2013");
		return account;
	}
}
