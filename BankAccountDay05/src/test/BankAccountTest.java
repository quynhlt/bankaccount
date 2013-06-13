/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.BankAccount;
import main.BankAccountDAO;
import main.BankAccountDTO;
import main.Transaction;
import main.TransactionDAO;
import main.TransactionDTO;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author quynhlt
 * 
 */
public class BankAccountTest {

	private BankAccountDAO mockBankAccountDAO = mock(BankAccountDAO.class);
	private Calendar mockCalendar = mock(Calendar.class);
	private TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);

	@Before
	public void setUp() {
		reset(mockBankAccountDAO);
		BankAccount.setBankAccountDAO(mockBankAccountDAO);
		BankAccount.setCalendar(mockCalendar);
		Transaction.setTransactionDAO(mockTransactionDAO);
	}

	@Test
	public void testOpenNewAccountHasZeroBalance() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(openAccount.capture());
		assertEquals(accountNumber, (openAccount.getValue()).getAccountNumber());
		assertEquals(0.0, openAccount.getValue().getBalance(), 0.01);
	}

	@Test
	public void testGetAccountByAccountNumber() {
		String accountNumber = "1234567890";
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccountDTO bankActual = BankAccount.getAccount(bankAccount.getAccountNumber());
		verify(mockBankAccountDAO, times(1)).getAccount(bankAccount.getAccountNumber());
		assertEquals(bankAccount, bankActual);
	}

	@Test
	public void testDepositTransaction() {
		String accountNumber = "1234567890";
		float amount = 200F;
		String description = "Deposit with amount is 200";
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.deposit(accountNumber, amount, description);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	@Test
	public void testDepositShouldBeSaveToDB() {
		String accountNumber = "1234567890";
		float amount = 200F;
		String description = "Deposit with amount is 200";
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		Long timeStamp = mockCalendar.getTimeInMillis();
		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).doTransaction(argument.capture());
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(amount, argument.getValue().getAmount());
		assertEquals(description, argument.getValue().getDescription());
		assertEquals(timeStamp, argument.getValue().getTimestamp());
	}

	@Test
	public void testWithdrawTransaction() {
		String accountNumber = "1234567890";
		float balance = 100F;
		float amount = 50F;
		String description = "Withdraw with amount is 50";
		float expected = balance - amount;
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber, balance);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.withdraw(accountNumber, amount, description);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(expected, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	@Test
	public void testWithdrawShouldBeSaveToDB() {
		String accountNumber = "1234567890";
		float balance = 0F;
		float amount = 50F;
		String description = "Withdraw with amount is 50";
		float expected = balance - amount;
		Long timeStamp = mockCalendar.getTimeInMillis();
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		when(mockBankAccountDAO.getAccount(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).doTransaction(argument.capture());
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(expected, argument.getValue().getAmount());
		assertEquals(description, argument.getValue().getDescription());
		assertEquals(timeStamp, argument.getValue().getTimestamp());
	}

	@Test
	public void testGetTransactionsOccurred() {
		List<TransactionDTO> expectedTransactions = new ArrayList<TransactionDTO>();
		String accountNumber = "1234567890";
		TransactionDTO deposit = new TransactionDTO(accountNumber, 200L, 10000L, "Deposit with amount is 200");
		TransactionDTO withdraw = new TransactionDTO(accountNumber, 50L, 10000L, "Withdraw with amount is 50");
		expectedTransactions.add(deposit);
		expectedTransactions.add(withdraw);
		when(mockTransactionDAO.getTransactionsOccurred(accountNumber)).thenReturn(expectedTransactions);
		List<TransactionDTO> actualTransactions = mockTransactionDAO.getTransactionsOccurred(accountNumber);
		verify(mockTransactionDAO, times(1)).getTransactionsOccurred(accountNumber);
		assertTrue(expectedTransactions.size() == actualTransactions.size());
		int length = actualTransactions.size();
		for (int i = 0; i < length; i++) {
			assertEquals(expectedTransactions.get(i), actualTransactions.get(i));
		}
	}

	@Test
	public void testGetTransactionsOccurredWithAPeriodOfTime() {
		List<TransactionDTO> expectedTransactions = new ArrayList<TransactionDTO>();
		String accountNumber = "1234567890";
		TransactionDTO deposit = new TransactionDTO(accountNumber, 200L, 10000L, "Deposit with amount is 200");
		TransactionDTO withdraw = new TransactionDTO(accountNumber, 50L, 20000L, "Withdraw with amount is 50");
		expectedTransactions.add(deposit);
		expectedTransactions.add(withdraw);
		Long startTime = 10000L;
		Long stopTime = 50000L;
		when(mockTransactionDAO.getTransactionsOccurred(accountNumber, startTime, stopTime)).thenReturn(expectedTransactions);
		List<TransactionDTO> actualTransactions = mockTransactionDAO.getTransactionsOccurred(accountNumber, startTime, stopTime);
		verify(mockTransactionDAO, times(1)).getTransactionsOccurred(accountNumber, startTime, stopTime);
		int length = actualTransactions.size();
		assertTrue(length == actualTransactions.size());
		for (int i = 0; i < length; i++) {
			assertEquals(expectedTransactions.get(i), actualTransactions.get(i));
		}
	}

	@Test
	public void testGetNumberOfNewTransactions() {
		List<TransactionDTO> expectedTransactions = new ArrayList<TransactionDTO>();
		String accountNumber = "1234567890";
		TransactionDTO deposit = new TransactionDTO(accountNumber, 200L, 10000L, "Deposit with amount is 200");
		TransactionDTO withdraw = new TransactionDTO(accountNumber, 50L, 20000L, "Withdraw with amount is 50");
		expectedTransactions.add(deposit);
		expectedTransactions.add(withdraw);
		int n = 2;
		when(mockTransactionDAO.getNumberOfNewTransactions(accountNumber, n)).thenReturn(expectedTransactions);
		List<TransactionDTO> actualTransactions = mockTransactionDAO.getNumberOfNewTransactions(accountNumber, n);
		verify(mockTransactionDAO, times(1)).getNumberOfNewTransactions(accountNumber, n);
		int length = actualTransactions.size();
		assertTrue(length == actualTransactions.size());
		for (int i = 0; i < length; i++) {
			assertEquals(expectedTransactions.get(i), actualTransactions.get(i));
		}
	}

}
