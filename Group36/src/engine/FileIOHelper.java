package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileIOHelper {

	public static Object readObject(String file)throws FileNotFoundException, IOException, ClassNotFoundException{
		
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream objIn = new ObjectInputStream(in);
		
		Object obj = objIn.readObject();
		
		objIn.close();
		return obj;
	}
	
	public static void writeObject(String file, Object obj)throws FileNotFoundException, IOException{
		
		File creat = new File(file);
		
		if(!creat.exists())
			creat.createNewFile();
		
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		objOut.writeObject(obj);
		
		objOut.close();
	}
}
