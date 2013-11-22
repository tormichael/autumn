package autumn.tabella;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import sun.util.calendar.CalendarDate;
import sun.util.calendar.BaseCalendar.Date;
import JCommonTools.CC;

public class tPerson extends tObj 
{
	public final static int SEX_UNKNOWN = 0;
	public final static int SEX_WOMEN = 1;
	public final static int SEX_MAN = 2;
	
	/**
	 * last name
	 */
	private String LName;
	/**
	 * first name
	 */
	private String FName;
	/**
	 * patronymic name
	 */
	private String PName;
	/**
	 * birthday in format YYYYMMDD
	 */
	private String BDay;
	/**
	 * sex 0-unknown; 1-women; 2-man; 
	 */
	private int Sex;
	/**
	 * Notes
	 */
	private String Notes;
	/**
	 * Personal images 
	 */
    @XmlElementWrapper (name = "ImgColl")
    @XmlElement (name = "tBin")
    private ArrayList<tBin> ImgColl;
	
    /** 
     * personal contacts (phones, address, email, www and so on) collection 
     */
    @XmlElementWrapper (name = "ContactColl")
    @XmlElement (name = "tVTN")
    private ArrayList<tVTN> ContactColl;

    /** 
     * personal addresses
     */
    @XmlElementWrapper (name = "AddrColl")
    @XmlElement (name = "tAdr")
    private ArrayList<tAdr> AddrColl;

	/** 
     * personal document collection
     */
    @XmlElementWrapper (name = "Docs")
    @XmlElement (name = "tDoc")
    private ArrayList<tDoc> DocColl;

    
    
    
    public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getPName() {
		return PName;
	}
	public void setPName(String pName) {
		PName = pName;
	}
	public String getBDay() {
		return BDay;
	}
	public void setBDay(String bDay) {
		BDay = bDay;
	}
	public int getSex() {
		return Sex;
	}
	public void setSex(int sex) {
		Sex = sex;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public ArrayList<tBin> getImgColl() {
		return ImgColl;
	}
	public void setImgColl(ArrayList<tBin> imgColl) {
		ImgColl = imgColl;
	}
	public ArrayList<tVTN> getContactColl() {
		return ContactColl;
	}
	public void setContactColl(ArrayList<tVTN> contactColl) {
		ContactColl = contactColl;
	}
	public ArrayList<tAdr> getAddrColl() {
		return AddrColl;
	}
	public void setAddrColl(ArrayList<tAdr> addrColl) {
		AddrColl = addrColl;
	}
	public ArrayList<tDoc> getDocColl() {
		return DocColl;
	}
	public void setDocColl(ArrayList<tDoc> docColl) {
		DocColl = docColl;
	}
	public tPerson(String aLName, String aFName, String aPName, String aBDay, int aSex)
    {
    	LName = aLName;
    	FName = aFName;
    	PName = aPName;
    	BDay = aBDay;
    	Sex = aSex;
    	
    	ImgColl =new ArrayList<tBin>();
    	ContactColl = new ArrayList<tVTN>();
    	AddrColl = new ArrayList<tAdr>();
    	DocColl = new ArrayList<tDoc>();
    	
    }
    public tPerson(String aLName, String aFName, String aPName)
    {
    	this (aLName, aFName, aPName, CC.STR_EMPTY, SEX_UNKNOWN);
    }
    public tPerson()
    {
    	this (CC.STR_EMPTY, CC.STR_EMPTY, CC.STR_EMPTY, CC.STR_EMPTY, SEX_UNKNOWN);
    }
    
	public java.util.Date getDBDate()
	{
		return  new GregorianCalendar(
				Integer.parseInt(BDay.substring(0, 4)),
				Integer.parseInt(BDay.substring(4, 2)), 
				Integer.parseInt(BDay.substring(6, 2))
		).getTime();
	}
	public void setBDDate(java.util.Date bdd)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		BDay = df.format(bdd);
	}
	
	public void addImageAsBytes (byte[] arrBytes)
	{
		tBin bin = new tBin();
		bin.setBin(arrBytes);
		ImgColl.add(bin);
	}
	
	public void addTelephone()
	{
		
	}
    
}
