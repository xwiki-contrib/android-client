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

package org.xwiki.android.components.login;
import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.rest.HttpConnector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * UI Component of the user authentication screen
 * */
public class LoginActivity extends Activity
{
    public static final String INTENT_EXTRA_GET_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_GET_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_GET_PASSWORD = IntentExtra.PASSWORD;

    private String username, password, url;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginButton = (Button) findViewById(R.id.button_login_login);

        loginButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                EditText et_username = (EditText) findViewById(R.id.editText_login_username);
                EditText et_password = (EditText) findViewById(R.id.editText_login_password);
                EditText et_url = (EditText) findViewById(R.id.editText_login_url);

                username = et_username.getText().toString();
                password = et_password.getText().toString();
                url = et_url.getText().toString();

                handler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {                       
                            AlertDialog.Builder alertbox = new AlertDialog.Builder(LoginActivity.this);
                            alertbox.setMessage(msg.getData().getString("msg"));
                            alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                }
                            });
                            alertbox.show();                       
                        
                    }
                };

                final ProgressDialog myProgressDialog;
                myProgressDialog =
                    ProgressDialog.show(LoginActivity.this, "Login", "Please wait while connecting...", true);
                Thread t = new Thread(new Runnable()
                {

                    @Override
                    public void run()
                    {

                        HttpConnector httpConnector = new HttpConnector();
                        int code = httpConnector.checkLogin(username, password, url);
                        myProgressDialog.dismiss();
                        login(code);
                    }

                });
                t.start();
            }

        });       
    }

    private void login(int loginStatus)
    {

        if (loginStatus == 200) {
            Log.d("Login", "successful");
            getIntent().putExtra(INTENT_EXTRA_GET_USERNAME, username);
            getIntent().putExtra(INTENT_EXTRA_GET_PASSWORD, password);
            getIntent().putExtra(INTENT_EXTRA_GET_URL, url);
            setResult(Activity.RESULT_OK, getIntent());
            finish();
        } else if (loginStatus == 401) {
            Log.d("Login", "wrong username or password");
            Message ms = new Message();
            ms.arg1 = 0;
            Bundle data=new Bundle();
            //"msg" is an internally used key.So not defined as a constant
            data.putString("msg", "Authentication failed \n check username password");
            ms.setData(data);
            handler.sendMessage(ms);

        }else if(loginStatus ==HttpConnector.RESP_CODE_CLIENT_CON_TIMEOUT){
        	Message ms=Message.obtain();
        	Bundle data=new Bundle();
        	data.putString("msg", "Cannot establish Connection with server. \n Client Connection Timed out");
            ms.setData(data);
        	ms.arg1=HttpConnector.RESP_CODE_CLIENT_CON_TIMEOUT;
        	handler.sendMessage(ms);
        }else {
            Log.d("Login", "unable to connect");
            Message ms = new Message();
            Bundle data=new Bundle();
        	data.putString("msg", "Unable to connect \n Resp Code: "+ loginStatus);
            ms.setData(data);
            ms.arg1 = 1;
            handler.sendMessage(ms);
        }

    }

}
