import java.io.*;
import java.net.*;
import java.util.*;

class ReadBIN 
{
	String ur;

	ReadBIN(String u)
	{	ur=u;
	}
	
	public void save()
	{
    try {
	  String filename=ur.substring(
		  ur.lastIndexOf("/")+1);
	  System.out.println(filename);

      URL url = new URL(ur);

      DataInputStream in =	
		new DataInputStream(
		new BufferedInputStream(
		url.openStream()));

	  DataOutputStream out =
		new DataOutputStream(
		new FileOutputStream(
		new File(filename)));

	  try{
      while(true)out.writeByte(in.readByte());
	  }catch(Exception e){}

      in.close();	// Close the input stream
      out.close();	// Close the input stream
    } catch(Exception e){
      System.out.println("Error:\n" + e);
    }
  }
/*
	public static void main(String[] args) 
	{
		ReadBIN w = new ReadBIN(
			"http://10.214.47.199:8080/masm/ywdata/hzlou.jpg");
		w.save();
		System.out.println("Hello World!");
	}
*/
}