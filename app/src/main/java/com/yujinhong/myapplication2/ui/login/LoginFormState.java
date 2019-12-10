package com.yujinhong.myapplication2.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private Integer password1Error;

    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer password1Error) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.password1Error = password1Error;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.password1Error = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getPassword1Error() {
        return password1Error;
    }
    boolean isDataValid() {
        return isDataValid;
    }
}
