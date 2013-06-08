/**
 * 
 */
package main;

/**
 * @author quynhlt
 * 
 */
public class BankAccountDTO {
	private String accountNumber;
	private float balance;
	private String openTimestampt;
	private String description;
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the balance
	 */
	public float getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(float balance) {
		this.balance = balance;
	}

	/**
	 * @return the openTimestampt
	 */
	public String getOpenTimestampt() {
		return openTimestampt;
	}

	/**
	 * @param openTimestampt
	 *            the openTimestampt to set
	 */
	public void setOpenTimestampt(String openTimestampt) {
		this.openTimestampt = openTimestampt;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
