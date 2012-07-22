/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.android.components.tests;

import org.xwiki.android.components.login.LoginActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivityTests extends ActivityInstrumentationTestCase2<LoginActivity>
{
    private Activity loginActivity;

    private AutoCompleteTextView editTextUsername, editTextPassword, editTextURL;

    private Button buttonLogin;

    public LoginActivityTests()
    {
        super("org.xwiki.android.tests", LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        loginActivity = this.getActivity();
        editTextUsername =
            (AutoCompleteTextView) loginActivity.findViewById(org.xwiki.android.components.R.id.actv_login_username);
        editTextPassword =
            (AutoCompleteTextView) loginActivity.findViewById(org.xwiki.android.components.R.id.actv_login_password);
        editTextURL = (AutoCompleteTextView) loginActivity.findViewById(org.xwiki.android.components.R.id.actv_login_url);
        buttonLogin = (Button) loginActivity.findViewById(org.xwiki.android.components.R.id.button_login_login);
    }

    public void testGetActivty() throws Throwable
    {

        assertNotNull(loginActivity);
        assertNotNull(editTextUsername);
        assertNotNull(editTextPassword);
        assertNotNull(editTextURL);
    }

    public void testInputUsername() throws Throwable
    {
        loginActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                editTextUsername.requestFocus();

            }
        });

        for (int i = 0; i < 15; i++) {
            this.sendKeys(KeyEvent.KEYCODE_DEL);
        }

        this.sendKeys(KeyEvent.KEYCODE_A);
        this.sendKeys(KeyEvent.KEYCODE_D);
        this.sendKeys(KeyEvent.KEYCODE_M);
        this.sendKeys(KeyEvent.KEYCODE_I);
        this.sendKeys(KeyEvent.KEYCODE_N);

        assertEquals("admin", editTextUsername.getText().toString());
    }

    public void testInputPassword() throws Throwable
    {
        loginActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                editTextPassword.requestFocus();

            }
        });

        for (int i = 0; i < 15; i++) {
            this.sendKeys(KeyEvent.KEYCODE_DEL);
        }

        this.sendKeys(KeyEvent.KEYCODE_A);
        this.sendKeys(KeyEvent.KEYCODE_D);
        this.sendKeys(KeyEvent.KEYCODE_M);
        this.sendKeys(KeyEvent.KEYCODE_I);
        this.sendKeys(KeyEvent.KEYCODE_N);

        assertEquals("admin", editTextPassword.getText().toString());
    }

    public void testInputUrl() throws Throwable
    {
        loginActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                editTextURL.requestFocus();

            }
        });

        for (int i = 0; i < 15; i++) {
            this.sendKeys(KeyEvent.KEYCODE_DEL);
        }

        this.sendKeys(KeyEvent.KEYCODE_W);
        this.sendKeys(KeyEvent.KEYCODE_W);
        this.sendKeys(KeyEvent.KEYCODE_W);
        this.sendKeys(KeyEvent.KEYCODE_PERIOD);
        this.sendKeys(KeyEvent.KEYCODE_X);
        this.sendKeys(KeyEvent.KEYCODE_W);
        this.sendKeys(KeyEvent.KEYCODE_I);
        this.sendKeys(KeyEvent.KEYCODE_K);
        this.sendKeys(KeyEvent.KEYCODE_I);
        this.sendKeys(KeyEvent.KEYCODE_PERIOD);
        this.sendKeys(KeyEvent.KEYCODE_O);
        this.sendKeys(KeyEvent.KEYCODE_R);
        this.sendKeys(KeyEvent.KEYCODE_G);

        assertEquals("www.xwiki.org", editTextURL.getText().toString());
    }

    public void testLoginButton() throws Throwable
    {
        loginActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                buttonLogin.requestFocus();

            }
        });

        this.sendKeys(KeyEvent.KEYCODE_ENTER);
    }

}
