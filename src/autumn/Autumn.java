package autumn;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.security.auth.login.AppConfigurationEntry;
import javax.swing.JFrame;

import org.omg.CORBA.Environment;

import autumn.tabella.tPerson;

public class Autumn 
{
	private tPerson			_prs;
	private ResourceBundle 	_bnd;
	
	public final static String FN_RESOURCE_TEXT = "autumn/rsc/autumnText";
	public final static String FD_RESOURCE_ICONS = "rsc/icons/";

	public final static int TOOL_BAR_ICON_SIZE = 24;
	
	public ResourceBundle get_bnd()
	{
		return _bnd;
	}
	
	public String getString(String key)
	{
		return _bnd.getString(key);
	}
	
	public tPerson getPerson()
	{
		return _prs;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		wPerson prsn = new wPerson(new Autumn());
		
		if (args != null && args.length > 0)
			prsn.setFileName(args[0]);
		
		prsn.setVisible(true);
		
		
	}
	
	public Autumn()
	{
		_prs = new tPerson();
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
}
