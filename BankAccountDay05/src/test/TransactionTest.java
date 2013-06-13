/**
 * 
 */
package test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;


import java.util.Calendar;

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
	TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);
	Calendar calendar = mock(Calendar.class);
	@Before
	public void setUp() {
		reset(mockTransactionDAO);
		Transaction.setTransactionDAO(mockTransactionDAO);
	}

	@Test
	public void testTransactionShouldBeSaveToDB() {
		String accountNumber = "1234567890";
		float amount = 200F;
		Long timestamp = System.currentTimeMillis();
        when(calendar.getTimeInMillis()).thenReturn(timestamp);
		String description = "Deposit";
		Transaction.doTransaction(accountNumber, timestamp, amount, description);
		ArgumentCaptor<TransactionDTO> logTransaction = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO).doTransaction(logTransaction.capture());
		assertEquals(logTransaction.getValue().getAccountNumber(), accountNumber);
		assertEquals((logTransaction.getValue()).getAmount(), amount, 0.01);
		assertEquals((logTransaction.getValue()).getTimestamp(), timestamp);
		assertEquals((logTransaction.getValue()).getDescription(), description);
		
	}


}
