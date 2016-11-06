import java.io.*;
import java.net.*;
import java.util.*;

class ReadHTM 
{
  public static void main(String[] args)
  {
    try {
      // Define a URL
	  String htm= "http://cspo.zju.edu.cn/redir.php?catalog_id=21530&object_id=681517";
      URL url = new URL(htm);

      // Get a character input stream for the URL
      BufferedReader in =
		  new BufferedReader(
		  new InputStreamReader(
		  url.openStream()));

      // Read  the URL and write it to a file
      String buf, str;
	  int		i, j;
      while(!(null==(buf=in.readLine()))){
		i=j=0;		str="";
		while((i=buf.indexOf("<",j))>=0){
			str+=buf.substring(j,i);
			j=buf.indexOf(">", i)+1;
		}
		System.out.println(str);
	  }

      in.close();	// Close the input stream
    } catch(Exception e){
      System.out.println("Error:\n" + e);
    }
  }
}