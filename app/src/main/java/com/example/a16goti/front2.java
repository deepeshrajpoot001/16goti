package com.example.a16goti;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.a16goti.databinding.ActivityFront2Binding;
import com.example.a16goti.databinding.BottomSheetLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class front2 extends AppCompatActivity {
    ActivityFront2Binding binding;
    //deepesh rajpoot

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFront2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    private void showBottomSheetDialog() {
        BottomSheetLayoutBinding binding1 = BottomSheetLayoutBinding.inflate(getLayoutInflater());

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(binding1.getRoot());

        binding1.player1name.requestFocus();

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                Toast.makeText(getApplicationContext(),"play",Toast.LENGTH_SHORT);
            }
        });

        binding1.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p1,p2;
                if(binding1.player1name.getText().toString().isEmpty()){
                    p1 = "player1";
                } else{
                    p1 = binding1.player1name.getText().toString();
                }
                if(binding1.player2name.getText().toString().isEmpty()){
                    p2 = "player2";
                }else{
                    p2 = binding1.player2name.getText().toString();

                }
                Intent intent = new Intent(front2.this,MainActivity.class);
                intent.putExtra("player1Name",p1);
                intent.putExtra("player2Name",p2);
                startActivity(intent);
            }
        });



        bottomSheetDialog.show();
    }
}