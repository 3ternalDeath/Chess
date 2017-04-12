package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileIOHelper {
	
	/**
	 * Reads a Serializable object from a file and creates it accordingly.
	 * @param file The filename to read from.
	 * @return The created object.
	 * @throws FileNotFoundException if the filename points to nothing.
	 * @throws IOException in case of an unknown error reading the file.
	 * @throws ClassNotFoundException if the class cannot be loaded properly.
	 */
	public static Object readObject(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
		//Read from file
		FileInputStream in = new FileInputStream(file);
		ObjectInputStream objIn = new ObjectInputStream(in);
		
		Object obj = objIn.readObject();
		
		objIn.close();
		
		return obj;
	}
	
	/**
	 * Writes a Serializable object to a file.
	 * @param file The filename to write to.
	 * @param obj The object to write.
	 * @throws FileNotFoundException if the file cannot be written to properly.
	 * @throws IOException in case of an unknown error while writing the object.
	 */
	public static void writeObject(String file, Object obj) throws FileNotFoundException, IOException {
		File create = new File(file);
		
		if(!create.exists())
			create.createNewFile();
		
		//Write to file
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		objOut.writeObject(obj);
		objOut.close();
	}
}
