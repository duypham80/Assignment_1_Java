/*
* Course Name: CST8284_20S
* Student Name: Duy Pham
* Student Number: 040953368
* Class Name: Object Oriented Programming(Java)
* Assignment 1
* Date: Jun 22, 2020
*/


package cst8284.asgmt1.landRegistry;
import java.util.ArrayList;
import java.util.Scanner;

public class RegView {
	 	
	private static Scanner scan = new Scanner(System.in);														
	private static RegControl rc;
	
	private static final String PROPERTIES_FILE = "LandRegistry.prop";
	private static final String REGISTRANTS_FILE = "LandRegistry.reg";
	
	private static final int ADD_NEW_REGISTRANT = 1; 
	private static final int FIND_REGISTRANT = 2;
	private static final int LIST_REGISTRANTS = 3;
	private static final int DELETE_REGISTRANT = 4;
	private static final int ADD_NEW_PROPERTY = 5;
	private static final int DELETE_PROPERTY = 6;
	private static final int CHANGE_PROPERTY_REGISTRANT = 7;
	private static final int LIST_PROPERTY_BY_REGNUM = 8;
	private static final int LIST_ALL_PROPERTIES = 9;
	private static final int LOAD_LAND_REGISTRY_FROM_BACKUP = 10;
	private static final int SAVE_LAND_REGISTRY_TO_BACKUP = 11;
	private static final int EXIT = 0;

	
	// this constructor create new RegControl when a new instance is created
	public RegView() {
		rc = new RegControl();
		viewLoadLandRegistryFromBackUp();
	}
	
	//this code provided by Dave Houtman [2020] personal communication
	private static String getResponseTo(String s) { // get user input as a string
		System.out.print(s);
		return (scan.nextLine());
	}

	private static RegControl getRegControl() {
		return rc;
	}

	// this consists of a do-while loop. If the choice is 0, it stops the loop
	public static void launch() {
		int choice = EXIT;
		do {
			choice = displayMenu();
			executeMenuItem(choice);
		} while (choice != EXIT);
	}

	//Display Menu to user to chose 
	private static int displayMenu() { // From Assignment2 Starter Code by Dave Houtman
		System.out.println("Enter a selection from the following menu:");
		System.out.println(
			ADD_NEW_REGISTRANT +       		". Enter a new registrant\n" +
			FIND_REGISTRANT + 				". Find registrant by registration number\n" +
			LIST_REGISTRANTS +     			". Display list of registrants\n" +
			DELETE_REGISTRANT + 			". Delete a registrant\n" +
			ADD_NEW_PROPERTY +        		". Register a new property\n" +
			DELETE_PROPERTY + 				". Delete property\n" +
			CHANGE_PROPERTY_REGISTRANT +	". Change a property's registrant\n" +
			LIST_PROPERTY_BY_REGNUM + 		". Display all properties with the same registration number\n" + 
			LIST_ALL_PROPERTIES  +     		". Display all registered properties\n" +
			LOAD_LAND_REGISTRY_FROM_BACKUP+ ". Load land registry from backup\n" +
			SAVE_LAND_REGISTRY_TO_BACKUP +  ". Save land registry to backup\n" +
			EXIT +                    		". Exit program\n"
		);
		int ch = scan.nextInt();
		scan.nextLine();      // 'eat' the next line in the buffer
		return ch;
	}

	// execute choice of user by using switch() 
	private static void executeMenuItem(int choice) { // From Assignment2 Starter Code by Dave Houtman
		switch (choice) {
			case ADD_NEW_REGISTRANT:   			viewAddNewRegistrant();			break;
			case FIND_REGISTRANT:	 			viewFindRegistrant();			break;
			case LIST_REGISTRANTS:    			viewListOfRegistrants();		break;
			case DELETE_REGISTRANT: 			viewDeleteRegistrant(); 		break;
			case ADD_NEW_PROPERTY: 				viewAddNewProperty();			break;
			case DELETE_PROPERTY: 				viewDeleteProperty();			break;
			case CHANGE_PROPERTY_REGISTRANT:	viewChangePropertyRegistrant(); break;
			case LIST_PROPERTY_BY_REGNUM: 		viewListPropertyByRegNum();		break;	
			case LIST_ALL_PROPERTIES:   		viewListAllProperties();		break;  
			case LOAD_LAND_REGISTRY_FROM_BACKUP: viewLoadLandRegistryFromBackUp(); break;
			case SAVE_LAND_REGISTRY_TO_BACKUP:  viewSaveLandRegistryToBackUp(); break;
			case EXIT: 	
				System.out.println("Exiting Land Registry\n"); 
				viewSaveLandRegistryToBackUp();		
				break;
			default: System.out.println("Invalid choice: try again. (Select " + EXIT + " to exit.)\n");
		}
		System.out.println();  // add blank line after each output
	}

