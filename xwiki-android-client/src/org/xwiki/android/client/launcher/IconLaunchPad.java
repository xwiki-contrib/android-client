package org.xwiki.android.client.launcher;

import java.util.ArrayList;
import java.util.List;

import org.xwiki.android.client.R;
import org.xwiki.android.client.XWikiIntentFilters;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;

public class IconLaunchPad extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.launchpad);
		GridView gridview =(GridView) findViewById(R.id.gv_launchpad);
		gridview.setNumColumns(3);
		// querry activities that have action XWiki Main
		Intent intent = new Intent(XWikiIntentFilters.ACTION_MAIN);
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, PackageManager.GET_ACTIVITIES);
		List<ImageButton> imgBtns = new ArrayList<ImageButton>();

		int i = 0;
		for (ResolveInfo r : list) {
			// set img btn
			int iconId = r.getIconResource();
			ImageButton btn = new ImageButton(this);
			btn.setImageResource(iconId);
			btn.setBackgroundColor(Color.TRANSPARENT);
			imgBtns.add(btn);
			Log.d(this.getClass().getSimpleName(), "Btn for:"
					+ r.activityInfo.name + "icon id is:" + iconId);
			// set on clieck listener.
			btn.setTag(r.activityInfo.name);
			btn.setOnClickListener(this);
			i++;
		}
		ImgBtnAdapter adapter = new ImgBtnAdapter(imgBtns);
		gridview.setAdapter(adapter);
		
	}

	@Override
	public void onClick(View v) {
		String classname = (String) v.getTag();
		try {
			Class actCls = Class.forName(classname);
			Intent intent = new Intent(this, actCls);
			startActivity(intent);
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), e.getMessage() + "::"
					+ classname);
		}
	}

}
