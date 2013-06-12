/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
	public void testOpenNewAccount() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(openAccount.capture());
		assertEquals(accountNumber, (openAccount.getValue()).getAccountNumber());
		assertEquals(0.0, openAccount.getValue().getBalance(), 0.01);
	}

	@Test
	public void testDepositTransaction() {
		String accountNumber = "1234567890";
		float amount = 200F;
		String description = "Deposit with amount is 200";
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		when(mockBankAccountDAO.getByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
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
		when(mockBankAccountDAO.getByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		Long timeStamp = mockCalendar.getTimeInMillis();
		BankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).createTransaction(argument.capture());
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
		when(mockBankAccountDAO.getByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccount.withdraw(accountNumber, amount, description);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(expected, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	@Test
	public void testWithdrawShouldBeSaveToDB() {
		String accountNumber = "1234567890";
		float balance = 100F;
		float amount = 50F;
		String description = "Withdraw with amount is 50";
		float expected = balance - amount;
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber, balance);
		when(mockBankAccountDAO.getByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		Long timeStamp = mockCalendar.getTimeInMillis();
		BankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor.forClass(TransactionDTO.class);
		verify(mockTransactionDAO, times(1)).createTransaction(argument.capture());
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(expected, argument.getValue().getAmount());
		assertEquals(description, argument.getValue().getDescription());
		assertEquals(timeStamp, argument.getValue().getTimestamp());
	}

	@Test
	public void testGetAccountByAccountNumber() {
		String accountNumber = "1234567890";
		BankAccountDTO bankAccount = new BankAccountDTO(accountNumber);
		when(mockBankAccountDAO.getByAccountNumber(bankAccount.getAccountNumber())).thenReturn(bankAccount);
		BankAccountDTO bankActual = BankAccount.getByAccountNumber(bankAccount.getAccountNumber());
		verify(mockBankAccountDAO, times(1)).getByAccountNumber(bankAccount.getAccountNumber());
		assertEquals(bankAccount, bankActual);
	}

}
