package com.example.menuorder_a1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class OrderContentProviders extends ContentProvider {
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "customerDB";
    static final String TABLE_NAME = "order";
    static final int DATABASE_VERSION = 1;
    static final String PROVIDER_NAME = "com.example.menuorder_a1.provider";
    static final String URL = "content://" + PROVIDER_NAME + "/order";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String QredVelvet = "QredVelvet";
    static final String Qhazelnut = "Qhazelnut";
    static final String Qtruffle = "Qtruffle";
    static final int uriCode = 1;
    static UriMatcher uriMatcher;
    private static HashMap<String, String> values;
    static final String CREATE_DB2_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (QredVelvet TEXT NOT NULL, "
            + " Qhazelnut TEXT NOT NULL, "
            + " Qtruffle TEXT NOT NULL)";
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB2_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "order", uriCode);
        //uriMatcher.addURI(PROVIDER_NAME, "users/*", uriCode);
    }

    public OrderContentProviders() {
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "com.example.menuorder_a1.provider/order";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        OrderContentProviders.DatabaseHelper dbHelper = new OrderContentProviders.DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db.insert(TABLE_NAME, null, values);
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
