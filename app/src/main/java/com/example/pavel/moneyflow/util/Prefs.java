package com.example.pavel.moneyflow.util;

import android.net.Uri;

public class Prefs {
    public static final String LOG_TAG = "MoneyFlow";

    //------------------------Provider----------------------------
    public static final String AUTHORITY = "com.example.pavel.moneyflow.provider";
    public static String URI_TYPE_EXPENSE = "expenses";
    public static String URI_TYPE_EXPENSE_NAME = "expenses_name";
    public static String URI_TYPE_EXPENSES_JOINED = "expenses_joined";
    public static final Uri URI_EXPENSE = Uri.parse("content://" + AUTHORITY + "/" + URI_TYPE_EXPENSE);
    public static final Uri URI_EXPENSE_NAME = Uri.parse("content://" + AUTHORITY + "/" + URI_TYPE_EXPENSE_NAME);
//    public static final Uri URI_EXPENSE_NAME_ID = Uri.parse("content://" + AUTHORITY + "/" + URI_TYPE_EXPENSE_NAME + "/#");
    public static final Uri URI_EXPENSE_JOINED = Uri.parse("content://" + AUTHORITY + "/" + URI_TYPE_EXPENSES_JOINED);

    //--------------------DB Constants-----------------------------------
    public static final String DB_NAME = "MoneyFlowDB";
    public static final int DB_CURRENT_VERSION = 1;
    public static final String FIELD_ID = "_id";

    //___________________EXPENSES TABLE ____________________________
    public static final String TABLE_EXPENSES = "expenses";
    public static final String EXPENSE_FIELD_ID_PASSIVE = "id_passive";
    public static final String EXPENSE_FIELD_VOLUME = "volume";
    public static final String EXPENSE_FIELD_DATE = "date";

    //_________________EXPENSES NAMES TABLE_______________________
    public static final String TABLE_EXPENSES_NAMES = "expenses_names";
    public static final String EXPENCE_NAMES_FIELDS_NAME = "name";

    //_________________Loader Constants_________________________________
    public static final int ID_LOADER_EXPENSE_NAMES = 1;

}
