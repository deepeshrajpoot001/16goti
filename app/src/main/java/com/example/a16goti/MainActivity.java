package com.example.a16goti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16goti.databinding.ActivityMainBinding;
import com.example.a16goti.databinding.BottomSheetLayoutBinding;
import com.example.a16goti.databinding.WinSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Point[] point= new Point[37];
    Animation scale,alpha,move,alphaRev,turn;
    Player p1,p2;
    ArrayList<Integer> movablePoint= new ArrayList<>();
    ArrayList<Integer> killableGoti = new ArrayList<>();
    ArrayList<Integer> killablePoint = new ArrayList<>();
    ImageView image;
    MediaPlayer mediaPlayer;
    TextView textView;
    int temp=-1;
    int temp2=-1;

    boolean moverFlag=true;
    boolean guiderFlag=false;
    boolean check=true;
    int player = 1;
    int oppositePlayer=2;



    public void guider(int tappedImage){



        if(!check&&tappedImage!=temp2){
            mediaPlayer = MediaPlayer.create(this,R.raw.error);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mediaPlayer= null;
                }
            });
            mediaPlayer.start();
            Toast.makeText(this, "please! kill goti again", Toast.LENGTH_SHORT).show();
            return;
        }



        if(point[tappedImage].getGoti()==1 && player==2) {
            mediaPlayer = MediaPlayer.create(this,R.raw.error);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mediaPlayer= null;
                }
            });
            mediaPlayer.start();
            guideRemoverWithAnimation();
            Toast.makeText(this, "please! tap on right goti", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(point[tappedImage].getGoti()==2 && player==1 ) {
            mediaPlayer = MediaPlayer.create(this,R.raw.error);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mediaPlayer= null;
                }
            });
            mediaPlayer.start();
            guideRemoverWithAnimation();
            Toast.makeText(this, "please! tap on right goti", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(point[tappedImage].getGoti()==0 ){
            mediaPlayer = MediaPlayer.create(this,R.raw.error);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mediaPlayer= null;
                }
            });
            mediaPlayer.start();
           guideRemoverWithAnimation();
          //  Toast.makeText(this, "please! tap on right goti", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaPlayer = MediaPlayer.create(this,R.raw.beap);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mediaPlayer= null;
            }
        });
        mediaPlayer.start();


        if(guiderFlag){
            for (int i = 0; i < movablePoint.size(); i++) {
                String name = "imageView" + movablePoint.get(i);
                int x = getResources().getIdentifier(name, "id", getPackageName());
                image = (ImageView) findViewById(x);
                image.setImageResource(0);
            }
        }
        movablePoint.clear();
        killablePoint.clear();
        killableGoti.clear();

        for (int i = 0; i < 8; i++) {
            if (point[tappedImage].getNextPointId()[i] != -1) {
                int nextPointGoti=point[point[tappedImage].getNextPointId()[i]].getGoti();
                if (nextPointGoti == 0&&check) {
                    String name = "imageView" + point[tappedImage].getNextPointId()[i];
                    int x = getResources().getIdentifier(name, "id", getPackageName());
                    image = (ImageView) findViewById(x);
                    name = "guide" + 1;
                    x = getResources().getIdentifier(name, "drawable", getPackageName());
                    image.setImageResource(x);
                    image.startAnimation(alpha);
                    movablePoint.add(point[tappedImage].getNextPointId()[i]);
                    killableGoti.add(-1);
                    killablePoint.add(-1);

                } else if (nextPointGoti  == oppositePlayer) {
                    int target = point[point[tappedImage].getNextPointId()[i]].getCurPointId();
                    if (point[target].getNextPointId()[i] != -1) {
                        if (point[point[target].getNextPointId()[i]].getGoti() == 0) {
                            String name = "imageView" + point[target].getNextPointId()[i];
                            int x = getResources().getIdentifier(name, "id", getPackageName());
                            image = (ImageView) findViewById(x);
                            name = "guide" + 2;
                            x = getResources().getIdentifier(name, "drawable", getPackageName());
                            image.setImageResource(x);
                            image.startAnimation(alpha);
                            movablePoint.add(point[target].getNextPointId()[i]);
                            killablePoint.add(point[target].getNextPointId()[i]);
                            killableGoti.add(point[tappedImage].getNextPointId()[i]);
                        }
                    }
                }
            }
        }

        if (movablePoint.size()==0&&!check){
            if(player==1) {
                player = 2;
                oppositePlayer= 1;
                binding.player1Turn.clearAnimation();
                binding.player1Turn.setVisibility(View.INVISIBLE);
                binding.player2Turn.setVisibility(View.VISIBLE);
                binding.player2Turn.startAnimation(turn);
            }
            else {
                player = 1;
                oppositePlayer=2;
                binding.player2Turn.clearAnimation();
                binding.player2Turn.setVisibility(View.INVISIBLE);
                binding.player1Turn.setVisibility(View.VISIBLE);
                binding.player1Turn.startAnimation(turn);
            }
            check= true;

        }

        temp = tappedImage;
        guiderFlag=true;

    }

    public void mover(int tappedImage){

        for (int i = 0; i < movablePoint.size(); i++) {

            if (tappedImage == movablePoint.get(i)) {
                String name = "imageView" + movablePoint.get(i);
                int x = getResources().getIdentifier(name, "id", getPackageName());
                image = (ImageView) findViewById(x);
                //image.setImageResource(0);
                name= "gote"+player;
                int y = getResources().getIdentifier(name, "drawable", getPackageName());
                image.setImageResource(y);

                point[movablePoint.get(i)].setGoti(player);
                point[temp].setGoti(0);

                if(tappedImage== killablePoint.get(i)){

                    mediaPlayer = MediaPlayer.create(this,R.raw.kill);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mediaPlayer= null;
                        }
                    });
                    mediaPlayer.start();

                    temp2= killablePoint.get(i);
                    if(player==1) {
                        p1.score++;
                        String sc1 = p1.score+"";
                        binding.player1ScoreCard.setText(sc1);
                        if(p1.score==16)
                            showBottomSheetDialog(p1.playerName+" WON");
                    }
                    else {
                        p2.score++;
                        String sc2 = p2.score+"";
                        binding.player2ScoreCard.setText(sc2);
                        if(p2.score==16)
                            showBottomSheetDialog(p2.playerName + " WON");


                    }
                    name = "imageView" + killableGoti.get(i);
                    x = getResources().getIdentifier(name, "id", getPackageName());
                    image = (ImageView) findViewById(x);
                    image.setImageResource(0);
                    point[killableGoti.get(i)].setGoti(0);
                }
                else{
                    mediaPlayer = MediaPlayer.create(this,R.raw.move);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mediaPlayer= null;
                        }
                    });
                    mediaPlayer.start();

                }

                name= "imageView"+temp;
                x = getResources().getIdentifier(name, "id", getPackageName());
                image = (ImageView) findViewById(x);
                image.setImageResource(0);

                if(temp2==-1) {
                    if (player == 1) {
                        player = 2;
                        oppositePlayer = 1;
                        binding.player1Turn.clearAnimation();
                        binding.player1Turn.setVisibility(View.INVISIBLE);
                        binding.player2Turn.setVisibility(View.VISIBLE);
                        binding.player2Turn.startAnimation(turn);
                    } else {
                        player = 1;
                        oppositePlayer = 2;
                        binding.player2Turn.clearAnimation();
                        binding.player2Turn.setVisibility(View.INVISIBLE);
                        binding.player1Turn.setVisibility(View.VISIBLE);
                        binding.player1Turn.startAnimation(turn);
                    }
                }
            }
            else {
                String name = "imageView" + movablePoint.get(i);
                int x = getResources().getIdentifier(name, "id", getPackageName());
                image = (ImageView) findViewById(x);
                image.setImageResource(0);
            }
        }


        movablePoint.clear();
        killablePoint.clear();
        killableGoti.clear();
        guiderFlag=false;
        moverFlag=true;

        if(temp2!=-1) {
            check = false;
            guider(temp2);
            temp2=-1;
        }
    }


    public void tap(View view) {

        image = (ImageView) view;
        int tappedImage = Integer.parseInt(image.getTag().toString());

        for(int i=0;i<movablePoint.size();i++){
            if(tappedImage==movablePoint.get(i))
                moverFlag=false;
        }

        if(moverFlag) {
            image.startAnimation(scale);
            guider(tappedImage);
        }
        else
            mover(tappedImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        p1= new Player();
        p2= new Player();
        Intent intent = getIntent();
        p1.playerName= intent.getStringExtra("player1Name");
        p2.playerName= intent.getStringExtra("player2Name");

       p1.score = 0;
       p2.score = 0;


        Random random = new Random();
        int a = random.nextInt(2)+1;
        if(a==1) {
            binding.player1Name.setText(p1.playerName);
            binding.player2Name.setText(p2.playerName);
        }
        else{
            binding.player1Name.setText(p2.playerName);
            binding.player2Name.setText(p1.playerName);
        }


        ImageView image;
        String name;
        int x;
        for(int i=0,j=36;i<16;i++,j--){
            name= "imageView"+ i;
            x = getResources().getIdentifier(name,"id",getPackageName());
            image = findViewById(x);
            image.setImageResource(R.drawable.gote1);
            point[i]= new Point();
            point[i].setCurPointId(i);
            point[i].setGoti(1);

            name= "imageView"+ j;
            x = getResources().getIdentifier(name,"id",getPackageName());
            image = findViewById(x);
            image.setImageResource(R.drawable.gote2);
            point[j]= new Point();
            point[j].setCurPointId(j);
                point[j].setGoti(2);
        }
        for(int i=16;i<21;i++){
            point[i]= new Point();
            point[i].setCurPointId(i);
                point[i].setGoti(0);
        }

        point[0].setNextPointId(1,3,-1,-1,-1,-1,-1,-1);
        point[1].setNextPointId(2,-1,4,-1,0,-1,-1,-1);
        point[2].setNextPointId(-1,-1,-1,5,1,-1,-1,-1);
        point[3].setNextPointId(4,8,-1,-1,-1,0,-1,-1);
        point[4].setNextPointId(5,-1,8,-1,3,-1,1,-1);
        point[5].setNextPointId(-1,-1,-1,8,4,-1,-1,2);
        point[6].setNextPointId(7,12,11,-1,-1,-1,-1,-1);
        point[7].setNextPointId(8,-1,12,-1,6,-1,-1,-1);
        point[8].setNextPointId(9,14,13,12,7,3,4,5);
        point[9].setNextPointId(10,-1,14,-1,8,-1,-1,-1);
        point[10].setNextPointId(-1,-1,15,14,9,-1,-1,-1);
        point[11].setNextPointId(12,-1,16,-1,-1,-1,6,-1);
        point[12].setNextPointId(13,18,17,16,11,6,7,8);
        point[13].setNextPointId(14,-1,18,-1,12,-1,8,-1);
        point[14].setNextPointId(15,20,19,18,13,8,9,10);
        point[15].setNextPointId(-1,-1,20,-1,14,-1,10,-1);
        point[16].setNextPointId(17,22,21,-1,-1,-1,11,12);
        point[17].setNextPointId(18,-1,22,-1,16,-1,12,-1);
        point[18].setNextPointId(19,24,23,22,17,12,13,14);
        point[19].setNextPointId(20,-1,24,-1,18,-1,14,-1);
        point[20].setNextPointId(-1,-1,25,24,19,14,15,-1);
        point[21].setNextPointId(22,-1,26,-1,-1,-1,16,-1);
        point[22].setNextPointId(23,28,27,26,21,16,17,18);
        point[23].setNextPointId(24,-1,28,-1,22,-1,18,-1);
        point[24].setNextPointId(25,30,29,28,23,18,19,20);
        point[25].setNextPointId(-1,-1,30,-1,24,-1,20,-1);
        point[26].setNextPointId(27,-1,-1,-1,-1,-1,21,22);
        point[27].setNextPointId(28,-1,-1,-1,26,-1,22,-1);
        point[28].setNextPointId(29,33,32,31,27,22,23,24);
        point[29].setNextPointId(30,-1,-1,-1,28,-1,24,-1);
        point[30].setNextPointId(-1,-1,-1,-1,29,24,25,-1);
        point[31].setNextPointId(32,-1,-1,34,-1,-1,-1,28);
        point[32].setNextPointId(33,-1,35,-1,31,-1,28,-1);
        point[33].setNextPointId(-1,36,-1,-1,32,28,-1,-1);
        point[34].setNextPointId(35,-1,-1,-1,-1,-1,-1,31);
        point[35].setNextPointId(36,-1,-1,-1,34,-1,32,-1);
        point[36].setNextPointId(-1,-1,-1,-1,35,33,-1,-1);


        scale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
        alpha = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        move = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
        alphaRev = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha_rev);
        turn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.turn);
        binding.player1Turn.startAnimation(turn);



    }

    public void guideRemoverWithAnimation(){
        if(guiderFlag){
            for (int i = 0; i < movablePoint.size(); i++) {
                String name = "imageView" + movablePoint.get(i);
                int x = getResources().getIdentifier(name, "id", getPackageName());
                image = (ImageView) findViewById(x);
                image.startAnimation(alphaRev);
            }
            new CountDownTimer(500,100){
                @Override
                public void onTick(long millisUntilFinished){

                }
                @Override
                public void onFinish(){
                    for (int i = 0; i < movablePoint.size(); i++) {
                        String name = "imageView" + movablePoint.get(i);
                        int x = getResources().getIdentifier(name, "id", getPackageName());
                        image = (ImageView) findViewById(x);
                        image.setImageResource(0);
                    }
                    movablePoint.clear();
                    killablePoint.clear();
                    killableGoti.clear();
                }
            }.start();
        }
    }

    private void showBottomSheetDialog(String name) {
        WinSheetLayoutBinding binding1 = WinSheetLayoutBinding.inflate(getLayoutInflater());

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(binding1.getRoot());

        binding1.message.setText(name);



        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
           finish();

            }
        });

        binding1.winLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        bottomSheetDialog.show();
    }
}