package com.yujinhong.myapplication2.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
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
import com.kakao.util.helper.log.Logger;
import com.yujinhong.myapplication2.MainActivity;
import com.yujinhong.myapplication2.R;
import com.yujinhong.myapplication2.ui.login.LoginViewModel;
import com.yujinhong.myapplication2.ui.login.LoginViewModelFactory;

import java.util.Set;
import java.util.TreeSet;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
//        final EditText password1EditText = findViewById(R.id.password1);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

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
//                if (loginFormState.getPassword1Error() != null) {
//                    password1EditText.setError(getString(loginFormState.getPassword1Error()));
//                }
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
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
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
                userLogin(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                loadingProgressBar.setVisibility(View.INVISIBLE);
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void userLogin(final String email, final String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
                            SharedPreferences loginInformation = getSharedPreferences("setting",0);
                            SharedPreferences.Editor editor = loginInformation.edit();
                            Set<String> prevEmailSet = loginInformation.getStringSet("email", null);
                            Set<String> prevPwSet = loginInformation.getStringSet("password", null);
                            Set<String> emailSet = new TreeSet<>();
                            if(prevEmailSet != null) {
                                String[] emailStrings = prevEmailSet.toArray(new String[]{});
                                for(int i=0;i<emailStrings.length;i++) {
                                    emailSet.add(emailStrings[i]);
                                }
                            }
                            emailSet.add(email);
                            editor.putStringSet("email", emailSet);

                            Set<String> pwSet = new TreeSet<>();
                            if(prevPwSet != null) {
                                String[] pwStrings = prevPwSet.toArray(new String[]{});
                                for (int i = 0; i < pwStrings.length; i++) {
                                    pwSet.add(pwStrings[i]);
                                }
                            }
                            pwSet.add(password);
                            editor.putStringSet("password", pwSet);
                            editor.commit();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "ID 혹은 비밀번호가 잘못되었습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
