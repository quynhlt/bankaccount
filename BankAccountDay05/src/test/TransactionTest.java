/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
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
	TransactionDAO mockTransactionDao = mock(TransactionDAO.class);

	@Before
	public void setUp() {
		reset(mockTransactionDao);
		Transaction.setTransactionDAO(mockTransactionDao);
	}

	@Test
	public void testTransactionIsExecutedAndIsPersistent() {
		String accountNumber = "1234567890";
		float amount = 200F;
		Long timestamp = System.currentTimeMillis();
		String description = "Deposit";
		Transaction.createTransaction(accountNumber, timestamp, amount, description);

		ArgumentCaptor<TransactionDTO> logTransaction = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDao).save(logTransaction.capture());
		assertEquals(logTransaction.getValue().getAccountNumber(), accountNumber);
		assertEquals((logTransaction.getValue()).getAmount(), amount, 0.01);
		assertEquals((logTransaction.getValue()).getTimestamp(), timestamp);
		assertEquals((logTransaction.getValue()).getDescription(), description);
	}

}
