package org.xwiki.android.bgsvcs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.dal.EntityManager;
import org.xwiki.android.entity.SyncOutEntity;
import org.xwiki.android.entity.SyncOutEntity.StatusType;
import org.xwiki.android.fileStore.DocumentFao;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.fileStore.FileStoreManager;
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
public class SyncBackgroundService extends Service
{
	private static final String tag="SyncBackgroundService";
	private static final int SYNC_PERIOD = 60000;// sync every min.
	private Timer timer;
	private TimerTask task;
	
	

	@Override
	public void onCreate()
	{
		Log.d(tag,"bg service created");
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
							FSDocumentReference ref=s.getDocref();
							Document doc=fao.load(ref);
							try {
								rao.create(doc);
								dao.delete(s);
							} catch (RestConnectionException e) {
							    allOk=false;
							    s.setLastTried(new Date());
							    dao.update(s);
								e.printStackTrace();
								break;//abort sync. retry after SYNC_PERIOD
							} catch (RaoException e) {
								//doc may be already created.
								try {
									rao.update(doc);
									dao.delete(s);
								} catch (RestConnectionException e1) {	
								    allOk=false;
								    s.setLastTried(new Date());
	                                dao.update(s);
									e1.printStackTrace();
									break;
								} catch (RaoException e1) {
									// we cannot do an update also.
									//donot delete and recreate. Just mark failed.
									s.setStatus(StatusType.ABORTED);
									dao.update(s);
									e1.printStackTrace();
								}
								e.printStackTrace();
							}
						}
					}else{
						Log.d(tag, "terminating SyncBackgroundService. No docs to sync out");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				stopSelf();//stop native service after all sync entries are synced.
			}
		};

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Toast.makeText(this, "Sync Service Starting", Toast.LENGTH_SHORT).show();
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
