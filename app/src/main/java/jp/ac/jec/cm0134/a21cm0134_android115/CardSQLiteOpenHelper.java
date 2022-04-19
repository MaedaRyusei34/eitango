package jp.ac.jec.cm0134.a21cm0134_android115;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CardSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CARD_DB";
    public static final int version = 1;
    public static final String TABLE_NAME = "CARD";

    public CardSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "english TEXT, japanese TEXT)");

        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (1,'apple', 'リンゴ')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (2,'banana','バナナ')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (3,'lemon','レモン')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public ArrayList<Card> getAllCard() {

        ArrayList<Card> ary = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if (db == null) {
            return null;
        }

        try {
            String[] column = new String[]{"japanese", "english", "_id"};

            Cursor cur = db.query(TABLE_NAME, column, null, null, null, null, null);

            while (cur.moveToNext()) {
                Card tmp = new Card(cur.getString(0), cur.getString(1), cur.getInt(2));
                ary.add(tmp);
            }

            cur.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return  ary;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

//    public boolean isExistWord(String a) {
//        SQLiteDatabase db = getReadableDatabase();
//        try {
//            String t = "SELECT count(*) FROM CARD WHERE english = " + a + ";";
//            db.execSQL(t);
//        } finally {
//            db.close();
//        }
//        return true;
//    }

    public boolean insertCard(Card newCard) {
        long ret;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("english", newCard.getEnglish());
            values.put("japanese", newCard.getJapanese());

            ret = db.insert("CARD", null, values);
        } finally {
            db.close();
        }
        return ret != -1;
    }


    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}


