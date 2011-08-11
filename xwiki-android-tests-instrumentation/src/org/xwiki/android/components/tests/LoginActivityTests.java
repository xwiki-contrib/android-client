package org.xwiki.android.components.tests;

import org.xwiki.android.components.login.LoginActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class LoginActivityTests extends ActivityInstrumentationTestCase2<LoginActivity>
{
    private Activity loginActivity;

    public LoginActivityTests()
    {
        super("org.xwiki.android.tests", LoginActivity.class);
    }

    public void testSomething() throws Throwable
    {
        
        loginActivity = this.getActivity();
    }

}
