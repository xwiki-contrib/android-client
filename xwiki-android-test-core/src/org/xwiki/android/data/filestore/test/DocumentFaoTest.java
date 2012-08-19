package org.xwiki.android.data.filestore.test;

import java.io.File;
import java.util.List;

import org.xwiki.android.context.XWikiApplicationContext;
import org.xwiki.android.core.test.properties.TestConstants;
import org.xwiki.android.data.fileStore.DocumentFao;
import org.xwiki.android.data.fileStore.FSDocumentReference;
import org.xwiki.android.data.fileStore.FileStoreException;
import org.xwiki.android.data.fileStore.FileStoreManager;
import org.xwiki.android.xmodel.blog.XBlogPost;
import org.xwiki.android.xmodel.entity.Comment;
import org.xwiki.android.xmodel.entity.Document;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

import android.database.SQLException;
import android.test.AndroidTestCase;

public class DocumentFaoTest extends AndroidTestCase
{

    String wikiName, spaceName, pageName, objectClassname;
    int objectNumber;
    FileStoreManager fm;
    DocumentFao fao;

    Document doc;
    XBlogPost xblg;
    Comment c;

    @Override
    protected void setUp() throws Exception
    {
        wikiName = TestConstants.WIKI_NAME;
        spaceName = TestConstants.SPACE_NAME;
        pageName = TestConstants.PAGE_NAME;
        objectClassname = TestConstants.OBJECT_CLASS_NAME_1;
        objectNumber = TestConstants.OBJECT_NUMBER;

        XWikiApplicationContext ctx = XWikiApplicationContext.getInstance();
        fm = ctx.getFileStoreManager();
        fao = fm.getDocumentFao();

        doc = new Document(wikiName, spaceName, pageName);
        xblg = new XBlogPost();
        xblg.setContent("hi");
        c = new Comment("hi");
        XSimpleObject xso = new XSimpleObject(objectClassname)
        {
        };

        doc.addComment(c);
        xso.setNumber(objectNumber);
        doc.setObject(xso);
        xblg.setNumber(0);
        doc.setObject(xblg);

    }

    public void testSave_01()
    {

        FSDocumentReference ref = fao.save(doc, "sas");
        // verify
        assertNotNull(ref.getFile());

    }

    public void testSave_02_saveTwice()
    {

        FSDocumentReference ref = fao.save(doc, "sas");
        FSDocumentReference ref2 = fao.save(doc, "sas");
        assertEquals(ref.getFile(), ref2.getFile());

    }

    public void testSave_03_saveTwice()
    {
        boolean ex = false;

        FSDocumentReference ref = fao.save(doc, "sas");
        try {
            FSDocumentReference ref2 = fao.save(doc, "exception now!"); // this is the current behaviour of save method.
            // Delete this test case if you need to change behaviour :-). Make sure to do a manual functional test after
            // semantics change.Should not affect anyway ;-). No support yet for multiple versions and other
            // fs locations.
        } catch (FileStoreException.UnsupportedOperation e) {
            ex = true;
        }
        assertTrue(ex);
    }

    public void testList_afterSave()
    {
        fao.save(doc, "sas");
        List<FSDocumentReference> lst = fao.listByTag("sas");

        assertNotNull(lst);
        assertTrue(lst.size() > 0);

    }

    public void testLoad_afterSave()
    {
        fao.save(doc, "sas");
        List<FSDocumentReference> lst = fao.listByTag("sas");
        Document ldoc = fao.load(lst.get(0).getFile());
        assertTrue(ldoc.getAllComments().size() > 0);
    }

    public void testDelete()
    {
        fao.save(doc, "sas");
        FSDocumentReference fsref=new FSDocumentReference();
        fsref.copyfrom(doc.getDocumentReference());
        assertTrue(fao.delete(fsref));
    }
}
