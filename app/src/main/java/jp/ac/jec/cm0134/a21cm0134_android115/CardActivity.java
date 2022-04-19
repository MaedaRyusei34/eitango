package jp.ac.jec.cm0134.a21cm0134_android115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {

    private ArrayList<Card> ary = new ArrayList<Card>();
    private int pos = 0;
    private int answer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        CardSQLiteOpenHelper helper = new CardSQLiteOpenHelper(this);
        ary = helper.getAllCard();


//        ary.add(new Card("ペン","pen",1));
//        ary.add(new Card("朝","morning",2));
//        ary.add(new Card("昼","lunch",3));
//        ary.add(new Card("リンゴ","apple",4));
//        ary.add(new Card("バナナ","banana",5));
//        ary.add(new Card("学校","school",6));


        dispOneCard();

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = 0;
                if (pos == ary.size() - 1){
                    Toast.makeText(CardActivity.this,"お疲れ様でした",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    pos++;
                    dispOneCard();

                    Button btnPrev = findViewById(R.id.btnPrev);
                    btnPrev.setEnabled(true);
                }
            }
        });

        Button btnAnswer = findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtJapanese = findViewById(R.id.txtJapanese);
                if (answer == 0){
                    txtJapanese.setVisibility(View.VISIBLE);
                    answer++;
                } else {
                    txtJapanese.setVisibility(View.INVISIBLE);
                    answer--;
                }

            }
        });

        Button btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setEnabled(false);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = 0;
                if (pos == 0){
                    Toast.makeText(CardActivity.this,"最初のカードです",Toast.LENGTH_SHORT).show();
                } else {
                    pos--;
                    dispOneCard();
                }
            }
        });

    }

    private void dispOneCard(){
        Card temp = ary.get(pos);
        TextView txtNumbmer = findViewById(R.id.txtNumber);
        txtNumbmer.setText(temp.getId() + "問目/全"  + ary.size() + "門中");
        TextView txtEnglish = findViewById(R.id.txtEnglish);
        txtEnglish.setText(temp.getEnglish());
        TextView txtJapanese = findViewById(R.id.txtJapanese);
        txtJapanese.setText(temp.getJapanese());
        txtJapanese.setVisibility(View.INVISIBLE);
    }
}