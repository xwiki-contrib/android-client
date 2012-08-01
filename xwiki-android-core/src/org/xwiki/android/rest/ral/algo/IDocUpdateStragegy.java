package org.xwiki.android.rest.ral.algo;

import org.xwiki.android.rest.RestConnectionException;
import org.xwiki.android.rest.ral.RaoException;
import org.xwiki.android.xmodel.entity.Document;

public interface IDocUpdateStragegy
{

    public abstract Document update(Document res) throws RestConnectionException, RaoException;

}
