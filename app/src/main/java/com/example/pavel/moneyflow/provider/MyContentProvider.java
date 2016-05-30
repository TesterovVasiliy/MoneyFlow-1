package com.example.pavel.moneyflow.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.pavel.moneyflow.db.DBHelper;
import com.example.pavel.moneyflow.util.Prefs;

public class MyContentProvider extends ContentProvider {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private static final int URI_CODE_EXPENSE = 1;
    private static final int URI_CODE_EXPENSE_NAME = 2;
    private static final int URI_CODE_EXPENSE_NAME_ID = 20;
    private static final int URI_CODE_EXPENSE_JOIN = 3;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(Prefs.AUTHORITY, Prefs.URI_TYPE_EXPENSE, URI_CODE_EXPENSE);
        matcher.addURI(Prefs.AUTHORITY, Prefs.URI_TYPE_EXPENSE_NAME, URI_CODE_EXPENSE_NAME);
        matcher.addURI(Prefs.AUTHORITY, Prefs.URI_TYPE_EXPENSE_NAME + "/#", URI_CODE_EXPENSE_NAME_ID);
        matcher.addURI(Prefs.AUTHORITY, Prefs.URI_TYPE_EXPENSES_JOINED, URI_CODE_EXPENSE_JOIN);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), Prefs.DB_CURRENT_VERSION);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = 0;
        switch (matcher.match(uri)) {
            case URI_CODE_EXPENSE:
                database = dbHelper.getWritableDatabase();
                result = database.delete(Prefs.TABLE_EXPENSES, selection, selectionArgs);
                break;
            case URI_CODE_EXPENSE_NAME:
                database = dbHelper.getWritableDatabase();
                result = database.delete(Prefs.TABLE_EXPENSES_NAMES, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri -> " + uri);
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        database = dbHelper.getWritableDatabase();
        Uri insertUri = null;
        long id;

        switch (matcher.match(uri)) {
            case URI_CODE_EXPENSE:
                id = database.insert(Prefs.TABLE_EXPENSES, null, values);
                insertUri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri,null);
                return insertUri;
            case URI_CODE_EXPENSE_NAME:
                id = database.insert(Prefs.TABLE_EXPENSES_NAMES, null, values);
                insertUri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri,null);
                return insertUri;
            default:
                throw new IllegalArgumentException("Unsupported uri -> " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (matcher.match(uri)) {
            case URI_CODE_EXPENSE:
                cursor = database.query(Prefs.TABLE_EXPENSES, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case URI_CODE_EXPENSE_NAME:
                cursor = database.query(Prefs.TABLE_EXPENSES_NAMES, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case URI_CODE_EXPENSE_JOIN:
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                qb.setTables(Prefs.TABLE_EXPENSES + " INNER JOIN " + Prefs.TABLE_EXPENSES_NAMES + " ON (" +
                        Prefs.TABLE_EXPENSES_NAMES + "." + Prefs.FIELD_ID + " = "
                        + Prefs.TABLE_EXPENSES + "." + Prefs.EXPENSE_FIELD_ID_PASSIVE + ")");
                cursor = qb.query(database, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri -> " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
