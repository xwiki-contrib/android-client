package org.xwiki.android.fileStore;

import java.io.File;

public interface FileAccessObject<T> {
	/**
	 * 
	 * @param entity The Entity to save
	 * @param mode :an android native access mode. Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE , Context. MODE_WORLD_WRITEABLE
	 * @return the File
	 */
	File save(T entity,File f,int mode); //todo: throw file IOException
	
	T load(File file);
}
