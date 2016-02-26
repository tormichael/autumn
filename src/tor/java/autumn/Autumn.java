package tor.java.autumn;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import JCommonTools.CodeText;
import JCommonTools.Tools;
import JCommonTools.Param.BookParam;
import JCommonTools.RefBook.RefBook;
import tor.java.thirteen.card.tObj;
import tor.java.thirteen.card.tOrg;
import tor.java.thirteen.card.tPerson;
import tor.java.thirteen.card.tRegister;

public class Autumn 
{
	private tRegister _ore;
	private ArrayList<CodeText>	_arrObjType;
	private ResourceBundle 	_bnd;
	private RefBookAutumn	_rfb;
	private BookParam 			_bp;
	
	public final static String FN_RESOURCE_TEXT = "autumnText";
	public final static String FD_RESOURCE_ICONS = "img/icons/";
	public final static String FD_RESOURCE_IMAGE = "img/";
	public final static String FD_RESOURCE_IMAGE_AUTUMN = "img/autumn/";

	public final static String ARGS_HELP = "help";
	public final static String ARGS_HELP_S = "h";
	public final static String ARGS_PARAM = "parameter";
	public final static String ARGS_PARAM_S = "prm";
	public final static String ARGS_PERSON = "person";
	public final static String ARGS_PERSON_S = "prs";
	public final static String ARGS_ORG = "organization";
	public final static String ARGS_ORG_S = "org";
	
	public final static String PREFERENCE_PATH = "/autumn";
	public final static String PARAM_DEFAUL_FN = "autumn.bpr";
	
	public final static int TOOL_BAR_ICON_SIZE = 24;

	//public final static int OBJ_TYPE_ABSTRACT = 0;
	//public final static int OBJ_TYPE_PERSON = 1;
	
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
	public void setRegister(tRegister aReg)
	{
		_ore = aReg;
		if (_ore.getRefBookFileName() != null && _ore.getRefBookFileName().length() > 0)
			_rfb.Load(_ore.getRefBookFileName());
		else
			_rfb.LoadDefault();
	}
	
	public BookParam getBookParam()
	{
		return _bp;
	}
	public void setBookParam(BookParam aBP)
	{
		_bp = aBP;
	}
	
	public RefBookAutumn getRefbook()
	{
		return _rfb;
	}
	public void setRefbook(RefBookAutumn aRB)
	{
		_rfb = aRB;
	}

	public ArrayList<CodeText> getArrObjType()
	{
		return _arrObjType;
	}
	/**
	 * @param args
	 * 		-h [--help] 					= this text  
	 * 		-prm [--parameter] filename  = set parameters file
	 * 		-prs [--person] <filename> = open personal form only with file <filename> if exist 
	 * 		-org [--organization] <filename>	= open organization form only with file <filename> if exist
	 */
	public static void main (String[] args) 
	{
		Autumn aut = new Autumn();
		
		boolean isHelp = false;
		JFrame frm = null;
		for (int ii = 0; ii < args.length; ii++)
		{
			if (args[ii].equals(ARGS_HELP) || args[ii].equals(ARGS_HELP_S))
			{
				isHelp = true;
				break;
			}
			else if (args[ii].equals(ARGS_PARAM) || args[ii].equals(ARGS_PARAM_S))
			{
				ii++;
				if (ii < args.length)
				{
					aut.setBookParam(BookParam.Load(args[ii]));
				}
				else
				{
					isHelp = true;
					break;
				}
			}
			else if (args[ii].equals(ARGS_PERSON) || args[ii].equals(ARGS_PERSON_S))
			{
				fPerson fp = new fPerson(aut);
				fp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ii++;
				if (ii < args.length)
				{
					if (args[ii].endsWith(tPerson.FILE_EXTENTION)
					|| args[ii].endsWith(tPerson.FILE_EXTENTION_CIPHER)
					|| args[ii].endsWith(PersonalVCard.FILE_EXTENTION))
					{
						fp.LoadFromFile(args[ii]);
					}
				}
				frm = fp;
			}
			else if (args[ii].equals(ARGS_ORG) || args[ii].equals(ARGS_ORG_S))
			{
				fOrg org = new fOrg(aut);
				org.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				ii++;
//				if (ii < args.length)
//				{
//					if (args[ii].endsWith(tPerson.FILE_EXTENTION)
//					|| args[ii].endsWith(tPerson.FILE_EXTENTION_CIPHER)
//					|| args[ii].endsWith(PersonalVCard.FILE_EXTENTION))
//					{
//						org.LoadFromFile(args[ii]);
//					}
//				}
				frm = org;
			}
			else
			{
				isHelp = true;
				break;
			}
		}
		
		if (isHelp)
		{
			System.out.println(String.format(aut.getString("Text.Args.help"), 
					ARGS_HELP_S, ARGS_HELP,
					ARGS_PARAM_S, ARGS_PARAM,
					ARGS_PERSON_S, ARGS_PERSON,
					ARGS_ORG_S, ARGS_ORG
				) 
			);
		}
		else if (frm == null)
		{
			frm = new fNavigator(aut);
		}
		
		if (aut.getBookParam() == null)
		{
			aut.setBookParam(BookParam.Load(PARAM_DEFAUL_FN));
		}
		
		frm.setVisible(true);
	}
	
	public Autumn()
	{
		_rfb = new RefBookAutumn(this);
		_bp = null; //new BookParam();
		setRegister(new tRegister());
		_bnd = ResourceBundle.getBundle(Autumn.FN_RESOURCE_TEXT, Locale.getDefault());
		initObjTypeArray();
	}
	
	private void initObjTypeArray()
	{
		_arrObjType = new ArrayList<CodeText>();
		_arrObjType.add(new CodeText(tObj.getType(), getString("Text.TypeName.Object")));
		_arrObjType.add(new CodeText(tPerson.getType(), getString("Text.TypeName.Person")));
		_arrObjType.add(new CodeText(tOrg.getType(), getString("Text.TypeName.Org")));
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

	public  Image getImageInRscImg(String aName)
	{
		return getImage(FD_RESOURCE_IMAGE+aName);
	}
	
	public  Image getImageInRscImgAutumn(String aName)
	{
		return getImage(FD_RESOURCE_IMAGE_AUTUMN+aName);
	}
	
	public  Image getImage(String aName)
	{
		URL url = this.getClass().getClassLoader().getResource(aName);
		if (url ==null)
			url = this.getClass().getResource(aName);
		
		if (url != null)
		{
			return Toolkit.getDefaultToolkit().getImage(url); //.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
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

	public void ShowError(Component aCmpParent, String aText)
	{
		JOptionPane.showConfirmDialog(aCmpParent, aText);
	}
}
