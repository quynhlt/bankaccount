/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import main.BankAccount;
import main.BankAccountDAO;
import main.BankAccountDTO;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author quynhlt
 * 
 */
public class BankAccountTest {

	private BankAccountDAO mockBankAccountDAO = mock(BankAccountDAO.class);

	@Before
	public void setUp() {
		reset(mockBankAccountDAO);
		BankAccount.setBankAccountDAO(mockBankAccountDAO);
	}

	// Step 1 and 2
	@Test
	public void testOpenAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> captorSaveAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO).save(captorSaveAccount.capture());
		assertEquals(captorSaveAccount.getValue().getBalance(), 0.0, 0.01);
		assertEquals((captorSaveAccount.getValue()).getAccountNumber(), accountNumber);
	}

	@Test
	public void transactionAndIsPersistent() {
		// open account
		String accountNumber = "1234567890";
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);

		// deposit
		float amount = 200F;
		String description = "Deposit with amount is 200";
		BankAccount.dotransaction(accountDTO, amount, description);
		verify(mockBankAccountDAO, times(2)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), amount, 0.01);
		assertEquals(argument.getValue().getDescription(), description);

		// withdraw
		amount = -50F;
		description = "Withdraw with amount is 50";
		BankAccount.dotransaction(accountDTO, amount, description);
		verify(mockBankAccountDAO, times(3)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), 150F, 0.01);
		assertEquals(argument.getValue().getDescription(), description);
	}

	@Test
	public void canGetAccountByAccountNumber() {
		String accountNumber = "1234567890";
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		BankAccount.findAccountByAccountNumber(accountNumber);
		verify(mockBankAccountDAO).save(argument.capture());
		assertEquals((argument.getValue()).getAccountNumber(), accountNumber);
	}
}
