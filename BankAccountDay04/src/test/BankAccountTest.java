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
	private BankAccountDAO mockDAO = mock(BankAccountDAO.class);

	@Before
	public void setUp() {
		reset(mockDAO);
		BankAccount.setBankAccountDAO(mockDAO);
	}

	// Step 1 and 2
	@Test
	public void testOpenAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> captorSaveAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockDAO).save(captorSaveAccount.capture());
		assertEquals(captorSaveAccount.getValue().getBalance(), 0.0, 0.01);
		assertEquals((captorSaveAccount.getValue()).getAccountNumber(), accountNumber);
	}

	// Step 3
	@Test
	public void testAfterDepositBalanceChangeAndIsPersistent() {
		String accountNumber = "1234567890";
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		float amount = 200F;
		String description = "deposit with amount is 200";
		BankAccount.dotransaction(accountDTO, amount, description);
		verify(mockDAO, times(2)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), amount, 0.01);
		assertEquals(argument.getValue().getDescription(), description);
	}

	// Step 5
	@Test
	public void testAfterWithdrawBalanceChangeAndIsPersistent() {
		String accountNumber = "1234567890";
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		BankAccountDTO accountDTO = BankAccount.openAccount(accountNumber);
		float amount = 200F;
		String description = "deposit with amount is 200";
		BankAccount.dotransaction(accountDTO, amount, description);
		verify(mockDAO, times(2)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), amount, 0.01);
		assertEquals(argument.getValue().getDescription(), description);

		amount = -50F;
		description = "withdraw with amount is 50";
		BankAccount.dotransaction(accountDTO, amount, description);
		verify(mockDAO, times(3)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), 150L, 0.01);
		assertEquals(argument.getValue().getDescription(), description);
	}
}
