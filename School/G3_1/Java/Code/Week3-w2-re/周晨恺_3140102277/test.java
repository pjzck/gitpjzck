import java.io.*;
import java.util.*;
import java.util.regex.*;
public class test
{
	public static void main(String[] args)
	{
		FileManager fm = new FileManager();
		String ParentHtm = "http://www.readers365.com/jinyong/08/";
		GetHtml test = new GetHtml();
		String s = test.read(ParentHtm + "index.htm");
		ParseHtml test_parse = new ParseHtml(s);

		test_parse.setParseType(ParseHtml.TYPE_NOVEL_INDEX);

		Vector vt_link = test_parse.getParseResults();
		Iterator itr_link = vt_link.iterator();  // Obtain an iterator

		test_parse.setParseType(ParseHtml.TYPE_NOVEL_CHAPTER);
		
		Vector vt_chapter = test_parse.getParseResults();
		Iterator itr_chapter = vt_chapter.iterator();
		int count = 0;

		while(itr_link.hasNext()){
			String ur = ParentHtm + (String)itr_link.next();
			String content = test.read(ur);
			String chapter_name = (String)itr_chapter.next();

			count++;
			test_parse.changeContent(content);
			test_parse.setParseType(ParseHtml.TYPE_NOVEL_CONTENT);
			
			Vector vt_content = test_parse.getParseResults();
			String fileName = "./novel/" + count + ".txt";

			System.out.println( chapter_name + "\r\n" + fileName + "\r\n" );
			
			content = (String)vt_content.get(0);
			fm.writeData(fileName, chapter_name + "\r\n" + content);
		}
	}
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

//            buffer.append(matcher.group());
//            buffer.append("\r\n");
        }
		Iterator itr = vt.iterator();  // Obtain an iterator
		while(itr.hasNext()){
			String ur = (String)itr.next();
			System.out.println( ur );
		}
    }
}
    
*/