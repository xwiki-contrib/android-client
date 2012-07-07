package org.xwiki.android.dal;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xwiki.android.context.GlobalConstants;
import org.xwiki.android.core.*;
import org.xwiki.android.entity.LoginAttempt;
import org.xwiki.android.entity.User;
import org.xwiki.android.fileStore.FSDocumentReference;
import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.xmodel.entity.Document;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class EntityManager extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_NAME = GlobalConstants.FILE_CORE_DATABASE;

    private static final int DATABASE_VERSION = 1;

    /**
     * This is called when the database is first created. Add logic to create all db tables for defined entities.
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
    {
        // TODO Auto-generated method stub
        try {
            Log.i(EntityManager.class.getSimpleName(), "onCreate(): create Database");
            // add TableUtils.createTable(connectionSource, MyEntity.class); for all your entities
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, FSDocumentReference.class);
            TableUtils.createTable(connectionSource, LoginAttempt.class);

        } catch (SQLException e) {
            Log.e(EntityManager.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub

    }

    public EntityManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

}
