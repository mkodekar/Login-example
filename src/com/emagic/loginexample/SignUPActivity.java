package com.emagic.loginexample;

import java.util.regex.Pattern;

import com.emagic.loginexample.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends Activity
{
	EditText  editTextName,editTextUserName,editTextPassword,editTextConfirmPassword,editTextEmail,editTextMobile,editTextAddress;
	Button btnCreateAccount;
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		// get Instance  of Database Adapter
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();
		
		// Get Refferences of Views
		editTextName = (EditText)findViewById(R.id.editTextName);
		editTextUserName=(EditText)findViewById(R.id.editTextUserName);
		editTextPassword=(EditText)findViewById(R.id.editTextPassword);
		editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
		editTextEmail = (EditText)findViewById(R.id.editTextEmail);
		editTextMobile = (EditText)findViewById(R.id.editTextMobile);
		editTextAddress = (EditText)findViewById(R.id.editTextAddress);
		
		btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			String name = editTextName.getText().toString();
			String userName = editTextUserName.getText().toString();
			String password = editTextPassword.getText().toString();
			String confirmPassword = editTextConfirmPassword.getText().toString();
			String email = editTextEmail.getText().toString();
			String mobileNumber = editTextMobile.getText().toString();
			String address = editTextAddress.getText().toString();
			
			// check if any of the fields are vaccant
			if(name.equals("")||userName.equals("")||password.equals("")||confirmPassword.equals("")||email.equals("")||address.equals(""))
			{
					Toast.makeText(getApplicationContext(), "Please fill in the Required Fields", Toast.LENGTH_LONG).show();
					return;
			}
			// check if both password matches
			if(!password.equals(confirmPassword))
			{
				Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
				return;
			}
			else 
				if(mobileNumber.equals("")) {
				
				Toast.makeText(getApplicationContext(), "Mobile Number should not be blank", Toast.LENGTH_SHORT) .show();
			}
			else
				if(mobileNumber.length() < 10){
					
					Toast.makeText(getApplicationContext(), "This is not a Valid Mobile Number", Toast.LENGTH_SHORT).show();
				}
			else
			{
			    // Save the Data in Database
			    loginDataBaseAdapter.insertEntry(name, userName, password, email, mobileNumber, address);
			    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
			}
		}
	});
		
}
	
	InputFilter filter = new InputFilter() {


		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			for (int i = start; i < end; ++i)
            {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches())
                {
                    return "";
                }
            }
			return null;
		}
    };
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		loginDataBaseAdapter.close();
	}
}
