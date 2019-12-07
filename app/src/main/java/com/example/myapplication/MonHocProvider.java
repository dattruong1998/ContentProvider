package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.Nullable;

public class MonHocProvider extends ContentProvider {
    public static final String PROVIDER_NAME="com.example.myapplication";
    public static final Uri CONTENT_URI=Uri.parse("content://"+PROVIDER_NAME+"/monhoc");
    public static final String MAMONHOC="mamonhoc";
    public static final String TENMONHOC="tenmonhoc";
    public static final String SOCHI="sochi";
    private static final int MONHOC=1;
    private static final int MONHOC_ID=2;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"monhoc",MONHOC);
        uriMatcher.addURI(PROVIDER_NAME,"monhoc/#",MONHOC_ID);
    }
    private SQLiteDatabase mhdb;
    private static final String DATABASE_NAME="mh.db";
    private static final String DATABASE_TABLE="monhoc";
    private static final class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table monhoc(\n" +
                    "   mamonhoc int primary key,\n" +
                    "   tenmonhoc text,\n" +
                    "   sochi int\n" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
             db.execSQL("drop table if exists "+DATABASE_TABLE);
             onCreate(db);
        }
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
       int count=0;
       switch (uriMatcher.match(uri))
       {
           case MONHOC:
               count=mhdb.delete(DATABASE_TABLE,selection,selectionArgs);
               break;
           case MONHOC_ID:
               count=mhdb.delete(DATABASE_TABLE,MAMONHOC+" = "+uri.getPathSegments().get(1),selectionArgs);
               break;
       }
       return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long rowId=mhdb.insert(DATABASE_TABLE,null,values);
        if(rowId>0)
        {
            uri= ContentUris.withAppendedId(CONTENT_URI,rowId);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mhdb=new DBHelper(getContext()).getWritableDatabase();
        return !(mhdb==null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder sqLiteQueryBuilder=new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DATABASE_TABLE);
        if(uriMatcher.match(uri)==MONHOC_ID)
        {
            sqLiteQueryBuilder.appendWhere(MAMONHOC+" = "+uri.getPathSegments().get(1));
        }
        Cursor cursor=sqLiteQueryBuilder.query(mhdb,projection,selection,selectionArgs,null,null,sortOrder);
       return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
