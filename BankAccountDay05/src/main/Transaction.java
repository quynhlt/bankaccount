/**
 * 
 */
package main;

import java.util.List;

/**
 * @author quynhlt
 * 
 */
public class Transaction {
	private static TransactionDAO mockTransactionDao;

	public static void setTransactionDAO(TransactionDAO mockTransactionDao) {
		Transaction.mockTransactionDao = mockTransactionDao;
	}

	public static TransactionDTO createTransaction(String accountNumber, Long timestamp, float amount, String description) {
		TransactionDTO transaction = new TransactionDTO(accountNumber, amount, timestamp, description);
		mockTransactionDao.createTransaction(transaction);
		return transaction;
	}

	public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
		TransactionDTO transaction = new TransactionDTO(accountNumber);
		mockTransactionDao.createTransaction(transaction);
		return null;
	}
}
