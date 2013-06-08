package main;

public class Transaction {
	private static TransactionDAO mockTransaction;

	public static void setTransactionDAO(TransactionDAO mockTransaction) {
		Transaction.mockTransaction = mockTransaction;
	}

	public static TransactionDTO logTransaction(String accountNumber, Long timestamp, float amount, String description) {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setAccountNumber(accountNumber);
		transaction.setTimestamp(timestamp);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		return mockTransaction.save(transaction);
	}
	
}
