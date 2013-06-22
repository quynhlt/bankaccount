/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import main.BankAccount;
import main.BankAccountDAO;
import main.Transaction;
import main.TransactionDAO;
import main.TransactionDTO;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author quynhlt
 * 
 */
public class TransactionTest {

	private TransactionDAO mockTransaction = mock(TransactionDAO.class);
	private BankAccountDAO mockBankAccount = mock(BankAccountDAO.class);

	@Before
	public void setUp() {
		reset(mockBankAccount);
		reset(mockTransaction);
		BankAccount.setBankAccountDAO(mockBankAccount);
		Transaction.setTransactionDAO(mockTransaction);
	}

	@Test
	public void testLogTransactionWhenDeposit() {
		String accountNumber = "1234567890";
		ArgumentCaptor<TransactionDTO> captorLog = ArgumentCaptor.forClass(TransactionDTO.class);
		Long timestamp = 0L;
		float amount = 200F;
		String description = "deposit with amount is 200";
		Transaction.doTransaction(accountNumber, timestamp, amount, description);
		verify(mockTransaction).save(captorLog.capture());
		assertEquals(captorLog.getValue().getTimestamp(), timestamp, 0);
		assertEquals((captorLog.getValue()).getAmount(), amount);
	}
}
