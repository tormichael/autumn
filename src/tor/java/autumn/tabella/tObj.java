package tor.java.autumn.tabella;

import java.util.Date;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class tObj 
{
	protected String 	Name;
	protected String 	UID;
	protected Date		DateCreate;
	protected Date		DateLastModified;
	/**
	 * note about object or other common object's information 
	 */
	protected String Note;
    /** 
     * object images collection
     */
    @XmlElementWrapper (name = "Imagess")
    @XmlElement (name = "tBin")
    protected ArrayList<tBin> Images;
	
    public String getName()
    {
    	return Name;
    }
    public void setName(String aName)
    {
    	Name = aName;
    }
    
	public tObj()
	{
    	Images =new ArrayList<tBin>();
	}
	
}
