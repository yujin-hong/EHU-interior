package com.yujinhong.myapplication2.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.IgnoreExtraProperties;
import com.kakao.util.helper.log.Logger;
import com.yujinhong.myapplication2.MainActivity;
import com.yujinhong.myapplication2.R;
import com.yujinhong.myapplication2.sharedPreferenceArrayList;
import com.yujinhong.myapplication2.ui.login.LoginViewModel;
import com.yujinhong.myapplication2.ui.login.LoginViewModelFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@IgnoreExtraProperties
class User {
    public Integer type;
    public String addr;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(Integer type, String addr) {
        this.type = type;
        this.addr = addr;
    }
}

public class SignupActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public DatabaseReference mDatabase;
    SharedPreferences.Editor editor;
    SharedPreferences loginInformation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        loginInformation = this.getSharedPreferences("setting",0);
        editor = loginInformation.edit();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText password1EditText = findViewById(R.id.password1);
        final EditText addrText = findViewById(R.id.addr);

        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        Logger.e("hihihihihi");
//        Logger.e(conditionRef.toString());
//        conditionRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String text = dataSnapshot.getValue(String.class);
//                Logger.e(text);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Logger.e("err..");
//            }
//        });
//
//        conditionRef.setValue("plz..");
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
                if (loginFormState.getPassword1Error() != null) {
                    password1EditText.setError(getString(loginFormState.getPassword1Error()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        password1EditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        password1EditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(usernameEditText.getText().toString());
                Logger.e(passwordEditText.getText().toString());
                loadingProgressBar.setVisibility(View.VISIBLE);
                registerUser(usernameEditText.getText().toString(),passwordEditText.getText().toString(),
                        addrText.getText().toString());
                loadingProgressBar.setVisibility(View.INVISIBLE);



//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void isInterior (View V) {
        EditText addr = (EditText) findViewById(R.id.addr);
        if(addr.getVisibility() == View.VISIBLE) {
            addr.setVisibility(View.INVISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            ConstraintLayout constraintLayout = findViewById(R.id.container);
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.login,ConstraintSet.TOP,R.id.ruEnterior,ConstraintSet.BOTTOM,5);
//            constraintSet.connect(R.id.login,ConstraintSet.TOP,R.id.check_answer1,ConstraintSet.TOP,0);
            constraintSet.applyTo(constraintLayout);
        } else {
            addr.setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            ConstraintLayout constraintLayout = findViewById(R.id.container);
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.login,ConstraintSet.TOP,R.id.addr,ConstraintSet.BOTTOM,5);
//            constraintSet.connect(R.id.login,ConstraintSet.TOP,R.id.check_answer1,ConstraintSet.TOP,0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    private void registerUser(final String email, String password, String addr) {
        User user;

        if(addr.equals("")) {
            user = new User(0, addr);
        } else {
            user = new User(2, addr);
        }

        sharedPreferenceArrayList.setJsonArrayList(loginInformation, "email", email);
        sharedPreferenceArrayList.setJsonArrayList(loginInformation, "password", password);

        String changed_email = email.replace(".","");
        mDatabase.child("user_info").child(changed_email).setValue(user);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            //에러발생시
                            Toast.makeText(getApplicationContext(), "이미 가입된 이메일입니다. 다른 이메일을 사용해주세요", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
