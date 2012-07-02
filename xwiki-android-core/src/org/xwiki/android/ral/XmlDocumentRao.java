package org.xwiki.android.ral;

import java.util.List;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.ral.transformation.SimpleXmlDocpad;
import org.xwiki.android.ral.transformation.XMdlToSmplConverter;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.rest.PageResources;
import org.xwiki.android.rest.Requests;
import org.xwiki.android.rest.XWikiRestfulAPI;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.entity.SimpleDocument;
import org.xwiki.android.xmodel.reference.DocumentReference;
import org.xwiki.android.xmodel.reference.EntityReference;
/**
 * 
 * @author xwiki gsoc 2012
 * @version 0.8 alpha
 * uses simplexml Rest model.
 * Non public. Use XMLRestfulManager to create new.
 * Very primitive implementation with single Http Connection for all work. *
 */
//TODO: current impl only support single http connection through Requests class. Implement for multiple parallel connections to reduce latency.
class XmlDocumentRao implements Rao<Document> {
	private static int PAGE=XMdlToSmplConverter.PAGE;
	private static int OBJECTS=XMdlToSmplConverter.OBJECTS;
	
	private RaoCallbackForDocument mycallback;	
	
	public XmlDocumentRao(RaoCallbackForDocument callback){
		this.mycallback=callback;
	}
	
	@Override
	/**
	 * create the doc on server.
	 */
	public void create(Document res) {//Only simpleDocument is supported yet.
		
		ConnectionThread con1=new ConnectionThread((SimpleDocument) res){				
			public void run() {					
				XMdlToSmplConverter con=new XMdlToSmplConverter(PAGE+OBJECTS);
				SimpleXmlDocpad pad = con.convertDocument(doc);
				XWikiApplicationContext ctx=XWikiApplicationContext.getInstance();
				String urlPprefix=ctx.getUserSession().getRealm();		
				XWikiRestfulAPI api=new Requests(urlPprefix);				
				Page page=pad.getPage();
				String wikiName=pad.getWikiName();
				String spaceName=pad.getSpaceName();
				String pageName=pad.getPageName();
				String statusLine=api.addPage(wikiName, spaceName, pageName, page);
				if(statusLine.contains("201")){ //TODO: knowledge of XWikiRestful API should be transfered to Request.
												// current approach will rslt in status line checking all over the code!
												// suitable exception hierarchy should be implemented. checked exceptions for
												// client side connection errors. Unchecked ones for bad api usage.(because they
												// cannot be rectified even if caught)
					for(Object object:pad.getNewObjects()){//TODO: these can be done in parallel
						if(statusLine.equals("error")) break;
						statusLine=api.addObject(wikiName, spaceName, pageName, object);					
					}
				}
				//there are no altered objects to be updated since the doc is new.				
				//TODO:current impl of Request does not return response body. So null for doc.
				if(!statusLine.equals("error")){
					//304 if there was error in page representation.
					//201 created.
					if(statusLine.contains("201")){
						mycallback.invokeCreated(null, true, statusLine);
					}
					else{
						mycallback.invokeCreated(null, false, statusLine);
					}
				}else{
					mycallback.invokeCreated(null, false, statusLine);
				}
			}
		};
		
		
		
	}

	@Override
	public void retreive(EntityReference<Document> docRef) {		
		
	}

	@Override
	public void querry() {
		
	}

	@Override
	public void update(Document res) {
		
	}

	@Override
	public void delete(EntityReference<Document> docRef) {
		// TODO Auto-generated method stub
		
	}
	
	
	private abstract class ConnectionThread extends Thread{
		SimpleDocument doc;
		ConnectionThread(SimpleDocument d){
			doc=d;
		}
		public abstract void run();
	}


	

	

}
