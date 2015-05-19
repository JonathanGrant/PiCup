package games.picup.com.picup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

/**
 * Authors: FreddieV4 & JonathanGrant
 * Purpose: Hack UMass II (Apr. 11-12th, 2015)
 */

public class LoginActivity extends FragmentActivity {

    private static Button mSignUpButton;
    private static Button confirm;
    private static SignUpFragment sign;
    private static UserSignUpInfoFragment info;
    static Intent intent;
    public static Toast toast;

    //Give your SharedPreferences file a name and save it to a static variable
    public static final String PREFFS = "LePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //font
        Typeface tp = Typeface.createFromAsset(getAssets(), "Logo.ttf");
        TextView mit = (TextView) findViewById(R.id.Logz);
        mit.setTypeface(tp);
        mit.setTextSize(100);

//        SharedPreferences settings1 = getSharedPreferences(LoginActivity.PREFFS, 0);
//        boolean parseInitialized = settings1.getBoolean("parseStarted", false);

//        if (parseInitialized) {
//            startParse();
//            LoginActivity.this.finish();
//        }

        toast = Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_LONG);

        sign = new SignUpFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.add(R.id.main_container, sign, "User Sign-Up Fragment");
        trans.commit();

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFFS, 0);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        intent = new Intent(getApplicationContext(), GameList.class);

        if (hasLoggedIn) {
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    public void startParse(){
        ParseObject.registerSubclass(ParseUser.class);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "B4rIuWBWbeVaHrdtdnUZcC5ziI2cqAm1ZneexOXy", "mcGiMCshfXbCH29AXXiiK7lU9KBxrCRb0r00psWB");
        SharedPreferences sets1 = getSharedPreferences(LoginActivity.PREFFS, 0); // 0 - for private mode
        SharedPreferences.Editor editor1 = sets1.edit();
        sets1.edit().clear().commit();
//        //Set "hasLoggedIn" to true
//        editor1.putBoolean("parseStarted", true);
//        // Commit the edits!
//        editor1.commit();
//        startActivity(intent);
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);

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

    public static class SignUpFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_signup, container, false);

            mSignUpButton = (Button) view.findViewById(R.id.sign_up_button);

            mSignUpButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    info = new UserSignUpInfoFragment();

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction trans = fm.beginTransaction();
                    trans.replace(R.id.main_container, info, "User Sign-Up Fragment");
                    trans.commit();
                }
            });
            return view;
        }
    }

    public static class UserSignUpInfoFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_userinfo, container, false);

            confirm = (Button) view.findViewById(R.id.confirm_signup);
            final EditText mUsername = (EditText) view.findViewById(R.id.username);
            final EditText mPassword = (EditText) view.findViewById(R.id.password);
//
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userString = mUsername.getText().toString();
                    String passString = mPassword.getText().toString();

                    //Check if email given is a USC Email
                    //first clear string of all extra spaces - there are no spaces in an email
                    userString = userString.replaceAll(" ","");
                    //make lowercase
                    userString = userString.toLowerCase();
                    mUsername.setText(userString); //display email w/o spaces now + lowercase
                    if(userString.endsWith("@usc.edu") && passString.length()>3) //check if it is a usc address + password is at least four characters
                    {
                        //create Parse User
                        ParseObject.registerSubclass(ParseUser.class);
                        ParseUser user = new ParseUser();
                        user.setUsername(userString);
                        //user.setEmail() no email functionality yet... needs to be added
                        user.setPassword(passString);

                        //create account in database
                        Parse.initialize(getActivity(), "B4rIuWBWbeVaHrdtdnUZcC5ziI2cqAm1ZneexOXy", "mcGiMCshfXbCH29AXXiiK7lU9KBxrCRb0r00psWB");
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(com.parse.ParseException e) {
                                if (e == null) {
                                    // Hooray! Let them use the app now.
                                    Intent i = new Intent(getActivity(), GameList.class);
                                    startActivity(i);

                                    // User has successfully logged in, save this information
                                    // We need an Editor object to make preference changes.
                                    SharedPreferences sets = getActivity().getSharedPreferences(LoginActivity.PREFFS, 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = sets.edit();
                                    sets.edit().clear().commit();
                                    //Set "hasLoggedIn" to true
                                    editor.putBoolean("hasLoggedIn", true);
                                    // Commit the edits!
                                    editor.commit();
                                    startActivity(intent);
                                    getActivity().finish();

                                    toast.setText("Created Account!");
                                    toast.show();
                                } else {
                                    // Sign up didn't succeed. Look at the ParseException
                                    // to figure out what went wrong
                                    toast.setText("Sign Up Failed");
                                    toast.show();
                                }
                            }
                        });
                    } else if(!userString.endsWith("@usc.edu")) {
                        toast.setText("Only valid USC email addresses are accepted.");
                        toast.show();
                    } else {
                        //password isn't long enough
                        toast.setText("Your password must be at least four characters long.");
                        toast.show();
                    }
                }
            });

            return view;
        }
    }

}