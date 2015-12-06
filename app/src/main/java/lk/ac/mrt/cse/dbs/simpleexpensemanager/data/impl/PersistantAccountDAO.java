package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.dblayer.DBhandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by Pamoda on 12/6/2015.
 */
public class PersistantAccountDAO implements AccountDAO {

    SQLiteDatabase db=null;
    DBhandler dbhandler=null;

    public  PersistantAccountDAO(Context context){
        if (dbhandler==null){
            dbhandler=new DBhandler(context);
        }
        db=dbhandler.getWritableDatabase();
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor accountList = db.query(DBhandler.TABLE_Account, new String[]{DBhandler.accountNo}, null, null, null, null, null);

        List<String> accList=new ArrayList<String>();
        while(accountList.moveToNext()){
            accList.add(accountList.getString(accountList.getColumnIndex(DBhandler.accountNo)));
        }

        return accList;
    }

    @Override
    public List<Account> getAccountsList() {

        Cursor accountList = db.query(DBhandler.TABLE_Account, new String[]{DBhandler.accountNo}, null, null, null, null, null);

        List<Account> accList=new ArrayList<Account>();
        while(accountList.moveToNext()){
            String accountNo=accountList.getString(accountList.getColumnIndex(DBhandler.accountNo));
            String bankName=accountList.getString(accountList.getColumnIndex(DBhandler.bankName));
            String accHolderName=accountList.getString(accountList.getColumnIndex(DBhandler.accountHolderName));
            double balance=accountList.getDouble(accountList.getColumnIndex(DBhandler.balance));
            accList.add(new Account(accountNo,bankName,accHolderName,balance));
        }

        return accList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        Cursor accountList = db.query(DBhandler.TABLE_Account,null,"accountNo=?",new String[]{accountNo},null,null,null);
        String bankName=accountList.getString(accountList.getColumnIndex(DBhandler.bankName));
        String accHolderName=accountList.getString(accountList.getColumnIndex(DBhandler.accountHolderName));
        double balance=accountList.getDouble(accountList.getColumnIndex(DBhandler.balance));
        return new Account(accountNo,bankName,accHolderName,balance);

    }

    @Override
    public void addAccount(Account account) {
        ContentValues values =new ContentValues();
        values.put(dbhandler.accountNo, account.getAccountNo());
        values.put(dbhandler.bankName, account.getBankName());
        values.put(dbhandler.accountHolderName, account.getAccountHolderName());
        values.put(dbhandler.balance, account.getBalance());
        db.insert(dbhandler.TABLE_Account,null,values);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
       db.delete(DBhandler.TABLE_Account, DBhandler.accountNo+ " = " + accountNo, null);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        ContentValues values = new ContentValues();
        values.put(dbhandler.balance, amount);
        db.update(dbhandler.TABLE_Account,values, dbhandler.accountNo + "=?", new String[]{accountNo});
    }

}
