import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ParseHtml
{
	private StringBuffer sourceHtml;
	private StringBuffer ansHtml;
	private static long lastStart;
	private static long lastEnd;
	final static int state_header_s		= 0;
	final static int state_header_d		= 1;
	final static int state_body_s		= 2;
	final static int state_body_nt		= 3;
	final static int state_body_sco		= 4;
	final static int state_body_co		= 5;
	final static int state_finish		= 6;
	static int nowState;
	
	ParseHtml()
	{
		this.nowState = state_header_s;
		this.sourceHtml = new StringBuffer();
		this.ansHtml = new StringBuffer();
		this.lastStart = 0;
		this.lastEnd = 0;
	}

	public boolean finish()
	{
		if(nowState == state_finish)
			return true;
		else
			return false;
	}

	public void firstAppend(String s)
	{
		this.sourceHtml.append(s);
		lastEnd = this.sourceHtml.length();
	}

	public void secondAppend(String s)
	{
		this.sourceHtml.append(s);
	}

	public void append(String s)
	{

		if(nowState != state_body_co)
		{
			this.sourceHtml.delete(0,(int)lastEnd);
			lastEnd = this.sourceHtml.length();
			this.sourceHtml.append(s).append("\r\n");
		}
		else if(nowState != state_finish)
		{
			this.sourceHtml.append(s).append("\r\n");
		}
	}

	public String returnAns()
	{
		System.out.println(ansHtml.toString());
//		System.out.println(nowState);
//		System.out.println("�밴���������");
//		Scanner input = new Scanner(System.in); 
//		int str = input.nextInt();
		return ansHtml.toString();
	}

	public void checkState()
	{
		switch(nowState)
		{
			case state_header_s:
			case state_header_d: this.parseHeader(); break;
			case state_body_s:
			case state_body_nt:
			case state_body_sco:
			case state_body_co:  this.parseBody(); break;
			case state_finish:   this.parseFinish(); break;
			default: break;
		}
	}

	private void parseHeader()
	{		
		if( nowState == state_header_s )
		{
			if(sourceHtml.indexOf("<head>") != -1)
			{
				nowState = state_header_d;
				return;
			}
		}
		else
		{
			long titleS = sourceHtml.indexOf("<title>");
			long titleE = sourceHtml.indexOf("</title>");
			if(titleS != -1 && titleE != -1)
			{
				ansHtml.append("Title: ").append(sourceHtml.substring((int)(titleS+8), (int)titleE)).append("\r\n");
				nowState = state_body_s;
				return;
			}
		}
	}

	private void parseBody()
	{
		if(nowState == state_body_s)
		{
			if(sourceHtml.indexOf("<body>") != -1)
			{
				nowState = state_body_nt;
				return;
			}
		}
		else if(nowState == state_body_nt)
		{
			long titleS = sourceHtml.indexOf("<div class=\"newstitle\">");
			long titleE = sourceHtml.indexOf("</div>");
			if(titleS != -1 && titleE != -1)
			{
				ansHtml.append("NewsTitle: ").append(sourceHtml.substring((int)(titleS+23), (int)titleE)).append("\r\n");
				nowState = state_body_sco;
				return;
			}	
		}
		else if(nowState == state_body_sco)
		{
			if(sourceHtml.indexOf("<div class=\"content\">") != -1)
			{
				nowState = state_body_co;
				return;
			}
		}
		else
		{
			if(sourceHtml.lastIndexOf("<div id=\"foot\">") != -1)
			{
				nowState = state_finish;
				return;
			}
		}
	}

	private void parseFinish()
	{
		String tmp = sourceHtml.toString();
		Pattern pattern = Pattern.compile("<.+?>",Pattern.DOTALL);
		Matcher m = pattern.matcher(tmp);
		tmp = m.replaceAll("");
		tmp = tmp.replaceAll("&nbsp;"," ");
		ansHtml.append(tmp);
	}
}