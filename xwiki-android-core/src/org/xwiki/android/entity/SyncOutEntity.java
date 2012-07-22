package org.xwiki.android.entity;

import java.util.Date;

import org.xwiki.android.fileStore.FSDocumentReference;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 
 * @author xwiki gsoc 2012
 * @version 1.0
 * Marker to sync documents (out)to the server. * 
 * Sync out= sync direction: device to server.
 * Sync in = sync direction: server to device.
 */


@DatabaseTable(tableName="C_SyncOut")
public class SyncOutEntity
{
	@DatabaseField(generatedId=true)
	int _id;
	
	@DatabaseField(foreign=true, unique=true)
	private FSDocumentReference docref;
	
	@DatabaseField
	private boolean delAftrSync;
	
	@DatabaseField
	private StratergyType stratery;//not used yet.For advanced use. Things like do Create doc, abort if already created.
	
	@DatabaseField 
	private StatusType status;
	
	@DatabaseField
	private Date lastTried;
	
	private SyncOutEntity()
	{
		//for ORMlite
	}
	
	public SyncOutEntity(FSDocumentReference ref)
	{
		this.docref=ref;
		stratery=StratergyType.DEFALUT;
		delAftrSync=true;		
	}
	
	public enum StratergyType{
		DEFALUT, CREATE , UPDATE	
	}
	public enum StatusType{
		SUCCESS, PARTIALLYCOMPLETE, FAILED	
	}

	
	public FSDocumentReference getDocref()
	{
		return docref;
	}

	public void setDocref(FSDocumentReference docref)
	{
		this.docref = docref;
	}

	public boolean isDelAftrSync()
	{
		return delAftrSync;
	}

	public void setDelAftrSync(boolean delAftrSync)
	{
		this.delAftrSync = delAftrSync;
	}

	public StratergyType getStratery()
	{
		return stratery;
	}

	public void setStratery(StratergyType stratery)
	{
		this.stratery = stratery;
	}

	public StatusType getStatus()
	{
		return status;
	}

	public void setStatus(StatusType status)
	{
		this.status = status;
	}

	public Date getLastTried()
	{
		return lastTried;
	}

	public void setLastTried(Date lastTried)
	{
		this.lastTried = lastTried;
	}

		
	
}
