package org.xwiki.android.rest.ral.wrappers;

import org.xwiki.android.xmodel.entity.Document;

public class TranspDocumentWrapper extends DocumentWrapper
{

	boolean pageSummary;
	boolean page;
	boolean historySummaries;
	boolean comments;
	boolean attachmentSummaries;
	
	
	public TranspDocumentWrapper(Document doc)
	{
		super(doc);		
	}

}

/**

page summary
<link></link>
<id>xwiki:Blog.BlogIntroduction</id>
<fullName>Blog.BlogIntroduction</fullName>
<wiki>xwiki</wiki>
<space>Blog</space>
<name>BlogIntroduction</name>
<title>First blog post</title>
<parent>Blog.WebHome</parent>
<parentId>xwiki:Blog.WebHome</parentId>
<xwikiRelativeUrl>
http://localhost:8080/xwiki/bin/view/Blog/BlogIntroduction
</xwikiRelativeUrl>
<xwikiAbsoluteUrl>
http://localhost:8080/xwiki/bin/view/Blog/BlogIntroduction
</xwikiAbsoluteUrl>
<translations/>
<syntax>xwiki/2.0</syntax>



page

<link rel="http://www.xwiki.org/rel/class" href="http://localhost:8080/xwiki/rest/wikis/xwiki/classes/Blog.BlogIntroduction"/>
<id>xwiki:Blog.BlogIntroduction</id>
<fullName>Blog.BlogIntroduction</fullName>
<wiki>xwiki</wiki>
<space>Blog</space>
<name>BlogIntroduction</name>
<title>First blog post</title>
<parent>Blog.WebHome</parent>
<parentId>xwiki:Blog.WebHome</parentId>
<xwikiRelativeUrl>
http://localhost:8080/xwiki/bin/view/Blog/BlogIntroduction
</xwikiRelativeUrl>
<xwikiAbsoluteUrl>
http://localhost:8080/xwiki/bin/view/Blog/BlogIntroduction
</xwikiAbsoluteUrl>
<translations/>
<syntax>xwiki/2.0</syntax>
<language/>
<version>14.1</version>
<majorVersion>14</majorVersion>
<minorVersion>1</minorVersion>
<created>2009-03-11T14:58:10+05:30</created>
<creator>XWiki.Admin</creator>
<modified>2012-07-11T16:58:21+05:30</modified>
<modifier>XWiki.superadmin</modifier>
<content/>


<objectSummary>
<link rel="http://www.xwiki.org/rel/object" href="http://localhost:8080/xwiki/rest/wikis/xwiki/spaces/Blog/pages/BlogIntroduction/objects/Blog.BlogPostClass/0"/>
<link rel="http://www.xwiki.org/rel/properties" href="http://localhost:8080/xwiki/rest/wikis/xwiki/spaces/Blog/pages/BlogIntroduction/objects/Blog.BlogPostClass/0/properties"/>
<id>
xwiki:Blog.BlogIntroduction:49ea4e6b-9a83-4f63-9ab9-7b596176bce8
</id>
<guid>49ea4e6b-9a83-4f63-9ab9-7b596176bce8</guid>
<pageId>xwiki:Blog.BlogIntroduction</pageId>
<wiki>xwiki</wiki>
<space>Blog</space>
<pageName>BlogIntroduction</pageName>
<className>Blog.BlogPostClass</className>
<number>0</number>
<headline>Blog.News</headline>
</objectSummary>



<historySummary>
<link rel="http://www.xwiki.org/rel/page" href="http://localhost:8080/xwiki/rest/wikis/xwiki/spaces/Blog/pages/BlogIntroduction/history/14.1"/>
<pageId>xwiki:Blog.BlogIntroduction</pageId>
<wiki>xwiki</wiki>
<space>Blog</space>
<name>BlogIntroduction</name>
<version>14.1</version>
<majorVersion>14</majorVersion>
<minorVersion>1</minorVersion>
<modified>2012-07-11T16:58:21+05:30</modified>
<modifier>XWiki.superadmin</modifier>
</historySummary>

then comes hisotry page.


**/

