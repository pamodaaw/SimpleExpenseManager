package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

/*
 * Created by Pamoda on 12/6/2015.
 */

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistantAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistantTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistantExpenseManager extends ExpenseManager {


    Context context;
    public PersistantExpenseManager(Context context) throws ExpenseManagerException {
        this.context=context;
        setup();
    }
    public void setup() {
        TransactionDAO persistantTransactionDAO = new PersistantTransactionDAO(context);
        setTransactionsDAO(persistantTransactionDAO);

        AccountDAO persistantAccountDAO = new PersistantAccountDAO(context);
        setAccountsDAO(persistantAccountDAO);



        /*** End ***/
    }
}
