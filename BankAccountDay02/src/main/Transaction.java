package main;

import java.util.List;

public class Transaction {
	private static TransactionDAO transactionDAO;

	public static void setTransactionDAO(TransactionDAO mockTransaction) {
		Transaction.transactionDAO = mockTransaction;
	}

	public static TransactionDTO doTransaction(String accountNumber, Long timestamp, float amount, String description) {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountNumber(accountNumber);
		transaction.setTimestamp(timestamp);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		return transactionDAO.save(transaction);
	}

	public static List<TransactionDTO> getTransactions(String accountNumber) {
		return transactionDAO.getTransactions(accountNumber);
	}
	
}
