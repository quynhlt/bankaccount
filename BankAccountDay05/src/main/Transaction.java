/**
 * 
 */
package main;

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
		return null;
	}

}
