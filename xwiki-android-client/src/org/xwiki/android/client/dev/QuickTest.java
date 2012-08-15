package org.xwiki.android.client.dev;

import java.sql.SQLException;
import java.util.List;

import org.xwiki.android.client.R;
import org.xwiki.android.components.login.LoginActivity;
import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.rdb.EntityManager;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.entity.SyncOutEntity;

import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.rest.reference.DocumentReference;
import org.xwiki.android.security.Master;

import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * class for test purposes .
 * 
 * @author xwiki gsoc 2012
 * 
 */
public class QuickTest extends Activity
{
	private static final String TAG = "Quikc TEST";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quicktest);
		AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
		alertbox.setMessage("Only a code testing/exploration activity.remove this from production");
		alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface arg0, int arg1)
			{
			}
		});
		alertbox.show();

		/*
		 * XWikiApplicationContext
		 * ctx=(XWikiApplicationContext)getApplicationContext(); EntityManager
		 * em=ctx.newEntityManager(); try { Dao<SyncOutEntity,Integer>
		 * dao=em.getDao(SyncOutEntity.class); List<SyncOutEntity> lst =
		 * dao.queryForAll();
		 * 
		 * SyncOutEntity s=lst.get(0); FSDocumentReference dr=s.getFSDocref();
		 * Dao<FSDocumentReference,Integer>
		 * fsdao=em.getDao(FSDocumentReference.class);
		 * dr=fsdao.queryForSameId(dr);
		 * alertbox.setMessage("dr Page Name: "+dr.getPageName
		 * ()+"  id:"+dr.get_id()); alertbox.show();
		 * 
		 * } catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// test enc
		// Master secMstr=new Master();
		// String
		// cipher=secMstr.encryptPassword("abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()");
		// Log.d( TAG, cipher);
		// Log.d(TAG, secMstr.decryptPassword(cipher));

		/**
		 * test doc create
		 */
		// Comment c1,c2;
		// Document doc=new Document("xwiki", "Blog", "tempTestPage" );
		// doc.setTitle("tempTestPage");
		// c1 = new Comment("hi");
		// c2 = new Comment("reply to hi");
		// c1.addReplyComment(c2);
		// doc.addComment(c1, true);

		// ;;;;;;;;;;;;;
		Comment c1, c2, c3, c4;
		Document doc = new Document("xwiki", "Blog", "tempTestPage");
		doc.setTitle("tempTestPage");
		c1 = new Comment("0");
		c2 = new Comment("1");
		c3 = new Comment("2");
		c3.setId(2);
		c4 = new Comment("3");
		c4.setId(3);

		c1.addReplyComment(c2);
		c3.addReplyComment(c4);

		doc.addComment(c1, true);
		doc.setComment(c3);
		doc.setComment(c4);		

		DocumentRao rao = XWikiApplicationContext.getInstance().newRESTfulManager().newDocumentRao();

		try {
			rao.delete(doc);
			rao.create(doc);
		} catch (RestConnectionException e) {
			e.printStackTrace();
		} catch (RaoException e) {
			e.printStackTrace();
		}
	}

}
