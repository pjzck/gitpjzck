import java.io.*;
import java.net.*;
import java.util.*;

public class GetHtml
{
	String htm;

	GetHtml(){}

	public String read(String s_htm)
	{
	
	try{
		this.htm = s_htm;
		URL	url = new URL(this.htm);
		StringBuffer sourceHtm = new StringBuffer();
		BufferedReader in = new BufferedReader(
			new InputStreamReader( url.openStream() ) );
		String buffer = new String();

		while(!(null == (buffer = in.readLine())))
			sourceHtm.append(buffer).append("\r\n");
//		System.out.println(sourceHtm.toString());
		return sourceHtm.toString();
		}catch(Exception e){
			System.out.println("Error:\n" + e);
		}
		
		return null;
	}
/*
	public static void main(String[] args)
	{
		GetHtml test = new GetHtml();
		test.read("http://news.ifeng.com/a/20161013/50094299_0.shtml");
	}
*/
}