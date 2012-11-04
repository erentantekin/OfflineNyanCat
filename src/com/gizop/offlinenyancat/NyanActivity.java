package com.gizop.offlinenyancat;


import com.gizop.offlinenyancat.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class NyanActivity extends Activity {

	public static MediaPlayer mediaPlayer;
	public static NotificationManager notificationManager;
	public static boolean isPlaying = false;

	public Button btnAudio;
	public ImageView imgMain;
	public AnimationDrawable frameAnimation;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  

        // initialize ui elements
    	btnAudio = (Button) this.findViewById(R.id.btnAudio);
    	imgMain = (ImageView)findViewById(R.id.imgMain);

    	if (!isPlaying) {
    		btnAudio.setText(R.string.start_audio); 
    		if (savedInstanceState == null) {  // first run
    			startNyanAudio();
    			}
    		}
    }
    
    public void onWindowFocusChanged(boolean hasFocus) {	  
    	// set the animation
    	frameAnimation = (AnimationDrawable) imgMain.getBackground();
    	imgMain.setBackgroundResource(R.drawable.nyan_animation);
    	AnimationDrawable frameAnimation = (AnimationDrawable) imgMain.getBackground();

    	if (hasFocus) {
    		frameAnimation.start();
    	}
    }
         
	public void btnAudioClick(View view) {
		if (isPlaying) {
			stopNyanAudio();
		}
		else {
			startNyanAudio(); 
		}
	}

    // starts the nyan audio loop
    public void startNyanAudio() {
    	isPlaying = true;
		btnAudio.setText(R.string.stop_audio);
        notification("Offline Nyan Cat", "Nyan cat is nyanning!");
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.nyan);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    
    // stops the nyan audio loop
    public void stopNyanAudio() {
    	isPlaying = false;
		btnAudio.setText(R.string.start_audio);
    	notificationManager.cancelAll();
    	mediaPlayer.release();
    }
    
    // pushes notification to notification bar
    private void notification (String notificationTitle, String notificationMessage)
    {
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.icon, "Nyan nyan!", System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;
         
        long[] vibrate = {0,250,30,250};
        notification.vibrate = vibrate;
 
        Intent notificationIntent = new Intent(this, NyanActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
 
        notification.setLatestEventInfo(NyanActivity.this, notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(10001, notification);
    }
    
    // pops up the dialog box
    public void dialog () {    	
    	Dialog dialog = new Dialog(this);
    	dialog.setContentView(R.layout.credits);
    	dialog.setTitle(R.string.credits);
    	dialog.show();
    	
        //set up dialog ui elements and listeners
        TextView txtEren = (TextView) dialog.findViewById(R.id.txtEren);
        TextView txtErenUrl = (TextView) dialog.findViewById(R.id.txtErenUrl);
        TextView txtRoberto = (TextView) dialog.findViewById(R.id.txtRoberto);
        TextView txtRobertoUrl = (TextView) dialog.findViewById(R.id.txtRobertoUrl);
        
        txtEren.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.eren_url))));
            }
        });
        
        txtErenUrl.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.eren_url))));
            }
        });
        
        txtRoberto.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.roberto_url))));
            }
        });
        
        txtRobertoUrl.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.roberto_url))));
            }
        });   
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//load the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
        case R.id.credits:
        	dialog();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}