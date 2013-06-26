/**
 * 
 */
package main;

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
		transactionDAO.save(transaction);
	}
}
