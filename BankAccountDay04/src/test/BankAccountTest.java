/**
 * 
 */
package test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;
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

	@Test
	public void testOpenAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> captorSaveAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockDAO).save(captorSaveAccount.capture());
		assertEquals(captorSaveAccount.getValue().getBalance(), 0.0, 0.01);
		assertEquals((captorSaveAccount.getValue()).getAccountNumber(), accountNumber);
	}

}
