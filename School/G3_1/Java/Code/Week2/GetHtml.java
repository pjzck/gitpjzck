import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.PrintWriter;

public class GetHtml
{
	GetHtml(){}
	public static String getHtml(String urlString) 
	{
		try 
		{
			ParseHtml res_html = new ParseHtml();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String temp;
			temp = br.readLine();
			res_html.firstAppend(temp);
			temp = br.readLine();
			res_html.secondAppend(temp);
			res_html.checkState();

			while ((temp = br.readLine()) != null) 
			{
				res_html.append(temp);
				res_html.checkState();
				if(res_html.finish())
					break;
			}
			res_html.checkState();
			br.close();
			isr.close();
			return res_html.returnAns();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean writeToFile(String filePath, String content) throws Exception
	{
		File fout = new File( filePath );
		PrintWriter output = new PrintWriter ( fout );
		output.println(content);
		output.close();
		return true;
	}

	public static void main(String[] args) 
	{
		GetHtml test = new GetHtml();
		try{
			test.writeToFile("./b.txt", test.getHtml("http://hr.zju.edu.cn/cn/redir.php?catalog_id=10081&object_id=72141"));
		}
		catch ( Exception e )
		{
			System.out.println("error");
		}
	}
}
