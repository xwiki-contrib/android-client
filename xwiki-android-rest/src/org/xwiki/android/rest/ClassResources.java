package org.xwiki.android.rest;



import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xwiki.android.resources.Classes;
import org.xwiki.android.resources.Class;


public class ClassResources extends HttpConnector
{
    private final String PAGE_URL_PREFIX = "/xwiki/rest/wikis/";

  

    private String URLprefix;

    private String wikiName;

    

    public ClassResources(String URLprefix, String wikiName)
    {
        this.URLprefix = URLprefix;
        this.wikiName = wikiName;
    }

    public Classes getWikiClasses()
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/classes" ;

        return buildClasses(super.getResponse(Uri));
    }
    
    public Class getWikiClasses(String classname)
    {
        String Uri = "http://" + URLprefix + PAGE_URL_PREFIX + wikiName + "/classes/" + classname ;

        return buildClass(super.getResponse(Uri));
    }
    
    // decode xml content to Classes element
    private Classes buildClasses(String content)
    {
        Classes classes = new Classes();

        Serializer serializer = new Persister();

        try {
            classes = serializer.read(Classes.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return classes;
    }
    
    // decode xml content to Class element
    private Class buildClass(String content)
    {
        Class clas =new Class();

        Serializer serializer = new Persister();

        try {
            clas = serializer.read(Class.class, content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return clas;
    }

}
