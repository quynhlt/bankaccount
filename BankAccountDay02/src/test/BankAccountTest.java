/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
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
	private BankAccountDAO mockAccountDAO = mock(BankAccountDAO.class);
	private Calendar mockCalendar = mock(Calendar.class);
	private TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);

	@Before
	public void setUp() {
		reset(mockAccountDAO);
		BankAccount.setBankAccountDAO(mockAccountDAO);
		BankAccount.setCalendar(mockCalendar);
		Transaction.setTransactionDAO(mockTransactionDAO);
	}

	// Step 1
	@Test
	public void testOpenAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "0123456789";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockAccountDAO, times(1)).save(openAccount.capture());
		assertEquals(accountNumber, (openAccount.getValue()).getAccountNumber());
		assertEquals(0.0, openAccount.getValue().getBalance(), 0.01);
	}

	// Step 2
	@Test
	public void testCanGetAccountByAccountNumber() {
		String accountNumber = "0123456789";
		BankAccountDTO bankAccount = BankAccount.openAccount(accountNumber);
		when(mockAccountDAO.get(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccountDTO bankActual = BankAccount.get(bankAccount.getAccountNumber());
		verify(mockAccountDAO, times(1)).get(bankAccount.getAccountNumber());
		assertEquals(bankAccount, bankActual);
	}

	// step 3
	@Test
	public void testAfterDepositBalanceHasIncrease() {
		String accountNumber = "0123456789";
		float amount = 200F;
		String description = "Deposit transaction";
		BankAccountDTO bankAccount = BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		when(mockAccountDAO.get(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.deposit(accountNumber, amount, description);
		verify(mockAccountDAO, times(2)).save(argument.capture());
		assertEquals(amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// Step 4
	@Test
	public void testAfterDepositTransactionShouldBeSaveToDB() {
		String accountNumber = "0123456789";
		float amount = 200F;
		String description = "Deposit transaction";
		BankAccountDTO bankAccount = BankAccount.openAccount(accountNumber);
		when(mockAccountDAO.get(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		Long timeStamp = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).save(argument.capture());
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(amount, argument.getValue().getAmount());
		assertEquals(description, argument.getValue().getDescription());
		assertEquals(timeStamp, argument.getValue().getTimestamp());

	}

	// Step 5
	@Test
	public void testAfterWithdrawBalanceHasDecrease() {
		String accountNumber = "0123456789";
		float balance = 100F;
		float amount = 50F;
		String description = "Withdraw transaction";
		float expectedAmount = balance - amount;
		BankAccountDTO bankAccount = BankAccount.openAccount(accountNumber);
		bankAccount.setBalance(balance);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		when(mockAccountDAO.get(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.withdraw(accountNumber, amount, description);
		verify(mockAccountDAO, times(2)).save(argument.capture());
		assertEquals(expectedAmount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// Step 6
	@Test
	public void testAfterWithdrawTransactionShouldBeSaveToDB() {
		String accountNumber = "0123456789";
		float amount = 50F;
		String description = "Withdraw transaction";
		Long timeStamp = System.currentTimeMillis();
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		BankAccountDTO bankAccount = BankAccount.openAccount(accountNumber);
		when(mockAccountDAO.get(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).save(argument.capture());
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(-amount, argument.getValue().getAmount());
		assertEquals(description, argument.getValue().getDescription());
		assertEquals(timeStamp, argument.getValue().getTimestamp());
	}

	// Step 7
	@Test
	public void testGetTransactionsOccurred() {
		String accountNumber = "0123456789";
		Transaction.getTransactions(accountNumber);
		verify(mockTransactionDAO, times(1)).getTransactions(accountNumber);
	}
}
