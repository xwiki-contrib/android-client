package org.xwiki.android.ral;

import java.util.List;

import org.xwiki.android.ral.reference.EntityReference;

/**
 * @author xwiki gsoc 2012
 * @version 1.0 RESTful Access Object Interface [1]Behavior is unspecified for multiple concurrent requests for the same
 *          Rao.
 * @param <T> : type of the RAO.<Document | Space | Wiki >
 */
// TODO: rectify [1] above.
public interface Rao<T>
{
    void create(T resrc);

    void retreive(EntityReference<T> ref);

    void querry();

    void update(T resrc);// TODO: not yet specified.

    void delete(EntityReference<T> ref);

}

/**
 * note: When implementing do not give the responsibilities of off line syncing to components below RAOs. Use seperate
 * android Services to access RAOs from above and use them to sync when online. Those responsibilities go to the
 * seperate sync module.
 **/
