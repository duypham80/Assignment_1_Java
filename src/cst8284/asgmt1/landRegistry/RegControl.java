/*
* Course Name: CST8284_20S
* Student Name: Duy Pham
* Student Number: 040953368
* Class Name: Object Oriented Programming(Java)
* Assignment 1
* Date: Jun 22, 2020
*/

package cst8284.asgmt1.landRegistry;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RegControl {
	private ArrayList<Registrant> registrants = new ArrayList<Registrant>();
	private ArrayList<Property> properties = new ArrayList<Property>();
	
    private ArrayList<Registrant> getRegistrants() {return registrants;}   
    private ArrayList<Property> getProperties() {return properties;}
    
    public Registrant findRegistrant(int regNum) {
    	for (int i = 0; i < getRegistrants().size(); i++) {
    		if (getRegistrants().get(i).getRegNum() == regNum) return getRegistrants().get(i);
    	}
    	return null;
    }
    
    public ArrayList<Registrant> listOfRegistrants() {
    	return getRegistrants();
    }
    
    public Registrant deleteRegistrant(int regNum) {
    	Registrant foundRegistrant = findRegistrant(regNum);
    	if (foundRegistrant != null) {
    		getRegistrants().remove(foundRegistrant);
    	}
    	return foundRegistrant;
    }

	public Property addNewProperty(Property prop) {
		// check if the input prop overlaps another one
    	Property prop_overlap = propertyOverlaps(prop);
		if (prop_overlap != null) {
			prop = prop_overlap;
		} else {
			getProperties().add(prop);
		}
		return prop;
	}
	
	public boolean deleteProperties(ArrayList<Property> properties) {
    	if (properties.size() == 0) {
    		return false;
    	}
    	
		for (Property property: properties) {
			getProperties().remove(property);
		}
		return true;
	}

	public Property changePropertyRegistrant(Property originalProperty, int newRegNum) {
		// call findRegistrant to find the available registrant which has the same
		// regNum as originalProperty
		Registrant found_Registrant = findRegistrant(originalProperty.getRegNum());
		if (found_Registrant != null) { // if the registrant is found, change the regNum of the originalProperty
			return new Property(originalProperty, newRegNum);
		} else { // return null if the program can't find the registrant
			return null;
		}
	}

	public ArrayList<Property> listOfProperties(int regNum) {
    	ArrayList<Property> prop_list = new ArrayList<Property>();
    	for (Property prop: listOfAllProperties()) 
    		if (prop.getRegNum() == regNum) {
    			prop_list.add(prop);
    		}
    	return prop_list;
    }

	public ArrayList<Property> listOfAllProperties() {
		return getProperties();
	}

	private Property propertyOverlaps(Property prop) {
		// loop through all the properties and use overLaps() method to check if the
		// prop overlaps each one
		for (Property property : listOfAllProperties()) {
			if (property.overLaps(prop)) {
				return property;
			}
		}
		return null;
	}
	
	public <T> boolean saveToFile(ArrayList<T> source, String fileName) { // From Hybrid-06 by Dave Houtman
		File file = new File(fileName);
		try (PrintWriter output = new PrintWriter(file);
			FileOutputStream objectFileStream = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(objectFileStream);
		) {
			for (T object: source) {
				oos.writeObject(object);
			}
			output.close();
			return true;
		} catch (FileNotFoundException ex){
			System.out.println("File not found; check the path for file " + fileName);
			return false;
		} catch (IOException ex) {
			return false;
		}
	}
	
	public <T> ArrayList<T> loadFromFile(String fileName) { // From Hybrid-06 by Dave Houtman
		File file = new File(fileName);
		T obj;
		ArrayList<T> objList = new ArrayList<T>();
		try (FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
		) {
			while (true) {
				obj = (T)(ois.readObject());
				objList.add(obj);
			}
		} 
		catch (EOFException ex){
			System.out.println("Array of employees has been transfered from file");
			return objList;
		}
		catch (ClassNotFoundException ex){
			System.out.println("File not found");
			return null;
		} 
		catch (FileNotFoundException ex){
			System.out.println("File not found; check the path for file " + fileName);
			return null;
		} 
		catch (IOException ex) {
			return null;
		}
	}
}


