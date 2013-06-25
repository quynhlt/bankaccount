/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

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
	private BankAccountDAO mockBankAccount = mock(BankAccountDAO.class);
	private TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);
	private Calendar mockCalendar = mock(Calendar.class);

	@Before
	public void setUp() throws Exception {
		reset(mockBankAccount);
		reset(mockTransactionDAO);
		reset(mockCalendar);
		BankAccount.setBankAccountDAO(mockBankAccount);
		Transaction.setTransactionDAO(mockTransactionDAO);
		BankAccount.setCalendar(mockCalendar);
	}

	// 1
	@Test
	public void testOpenAccountHasZeroBalance() {
		String accountNumber = "0123456789";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccount, times(1)).save(openAccount.capture());
		assertEquals(accountNumber, openAccount.getValue().getAccountNumber());
		assertEquals(0.0, openAccount.getValue().getBalance(), 0.01);
	}

	// 2
	@Test
	public void testCanGetAccountByAccountNumber() {
		String accountNumber = "0123456789";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccount.get(accountNumber)).thenReturn(accountDTO);
		BankAccountDTO bankActual = BankAccount.get(accountNumber);
		verify(mockBankAccount, times(1)).get(accountNumber);
		assertEquals(accountDTO, bankActual);
	}

	// 3
	@Test
	public void testAfterDepositTransactionBalanceHaIncrease() {
		String accountNumber = "0123456789";
		int amount = 100;
		String description = "Deposit";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccount.get(accountNumber)).thenReturn(accountDTO);
		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccount, times(2)).save(argument.capture());
		assertEquals(amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// 4
	@Test
	public void testAfterDepositTransactionShouldSaveToDB() {
		String accountNumber = "0123456789";
		int amount = 100;
		String description = "Deposit";
		Long timeStamp = System.currentTimeMillis();
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		when(mockBankAccount.get(accountNumber)).thenReturn(accountDTO);
		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).save(argument.capture());
		assertEquals(amount, argument.getValue().getAmount(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// 5
	@Test
	public void testAfterWithdrawTransactionBalanceHaDecrease() {
		String accountNumber = "0123456789";
		int amount = 100;
		String description = "Withdraw";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccount.get(accountNumber)).thenReturn(accountDTO);
		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccount, times(2)).save(argument.capture());
		assertEquals(-amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// 6
	@Test
	public void testAfterWithdrawTransactionShouldSaveToDB() {
		String accountNumber = "0123456789";
		int amount = 100;
		String description = "Deposit";
		Long timeStamp = System.currentTimeMillis();
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		when(mockBankAccount.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).save(argument.capture());
		assertEquals(-amount, argument.getValue().getAmount(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// 7
	@Test
	public void testgetTransactionsOccurred() {
		String accountNumber = "0123456789";
		Transaction.getTransactions(accountNumber);
		verify(mockTransactionDAO, times(1)).get(accountNumber);
	}

	// 8
	@Test
	public void testgetTransactionsWithAPeriodOfTime() {
		String accountNumber = "0123456789";
		Long startTime = 10000L;
		Long stopTime = 20000L;
		Transaction.getTransactions(accountNumber, startTime, stopTime);
		verify(mockTransactionDAO, times(1)).get(accountNumber, startTime, stopTime);
	}

	// 9
	@Test
	public void testgetTransactionsWithANumber() {
		String accountNumber = "0123456789";
		int n = 1;
		Transaction.getTransactions(accountNumber, n);
		verify(mockTransactionDAO, times(1)).get(accountNumber, n);
	}

	// Step 10
	@Test
	public void testOpenAccountShouldSaveTimeStampToDB() {
		String accountNumber = "0123456789";
		BankAccount.openAccount(accountNumber);
		Long timeStamp = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccount, times(1)).save(openAccount.capture());
		assertTrue(openAccount.getValue().getOpenTimeStamp() != null);
		assertEquals(timeStamp, openAccount.getValue().getOpenTimeStamp());
	}
}
