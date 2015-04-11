package games.picup.com.picup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginActivity extends FragmentActivity {

    private static Button mSignUpButton;
    private static Button confirm;
    private SignUpFragment sign;
    private static UserSignUpInfoFragment info;


    //Give your SharedPreferences file a name and save it to a static variable
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.
//        Parse.enableLocalDatastore(this);

//        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
//        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
//        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
//
//        final Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
//
//        if (hasLoggedIn) {
//            startActivity(intent);
//            LoginActivity.this.finish();
//        }

        sign = new SignUpFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.add(R.id.main_container, sign, "User Sign-Up Fragment");
        trans.commit();

//
//        // User has successfully logged in, save this information
//        // We need an Editor object to make preference changes.
//        SharedPreferences sets = getSharedPreferences(LoginActivity.PREFS_NAME, 0); // 0 - for private mode
//        SharedPreferences.Editor editor = sets.edit();
//        //Set "hasLoggedIn" to true
//        editor.putBoolean("hasLoggedIn", true);
//        // Commit the edits!
//        editor.commit();
//        startActivity(intent);
//        LoginActivity.this.finish();

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
//            Parse.initialize(getActivity(), "yzyZoFIKPhTU9yOrC6ZcNOxoemEhL35ldYQM6UzR", "arrposL9cr9UAem4aGikI2Ts2FYfhGT8jaZ6xLXh");

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
//            final EditText name = (EditText) view.findViewById(R.id.nameText);
//            final EditText phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
//
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String nameString = name.getText().toString();
//                    String phoneString = phoneNumber.getText().toString();
//
//                    ParseObject testObject = new ParseObject("SignUpObject");
//                    testObject.put("Name", nameString);
//                    testObject.put("Number", phoneString);
//                    testObject.saveInBackground();
//
//                    Intent i = new Intent(getActivity(), FeedActivity.class);
//                    startActivity(i);
                }
            });

            return view;
        }
    }

}