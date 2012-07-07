package org.xwiki.android.ral.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.xwiki.android.ral.reference.DocumentReference;
import org.xwiki.android.resources.Attribute;
import org.xwiki.android.resources.Object;
import org.xwiki.android.resources.Page;
import org.xwiki.android.resources.Property;
import org.xwiki.android.resources.Translation;
import org.xwiki.android.resources.Translations;
import org.xwiki.android.xmodel.entity.SimpleDocument;
import org.xwiki.android.xmodel.xobjects.XProperty;
import org.xwiki.android.xmodel.xobjects.XSimpleObject;

public class XMdlToSmplConverter
{

    // conversion param
    public static int PAGE = 1;

    public static int OBJECTS = 2;

    public static int COMMENTS = 4;

    public static int TAGS = 8;

    public static int ATTACHMENTS = 16;

    public static int ALL = 31;

    // public static int PAGE_AND_OBJECTS=3;

    private int parts;

    /**
     * @param parts {[PAGE]?|[OBJECTS]?|[COMMENTS]?|[TAGS]?} or ALL Ex1: make simple xml page + simple xml objects
     *            (PAGE+OBJECTS) or (PAGE|OBJECTS) Ex2: make Simplexml Attachment objects + comments + objects (first
     *            class entities derived from Object objects) (ATTACHMENTS+COMMENTS+OBJECTS)
     */
    public XMdlToSmplConverter(int parts)
    {
        this.parts = parts;
    }

    public XMdlToSmplConverter()
    {
        // defalut
        parts = PAGE + OBJECTS;
    }

    public SimpleXmlDocpad convertDocument(SimpleDocument doc)
    {
        SimpleXmlDocpad pad = new SimpleXmlDocpad();
        // save coordinates
        DocumentReference dref = doc.getDocumentReference();
        pad.wikiName = dref.getWikiName();
        pad.spaceName = dref.getSpaceName();
        pad.pageName = dref.getPageName();

        if ((parts & OBJECTS) == OBJECTS) {
            // add new objects
            for (XSimpleObject xobj : doc.getAllNewObjects()) {
                Object smplObj = toSimpleXmlObj(xobj);
                pad.newObjects.add(smplObj);
            }
            // put altered objects
            Map<String, XSimpleObject> xobjs = doc.getAllEditedObjects();
            Set<Entry<String, XSimpleObject>> entrySet = xobjs.entrySet();
            for (Entry<String, XSimpleObject> en : entrySet) {
                pad.editedObjects.put(en.getKey(), toSimpleXmlObj(en.getValue()));
            }
            // mark deleted objects.
            List<String> delObjs = doc.getAllDeletedObjects();
            for (String objKey : delObjs) {
                pad.deletedObjects.add(objKey);
            }
        }
        if ((parts & PAGE) == PAGE) {
            Page page = new Page();
            page.id = doc.getRemoteId();
            page.fullName = doc.getFullName();
            page.wiki = doc.getWikiName();
            page.space = doc.getSpaceName();
            page.name = doc.getSimpleName();
            page.title = doc.getTitle();
            page.parent = doc.getParentFullName();
            page.parentId = doc.getParentId();
            page.xwikiRelativeUrl = doc.getXwikiRelativeUrl();
            page.xwikiAbsoluteUrl = doc.getXwikiAbsoluteUrl();
            page.translations = null;
            { // set page.translations
                List<String> ts = doc.getTranslations();
                if (ts != null) {
                    List<Translation> trnList = new ArrayList<Translation>();
                    for (String t : ts) {
                        Translation trn = new Translation();
                        trn.setLanguage(t);
                        trnList.add(trn);
                    }
                    Translations trsElement = new Translations();
                    trsElement.translations = trnList;
                    page.translations = trsElement;
                }
            }
            page.syntax = doc.getSyntax();
            page.language = doc.getLanguage();
            page.version = doc.getVersion();
            page.majorVersion = doc.getMajorVersion();
            page.minorVersion = doc.getMinorVersion();
            page.created = doc.getCreated();
            page.creator = doc.getCreator();
            page.modified = doc.getModified();
            page.modifier = doc.getModifier();
            page.content = doc.getContent();
            pad.page = page;
        }

        if ((parts & COMMENTS) == COMMENTS) {
            throw new UnsupportedOperationException("converting to first class entities not supported");
        }
        if ((parts & TAGS) == TAGS) {
            throw new UnsupportedOperationException("converting to first class entities not supported");
        }
        if ((parts & ATTACHMENTS) == ATTACHMENTS) {
            throw new UnsupportedOperationException("converting to first class entities not supported");
        }

        // TODO: implement for COMMENTS,TAGS,ATTACHMENTS. note: no need for
        // "ALL", its automatically done as 31=1+2+...
        return pad;
    }

    // public SimpleXmlSpacepad convertSpace(){
    // //TODO: implement
    // throws new UnsupportedOperationException("not yet imlplemented");
    // }

    private org.xwiki.android.resources.Object toSimpleXmlObj(XSimpleObject xobj)
    {
        Object obj = new Object();
        obj.id = xobj.getId();
        obj.guid = xobj.getGuid();
        obj.pageId = xobj.getPageid();
        obj.wiki = xobj.getWiki();
        obj.space = xobj.getSpace();
        obj.pageName = xobj.getPageName();
        obj.number = xobj.getNumber();
        obj.className = xobj.getClassName();
        obj.headline = xobj.getHeadline();

        Map<String, XProperty> xprops = xobj.getProperties();
        Set<Entry<String, XProperty>> entrySet = xprops.entrySet();

        List<Property> plist = new ArrayList<Property>();
        for (Entry<String, XProperty> entry : entrySet) {

            XProperty xp = entry.getValue();
            String pName = entry.getKey();
            // transfer fields to resource.Property
            Property p = new Property();
            p.setName(pName);
            p.setType(xp.getType());
            p.setValue(xp.toString());

            Map<String, java.lang.Object> attrs = xp.getAllAttributes();
            Set<Entry<String, java.lang.Object>> atrEntrySet = attrs.entrySet();

            List<Attribute> attrList = new ArrayList();
            for (Entry<String, java.lang.Object> atrEn : atrEntrySet) {
                Attribute atr = new Attribute();
                atr.setName(atrEn.getKey());
                atr.setValue(atrEn.getValue().toString());
                attrList.add(atr);
            }
            plist.add(p.withAttributes(attrList));
        }
        return obj.withProperties(plist);
    }
}
