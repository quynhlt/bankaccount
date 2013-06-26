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

	private BankAccountDAO mockBankAccountDAO = mock(BankAccountDAO.class);
	private TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);
	private Calendar mockCalendar = mock(Calendar.class);

	@Before
	public void setUp() throws Exception {
		reset(mockBankAccountDAO);
		reset(mockTransactionDAO);
		reset(mockCalendar);
		BankAccount.setBankAccountDAO(mockBankAccountDAO);
		Transaction.setTransactionDAO(mockTransactionDAO);
		BankAccount.setCalendar(mockCalendar);
	}

	// step 1
	@Test
	public void testOpenAccountHasZeroBalanceAndPersistent() {
		String accountNumber = "0123456789";
		BankAccount.openAccount(accountNumber);

		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(openAccount.capture());

		assertEquals(accountNumber, openAccount.getValue().getAccountNumber());
		assertEquals(0.0, openAccount.getValue().getBalance(), 0.01);
	}

	// step 2
	@Test
	public void testGetBankAccountByAccountNumber() {
		String accountNumber = "0123456789";
		BankAccountDTO account = BankAccount.openAccount(accountNumber);

		when(mockBankAccountDAO.get(accountNumber)).thenReturn(account);
		BankAccountDTO accountActual = BankAccount.get(accountNumber);
		verify(mockBankAccountDAO, times(1)).get(accountNumber);

		assertEquals(account, accountActual);
	}

	// step 3
	@Test
	public void testDepositTransactionBalanceHasIncrease() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Deposit";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccountDAO.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(2)).save(argument.capture());

		assertEquals(amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 4
	@Test
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
		verify(mockTransactionDAO, times(1)).save(argument.capture());

		assertEquals(amount, argument.getValue().getAmount(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 5
	@Test
	public void testWithdrawTransactionBalanceHasDecrease() {
		String accountNumber = "0123456789";
		int amount = 500;
		String description = "Withdraw";
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		when(mockBankAccountDAO.get(accountNumber)).thenReturn(accountDTO);

		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(2)).save(argument.capture());

		assertEquals(-amount, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}

	// step 6
	@Test
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
		verify(mockTransactionDAO, times(1)).save(argument.capture());

		assertEquals(-amount, argument.getValue().getAmount(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(description, argument.getValue().getDescription());
	}
	
}
