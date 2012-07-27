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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.xwiki.android.components.IntentExtra;
import org.xwiki.android.components.R;
import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.rest.HttpConnector;
import org.xwiki.android.svc.cmn.LoginFacade;
import org.xwiki.android.entity.*;

import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;

/**
 * UI Component of the user authentication screen
 * */
public class LoginActivity extends Activity implements OnItemSelectedListener,OnItemClickListener
{
    public static final String INTENT_EXTRA_GET_URL = IntentExtra.URL;

    public static final String INTENT_EXTRA_GET_USERNAME = IntentExtra.USERNAME;

    public static final String INTENT_EXTRA_GET_PASSWORD = IntentExtra.PASSWORD;

    private String username, password, url;
    private boolean  remPwd;
    
    AutoCompleteTextView actvUn;
    AutoCompleteTextView actvPwd;
    AutoCompleteTextView actvUrl;
    CheckBox cbPwdRem;
    Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);  
        actvUn = (AutoCompleteTextView) findViewById(R.id.actv_login_username);
        actvPwd = (AutoCompleteTextView) findViewById(R.id.actv_login_password);
        actvUrl = (AutoCompleteTextView) findViewById(R.id.actv_login_url);
        cbPwdRem=(CheckBox)findViewById(R.id.cb_login_pwdRem);
        Button loginButton = (Button) findViewById(R.id.button_login_login);
        loginButton.setOnClickListener(new OnClickListener()
        {
            
            public void onClick(View arg0)
            {   
            	username = actvUn.getText().toString();
                password = actvPwd.getText().toString();
                url = actvUrl.getText().toString();
                remPwd=cbPwdRem.isChecked();
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

                    
                    public void run()
                    {
                    	if(remPwd)Log.d(getClass().getSimpleName(),"pwd rem activated");
                    	int code=new LoginFacade().login(username, password, url,remPwd) ; 
                    	myProgressDialog.dismiss();                    	
                    	login(code);
                    	
                    	
                    }

                });
                t.start();
            }

        });
        
        //TODO:suggest: If slow move following to an async task
        EntityManager helper=new EntityManager(this);
        List<User> suggestions=new ArrayList();//empty array list
        try {
			Dao<User,Integer> udao=helper.getDao(User.class);
			suggestions=udao.queryForAll();//to add known users			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
        if(!suggestions.isEmpty()){
        	String[] userNames=new String[suggestions.size()];
        	String[] pwds=new String[suggestions.size()];
        	String[] urls=new String[suggestions.size()];
        	for(int i=0; i<suggestions.size();i++){
        		User u=suggestions.get(i);
        		userNames[i]=u.getUserName();
        		pwds[i]=u.getPassword();
        		urls[i]=u.getWikiRealm();
        	}
        	  	
        	
        	ArrayAdapter<String> adapterUns=new ArrayAdapter<String>(this, R.layout.searchresults_list_item, userNames);
        	ArrayAdapter<String> adapterPwds=new ArrayAdapter<String>(this, R.layout.searchresults_list_item, pwds);
        	ArrayAdapter<String> adapterUrls=new ArrayAdapter<String>(this, R.layout.searchresults_list_item, urls);
        	actvUn.setAdapter(adapterUns);   
        	actvPwd.setAdapter(adapterPwds);
        	actvUrl.setAdapter(adapterUrls);
        	//disable autocomplete dropdown for pwds
        	actvPwd.setDropDownHeight(0);
        	actvPwd.setThreshold(-1); 
        	actvUn.setOnItemSelectedListener(this);        
        }
        Log.i(this.getClass().getSimpleName(),"size of suggestions list"+suggestions.size());        
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



	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {	
			ArrayAdapter<String>adapterPwd=(ArrayAdapter<String>) actvPwd.getAdapter();
			ArrayAdapter<String>adapterUrls=(ArrayAdapter<String>) actvUrl.getAdapter();
			String pwd, url;
			pwd=adapterPwd.getItem((int) id);
			url=adapterUrls.getItem((int) id);
			actvPwd.setText(pwd);
			actvUrl.setText(url);
			Log.i(this.getClass().getSimpleName(),"Item selected at pos+"+id+" from "+parent.getClass().getSimpleName());
	}


	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		onItemSelected(parent, view, position, id);
	}
    
  //TODO: Save asyncTask in  onRetainNonConfigurationInstance() activity lifecycle method if orientation change is allowed
  //load array of username strings from db and add to auto complete of edit text.LoadAsyncTask<Integer,Integer, String[]> 

}
