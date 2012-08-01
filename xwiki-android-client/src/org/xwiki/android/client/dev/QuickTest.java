package org.xwiki.android.client.dev;

import java.sql.SQLException;
import java.util.List;

import org.xwiki.android.client.R;
import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.rdb.EntityManager;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.entity.SyncOutEntity;


import org.xwiki.android.rest.reference.DocumentReference;
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
		setContentView(R.layout.quicktest);
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
			Dao<SyncOutEntity,Integer> dao=em.getDao(SyncOutEntity.class);
			List<SyncOutEntity> lst = dao.queryForAll();
			
			SyncOutEntity s=lst.get(0);
			FSDocumentReference dr=s.getFSDocref();
			Dao<FSDocumentReference,Integer> fsdao=em.getDao(FSDocumentReference.class);
			dr=fsdao.queryForSameId(dr);
			alertbox.setMessage("dr Page Name: "+dr.getPageName()+"  id:"+dr.get_id());
			alertbox.show();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       // RESTfulManager mngr=new XmlRESTFulManager();
//    }   // mngr.getDocumentRao(new DocumentRaoCallback(){});
	}

}
