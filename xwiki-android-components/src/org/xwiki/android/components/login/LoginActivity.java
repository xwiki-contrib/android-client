package org.xwiki.android.components.login;

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
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String username, password, url;
	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Button loginButton = (Button) findViewById(R.id.button_login_login);

		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				EditText et_username = (EditText) findViewById(R.id.editText_login_username);
				EditText et_password = (EditText) findViewById(R.id.editText_login_password);
				EditText et_url = (EditText) findViewById(R.id.editText_login_url);

				username = et_username.getText().toString();
				password = et_password.getText().toString();
				url = et_url.getText().toString();

				handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {

						if (msg.arg1 == 0) {
							AlertDialog.Builder alertbox = new AlertDialog.Builder(
									LoginActivity.this);
							alertbox.setMessage("Authentication failed");
							alertbox.setNeutralButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
										}
									});
							alertbox.show();
						} else if (msg.arg1 == 1) {
							AlertDialog.Builder alertbox = new AlertDialog.Builder(
									LoginActivity.this);
							alertbox.setMessage("Unable to connect");
							alertbox.setNeutralButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface arg0, int arg1) {
										}
									});
						}
					}
				};

				final ProgressDialog myProgressDialog;
				myProgressDialog = ProgressDialog.show(LoginActivity.this,
						"Login", "Please wait while connecting...", true);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {

						HttpConnector httpConnector = new HttpConnector();
						int code = httpConnector.checkLogin(username, password,
								url);
						myProgressDialog.dismiss();
						login(code);
					}

				});
				t.start();
			}

		});
	}

	private void login(int loginStatus) {

		if (loginStatus == 200) {
			Log.d("Login", "successful");
			getIntent().putExtra("username",username);
			getIntent().putExtra("password", password);
			getIntent().putExtra("url", url);
			setResult(Activity.RESULT_OK, getIntent());
			finish();
		} else if (loginStatus == 401) {
			Log.d("Login", "wrong username or password");
			Message ms = new Message();
			ms.arg1 = 0;
			handler.sendMessage(ms);

		} else {
			Log.d("Login", "unable to connect");
			Message ms = new Message();
			ms.arg1 = 1;
			handler.sendMessage(ms);
		}

	}

}
