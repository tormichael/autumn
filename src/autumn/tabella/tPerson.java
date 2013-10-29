package autumn.tabella;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import JCommonTools.CC;

public class tPerson extends tObj 
{
	public final static int SEX_UNKNOWN = 0;
	public final static int SEX_WOMEN = 1;
	public final static int SEX_MAN = 2;
	
	/**
	 * last name
	 */
	public String LName;
	/**
	 * first name
	 */
	public String FName;
	/**
	 * patronymic name
	 */
	public String PName;
	/**
	 * birthday in format YYYYMMDD
	 */
	public String BDay;
	/**
	 * sex 0-unknown; 1-women; 2-man; 
	 */
	public int Sex;
    /** 
     * personal document collection
     */
    @XmlElementWrapper (name = "Docs")
    @XmlElement (name = "tDoc")
    public ArrayList<tDoc> Docs;

    /** 
     * personal contacts (phones, address, email, www and so on) collection 
     */
    @XmlElementWrapper (name = "Contacts")
    @XmlElement (name = "tVTN")
    public ArrayList<tVTN> Contacts;

    public tPerson(String aLName, String aFName, String aPName, String aBDay, int aSex)
    {
    	LName = aLName;
    	FName = aFName;
    	PName = aPName;
    	BDay = aBDay;
    	Sex = aSex;
    	
    	//Docs = new ArrayList<tDoc>();
    	
    }
    public tPerson(String aLName, String aFName, String aPName)
    {
    	this (aLName, aFName, aPName, CC.STR_EMPTY, SEX_UNKNOWN);
    }
    public tPerson()
    {
    	this (CC.STR_EMPTY, CC.STR_EMPTY, CC.STR_EMPTY, CC.STR_EMPTY, SEX_UNKNOWN);
    }
    
}
