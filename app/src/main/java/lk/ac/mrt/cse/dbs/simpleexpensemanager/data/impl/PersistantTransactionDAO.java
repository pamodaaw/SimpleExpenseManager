package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.dblayer.DBhandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Pamoda on 12/6/2015.
 */
public class PersistantTransactionDAO implements TransactionDAO {

    SQLiteDatabase db=null;
    DBhandler dbhandler=null;

    public  PersistantTransactionDAO(Context context){
        if (dbhandler==null){
            dbhandler=new DBhandler(context);
        }
        db=dbhandler.getWritableDatabase();
    }



    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String dateString = df.format(date);
        ContentValues values = new ContentValues();
        values.put(dbhandler.date,dateString);
        values.put(dbhandler.accountNo,accountNo);
        values.put(dbhandler.expenseType,expenseType.name());
        values.put(dbhandler.amount, String.valueOf(amount));
        db.insert(dbhandler.TABLE_Transaction,null,values);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor c = db.query(DBhandler.TABLE_Account,null, null, null, null, null, null);

        List<Transaction> transList=new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                transList.add(cursorToTransaction(c));
            } while (c.moveToNext());
        }

        return transList;
    }

    private Transaction cursorToTransaction(Cursor cursor) {
        String dateString =cursor.getString(cursor.getColumnIndex(dbhandler.date));
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = df.parse(dateString);
        }
        catch (ParseException ex){
            Log.w("DateFormatError", ex.getMessage());
        }
        String accountno = cursor.getString(cursor.getColumnIndex(dbhandler.accountNo));
        String expenseType=cursor.getString(cursor.getColumnIndex(dbhandler.expenseType));
        double amount=cursor.getDouble(cursor.getColumnIndex(dbhandler.amount));
        Transaction transaction = new Transaction(date,accountno,ExpenseType.valueOf(expenseType),amount);

        return transaction;
    }
    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        List<Transaction> transactions = new ArrayList<>();
        transactions=getAllTransactionLogs();

        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }
}
