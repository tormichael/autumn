package tor.java.autumn.tabella;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import tor.java.autumn.Autumn;

public class tObj 
{
	protected String 			Name;
	protected String 			UID;
	protected Calendar		Create;
	protected Calendar		LastModified;
	protected Integer			Type;
	/**
	 * note about object or other common object's information 
	 */
	protected String Note;
    /** 
     * object images collection
     */
    protected ArrayList<tBin> Images;
	
    public String getName()
    {
    	return Name;
    }
    public void setName(String aName)
    {
    	Name = aName;
    }
    public Integer getType ()
    {
    	return Type;
    }
    public void setType(Integer aType)
    {
    	Type = aType;
    }
	public String getNote() 
	{
		return Note;
	}
	public void setNote(String note) 
	{
		Note = note;
	}

    @XmlElementWrapper (name = "Images")
    @XmlElement (name = "tBin")
	public ArrayList<tBin> getImageCollection() {
		return Images;
	}

	public void setImageCollection(ArrayList<tBin> imgColl) {
		Images = imgColl;
	}
   
    
    public Calendar getLastModified() 
    {
		return LastModified;
	}
	public void setLastModified(Calendar lastModified)
	{
		LastModified = lastModified;
	}

	//protected ResourceBundle 	mRsr;
	
	public tObj()
	{
		Type = 0; // abstract
    	Images =new ArrayList<tBin>();
    	
		//mRsr = ResourceBundle.getBundle(Autumn.FN_RESOURCE_TEXT, Locale.getDefault());
	}

	public byte[] getMainImageAsBytes()
	{
		if (Images.size() >0)
			return  Images.get(0).getBin();
		else
			return null;
	}
	public Image getMainImage()
	{
		Image ret = null;
		if (Images.size() >0)
		{
			tBin bin = Images.get(0);
			ByteArrayInputStream baiStream = new ByteArrayInputStream(bin.getBin());
			try
			{
				ret = ImageIO.read(baiStream);
			}
			catch (IOException ex)
			{
				
			}
		}
		return ret;
	}
	public void addImageAsBytes (byte[] arrBytes)
	{
		tBin bin = new tBin();
		bin.setBin(arrBytes);
		Images.add(bin);
	}
	
	@Override
	public String toString() 
	{
		return this.Name;
	}
}
