package ru.trumbull_pro.rebulicv_1_0;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	ImageButton menu_button; 
	Button p1, p2, p3, p4;
	TextView paragraph;
	LinearLayout content, menu;
	LinearLayout.LayoutParams contentParams;
	TranslateAnimation slide;
	int marginX, animateFromX, animateToX = 0;
	boolean menuOpen = false;
	String[] paragraphs;
	
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		paragraphs = getResources().getStringArray(R.array.lorem);
		
		menu = (LinearLayout)findViewById(R.id.menu);
		
		content = (LinearLayout)findViewById(R.id.content);
		contentParams = (LinearLayout.LayoutParams)content.getLayoutParams();
		contentParams.width = getWindowManager().getDefaultDisplay().getWidth();	// Ensures constant width of content during menu sliding
		contentParams.leftMargin = -(menu.getLayoutParams().width);		// Position the content at the start of the screen
		content.setLayoutParams(contentParams);
		
		
		menu_button = (ImageButton)findViewById(R.id.menu_button);
		menu_button.setOnClickListener(this);
		
		paragraph = (TextView)findViewById(R.id.paragraph);
		paragraph.setText(paragraphs[0]);
		
		p1 = (Button)findViewById(R.id.p1);
		p1.setOnClickListener(this);
		
		p2 = (Button)findViewById(R.id.p2);
		p2.setOnClickListener(this);
		
		p3 = (Button)findViewById(R.id.p3);
		p3.setOnClickListener(this);
		
		p4 = (Button)findViewById(R.id.p4);
		p4.setOnClickListener(this);	
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			if(menuOpen) {
				slideMenuIn(0, -(menu.getLayoutParams().width), -(menu.getLayoutParams().width)); 	// Pass slide in paramters
				menuOpen = false;
				return true;
			}
		}
		return super.onKeyDown(keyCode, keyEvent);
	}
	
	public void onClick(View v) {
		
		switch(v.getId()) {
		
		case R.id.p1:
			paragraph.setText(paragraphs[0]);			
			Intent intent = new Intent(this, Search_minerals.class);
		      startActivity(intent);
			break;
		case R.id.p2:
			paragraph.setText(paragraphs[1]);
			Intent intent1 = new Intent(this, Miner.class);
		      startActivity(intent1);
			break;
		case R.id.p3:
			paragraph.setText(paragraphs[2]);
			Intent intent2 = new Intent(this, Bourse_main.class);
		      startActivity(intent2);
			break;
		case R.id.p4:
			paragraph.setText(paragraphs[3]);
			Intent intent3 = new Intent(this, Graph_copper.class);
		      startActivity(intent3);
			break;
		}
		
		if(contentParams.leftMargin == -(menu.getLayoutParams().width)) {	// Menu is hidden (slide out parameters)
			animateFromX = 0;
			animateToX = (menu.getLayoutParams().width);
			marginX = 0;
			menuOpen = true;
		} else {	// Menu is visible (slide in parameter)
			animateFromX = 0;
			animateToX = -(menu.getLayoutParams().width);
			marginX = -(menu.getLayoutParams().width);
			menuOpen = false;
		}
		slideMenuIn(animateFromX, animateToX, marginX);
	}
		
	public void slideMenuIn(int animateFromX, int animateToX, final int marginX){
		slide = new TranslateAnimation(animateFromX, animateToX, 0, 0);
		slide.setDuration(300);
		slide.setFillEnabled(true);
		slide.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {		// Make movement of content permanent after animation has completed 
				contentParams.setMargins(marginX, 0, 0, 0);			// by positioning its left margin
				content.setLayoutParams(contentParams);
			}

			public void onAnimationRepeat(Animation animation) { }
			public void onAnimationStart(Animation animation) { }
		});
		content.startAnimation(slide);		// Slide menu in or out
	}
}