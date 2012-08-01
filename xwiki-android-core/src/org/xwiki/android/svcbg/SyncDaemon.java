package org.xwiki.android.svcbg;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.data.fileStore.DocumentFao;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.data.fileStore.FileStoreManager;
import org.xwiki.android.data.rdb.EntityManager;
import org.xwiki.android.entity.SyncOutEntity;
import org.xwiki.android.entity.SyncOutEntity.StatusType;
import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.DocumentRao;
import org.xwiki.android.rest.ral.RESTfulManager;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.svc.xmodel.DocumentRemoteSvcCallbacks;
import org.xwiki.android.xmodel.entity.Document;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

/**
 * 
 * @author xwiki gsoc 2012
 * @version 1.0 Handles syncing of Documents with the server.
 */

// dev note:Other remote syncing functionalities should also be put here.
public class SyncDaemon extends Service
{
	private static final String TAG="Sync Daemon";
	private static final int SYNC_PERIOD = 3000;// sync every min.
    
	private Timer timer;
	private TimerTask task;
	
	

	@Override
	public void onCreate()
	{
		Log.d(TAG,"bg service created :Sync Daemon");
		// HandlerThread thread = new HandlerThread("ServiceStartArguments",
		// Process.THREAD_PRIORITY_BACKGROUND);
		// thread.start();
		timer = new Timer();
		task = new TimerTask()
		{
			@Override
			public void run()
			{
				XWikiApplicationContext ctx=XWikiApplicationContext.getInstance();
				EntityManager em=ctx.newEntityManager();
				boolean allOk=true;
				try {
					Dao<SyncOutEntity, Integer> dao = em.getDao(SyncOutEntity.class);
					List<SyncOutEntity> list=dao.queryForAll();
					Log.d(TAG, list.size()+" entries to sync out");
					if(!list.isEmpty()){
						RESTfulManager rm=ctx.newRESTfulManager();
						FileStoreManager fm=ctx.getFileStoreManager();
						DocumentRemoteSvcCallbacks clbks=new DocumentRemoteSvcCallbacks()
						{
							public void onCreated(Document res, boolean success,RaoException ex) {
								
							};
						};
						
						DocumentRao rao=rm.newDocumentRao();
						DocumentFao fao=fm.getDocumentFao();
						for(SyncOutEntity s:list){
							FSDocumentReference ref=s.getFSDocref();
							Document doc=fao.load(ref);
							try {
								rao.create(doc);
								dao.delete(s);
								fao.delete(ref);
							} catch (RestConnectionException e) {//network failure
							    allOk=false;
							    s.setLastTried(new Date());
							    dao.update(s);
								Log.i(TAG, "Network connectivity issue. Retry later");
								break;//abort sync. retry after SYNC_PERIOD
							} catch (RaoException e) {
							  //doc may be already created.
								try {
								    Log.i(TAG, "Try update document as the doc is already in the server");
									rao.update(doc);
									dao.delete(s);
									fao.delete(ref);
								} catch (RestConnectionException e1) {//network failure
								    allOk=false;
								    s.setLastTried(new Date());
	                                dao.update(s);
	                                Log.i(TAG, "Network connectivity issue. Retry later");
									break;//abort syncing. retry later
								} catch (RaoException e1) {
									// we cannot do an update also.
									//donot delete and recreate. Just mark aborted.
									s.setStatus(StatusType.ABORTED);
									dao.update(s);
									Log.e("TAG", "cannot sync. Can neither create nor update");
								}								
							}
						}
					}else{
						Log.d(TAG, "terminating SyncBackgroundService. No docs to sync out");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				timer.cancel();
				stopSelf();//stop native service after all sync entries are synced.
			}
		};

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Sync Daemon Starting", Toast.LENGTH_SHORT).show();
		timer.schedule(task, 3000, SYNC_PERIOD);
		return START_NOT_STICKY;
	}
	
	

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;// not supported
	}

	@Override
	public void onDestroy()
	{
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
	}
}
