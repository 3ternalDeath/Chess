package engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileIOHelper {

	public static Object readObject(String file)throws FileNotFoundException, IOException, ClassNotFoundException{
		
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream objIn = new ObjectInputStream(in);
		
		Object obj = objIn.readObject();
		
		objIn.close();
		return obj;
	}
}
