import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class ParseHtml
{
	String sourceHtm;
	static int parseType;
	static final int TYPE_HTTP = 0;
	static final int TYPE_NOVEL_INDEX = 1;
	static final int TYPE_NOVEL_CHAPTER = 2;
	static final int TYPE_NOVEL_CONTENT = 3;
	private static Pattern pattern;

	ParseHtml(String s){
		this.sourceHtm = new String(s);
		this.setParseType(TYPE_HTTP);
		this.parseType = -1;
	}

	public void changeContent(String content){
		sourceHtm = content;
	}

	private static void setParseType(){
		switch(parseType)
		{
			case TYPE_HTTP :
				pattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
				break;
			case TYPE_NOVEL_INDEX :
				pattern = Pattern.compile("mydoc(\\d){3}.htm");
				break;
			case TYPE_NOVEL_CHAPTER:
				pattern = Pattern.compile("<A HREF=\"(.+?)\" >(.+?)</A>",Pattern.DOTALL);
				break;
			case TYPE_NOVEL_CONTENT:
				pattern = Pattern.compile("<!--HTMLBUILERPART0-->(.+?)<!--/HTMLBUILERPART0-->",Pattern.DOTALL);
				break;
			default:
				pattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
		}
	}

	public void setParseType(int p_type){
		parseType = p_type;
		this.setParseType();
	}

	private String preParse(String s){
		String tmp;
		
		if(parseType == TYPE_NOVEL_CHAPTER)
		{
			tmp = s.replaceAll("\r\n                                      ","");
			int i = tmp.indexOf(">");
			int j = tmp.indexOf("<",i);
			tmp = tmp.substring(i+1,j);
		}
		else if(parseType == TYPE_NOVEL_CONTENT)
		{
			tmp = s.replaceAll("<BR>","");
			tmp = tmp.replaceFirst("<!--HTMLBUILERPART0-->","");
			tmp = tmp.replaceFirst("<!--/HTMLBUILERPART0-->","");
			tmp = tmp.replaceAll("                            ¡¡¡¡","    ");
		}
		else
			tmp = s;
		
		return tmp;
	}

	public Vector getParseResults(){
		Vector vt = new Vector();
		
		Matcher matcher =pattern.matcher(sourceHtm);

		while(matcher.find()){
			String str = matcher.group();
			str = this.preParse(str);
			if(vt.indexOf(str)<0)vt.add(str);
		}

		return vt;
	}
	
/*
	public static void main(String[] args)
	{
		Vector vt = new Vector(5);
		Pattern pattern =Pattern.compile("(http://|https://){1}[\\w\\.\\-/:]+");
		GetHtml test = new GetHtml();
		String s = test.read("http://cspo.zju.edu.cn/");
		Matcher matcher =pattern.matcher(s);

		while(matcher.find()){
			String str = matcher.group();
			if(vt.indexOf(str)<0)vt.add(str);
//				buffer.append(matcher.group());
//				buffer.append("\r\n");
		}
		Iterator itr = vt.iterator();  // Obtain an iterator
		while(itr.hasNext()){
			String ur = (String)itr.next();
			System.out.println( ur );
		}
	}
*/
}