package tor.java.autumn.tabella;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ObjRegister")
public class tRegister 
{
	
	private Date	_dateCreated;
	private Date _dateLastModified;
	
	private String _rbFN;
	private String _bpFN;

    private ArrayList<tObj> _objColl;
	
    public Date getDateCreated() 
    {
		return _dateCreated;
	}
	public void setDateCreated(Date _dateCreated) 
	{
		this._dateCreated = _dateCreated;
	}
	public Date getDateLastModified() 
	{
		return _dateLastModified;
	}
	public void setDateLastModified(Date _dateLastModified) 
	{
		this._dateLastModified = _dateLastModified;
	}
	
	public String getRefBookFileName() {
		return _rbFN;
	}
	public void setRefBookFileName(String _rbFN) {
		this._rbFN = _rbFN;
	}
	public String getBookParamFileName() {
		return _bpFN;
	}
	public void setBookParamFileName(String _bpFN) {
		this._bpFN = _bpFN;
	}
	
	@XmlElementWrapper (name = "ObjColl")
    @XmlElements ({
		@XmlElement (name = "Obj", type =tObj.class),
		@XmlElement (name = "Person", type = tPerson.class)
    })
    public ArrayList<tObj> getObjColl()
    {
    	return _objColl;
    }
    public void setObjColl(ArrayList<tObj> aObjColl)
    {
    	_objColl = aObjColl;
    }
	
    public tRegister()
    {
    	_rbFN = null;
    	_bpFN = null;
    	_dateCreated = Calendar.getInstance().getTime();
    	_dateLastModified = Calendar.getInstance().getTime();
    	_objColl = new ArrayList<tObj>();
    }
 
    public void ClearObjectsCollection()
    {
    	_objColl.clear();
    }
    
 	public static tRegister Load(String aFileName)
	{
		tRegister ret = null;

		if (aFileName != null && aFileName.length() > 0)
		{
	    	try
	    	{
	    		JAXBContext context = JAXBContext.newInstance(tRegister.class);
	    		Unmarshaller um = context.createUnmarshaller();
	    		Object obj = um.unmarshal(new File(aFileName));
	    		ret = (tRegister) obj;
	    	}
	    	catch (JAXBException ex)
	    	{
	    		ex.printStackTrace();
	    	}
		}
    	
    	return ret;
		
	}
	
	public String Save (String aFileName)
	{
		String ret = null;
		
		if (aFileName != null && aFileName.length() > 0)
		{
	    	try
	    	{
	    		JAXBContext context = JAXBContext.newInstance(tRegister.class);
	    		Marshaller m = context.createMarshaller();
	    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    		m.marshal(this, new File(aFileName));
	    	}
	    	catch (JAXBException ex)
	    	{
	    		ret = ex.getMessage();
	    	}
		}
    	
    	return ret;
	}
	
	public void SortObjCollection()
	{
		Comparator<tObj> objComparator = new Comparator<tObj>() 
		{
			@Override
			public int compare(tObj o1, tObj o2) 
			{
				return o1.Name.compareToIgnoreCase(o2.Name);
			}
		};

		Collections.sort(_objColl, objComparator);
	}
}
