/**
 * 
 */
package com.example.bankaccount;

import java.util.List;


/**
 * @author quynhlt
 * 
 */
public class Transaction {
	private static TransactionDAO transactionDAO;

	public static void setTransactionDAO(TransactionDAO mockTransaction) {
		Transaction.transactionDAO = mockTransaction;
	}

	public static void doTransaction(String accountNumber, Long timestamp, int amount, String description) {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountNumber(accountNumber);
		transaction.setTimestamp(timestamp);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transactionDAO.insert(transaction);
	}

	public static List<TransactionDTO> getTransactions(String accountNumber) {
		return transactionDAO.get(accountNumber);
	}

	public static List<TransactionDTO> getTransactions(String accountNumber, Long startTime, Long stopTime) {
		return transactionDAO.get(accountNumber, startTime, stopTime);
	}

	public static List<TransactionDTO> getTransactions(String accountNumber, int n) {
		return transactionDAO.get(accountNumber, n);
	}
}
