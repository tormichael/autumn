package tor.java.autumn.tabella;

import java.util.Date;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class tObj 
{
	public String 	Name;
	public String 	UID;
	public Date		DateCreate;
	public Date		DateLastModified;
	/**
	 * note about object or other common object's information 
	 */
	public String Note;
    /** 
     * object images collection
     */
    @XmlElementWrapper (name = "Imagess")
    @XmlElement (name = "tBin")
    public ArrayList<tBin> Images;
	
	public tObj()
	{
		
	}
	
}
