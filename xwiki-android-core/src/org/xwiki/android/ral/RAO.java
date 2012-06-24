package org.xwiki.android.ral;

import java.util.List;

/**
 * 
 * @author xwiki gsoc 2012
 *
 * RESTful Access Object Interface
 * @param <T> : type of the RAO.<Document | Space | Wiki >
 */

public interface RAO<T> {
	T create(T resrc);
	T retreive(T resrc);
	List<T> querry();
	T update(T resrc);
	void delete();
}


/**
 * note:
 * When implementing do not give the responsibilities of off line syncing to components below RAOs. 
 * Use seperate android Services to access RAOs from above and use them to sync when online.
 * Those responsibilities go to the seperate sync module.
 **/