	// method to store value of request regNum 
	private static int requestRegNum() { // From Assignment2 Starter Code by Dave Houtman
		return (Integer.parseInt(getResponseTo("Enter registration number : ")));
	}

	private static Property makeNewPropertyFromUserInput() {
		// all the parseInt to convert String to integer (the getResponseTo() method return String)
		int regNum = requestRegNum();
		// check if a registrant with the regNum is available
		if (getRegControl().findRegistrant(regNum) == null) {
			System.out.println("Registrant number not found");
			return null;
		}
		String coordinateString = getResponseTo("Enter top and left coordinates of property (as X, Y): ");
		// split the xLeft and yTop from the string: "xLeft, yTop"
		String[] coordinates = coordinateString.split(", ");
		String dimensionString = getResponseTo("Enter length and width of property (as length, width): ");
		// split the xLength and yWidth from the string: "xLength, yWidth"
		String[] dimensions = dimensionString.split(", ");
		// convert all string in the lists to int and create a new Property object with them
		int xLeft = Integer.parseInt(coordinates[0]);
		int yTop = Integer.parseInt(coordinates[1]);
		int xLength = Integer.parseInt(dimensions[0]);
		int yWidth = Integer.parseInt(dimensions[1]);
		//Limit for registrant for property size minimum 20m x 10m and maximum size 1000m x 1000m
		if (xLength < 20 || yWidth < 10 || 
				(xLength + xLeft) > 1000 || (yTop + yWidth) > 1000) {
			System.out.println("Invalid dimensions/coordinates inputs");
			return null;
		}
		Property new_prop = new Property(xLength, yWidth,
				Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), regNum);
		return new_prop;
	}

	private static Registrant makeNewRegistrantFromUserInput() {
		// get user's names as String "firstName lastName", and create a new Registrant
		// object with it
		String input_name = getResponseTo("Enter registant's first and Last name: ");
		Registrant new_Registrant = new Registrant(input_name);
		return new_Registrant;
	}

	public static void viewAddNewRegistrant() {
		Registrant new_reg = makeNewRegistrantFromUserInput();	
    	getRegControl().listOfRegistrants().add(new_reg);
		System.out.println("Registrant added:");
		System.out.println(new_reg.toString());
	}

	public static void viewFindRegistrant() { // From Assignment2 Starter Code by Dave Houtman
		int regNum = requestRegNum();
    	Registrant reg = rc.findRegistrant(regNum);
    	
    	System.out.println(""+ ((reg==null)? // Registrant does not exist
    		"A registrant having the registration number\n" + 
    		regNum + " could not be found in the registrants list."
    		:  // Registrant found
    		"The registrant associated with that registration number " + 
    		"is\n"+ reg.toString() + "\n"));
	}

	public static void viewListOfRegistrants() {
		// call listOfRegistrants() and see the output. If the output's length is 0,
		// there are no Registrant in the list
		// else, print all of them using toString()
		ArrayList<Registrant> list_reg = getRegControl().listOfRegistrants();
		if (list_reg.size() == 0) {
			System.out.println("No Registrants loaded yet\n");
		} else {
			System.out.println("List of registrants:\n");
			for (Registrant registrant : list_reg) {
				System.out.println(registrant.toString());
			}
		}
	}

	public static void viewDeleteRegistrant() {
    	int regNum = requestRegNum();
    	Registrant deletedRegistrant = getRegControl().deleteRegistrant(regNum);
    	if (deletedRegistrant == null) {
    		System.out.println("No registrations number found.");
    	} else {
    		System.out.printf("The registrant with registrants number %d deleted.\n", regNum);
    	}
	}
	
	public static void viewAddNewProperty() {
		Property new_prop = makeNewPropertyFromUserInput();
		if (new_prop != null) {
			// added_prop store the output of the method addNewProperty()
			Property added_prop = getRegControl().addNewProperty(new_prop);
	
			// if added_prop == null, the array of Property is full
			// if added_prop is the same as new_prop, the method successfully added the
			// Property
			// if added_prop is different from new_prop, the new_prop overlaps another
			// Property in the list
			
			if (new_prop == added_prop) {
				System.out.println("New property has been registered as:");
			} else {
				System.out.println("New property could not be registered;\n" + "There is already a property registered at:");
			}
			System.out.println(added_prop.toString());
		}
	}

	public static void viewChangePropertyRegistrant() {
		// From Assignment2 Starter Code by Dave Houtman
		int original_regNum = Integer.parseInt(getResponseTo("Enter original registrants number: "));
		// get all Properties which have the same original_regNum
		ArrayList<Property> prop_list = getRegControl().listOfProperties(original_regNum);
		// if there is no Property in prop_list, not Property with the given regNum is found
		if (getRegControl().findRegistrant(original_regNum) == null) { 																		
			System.out.println("The original registrants number not found");
			// TODO scan.nextLine(); // Remove CRLF from input buffer
			return;
		}
		// From Assignment2 Starter Code by Dave Houtman
		int new_regNum = Integer.parseInt(getResponseTo("Enter new registrants number: "));
		scan.nextLine(); // Remove CRLF from input buffer
		// loop through all found Properties to change their regNum
		ArrayList<Property> all_prop = getRegControl().listOfAllProperties();
		for (int i = 0; i < all_prop.size(); i++) {
			if (all_prop.get(i).getRegNum() == original_regNum) {
				Property changed_prop = getRegControl().changePropertyRegistrant(all_prop.get(i), new_regNum);
				// if changed_prop == null (the output of changePropertyRegistrant())
				// which means no Registrant matches the given regNum
				if (changed_prop == null) {
					System.out.println("No Registrant matches the given registrants number\n");
					return;
				}
				all_prop.set(i, changed_prop);
			}
		}
		System.out.printf(
				"Operation completed; the new registration number, %d, has replaced %d in all affected properties.\n",
				original_regNum, new_regNum);
	}
	
	public static void viewDeleteProperty() {
    	int regNum = requestRegNum();
    	ArrayList<Property> prop_list = rc.listOfProperties(regNum);
    	if (prop_list.size() == 0) {
    		System.out.println("No properties are associated with that registration number.");
    	} else {
    		rc.deleteProperties(prop_list);
    	}
    }

	public static void viewListPropertyByRegNum() {
		int input_regNum = requestRegNum();
//		TODO scan.nextLine(); // Remove CRLF from input buffer
		// get the property list by the given regNum
		ArrayList<Property> found_prop_list = getRegControl().listOfProperties(input_regNum);
		// if the found_prop_list is empty, print out the warning message and stop the method
		if (found_prop_list.size() == 0) {
			System.out.println("No Property with the given registration number found");
			return;
		}
		System.out.println("\nList of properties by the given registration number:");
		// loop through all found properties and print out their toString()
		for (Property property : found_prop_list) {
			System.out.println(property.toString());
		}
	}

	public static void viewListAllProperties() {
		// using the method listOfAllProperties() to get a list of all registered properties
		ArrayList<Property> prop_list = getRegControl().listOfAllProperties();
		// if the prop_list is empty, print out the warning message and stop the method
		if (prop_list.size() == 0) {
			System.out.println("Property Registry empty; no properties to display\n");
			return;
		}
		// loop through all found properties and print out their toString()
		for (Property property : prop_list) {
			System.out.println(property.toString());
		}
	}
	
	public static void viewLoadLandRegistryFromBackUp() {
    	if (getResponseTo("You are about to overwrite existing records; do you wish to continue? (Enter ‘Y’ to proceed.) ") == "Y") {
			ArrayList<Property> prop_list = rc.loadFromFile(PROPERTIES_FILE);
			ArrayList<Registrant> reg_list = rc.loadFromFile(REGISTRANTS_FILE);
			if (prop_list == null || reg_list == null) {
				System.out.println("Load from backup failed.");
			} else {
				getRegControl().listOfAllProperties().clear();
				for (Property property: prop_list) {
					getRegControl().addNewProperty(property);
				}
				getRegControl().listOfRegistrants().clear();
				for (Registrant registrant: reg_list) {
					getRegControl().listOfRegistrants().add(registrant);
				}
			}
    	}
    }
    
    public static void viewSaveLandRegistryToBackUp() {
    	System.out.println("Saving land registry to backup...");
    	boolean save_prop = getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE);
    	boolean save_reg = getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE);
    	if (save_prop == false || save_reg == false) {
    		System.out.println("Unable to save land registry.");
    	} else {
    		System.out.println("Land registry saved to backup.");
    	}
    	
	}
}
