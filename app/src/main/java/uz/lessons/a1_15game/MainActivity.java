package uz.lessons.a1_15game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button[][] buttons;
    @BindView(R.id.rebutton)
    private Button rebutton;
    @BindView(R.id.quitbtn)
    private Button quitbutton;
    int[] solutionArray = { 1, 2, 3, 4, 5, 6,7,8,9,10, 15, 14, 13, 12, 11 };
    boolean win=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layot);
        ButterKnife.bind(this);

        rebutton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                reload();
                return true;
            }
        });
        LinearLayout container = findViewById(R.id.cntnr);
        buttons = new Button[container.getChildCount()][];
        for (int i = 0; i < container.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) container.getChildAt(i);
            buttons[i] = new Button[row.getChildCount()];
            for (int j = 0; j < row.getChildCount(); j++) {
                Button btn = (Button) row.getChildAt(j);
                buttons[i][j] = btn;
                btn.setOnClickListener(this);
            }
        }
        shuffleArray(solutionArray);
    }

    private void reload() {
        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j <4 &&(i*4+j)<14; j++) {
                buttons[i][j].setText(String.valueOf(i*4+j+1));
                buttons[i][j].setEnabled(true);
            }
        }
        buttons[3][2].setEnabled(false);
        buttons[3][2].setText("");
        buttons[3][3].setEnabled(true);
        buttons[3][3].setText("15");
    }

    private void shuffleArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4 ; j++) {
                if((i*4+j)<15) {
                    buttons[i][j].setText(String.valueOf(solutionArray[i * 4 + j]));
                }
                buttons[i][j].setEnabled(true);
            }


        }
        buttons[3][3].setEnabled(false);
        buttons[3][3].setText("");

    }
    /*
    private void swap(){

    }*/



    private void ij(int i, int j){
        if (i<3){
            if (!buttons[i+1][j].isEnabled() ){
                String tmp =buttons[i][j].getText().toString();
                buttons[i][j].setText("");
                buttons[i+1][j].setText(tmp);
                buttons[i][j].setEnabled(false);
                buttons[i+1][j].setEnabled(true);
            }
        }
        if (i>0){
            if (!buttons[i-1][j].isEnabled() ){
                String tmp =buttons[i][j].getText().toString();
                buttons[i][j].setText("");
                buttons[i-1][j].setText(tmp);
                buttons[i][j].setEnabled(false);
                buttons[i-1][j].setEnabled(true);
            }
        }
        if (j<3){
            if (!buttons[i][j+1].isEnabled() ){
                String tmp =buttons[i][j].getText().toString();
                buttons[i][j].setText("");
                buttons[i][j+1].setText(tmp);
                buttons[i][j].setEnabled(false);
                buttons[i][j+1].setEnabled(true);
            }
        }
        if (j>0){
            if (!buttons[i][j-1].isEnabled() ){
                String tmp =buttons[i][j].getText().toString();
                buttons[i][j].setText("");
                buttons[i][j-1].setText(tmp);
                buttons[i][j].setEnabled(false);
                buttons[i][j-1].setEnabled(true);
            }
        }

    }

    private void win(int l){
        win = true;
        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j < 4 && (i*4+j)<15; j++) {
                if (l==15&&(i*4+j)<14){
                    if(!buttons[i][j].getText().equals(String.valueOf(i*4+j+1))){
                        win= false;
                    }
                }
                if (l==12){
                    if(i==2&& j==3){
                        continue;
                    }
                    if(!buttons[i][j].getText().equals(String.valueOf(i*4+j+1))){
                        win = false;
                    }
                }

            }
        }
        if (win==true){

            for (int i = 0; i <4 ; i++) {
                for (int j = 0; j <4 ; j++) {
                    buttons[i][j].setText("");
                }
            }
            rebutton.setText("PLAY");
            buttons[1][3].setVisibility(View.GONE);
            buttons[2][3].setVisibility(View.GONE);
            buttons[1][0].setText("Y");
            buttons[1][1].setText("O");
            buttons[1][2].setText("U");
            buttons[2][0].setText("W");
            buttons[2][1].setText("I");
            buttons[2][2].setText("N");



            Toast.makeText(this, "Y O U   W I N", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.quitbtn,R.id.rebutton})
    public void onClick(View v) {
         Button btn = (Button) v;

        if (!buttons[3][2].isEnabled()){

            win(15);
        }

        if (!buttons[2][3].isEnabled()){
            win(12);
        }
        for (int i = 0; i <4 ; i++) {
            for (int j = 0; j <4 ; j++) {
                if (btn == buttons[i][j]){
                    ij(i,j);
                }
            }
        }
        if (btn==rebutton){
            buttons[1][3].setVisibility(View.VISIBLE);
            buttons[2][3].setVisibility(View.VISIBLE);
            shuffleArray(solutionArray);
            rebutton.setText("RESTART");
        }
        if (btn == quitbutton){
            finish();
        }
    }
}
