/**
 * 
 */
package com.example.bankaccount.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import com.example.bankaccount.BankAccount;
import com.example.bankaccount.BankAccountDAO;
import com.example.bankaccount.BankAccountDTO;
import com.example.bankaccount.Transaction;
import com.example.bankaccount.TransactionDAO;
import com.example.bankaccount.TransactionDTO;

/**
 * @author quynhlt
 * 
 */

public class BankAccountTest extends TestCase {
	
	private BankAccountDAO mockBankAccountDAO;
	private TransactionDAO mockTransactionDAO;
	private Calendar mockCalendar;

	protected void setUp() throws Exception {
		mockBankAccountDAO = mock(BankAccountDAO.class);
		mockTransactionDAO = mock(TransactionDAO.class);
		mockCalendar = mock(Calendar.class);
		reset(mockBankAccountDAO);
		reset(mockTransactionDAO);
		reset(mockCalendar);
		BankAccount.setBankAccountDAO(mockBankAccountDAO);
		Transaction.setTransactionDAO(mockTransactionDAO);
		BankAccount.setCalendar(mockCalendar);
	}

	// step 1
	public void testOpenAccountHasZeroBalanceAndPersistent() {

		String accountNumber = "0123456789";
		BankAccount.openAccount(accountNumber);

		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).insert(openAccount.capture());

		assertEquals(accountNumber, openAccount.getValue().getAccountNumber());
		assertEquals(0.0, openAccount.getValue().getBalance(), 0.01);
	}

	// step 2

	public void testGetBankAccountByAccountNumber() {
		String accountNumber = "0123456789";
		BankAccountDTO account = BankAccount.openAccount(accountNumber);

		when(mockBankAccountDAO.get(accountNumber)).thenReturn(account);
		BankAccountDTO accountActual = BankAccount.get(accountNumber);
		verify(mockBankAccountDAO, times(1)).get(accountNumber);

		assertEquals(account, accountActual);
	}

	// step 3

	public void testDepositTransactionBalanceHasIncrease() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccountDAO.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).update(argument.capture());

		assertEquals(amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 4

	public void testDepositTransactionShouldSaveToDB() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		Long timeStamp = System.currentTimeMillis();
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);

		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		when(mockBankAccountDAO.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).insert(argument.capture());

		assertEquals(amount, argument.getValue().getAmount(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 5

	public void testWithdrawTransactionBalanceHasDecrease() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Withdraw";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccountDAO.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).update(argument.capture());

		assertEquals(-amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 6

	public void testAfterWithdrawTransactionShouldSaveToDB() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		Long timeStamp = System.currentTimeMillis();
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		when(mockBankAccountDAO.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).insert(argument.capture());

		assertEquals(-amount, argument.getValue().getAmount(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 7

	public void testGetTransactionsOccurredByAccountNumber() {
		String accountNumber = "0123456789";
		Transaction.getTransactions(accountNumber);
		verify(mockTransactionDAO, times(1)).get(accountNumber);
	}

	// step 8

	public void testGetTransactionsOccurredWithPeriodOfTime() {
		String accountNumber = "0123456789";
		Long startTime = 1000L;
		Long stopTime = 2000L;
		Transaction.getTransactions(accountNumber, startTime, stopTime);
		verify(mockTransactionDAO, times(1)).get(accountNumber, startTime, stopTime);
	}

	// step 9

	public void testGetTransactionsOccurredWithANumber() {
		String accountNumber = "0123456789";
		int n = 10;
		Transaction.getTransactions(accountNumber, n);
		verify(mockTransactionDAO, times(1)).get(accountNumber, n);
	}

	// step 10

	public void testOpenAccountHasTimeStamp() {
		String accountNumber = "0123456789";
		BankAccount.openAccount(accountNumber);
		Long timeStamp = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).insert(openAccount.capture());
		assertTrue(openAccount.getValue().getTimeStamp() != null);
		assertEquals(timeStamp, openAccount.getValue().getTimeStamp());
	}

}
