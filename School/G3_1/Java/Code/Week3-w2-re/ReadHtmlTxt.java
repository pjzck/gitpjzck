import java.io.*;
import java.net.*;

public class ReadHtmlTxt
{
  String urlstr;
  ReadHtmlTxt(String u)
  {
	urlstr=u;
  }

  public String getHtml()
  {
	String str="", buf;
    try{
	  URL url = new URL(urlstr);
      DataInputStream in =	
		new DataInputStream(
		new BufferedInputStream(
		url.openStream()));

	  String ms0="<!--HTMLBUILERPART0-->";
	  String ms1="<!--/HTMLBUILERPART0-->";
	  boolean flag=false;
	  int	i, j, k;
	  while((buf=in.readLine())!=null){
		buf =(new String(buf.getBytes("ISO-8859-1"),"GBK"));
		if((i=buf.indexOf(ms0))>=0)flag=true;
		if((i=buf.indexOf(ms1))>=0)flag=false;
		if(flag)str+=buf+"\n";
	  }
      in.close();
    }catch(Exception e){
    }
	return str;
  }

  public static void main(String[] args)
  {
	String urlstr="http://www.readers365.com/jinyong/08/mydoc001.htm";
	ReadHtmlTxt w = new ReadHtmlTxt(urlstr);
	System.out.println(w.getHtml());
  }
}