package org.xwiki.android.client.launcher;

import java.util.List;

import org.xwiki.android.client.XWikiIntentFilters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;

public class ImgBtnAdapter extends BaseAdapter implements ListAdapter {
	
	ImageButton btns[];
	ImgBtnAdapter(List<ImageButton> btnList){		
		btns=new ImageButton[btnList.size()];
		btns=btnList.toArray(btns);		
	}
	
	@Override
	public int getCount() {		
		return btns.length;
	}

	@Override
	public Object getItem(int position) {		
		return btns[position];
	}
	/**
	 * @return 0
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		ImageButton btn;
		if(convertView==null){
			btn=btns[position];
		}else{
			btn=(ImageButton)convertView;
		}		
		return btn;
	}

}
