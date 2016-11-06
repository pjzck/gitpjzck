import java.io.*;

class FileManager
{
//	String FileName;
//	String WriteData;

	FileManager(){}

	public void writeData(String f_name, String f_data){
	try{
		File fout = new File( f_name );
		PrintWriter output = new PrintWriter ( fout );
		
		output.println(f_data);
		output.close();
	}catch(Exception e){
		System.out.println("Error: " + e);
	}
	
	}
}