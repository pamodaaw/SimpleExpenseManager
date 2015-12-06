package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.dblayer;

/**
 * Created by Pamoda on 12/6/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhandler  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "130676E.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_Account ="Account";
    public static final String accountNo="accountNo";
    public static final String bankName="bankName";
    public static final String accountHolderName="accountHolderName";
    public static final  String balance="balance";

    public static final String TABLE_Transaction ="Transaction";
    public static final String expenseType="expenseType";
    public static String amount="amount";
    public static final String date="date";
    public static String transactionId="transactionId";

    public DBhandler(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static final String CREATE_account ="CREATE TABLE IF NOT EXISTS "+ TABLE_Account +"(" +
            accountNo+" varchar(10) PRIMARY KEY," +
            bankName+" varchar(20),"+
            accountHolderName+" varchar(30),"+
            balance+" double"+")";

    public static final String CREATE_transaction ="CREATE TABLE IF NOT EXISTS "+ TABLE_Transaction +"(" +
            accountNo+" varchar(10)," +
            transactionId+" varchar(20) PRIMARY KEY AUTO INCREMENT,"+
            date+" date,"+
            expenseType+" varchar(7),"+
            amount+" double"+")";




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_account);
        db.execSQL(CREATE_transaction);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Account);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Transaction);

        onCreate(db);

    }

}
