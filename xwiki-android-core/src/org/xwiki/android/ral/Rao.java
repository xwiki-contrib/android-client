package org.xwiki.android.ral;


import java.util.List;

import org.xwiki.android.ral.reference.EntityReference;
import org.xwiki.android.rest.RestConnectorException;

/**
 * @author xwiki gsoc 2012
 * @version 1.0 RESTful Access Object Interface [1]Behavior is unspecified for multiple concurrent requests for the same
 *          Rao.
 * @param <T> : type of the RAO.<Document | Space | Wiki >
 */
// TODO: rectify [1] above.
interface Rao<T>
{
    T create(T resrc) throws RestConnectorException,RaoException;

    T retreive(T resrc) throws RestConnectorException,RaoException;

    List<T> querry()throws RestConnectorException,RaoException;

    T update(T resrc)throws RestConnectorException,RaoException;// TODO: not yet specified.

    void delete(T resrc)throws RestConnectorException,RaoException;

}

/**
 * note: When implementing do not give the responsibilities of off line syncing to components below RAOs. Use seperate
 * android Services to access RAOs from above and use them to sync when online. Those responsibilities go to the
 * seperate sync module.
 **/
