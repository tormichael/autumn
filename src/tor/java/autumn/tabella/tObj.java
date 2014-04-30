package tor.java.autumn.tabella;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class tObj 
{
	protected String 			Name;
	protected String 			UID;
	protected Calendar		Create;
	protected Calendar		LastModified;
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
	public String getNote() 
	{
		return Note;
	}
	public void setNote(String note) 
	{
		Note = note;
	}
	public ArrayList<tBin> getImgColl() {
		return Images;
	}
	public void setImgColl(ArrayList<tBin> imgColl) {
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
	
	public tObj()
	{
    	Images =new ArrayList<tBin>();
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
