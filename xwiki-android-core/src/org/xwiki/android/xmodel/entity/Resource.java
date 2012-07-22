package org.xwiki.android.xmodel.entity;

import java.io.Serializable;

public class Resource implements Serializable
{

    private boolean isNew;

    private boolean edited;

    public void setNew(boolean val)
    {
        isNew = val;
    }

    /**
     * This value is set/get for the following purpose. if a resource is new always add this resource to the containing
     * resource's new list. For example Document.add(myobj); myedit(myobj);//update the myobj in the newObjects list of
     * Document. If the client edited a clone of the myobj and now wants to reset myobj. Use
     * Document.setObject("client side gen key for myobj", myobj2); setObject will check whether the resource is new and
     * update the newObjects list also. The Document.addObject() method will set this property to true. Otherwise do not
     * touch this property explicitly
     */
    public boolean isNew()
    {
        return isNew;
    }

    public boolean isEdited()
    {
        return edited;
    }
    /**
     * 
     * @param altered true=mark object for update.
     */
    public void setEdited(boolean altered)
    {
        this.edited = altered;
    }

}
