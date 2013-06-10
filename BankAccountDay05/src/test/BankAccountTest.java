/**
 * 
 */
package test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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
	public void openAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> captorSaveAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO).save(captorSaveAccount.capture());
		assertEquals(captorSaveAccount.getValue().getBalance(), 0.0, 0.01);
		assertEquals((captorSaveAccount.getValue()).getAccountNumber(), accountNumber);
	}

}
