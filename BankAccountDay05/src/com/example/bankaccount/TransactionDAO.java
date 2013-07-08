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
public class TransactionDAO {
	private SQLiteDatabase db;

	public TransactionDAO(Context context, String name) {
		DBHelper openHelper = new DBHelper(context, name);
		this.db = openHelper.getWritableDatabase();
	}

	public long insert(TransactionDTO transaction) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.ACCOUNT_NUMBER, transaction.getAccountNumber());
		values.put(DBHelper.AMOUNT, transaction.getAmount());
		values.put(DBHelper.OPEN_TIME_STAMP, transaction.getTimestamp());
		values.put(DBHelper.DESCRIPTION, transaction.getDescription());
		return db.insert(DBHelper.TABLE_TRANSACTION, null, values);
	}

	public List<TransactionDTO> get(String accountNumber) {
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();
		Cursor cursor = db.query(DBHelper.TABLE_TRANSACTION, new String[] { DBHelper.KEY_ID, DBHelper.ACCOUNT_NUMBER, DBHelper.AMOUNT,
				DBHelper.OPEN_TIME_STAMP, DBHelper.DESCRIPTION }, DBHelper.ACCOUNT_NUMBER + "=?", new String[] { accountNumber }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			TransactionDTO transactionDTO = new TransactionDTO();
			transactionDTO.setAccountNumber(cursor.getString(cursor.getColumnIndex(DBHelper.ACCOUNT_NUMBER)));
			transactionDTO.setAmount(cursor.getLong(cursor.getColumnIndex(DBHelper.AMOUNT)));
			transactionDTO.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION)));
			transactionDTO.setTimestamp(cursor.getLong(cursor.getColumnIndex(DBHelper.OPEN_TIME_STAMP)));
			list.add(transactionDTO);
		} else {
			list = null;
		}
		return list;
	}

	public List<TransactionDTO> get(String accountNumber, Long startTime, Long stopTime) {
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();
		String[] columns = new String[] { DBHelper.KEY_ID, DBHelper.ACCOUNT_NUMBER, DBHelper.AMOUNT, DBHelper.OPEN_TIME_STAMP, DBHelper.DESCRIPTION };
		String whereClause = DBHelper.ACCOUNT_NUMBER + "=? and " + DBHelper.OPEN_TIME_STAMP + ">=? and " + DBHelper.OPEN_TIME_STAMP + "<=?";
		String[] whereArgs = new String[] { accountNumber, String.valueOf(startTime), String.valueOf(stopTime) };
		Cursor cursor = db.query(DBHelper.TABLE_TRANSACTION, columns, whereClause, whereArgs, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setAccountNumber(cursor.getString(cursor.getColumnIndex(DBHelper.ACCOUNT_NUMBER)));
				transactionDTO.setAmount(cursor.getLong(cursor.getColumnIndex(DBHelper.AMOUNT)));
				transactionDTO.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION)));
				transactionDTO.setTimestamp(cursor.getLong(cursor.getColumnIndex(DBHelper.OPEN_TIME_STAMP)));
				list.add(transactionDTO);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public List<TransactionDTO> get(String accountNumber, int n) {
		List<TransactionDTO> list = new ArrayList<TransactionDTO>();
		String countQuery = "SELECT * FROM " + DBHelper.TABLE_TRANSACTION + " WHERE " + DBHelper.ACCOUNT_NUMBER + " = '" + accountNumber + "' LIMIT "
				+ n;
		Cursor cursor = db.rawQuery(countQuery, null);
		if (cursor.moveToFirst()) {
			do {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setAccountNumber(cursor.getString(cursor.getColumnIndex(DBHelper.ACCOUNT_NUMBER)));
				transactionDTO.setAmount(cursor.getLong(cursor.getColumnIndex(DBHelper.AMOUNT)));
				transactionDTO.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.DESCRIPTION)));
				transactionDTO.setTimestamp(cursor.getLong(cursor.getColumnIndex(DBHelper.OPEN_TIME_STAMP)));
				list.add(transactionDTO);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
