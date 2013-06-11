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

	@Test
	public void testOpenAccountShouldSaveToDatabase() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(openAccount.capture());
		assertEquals((openAccount.getValue()).getAccountNumber(), accountNumber);
	}

	@Test
	public void testOpenAccountHasZeroBalanceAndIsPersistent() {
		String accountNumber = "1234567890";
		BankAccount.openAccount(accountNumber);
		ArgumentCaptor<BankAccountDTO> openAccount = ArgumentCaptor.forClass(BankAccountDTO.class);
		verify(mockBankAccountDAO, times(1)).save(openAccount.capture());
		assertEquals(openAccount.getValue().getBalance(), 0.0, 0.01);
	}

	@Test
	public void testBalanceHasChangedWhenDeposit() {
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		String accountNumber = "1234567890";
		float amountDefault = 0F;
		float amountDeposit = 50F;
		float amountExpected = amountDefault + amountDeposit;
		BankAccountDTO accountDTO = createAccount(accountNumber, amountDefault);
		String description = "Deposit with amount is 200";
		BankAccount.dotransaction(accountDTO, amountDeposit, description);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), amountExpected, 0.01);
		assertEquals(argument.getValue().getDescription(), description);
	}

	@Test
	public void testBalanceHasChangedWhenWithdraw() {
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		String accountNumber = "1234567890";
		float amountDefault = 200F;
		float amountWidraw = 50F;
		float amountExpected = amountDefault - amountWidraw;
		BankAccountDTO accountDTO = createAccount(accountNumber, amountDefault);
		String description = "Deposit with amount is 200";
		BankAccount.dotransaction(accountDTO, -amountWidraw, description);
		verify(mockBankAccountDAO, times(1)).save(argument.capture());
		assertEquals(argument.getValue().getBalance(), amountExpected, 0.01);
		assertEquals(argument.getValue().getDescription(), description);
	}

	@Test
	public void testCanGetAccountByAccountNumber() {
		String accountNumber = "1234567890";
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor.forClass(BankAccountDTO.class);
		BankAccount.findAccountByAccountNumber(accountNumber);
		verify(mockBankAccountDAO, times(1)).findAccountByAccountNumber(argument.capture());
		assertEquals((argument.getValue()).getAccountNumber(), accountNumber);
	}

	private BankAccountDTO createAccount(String accountNumber, float balance) {
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountNumber(accountNumber);
		account.setBalance(balance);
		account.setOpenTimestampt(System.currentTimeMillis());
		return account;
	}

}
