import java.io.*;
import java.net.*;
import java.util.*;

class ReadURL 
{
  public static void main(String[] args)
  {
	  Vector	vt=new Vector(5);
//	  String[]	us=new String[100];
	  int	n=0;
    try {
      // Define a URL
	  String htm= "http://cspo.zju.edu.cn/";
      URL url = new URL(htm);

      // Get a character input stream for the URL
      BufferedReader in =
		  new BufferedReader(
		  new InputStreamReader(
		  url.openStream()));

      // Create the stream for the output file
      PrintWriter out = new PrintWriter( 
                        new BufferedWriter(
                        new FileWriter(
                        new File("demo.txt"))));

      // Read  the URL and write it to a file
      String buf, str;
	  int		i, j;
      while(!(null==(buf=in.readLine()))){
        if((j=buf.indexOf(".gif"))>0){
			i=buf.lastIndexOf("\"", j)+2;
			str = htm+buf.substring(i, j+4);

			if(vt.indexOf(str)<0)vt.add(str);
		}
	  }
	  System.out.println("Size: "+vt.size());
	  
	  Iterator itr = vt.iterator();  // Obtain an iterator
	  while(itr.hasNext()){
		  String ur = (String)itr.next();
		  
		  new ReadBIN(ur).save();
//		  new ReadBINThr(ur).start();

		  System.out.println( ur );
	  }

      in.close();	// Close the input stream
      out.close();	// Close the input stream
    } catch(Exception e){
      System.out.println("Error:\n" + e);
    }
  }
}
/*
	  for(i=0; i<vt.size(); i++)
		  System.out.println(vt.get(i));
*/
/*

			for(i=0; i<n; i++)
				if(str.equals(us[i]))break;
			if(i==n){
				us[n++]=str;
				System.out.println(str);
			}
*/