package com.exodus.ander.phoneapp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class CallScreenActivity extends AppCompatActivity {


    public SeekBar seekbar;
    public TextView dialTimeTextView;
    private double finalTime;
    public int arrayIndex;

    String theName;
    String theNumber;
    int theMessage;
    MediaPlayer ringMP;
    MediaPlayer messageMP;

    private Handler mSeekbarUpdateHandler = new Handler();

    TextView dialedContactTextView;
    ImageButton pauseButton;
    ImageButton playButton;
    ImageButton replayButton;
    public final static Contact[] contactArray = ValidContactListSingleton.getInstance().getValidContactsArray();

    private Utilities utils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_screen);


        //gets the index of our array the contact is stored in
        arrayIndex = this.getIntent().getIntExtra(DialScreenActivity.ARRAYINDEX, -1);



        dialTimeTextView = findViewById(R.id.tv_dialing);

        theName = contactArray[arrayIndex].getContactName();
        theNumber = contactArray[arrayIndex].getContactNumber();
        theMessage = contactArray[arrayIndex].getContactMessage();

        dialedContactTextView = findViewById(R.id.tv_dialed_contact);
        dialedContactTextView.setText(theName);

        pauseButton = findViewById(R.id.btn_pause);
        playButton = findViewById(R.id.btn_play);
        replayButton = findViewById(R.id.btn_repeat);

        seekbar = findViewById(R.id.audio_sb);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(messageMP!=null&fromUser){
                    messageMP.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        playRing();
    }

    @Override
    protected void onDestroy() {
        if(ringMP!=null){
            ringMP.release();
        }
        if (messageMP!=null){
            messageMP.release();
        }

        if(mSeekbarUpdateHandler!=null) {
            mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
        }

        super.onDestroy();
    }

    public void onEndCall(View view)
    {
        this.finish();
    }



    private void playRing() {
        //TODO: Fix this
        dialTimeTextView.setText(getString(R.string.dialing));
        ringMP = MediaPlayer.create(CallScreenActivity.this, R.raw.phone_ringing_2_times);
        ringMP.start();
        ringMP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
                dialTimeTextView.setText("In-call");
                pauseButton.setVisibility(View.VISIBLE);
                playButton.setVisibility(View.VISIBLE);
                replayButton.setVisibility(View.VISIBLE);
                playMessage();

            }
        });
    }

    private void playMessage(){
        seekbar.setVisibility(View.VISIBLE);
        messageMP = MediaPlayer.create(CallScreenActivity.this, theMessage);
        seekbar.setMin(0);
        seekbar.setMax(messageMP.getDuration());
        messageMP.start();
        mUpdateSeekbar.run();
    }

   private Runnable mUpdateSeekbar = new Runnable() {
    @Override
    public void run() {
        int pos = messageMP.getCurrentPosition();
        //String currentTime = utils.milliSecondsToTimer(pos);
        seekbar.setProgress(pos);
        //How frequently the seekbar updates
        mSeekbarUpdateHandler.postDelayed(this, 100);
    }
};

    public void onPauseButtonClick(View view) {
        if(messageMP!=null&&messageMP.isPlaying())
        {
            messageMP.pause();
            mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            pauseButton.setImageResource(R.drawable.ic_pause_black_24dp);
            pauseButton.setBackgroundResource(R.drawable.selected_option_btn_shape);
        }
    }

    public void onPlayButtonClick(View view) {
        if(messageMP!=null&&!(messageMP.isPlaying()))
        {
            messageMP.start();
            mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            pauseButton.setImageResource(R.drawable.ic_pause_white_24dp);
            pauseButton.setBackgroundResource(R.drawable.option_btn_shape);
            mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 100);
        }
    }

    public void onRepeatButtonClick(View view) {
        if(messageMP!=null) {
            messageMP.seekTo(0);
            seekbar.setProgress(0);
            pauseButton.setImageResource(R.drawable.ic_pause_white_24dp);
            pauseButton.setBackgroundResource(R.drawable.option_btn_shape);
        }
    }

    /*private Runnable mUpdateTimer = new Runnable() {
        @Override
        public void run() {
            int pos = messageMP.getCurrentPosition();
            String currentTime = utils.milliSecondsToTimer(pos);
            //Toast.makeText(CallScreenActivity.this, currentTime, Toast.LENGTH_LONG);
            //dialTimeTextView.setText(currentTime);
            mSeekbarUpdateHandler.postDelayed(this, 1000);

        }
    };*/

    /*public void updateProgressBar() {
        mSeekbarUpdateHandler.postDelayed(mUpdateTimeTask, 100);
    }*/

    /*private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = messageMP.getDuration();
            long currentDuration = messageMP.getCurrentPosition();

            // Displaying time completed playing
            String temp = ""+utils.milliSecondsToTimer(currentDuration);
            dialTimeTextView.setText(temp);

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            seekbar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mSeekbarUpdateHandler.postDelayed(this, 100);
        }
    };*/

}
