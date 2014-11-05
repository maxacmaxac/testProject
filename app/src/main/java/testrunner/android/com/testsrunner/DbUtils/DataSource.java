package testrunner.android.com.testsrunner.DbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import testrunner.android.com.testsrunner.model.Email;


/**
 * Created by marjan on 11/1/2014.
 */
public class DataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] allColumns = {SQLHelper.COLUMN_ID, SQLHelper.COLUMN_EMAIL_NAME};

    public DataSource(Context context) {
        dbHelper = new SQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Insert new email in DB
     */
    public long insert(String emailName) {
        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_EMAIL_NAME, emailName);
        long insertId = database.insert(SQLHelper.TABLE_NAME, null,
                values);
        return insertId;
    }

    /**
     * Delete email from DB
     */
    public void delete(String name) {
        database.delete(SQLHelper.TABLE_NAME, SQLHelper.COLUMN_EMAIL_NAME + "= ?"
                , new String[]{name});
    }

    /**
     * Delete all emails from DB
     */
    public void deleteAll() {
        database.delete(SQLHelper.TABLE_NAME, null, null);
    }

    /**
     * Get all emails from DB
     */
    public List<Email> getAllData() {
        List<Email> resultDataArrayList = new ArrayList<Email>();

        Cursor cursor = database.query(SQLHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Email resultData = cursorToResluts(cursor);
            resultDataArrayList.add(resultData);
            cursor.moveToNext();
        }
        cursor.close();
        return resultDataArrayList;
    }

    public List<Email> getEmailByName(String name) {
        List<Email> resultDataArrayList = new ArrayList<Email>();

        Cursor cursor = database.query(SQLHelper.TABLE_NAME,
                allColumns, SQLHelper.COLUMN_EMAIL_NAME + "= ?", new String[]{name}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Email resultData = cursorToResluts(cursor);
            resultDataArrayList.add(resultData);
            cursor.moveToNext();
        }
        cursor.close();
        return resultDataArrayList;
    }

    private Email cursorToResluts(Cursor cursor) {
        Email email = new Email();
        email.setId(cursor.getInt(0));
        email.setEmailName(cursor.getString(1));
        return email;
    }
}
