package tor.java.autumn;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import tor.java.autumn.tabella.tPerson;
import tor.java.autumn.tabella.tRegister;

public class Autumn 
{
	private tRegister _ore;
	//private tPerson			_prs;
	private ResourceBundle 	_bnd;
	
	public final static String FN_RESOURCE_TEXT = "tor/java/autumn/rsc/autumnText";
	public final static String FD_RESOURCE_ICONS = "rsc/icons/";
	public final static String FD_RESOURCE_IMAGE = "rsc/icons/";
	
	public final static String PREFERENCE_PATH = "/autumn";
	
	public final static int TOOL_BAR_ICON_SIZE = 24;
	
	public ResourceBundle get_bnd()
	{
		return _bnd;
	}
	
	public String getString(String key)
	{
		return _bnd.getString(key);
	}
	
	public tRegister getRegister()
	{
		return _ore;
	}
//	public tPerson getPerson(tPerson aPrs)
//	{
//		tPerson prs = null;
//		if ()
//		_prs = aPrs;
//		return ret;
//	}
	
	/**
	 * @param args
	 */
	public static void main (String[] args) 
	{
		//wPerson prsn = new wPerson(new Autumn());
		//fPerson prsn = new fPerson(new Autumn());
		
		//if (args != null && args.length > 0)
		//	prsn.setFileName(args[0]);
		
		//prsn.setVisible(true);
		
		fNavigator nav = new fNavigator(new Autumn());
		nav.setVisible(true);
	}
	
	public Autumn()
	{
		//_prs = new tPerson();
		_ore = new tRegister();
		_bnd = ResourceBundle.getBundle(Autumn.FN_RESOURCE_TEXT, Locale.getDefault());
	}
	
	public static String StringFromUTF8(String aTxt){
		
		String ret = aTxt;
		
		if (aTxt != null && aTxt.length() > 0){
			
			try
			{
				ret = new String(aTxt.getBytes(), "UTF-8");
			}
			catch (UnsupportedEncodingException ex)
			{
				ex.printStackTrace();
			}
		}
		return ret;
	}
	
	public static String String2UTF8(String aTxt){
		
		String ret = aTxt;

		if (aTxt != null && aTxt.length() > 0){
			try
			{
				byte[] bts = aTxt.getBytes("UTF-8");
				ret = new String(bts);
				//ret = ret.replace("\uD03F", "\uD098");
				//ret = CC.STR_EMPTY;
				//for (int ii=0; ii < bts.length; ii++)
				//	ret += (char)bts[ii];
				//ret = ret.replace(new char[]{0xD0, 0x3F}, new char[]{0xD0, 0x98});
				//ret = new String(aTxt.getBytes("UTF-8"), "UTF-8");
				//ret = new String(ret.getBytes(), "CP1251");
//				BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(aTxt.getBytes("UTF-8"))));
//				ret = br.readLine();
//				br.close();
//				byte [] ttt = ret.getBytes();
			}
			catch (UnsupportedEncodingException ex)
//			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return ret;
	}

	public  ImageIcon getImageIcon(String aName)
	{
		URL url = this.getClass().getClassLoader().getResource(FD_RESOURCE_ICONS+aName);
		if (url ==null)
			url = this.getClass().getResource(FD_RESOURCE_ICONS+aName);
		
		if (url != null)
		{
			ImageIcon ico = new ImageIcon(url);
			return new ImageIcon(ico.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		}
		else
		{
			return new ImageIcon();
		}
	}

	public  Image getImage(String aName)
	{
		URL url = this.getClass().getClassLoader().getResource(FD_RESOURCE_IMAGE+aName);
		if (url != null)
		{
			return Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		}
		else
		{
			return new ImageIcon().getImage();
		}
		
		//return ImageTools.CreateIcon(aName, 24).getImage();
	}

	public static String Calendar2String(Calendar aCal)
	{
		Formatter fmt = new Formatter();
		return fmt.format("%td.%tm.%tY %tH:%tM:%tS (%tZ)"
				, aCal, aCal, aCal, aCal, aCal, aCal, aCal).toString();
	}
}
