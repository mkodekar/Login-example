package com.emagic.loginexample;

import com.emagic.loginexample.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	
	private SharedPreferences mPreferences;
	Button btnSignIn,btnSignUp,btnLogout;
	LoginDataBaseAdapter loginDataBaseAdapter;
	SessionManager session;
	TextView lblName, lblEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main_login);
	     
	     session = new SessionManager(getApplicationContext());
	     
	     Toast.makeText(getApplicationContext(), 
	                "User Login Status: " + session.isUserLoggedIn(), 
	                Toast.LENGTH_LONG).show();
	     
	     
	     if(session.isUserLoggedIn() == true) {
	    	 
	    	 Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
				startActivity(intentSignUP);
	    	 
	    	 
	     } else {
	    	 
	     
	     
	     // create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     // Get The Refference Of Buttons
	     btnSignIn=(Button)findViewById(R.id.buttonSignIN);
	     btnSignUp=(Button)findViewById(R.id.buttonSignUP);
	     btnLogout = (Button) findViewById(R.id.btnLogout);
	     
	     TextView lblName = (TextView) findViewById(R.id.lblName);
         
         Toast.makeText(getApplicationContext(), 
                 "User Login Status: " + session.isUserLoggedIn(), 
                 Toast.LENGTH_LONG).show();	
			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			/// Create Intent for SignUpActivity  and Start The Activity
			Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
			startActivity(intentSignUP);
			}
		});
	    
	   }
	}
	
	// Methos to handleClick Event of Sign In Button
	public void signIn(View V)
	   {
			final Dialog dialog = new Dialog(HomeActivity.this);
			dialog.setContentView(R.layout.login);
			mPreferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
		    dialog.setTitle("Login");
	
		    // get the Refferences of views
		    final  EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
		    final  EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);
		    
			final Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);
				
			// Set On ClickListener
			btnSignIn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// get The User name and Password
					String userName=editTextUserName.getText().toString();
					String password=editTextPassword.getText().toString();
					
					String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
					
					if(password.equals(storedPassword))
					{
						Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
						session.createUserLoginSession(userName, password);
						dialog.dismiss();
					}
					else
					{
						Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
					}
					
					
				}
			});
			
			dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		loginDataBaseAdapter.close();
	}
	

}
