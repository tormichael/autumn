package autumn.tabella;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class tDoc 
{
	public String Series;
	public String Number;
	public String Who;
	public Date When;
	public Date Finished;
	public String Type;
	public String Note;
	public int NN;
	
    @XmlElementWrapper (name = "DocImgColl")
    @XmlElement (name = "tBin")
	public ArrayList<tBin> ImgColl;
	
	public tDoc() 
	{
		ImgColl = new ArrayList<tBin>();
	}

}
