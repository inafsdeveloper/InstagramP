/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener{

  EditText usernameField;
  EditText passwordField;
  TextView changeSignUpModeTextView;
  Button  signUpButtonView;
  Boolean signUpMode = true;
  ImageView logoImage;
  TextView logoText;
  RelativeLayout relativeLayout;

  public void signUpOrLogIn(View view)
  {
//      Log.i("AppInfo",String.valueOf(usernameField.getText()));
//      Log.i("AppInfo",String.valueOf(passwordField.getText()));


      if (signUpMode) {
          ParseUser user = new ParseUser();
          user.setUsername(String.valueOf(usernameField.getText()));
          user.setPassword(String.valueOf(passwordField.getText()));

          user.signUpInBackground(new SignUpCallback() {
              @Override
              public void done(ParseException e) {
                  if (e == null) {
                      Log.i("AppInfo", " SignUp Successful.");
                  } else {
//                      e.printStackTrace();
                      Toast.makeText(getApplicationContext(),e.getMessage().substring(e.getMessage().indexOf(" ")),Toast.LENGTH_LONG).show();
                  }
              }
          });
      }else
      {
          ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
              @Override
              public void done(ParseUser user, ParseException e) {
                  if (user != null)
                  {
                      Log.i("AppInfo", "LogIn Successful.");
                  }else
                  {
                      Toast.makeText(getApplicationContext(),e.getMessage().substring(e.getMessage().indexOf(" ")),Toast.LENGTH_LONG).show();
                  }
              }
          });
      }
  }

  public void changeSignUpModeTextViewClicked(View view)
  {
      if (signUpMode)
      {
          signUpMode = false;
          changeSignUpModeTextView.setText("Sign Up");
          signUpButtonView.setText("Log In");
      }else
      {
          signUpMode = true;
          changeSignUpModeTextView.setText("Log In");
          signUpButtonView.setText("Sign Up");

      }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    usernameField = (EditText) findViewById(R.id.username);
    passwordField = (EditText) findViewById(R.id.password);
    changeSignUpModeTextView = (TextView) findViewById(R.id.changeSignUpMode);
    signUpButtonView = (Button) findViewById(R.id.signUpButton);
    logoImage = (ImageView) findViewById(R.id.logo);
    logoText = (TextView) findViewById(R.id.logoText);
    relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

    logoImage.setOnClickListener(this);
    logoText.setOnClickListener(this);
    relativeLayout.setOnClickListener(this);

    usernameField.setOnKeyListener(this);
    passwordField.setOnKeyListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
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

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            signUpOrLogIn(v);
        }

        return false;
    }

    @Override
    public void onClick(View v) {

//        Log.i("onClick","View  got clicked - " + String.valueOf(v.getId()));
        if (v.getId() == R.id.logo || v.getId() == R.id.logoText || v.getId() == R.id.relativeLayout)
        {
//            Log.i("onClick","if satisfied.");

            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            if (im != null) {
                im.hideSoftInputFromInputMethod(v.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
//            else
//            {
//                Log.i("onClick","im is null.");
//            }
        }

    }
}
