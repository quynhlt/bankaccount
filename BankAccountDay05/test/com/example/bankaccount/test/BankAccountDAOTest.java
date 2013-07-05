/**
 * 
 */
package com.example.bankaccount.test;

import android.test.AndroidTestCase;

import com.example.bankaccount.BankAccountDAO;
import com.example.bankaccount.BankAccountDTO;

/**
 * @author quynhlt
 * 
 */
public class BankAccountDAOTest extends AndroidTestCase {

	private BankAccountDAO bankaccountDAO;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		bankaccountDAO = new BankAccountDAO(getContext(), null);
	}

	public void testSaveNewAccount() {
		BankAccountDTO bankAccount = createBankAccount("0123456789");
		long result = bankaccountDAO.save(bankAccount);
		assertEquals(1, result);
		assertEquals(1, bankaccountDAO.getRecordSize());
	}

	public void testGetBankAccountByAccountNumber() {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccount = createBankAccount(accountNumber);
		bankaccountDAO.save(bankAccount);
		BankAccountDTO bankAccountActual = bankaccountDAO.get(accountNumber);
		assertTrue(null != bankAccountActual);
	}

	public void testSaveNewAccountDuplicateAccountNumber() throws Exception {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccount = createBankAccount(accountNumber);
		bankaccountDAO.save(bankAccount);
		bankAccount = bankaccountDAO.get(accountNumber);
		boolean existed = bankAccount == null ? true : false;
		if (existed) {
			bankAccount = createBankAccount(accountNumber);
			bankaccountDAO.save(bankAccount);
		}
		assertEquals(1, bankaccountDAO.getRecordSize());
	}

	public void testUpdateBankAccount() {
		//add new
		String accountNumber = "0123456789";
		bankaccountDAO.save(createBankAccount(accountNumber));
		
		//get to update
		BankAccountDTO bankAccount = bankaccountDAO.get(accountNumber);
		
		//update new value
		int amount = 500;
		Long timeStamp = System.currentTimeMillis();
		bankAccount.setBalance(bankAccount.getBalance() + amount);
		bankAccount.setTimeStamp(timeStamp);
		long result = bankaccountDAO.update(bankAccount);

		//get after update to compare
		bankAccount = bankaccountDAO.get(accountNumber);
		assertEquals(1, result);
		assertEquals(amount, bankAccount.getBalance(), 0.01);
		assertEquals(timeStamp, bankAccount.getTimeStamp());
	}

	private BankAccountDTO createBankAccount(String accountNumber) {
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		bankAccount.setTimeStamp(System.currentTimeMillis());
		bankAccount.setBalance(0);
		return bankAccount;
	}

}