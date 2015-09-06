package com.github.fumio_shimamura.dice01;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // プログレスバーの値
    private int bar1value;
    private int bar2value;
    // スタートボタンの有効／無効を管理する
    private boolean onStart;
    // ゲーム中であることのフラグ。ゲームボタンの有効／無効を管理する。
    private boolean onGame;

    public static final long TIME_TO_START = 2000;
    public static final long TIME_TO_FINISH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void changeBar1(View view){

        if(!onGame){
            return;
        }
        bar1value += 2;
        SeekBar bar = (SeekBar)findViewById(R.id.seekBar1);
        bar.setProgress(bar1value);

        //String str = String.valueOf(bar1value);
        //Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    public void changeBar2(View view){

        if(!onGame){
            return;
        }
        bar2value += 2;
        SeekBar bar = (SeekBar)findViewById(R.id.seekBar2);
        bar.setProgress(bar2value);
    }


    // スタートボタン押下時の処理
    public void startGame(View view){

        if (onStart) {
            return;
        }

        SeekBar bar1 = (SeekBar)findViewById(R.id.seekBar1);
        SeekBar bar2 = (SeekBar)findViewById(R.id.seekBar2);
        bar1value = 0;
        bar2value = 0;
        bar1.setProgress(bar1value);
        bar2.setProgress(bar2value);

        // カウントダウン開始
        new CountDownTimer(TIME_TO_START, 1000) {

            TextView tv = (TextView)findViewById(R.id.textView);
            public void onTick(long millisUntilFinished) {
                tv.setText("Ready...");
            }

            public void onFinish() {
                tv.setText("Go!");
                // ゲームスタートする
                playGame();
            }
        }.start();
        onStart = true;
    }

    // ゲーム中の処理
    public void playGame(){

        if(onGame){
            return;
        }

        // カウントダウン開始
        new CountDownTimer(TIME_TO_FINISH, 1000) {

            int count = 0;
            TextView tv = (TextView)findViewById(R.id.textView);
            public void onTick(long millisUntilFinished) {
                count += 1;
                if (count == 2) {
                    tv.setText(" ");
                } else if (count > 2) {
                    tv.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            public void onFinish() {
                if (bar1value > bar2value) {
                    tv.setText("1P Win!");
                }else if(bar2value > bar1value) {
                    tv.setText("2P Win!");
                }else {
                    tv.setText("Draw!");
                }
                onGame = false;
                onStart = false;
            }
        }.start();
        onGame = true;
    }
}
