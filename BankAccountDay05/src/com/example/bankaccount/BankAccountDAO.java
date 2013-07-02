/**
 * 
 */
package com.example.bankaccount;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author quynhlt
 * 
 */
public class BankAccountDAO {
	private SQLiteDatabase db;

	public BankAccountDAO(Context context) {
		DBHelper openHelper = new DBHelper(context);
		this.db = openHelper.getWritableDatabase();
	}

	public long save(BankAccountDTO bankAccountDTO) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.ACCOUNT_NUMBER, bankAccountDTO.getAccountNumber());
		values.put(DBHelper.BALANCE, bankAccountDTO.getBalance());
		values.put(DBHelper.OPEN_TIME_STAMP, bankAccountDTO.getTimeStamp());
		return db.insert(DBHelper.TABLE_ACCOUNT, null, values);
	}

	public int getRecordSize() {
		List<String> results = new ArrayList<String>();
		Cursor query = db.query(DBHelper.TABLE_ACCOUNT, null, null, null, null, null, null);
		while (query.moveToNext()) {
			results.add(query.getString(query.getColumnIndexOrThrow(DBHelper.ACCOUNT_NUMBER)));
		}
		query.close();
		return results.size();
	}

	public BankAccountDTO get(String accountNumber) {
		BankAccountDTO bankAccount = null;
		Cursor cursor = db.query(DBHelper.TABLE_ACCOUNT, new String[] { DBHelper.KEY_ID, DBHelper.ACCOUNT_NUMBER, DBHelper.BALANCE,
				DBHelper.OPEN_TIME_STAMP }, DBHelper.ACCOUNT_NUMBER + "=?", new String[] { accountNumber }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			bankAccount = new BankAccountDTO(cursor.getString(cursor.getColumnIndex(DBHelper.ACCOUNT_NUMBER)));
			bankAccount.setBalance(cursor.getInt(cursor.getColumnIndex(DBHelper.BALANCE)));
			bankAccount.setTimeStamp(cursor.getLong(cursor.getColumnIndex(DBHelper.OPEN_TIME_STAMP)));
		}
		return bankAccount;
	}
}
