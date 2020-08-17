/*
* Course Name: CST8284_20S
* Student Name: Duy Pham
* Student Number: 040953368
* Class Name: Object Oriented Programming(Java)
* Assignment 1
* Date: Jun 22, 2020
*/

package cst8284.asgmt1.landRegistry;

public class Registrant {
	public static final long serialVersionUID = 1L;
	private static final int REGNUM_START = 1000;
	private static int currentRegNum = REGNUM_START;
	private final int REGNUM = currentRegNum;
	private String firstName;
	private String lastName;

	public Registrant() {
		// default constructor which chains the parameterize constructor below with the
		// parameter is "unknown unknown"
		this("unknown unknown");
	}

	public Registrant(String firstLastName) {
		// split the first and last name by the space " " and store them into
		// coresponding variables using setters
		//this code is provide by :[webpage]. Retrieved from 
		//https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#split(java.lang.String)
		String[] nameStrings = firstLastName.split(" ");//this code provide by: 
		setFirstName(nameStrings[0]);
		setLastName(nameStrings[1]);
		// increase currentRegNum by 1 each time we create a new Registrant object
		incrToNextRegNum();
	}

	public int getRegNum() {
		return REGNUM;
	}

	private static void incrToNextRegNum() {
		currentRegNum++;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean equals(Object obj) {
		// compare firstname, lastname and regNum of this with obj
		//this code provided by Dave Houtman [2020] personal communication
		return this.getFirstName() == ((Registrant) obj).getFirstName()
				&& this.getLastName() == ((Registrant) obj).getLastName()
				&& this.getRegNum() == ((Registrant) obj).getRegNum();
	}

	public String toString() {
		// this code is provide by :[webpage]. Retrieved from
		// this code is provide by : https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html
		return String.format("Name: %s %s\nRegistration Number: #%d", getFirstName(), getLastName(), getRegNum());
	}
}
