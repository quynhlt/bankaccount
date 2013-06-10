/**
 * 
 */
package main;

/**
 * @author quynhlt
 * 
 */
public class TransactionDTO {
	private String accountNumber;
	private float amount;
	private Long timestamp;
	private String description;

	public TransactionDTO(String accountNumber, float amount, Long timestamp, String description) {
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.timestamp = timestamp;
		this.description = description;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

}
