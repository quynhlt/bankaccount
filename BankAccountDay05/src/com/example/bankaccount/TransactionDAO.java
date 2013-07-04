/**
 * 
 */
package com.example.bankaccount;

import java.util.List;

import android.content.Context;
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

	public long save(TransactionDTO transaction) {
		return 0;
	}

	public List<TransactionDTO> get(String accountNumber) {
		return null;
	}

	public List<TransactionDTO> get(String accountNumber, Long startTime, Long stopTime) {
		return null;
	}

	public List<TransactionDTO> get(String accountNumber, int n) {
		return null;
	}
}
