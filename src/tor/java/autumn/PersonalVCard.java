package tor.java.autumn;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import tor.java.autumn.tabella.tPerson;
import tor.java.autumn.tabella.tVTN;
import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.io.CompatibilityMode;
import net.sourceforge.cardme.io.VCardWriter;
import net.sourceforge.cardme.util.StringUtil;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.VCardImpl;
import net.sourceforge.cardme.vcard.arch.LanguageType;
import net.sourceforge.cardme.vcard.errors.VCardError;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;
import net.sourceforge.cardme.vcard.features.NameFeature;
import net.sourceforge.cardme.vcard.types.BDayType;
import net.sourceforge.cardme.vcard.types.FNType;
import net.sourceforge.cardme.vcard.types.NType;
import net.sourceforge.cardme.vcard.types.NoteType;
import net.sourceforge.cardme.vcard.types.PhotoType;
import net.sourceforge.cardme.vcard.types.TelType;
import JCommonTools.CC;

public class PersonalVCard 
{
	private Autumn			_aut;
	//private VCardImpl 		_vcard;
	VCard				_vcard;
	private Charset		_workcharset;
	private String 		_filename;
	

	public PersonalVCard(Autumn aut)
	{
		_aut = aut;
		_vcard = null;
		_workcharset = Charset.defaultCharset();
		
	}
	
	public void LoadFromVCardFile(String aFileName)
	{
		tPerson prs = new tPerson();
		_aut.setPerson(prs);
		
		VCardEngine vcardEngine = new VCardEngine();
		vcardEngine.setCompatibilityMode(CompatibilityMode.MS_OUTLOOK);
		vcardEngine.setForcedCharset("windows-1251");
		try
		{
			FileInputStream fis = new FileInputStream(aFileName);
			byte [] ba = new byte[100000];
			int reallen = fis.read(ba,0,ba.length);
			fis.close();
			String instr = new String(ba, 0, reallen, "windows-1251");
			
			_vcard = vcardEngine.parse(instr); //new File(aFileName));
			if (vcardEngine.isCharsetForced())
				_workcharset = vcardEngine.getForcedCharset(); 
		}
		catch (VCardParseException ex)
		{
			System.err.print(_aut.getString("Text.Error.NotParseVCardFile")+aFileName);
			ex.printStackTrace();
		}
		catch (IOException ioe)
		{
			System.err.print(_aut.getString("Text.Error.NotParseVCardFile")+aFileName);
			ioe.printStackTrace();
		}
	
		if (_vcard != null)
		{
			NType f = _vcard.getN();
			prs.setFName(f.getGivenName());
			prs.setLName(f.getFamilyName());
			//prs.setLName(ConvertStringFrom(f.getFamilyName(), f.getCharset()));
			List<String> itName = f.getAdditionalNames();
			String str = CC.STR_EMPTY;
			for (String si : itName)
				str += si + " ";
			if (str.length() > 0)
				prs.setPName(str.trim());
			
			FNType formattedName = _vcard.getFN();
			prs.setName(formattedName.getFormattedName());
			
			BDayType bd = _vcard.getBDay();
			
			if (bd != null)
				prs.setBDDate(bd.getBirthday().getTime());
				//_txtBirthday.setText(DisplayCalendar(vcard.getBirthDay().getBirthday()));
			else
				prs.setBDDate(Calendar.getInstance().getTime());
				//_txtBirthday.setText(CC.STR_EMPTY);

			List<PhotoType> itPhoto = _vcard.getPhotos();
			if (itPhoto != null)
				for (PhotoType ph : itPhoto)
				{
					prs.addImageAsBytes(ph.getPhoto());
				}
			
			List<TelType> itTel = _vcard.getTels();
			if (itTel != null)
				for (TelType tt : itTel)
				{
					tVTN tel = new tVTN(tt.getTelephone(), "");
					prs.getContactColl().add(tel);
				}

			List<NoteType> itNote = _vcard.getNotes();
			if (itNote != null && itNote.size() > 0)
				prs.setNotes(itNote.get(0).getNote());
			else
				prs.setNotes(CC.STR_EMPTY);
			
		}			
		_filename = aFileName;
	}

	public String SaveToVCardFile ()
	{
		String ret = CC.STR_EMPTY;
		
		tPerson prs = _aut.getPerson();
		try
		{
			if (_vcard != null)
			{
			}
			ret = "Save successfully!";
		}
		catch (Exception ex)
		{
			ret = ex.getMessage();
		}
		
		return ret;
	}
	
	public String ConvertStringFrom(String aTxt, Charset aCS){
		
		String ret = aTxt;
		String csName = (aCS != null && aCS.displayName().length() > 0) ? aCS.displayName() : Charset.defaultCharset().displayName();  
	
		if (aTxt != null && aTxt.length() > 0){
			
			try
			{
				ret = new String(aTxt.getBytes(aCS));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return ret;
	}
	

}
