package org.xwiki.android.context;

import org.xwiki.android.entity.User;

public class XAContextInitializer {

	public static void updateToAuthenticatedState(User u){
		XAContext ctx=XAContext.getInstance();
		ctx.getUserSession().initilize(u);
	}
}
