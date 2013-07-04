/**
 * 
 */
package com.example.bankaccount.test;

import android.test.AndroidTestCase;

import com.example.bankaccount.TransactionDAO;
import com.example.bankaccount.TransactionDTO;

/**
 * @author quynhlt
 * 
 */
public class TransactionDAOTest extends AndroidTestCase {

	private TransactionDAO transactionDAO;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		transactionDAO = new TransactionDAO(getContext(), null);
	}

	public void testSaveNewTransaction() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		Long timestamp = System.currentTimeMillis();
		TransactionDTO transaction = createNewTransaction(accountNumber, timestamp, amount, description);
		long result = transactionDAO.save(transaction);
		assertEquals(1, result);
	}

	private TransactionDTO createNewTransaction(String accountNumber, Long timestamp, int amount, String description) {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountNumber(accountNumber);
		transaction.setTimestamp(timestamp);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		return transaction;
	}
}
