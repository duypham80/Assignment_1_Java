/*
* Course Name: CST8284_20S
* Student Name: Duy Pham
* Student Number: 040953368
* Class Name: Object Oriented Programming(Java)
* Assignment 1
* Date: Jun 22, 2020
*/

package cst8284.asgmt1.landRegistry;

public class Property {
	public static final long serialVersionUID = 1L;
	private static final double TAX_RATE_PER_M2 = 12.50;
	private static final int DEFAULT_REGNUM = 999;
	private int xLeft;
	private int yTop;
	private int xLength;
	private int yWidth;
	private int regNum;
	private int area;
	private double taxes;

	// default Complex constructor; it will chain automatically to the (int, int, int, int) constructor
	public Property() {
		this(0, 0, 0, 0);
	}

	// Property Constructor take value xLength, yWidth, xLeft, yTop from user input and default int REGNUM,
	// this one will chain automatic (Property pro, int) constructor
	public Property(int xLength, int yWidth, int xLeft, int yTop) {
		this(xLength, yWidth, xLeft, yTop, DEFAULT_REGNUM);
	}

	// Property constructor take value xLength, yWidth, xLeft, yTop by using getter and value of regNum
	public Property(Property prop, int regNum) {
		// this code provided by Dave Houtman [2020] personal communication
		this(prop.getXLength(), prop.getYWidth(), prop.getXLeft(), prop.getYTop(), regNum);
	}

	// Property constructor set value of xLength, yWidth, xLeft, yTop and regNum and
	// store in integer
	public Property(int xLength, int yWidth, int xLeft, int yTop, int regNum) {
		setXLength(xLength);
		setYWidth(yWidth);
		setXLeft(xLeft);
		setYTop(yTop);
		setRegNum(regNum);
	}

	public int getXLeft() {
		return xLeft;
	}

	public void setXLeft(int left) {
		this.xLeft = left;
	}

	public int getXRight() {
		return getXLeft() + getXLength();
	}

	public int getYTop() {
		return yTop;
	}

	public void setYTop(int top) {
		this.yTop = top;
	}

	public int getYBottom() {
		return getYTop() + getYWidth();
	}

	public int getYWidth() {
		return yWidth;
	}

	public void setYWidth(int width) {
		this.yWidth = width;
	}

	public int getXLength() {
		return xLength;
	}

	public void setXLength(int length) {
		this.xLength = length;
	}

	public int getRegNum() {
		return regNum;
	}

	private void setRegNum(int regNum) {
		this.regNum = regNum;
	}

	// return Area of property using formula by length * width
	public int getArea() {
		area = xLength * yWidth;
		return area;
	}

	// calculate tax of Property by area * TAX_RATE_PER_M2
	public double getTaxes() {
		taxes = area * TAX_RATE_PER_M2;
		return taxes;
	}

	public String toString() {
		// using String.format to display out put value and infomation of Property
		// this code is provide by :[webpage]. Retrieved from
		// https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html
		return String.format(
				"Coordinates: %d, %d\n" + "Length: %d m  Width: %d m\n" + "Registrant: #%d\nArea: %d m2\n"
						+ "Property Taxes : $%.1f",
				this.getXLeft(), this.getYTop(), this.getXLength(), this.getYWidth(), this.getRegNum(), this.getArea(),
				this.getTaxes());
	}

	// check if the xLeft and yTop of this is equal to obj
	public boolean equals(Object obj) {
		// this code provided by Dave Houtman [2020] personal communication
		return this.getXLeft() == ((Property) obj).getXLeft() && this.getYTop() == ((Property) obj).getYTop();

	}

	// check if the xLength and yWidth of this is equal to obj
	public boolean hasSameSides(Property prop) {
		// this code provided by Dave Houtman [2020] personal communication
		return this.getXLength() == prop.getXLength() && this.getYWidth() == prop.getYWidth();
	}

	// ThePatelGuy(2019). Check if two rectangles overlap at any point.[Webpage]. Retrieved from	
	// https://stackoverflow.com/questions/23302698/java-check-if-two-rectangles-overlap-at-any-point
	public boolean overLaps(Property prop) {
		if (this.getXRight() < prop.getXLeft() || this.getXLeft() > prop.getXRight()
				|| this.getYTop() > prop.getYBottom() || this.getYBottom() < prop.getYTop()) {
			return false;
		}
		return true;
	}
}
