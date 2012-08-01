package org.xwiki.android.data.fileStore;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author xwiki gsoc 2012 
 * 		Marker interface only.(package lvl)
 * 		These objects are for saving entities in the FileStore. This is a combination of Database,
 *         simple File saving. ! If you just want to save a file to the device this is not the API you should use.
 * @param <T>
 */
interface Fao<T>
{

    /**
     * save the enttity to defalut file store location.
     * 
     * @param entity
     * @param mode
     * @return an FSEntity reference.
     */
    <R> R save(T entity, String tag);

    // /**
    // *
    // * @param entity The Entity to save
    // * @param mode :an android native access mode. Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE , Context.
    // MODE_WORLD_WRITEABLE
    // * @param f, File where to save.
    // * @return file where the entity was saved.
    // * Since you are saving a file into the File Store the Fles Store may still save the file to an it's own location
    // * if some errors occure. ex: you give a file path wich is in sd card. But stream to SD card suddenly gets an IO
    // error.
    // * filestore manager silently try another approach and give back the file where the entity was saved to.
    // *
    // * null if unsuccessful.
    // */
    // File save(T entity,String tag,File f,int mode);
    //
    // TODO: above method is for storing to external storage. Need will arise in future.

    /**
     * @param file
     * @return T object from the file, if success. null if fail.
     */
    T load(File file);

    /**
     * Use only if sure there are no more than a few entities with the given tag.
     * 
     * @param tag
     * @return Fully loaded entities which were saved with the given tag.
     */
    List<T> list(String tag);
}
