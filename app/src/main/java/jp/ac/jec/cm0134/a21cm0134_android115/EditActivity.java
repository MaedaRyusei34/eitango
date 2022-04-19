package jp.ac.jec.cm0134.a21cm0134_android115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    public static final String TABLE_NAME = "CARD";
    public ArrayList<Card> i = new ArrayList<Card>();
//    int forId;
//    private ArrayList<String> kore = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        kore.add("apple");
//        kore.add("banana");
//        kore.add("lemon");

        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(this);
        i = helper.getAllCard();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtJapanese = findViewById(R.id.edtJapanese);
                EditText edtEnglish = findViewById(R.id.edtEnglish);
                String English = edtEnglish.getText().toString();
                String Japanese = edtJapanese.getText().toString();
                CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(EditActivity.this);
                i = helper.getAllCard();

//                helper.isExistWord(English);



                if (isExistWord(English) == true) {
                    Toast.makeText(EditActivity.this, "重複した値です", Toast.LENGTH_LONG).show();
                    return;
                } else if (English.equals("") || Japanese.equals("") ) {
                    Toast.makeText(EditActivity.this, "文字を入力して下さい", Toast.LENGTH_LONG).show();
                    return;

//                if (English.equals("")) {
//                    Toast.makeText(EditActivity.this,"文字を入力して下さい",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (Japanese.equals("")){
//                    Toast.makeText(EditActivity.this,"文字を入力して下さい",Toast.LENGTH_LONG).show();
//                    return;
//                }

                } else {
//                    kore.add(English);
                    try {

                        SQLiteDatabase database = helper.getWritableDatabase();
//                        forId = i.size();
//                        forId++;
//                        String i = idNumber + English + Japanese;
//                        helper.isExistWord(i);
                        database.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, " + "'" + English + "'" + ", " + "'" + Japanese + "'" + ");");
                        Log.i("aaaa","INSERT INTO " + TABLE_NAME + " VALUES (" + i.size() + ", " + "'" + English + "'" + ", " + "'" + Japanese + "'" + ");");
                        Toast.makeText(EditActivity.this,"登録しました",Toast.LENGTH_LONG).show();
                    } catch (SQLException e) {
                        Toast.makeText(EditActivity.this,"エラー",Toast.LENGTH_LONG).show();
                        Log.i("aaaa","INSERT INTO " + TABLE_NAME + " VALUES (" + i.size() + ", " + "'" + English + "'" + ", " + "'" + Japanese + "'" + ");");
                        Log.i("aaaa","INSERT INTO " + TABLE_NAME + " VALUES (3,'lemon','レモン')");
                    }

                }




            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("確認");
                builder.setMessage("データを削除してよろしいですか？");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String a = "drop table CARD;";
                        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(EditActivity.this);

                        try {
                            SQLiteDatabase database = helper.getReadableDatabase();

                            database.execSQL(a);
                            Toast.makeText(EditActivity.this,"データを削除しました",Toast.LENGTH_LONG).show();

                            CardSQLiteOpenHelper w = new CardSQLiteOpenHelper(EditActivity.this);
                            SQLiteDatabase q = w.getReadableDatabase();
                            w.onCreate(q);
//                            kore.clear();
//                            kore.add("apple");
//                            kore.add("banana");
//                            kore.add("lemon");
//                            forId = 3;



                        } catch (Exception e) {
                            Toast.makeText(EditActivity.this,"エラー",Toast.LENGTH_LONG).show();
                            Log.i("aaaa",a);
                        }
                    }
                });
                builder.setNegativeButton("CANSEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EditActivity.this,"キャンセルしました",Toast.LENGTH_LONG).show();
                    }
                });builder.show();
            }
        });



        Button btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean isExistWord(String a) {
        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(EditActivity.this);


        try {
            String[] column = new String[]{"english"};
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("CARD",
                     column,
                    "english = ?",
                    new String[]{a},
                    null,
                    null,
                    null);

            if (cursor.moveToNext()){
                cursor.close();
                return true;
            }
        } finally {
            helper.close();
        }
        return false;
    }
}