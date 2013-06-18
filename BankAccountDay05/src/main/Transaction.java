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
	private static TransactionDAO transactionDao;

	public static void setTransactionDAO(TransactionDAO mockTransactionDao) {
		Transaction.transactionDao = mockTransactionDao;
	}

	public static TransactionDTO doTransaction(String accountNumber, Long timestamp, float amount, String description) {
		TransactionDTO transaction = new TransactionDTO(accountNumber, amount, timestamp, description);
		transactionDao.doTransaction(transaction);
		return transaction;
	}

	public static List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
		return transactionDao.getTransactionsOccurred(accountNumber);
	}

	public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, Long startTime, Long stopTime) {
		return transactionDao.getTransactionsOccurred(accountNumber, startTime, stopTime);
	}

	public static List<TransactionDTO> getTransactionsOccurred(String accountNumber, int n) {
		return transactionDao.getTransactionsOccurred(accountNumber, n);
	}
}
