/**
 * 
 */
package main;


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

}
