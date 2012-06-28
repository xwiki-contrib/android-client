package org.xwiki.android.client.dev;

import java.sql.SQLException;

import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.xmodel.entity.Document;

import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
/**
 * class for test purposes .
 * @author xwiki gsoc 2012
 *
 */
public class QuickTest extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage("Only a code testing/exploration activity.remove this from production");
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1){}
        });
        alertbox.show();   
        
        XWikiApplicationContext ctx=(XWikiApplicationContext)getApplicationContext();
        EntityManager em=ctx.newEntityManager();
        try {
			Dao<Document,Integer> docdao=em.getDao(Document.class);
			docdao.create(new Document());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
