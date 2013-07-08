/**
 * 
 */
package com.example.bankaccount.test;

import java.util.List;

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
		long result = transactionDAO.insert(transaction);
		assertEquals(1, result);
	}

	public void testGetTransactionsByAccountNumber() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		Long timestamp = System.currentTimeMillis();
		TransactionDTO transaction = createNewTransaction(accountNumber, timestamp, amount, description);
		transactionDAO.insert(transaction);
		List<TransactionDTO> list = transactionDAO.get(accountNumber);
		assertTrue(list != null);
		assertEquals(1, list.size());
	}

	public void testGetTransactionsOccurredWithPeriodOfTime() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		Long startTime = 1000L;
		Long stopTime = 2000L;
		TransactionDTO transaction = createNewTransaction(accountNumber, startTime, amount, description);
		transactionDAO.insert(transaction);

		description = "Withdraw";
		transaction = createNewTransaction(accountNumber, stopTime, -amount, description);
		transactionDAO.insert(transaction);

		List<TransactionDTO> list = transactionDAO.get(accountNumber, startTime, stopTime);
		assertTrue(list != null);
		assertEquals(2, list.size());
	}

	public void testGetTransactionsOccurredWithANumber() {
		String accountNumber = "0123456789";
		int amount = 10000;
		String description = "Deposit";
		for (int i = 0; i < 10; i++) {
			TransactionDTO transaction = createNewTransaction(accountNumber, System.currentTimeMillis(), amount + i, description + i);
			transactionDAO.insert(transaction);
		}

		int numberRecord = 2;
		List<TransactionDTO> list = transactionDAO.get(accountNumber, numberRecord);
		assertTrue(list != null);
		assertEquals(numberRecord, list.size());
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
