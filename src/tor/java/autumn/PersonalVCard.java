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

import tor.java.autumn.tabella.tAdr;
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
import net.sourceforge.cardme.vcard.types.AdrType;
import net.sourceforge.cardme.vcard.types.BDayType;
import net.sourceforge.cardme.vcard.types.EmailType;
import net.sourceforge.cardme.vcard.types.FNType;
import net.sourceforge.cardme.vcard.types.NType;
import net.sourceforge.cardme.vcard.types.NoteType;
import net.sourceforge.cardme.vcard.types.PhotoType;
import net.sourceforge.cardme.vcard.types.RevType;
import net.sourceforge.cardme.vcard.types.TelType;
import net.sourceforge.cardme.vcard.types.UrlType;
import net.sourceforge.cardme.vcard.types.params.AdrParamType;
import net.sourceforge.cardme.vcard.types.params.EmailParamType;
import net.sourceforge.cardme.vcard.types.params.TelParamType;
import net.sourceforge.cardme.vcard.types.params.UrlParamType;
import JCommonTools.CC;

public class PersonalVCard 
{
	private Autumn			_aut;
	//private VCardImpl 		_vcard;
	//VCard				_vcard;
	private Charset		_workcharset;
	private String 		_filename;
	

	public PersonalVCard(Autumn aut)
	{
		_aut = aut;
		//_vcard = null;
		_workcharset = Charset.defaultCharset();
		
	}
	
	public void LoadFromVCardFile(String aFileName)
	{
		//VCard vcard = null;
		List<VCard> vcards  = null;
		VCardEngine vcardEngine = new VCardEngine();
		vcardEngine.setCompatibilityMode(CompatibilityMode.MS_OUTLOOK);
		//vcardEngine.setForcedCharset("windows-1251");
		try
		{
			FileInputStream fis = new FileInputStream(aFileName);
			byte [] ba = new byte[1000000];
			int reallen = fis.read(ba,0,ba.length);
			fis.close();
			String instr = new String(ba, 0, reallen, "windows-1251");
			
			//vcard = vcardEngine.parse (instr); //new File(aFileName));
			vcards = vcardEngine.parseMultiple (aFileName); //instr);
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
		
		if (vcards != null)
		{
			for (VCard vcard: vcards )
			{
				tPerson prs = new tPerson();
				//_aut.setPerson(prs);
				_aut.getRegister().getObjColl().add(prs);
				vCard2tPerson(vcard, prs);
			}
		}
		_filename = aFileName;
	}
	
	protected void vCard2tPerson(VCard aVCard, tPerson aPrs)
	{
		if (aVCard == null)
			return;
		
		NType f = aVCard.getN();
		aPrs.setFName(f.getGivenName());
		aPrs.setLName(f.getFamilyName());
		//prs.setLName(ConvertStringFrom(f.getFamilyName(), f.getCharset()));
		List<String> itName = f.getAdditionalNames();
		String str = CC.STR_EMPTY;
		for (String si : itName)
			str += si + " ";
		if (str.length() > 0)
			aPrs.setPName(str.trim());
		
		FNType formattedName = aVCard.getFN();
		if (formattedName != null)
			aPrs.setName(formattedName.getFormattedName());
		
		// Birthday:
		BDayType bd = aVCard.getBDay();
		if (bd != null)
			aPrs.setBDDate(bd.getBirthday().getTime());
			//_txtBirthday.setText(DisplayCalendar(vcard.getBirthDay().getBirthday()));
		else
			aPrs.setBDDate(Calendar.getInstance().getTime());
			//_txtBirthday.setText(CC.STR_EMPTY);

		// Photo:
		List<PhotoType> itPhoto = aVCard.getPhotos();
		if (itPhoto != null)
			for (PhotoType ph : itPhoto)
			{
				aPrs.addImageAsBytes(ph.getPhoto());
			}
		
		// Telephone:
		List<TelType> itTel = aVCard.getTels();
		if (itTel != null)
			for (TelType tt : itTel)
			{
				tVTN tel = new tVTN(tt.getTelephone(), "");
				String nt = CC.STR_EMPTY;
				for (TelParamType tpt : tt.getParams())
				{
					nt += (nt.length() > 0 ? "; " : CC.STR_EMPTY) + tpt.getType();
				}
				tel.setType(nt);
				aPrs.getContactColl().add(tel);
			}

		// eMail:
		List<EmailType> etMail = aVCard.getEmails();
		if (etMail != null)
			for (EmailType et : etMail)
			{
				tVTN mail = new tVTN();
				mail.setValue(et.getEmail());
				String nt = CC.STR_EMPTY;
				for (EmailParamType ept : et.getParams())
				{
					nt += (nt.length() > 0 ? "; " : CC.STR_EMPTY) + ept.getType();
				}
				mail.setType(nt);					
				aPrs.getContactColl().add(mail);
			}

		// URL:
		List<UrlType> utWWW = aVCard.getUrls();
		if (utWWW != null)
			for (UrlType ut : utWWW)
			{
				tVTN url = new tVTN();
				url.setValue(ut.getRawUrl());
				String nt = CC.STR_EMPTY;
				for (UrlParamType upt : ut.getParams())
				{
					nt += (nt.length() > 0 ? "; " : CC.STR_EMPTY) + upt.getType();
				}
				url.setType(nt);					
				aPrs.getContactColl().add(url);
			}
		
		// Address:
		List<AdrType> iAdr = aVCard.getAdrs();
		if (iAdr != null)
			for (AdrType at : iAdr)
			{
				tAdr adr = new tAdr();
				adr.setIndex(at.getPostalCode());
				adr.setHouseStreet(at.getStreetAddress());
				adr.setCity(at.getLocality());
				adr.setRegion(at.getRegion());
				adr.setCountry(at.getCountryName());
				String nt = CC.STR_EMPTY;
				for (AdrParamType apt : at.getParams())
				{
					nt += (nt.length() > 0 ? "; " : CC.STR_EMPTY) + apt.getType();
				}
				adr.setType(nt);
				aPrs.getAddrColl().add(adr);
			}
		
		// Notes:
		List<NoteType> itNote = aVCard.getNotes();
		if (itNote != null && itNote.size() > 0)
			aPrs.setNote(itNote.get(0).getNote());
		else
			aPrs.setNote(CC.STR_EMPTY);
		
		// Revision:
		RevType rt = aVCard.getRev();
		if (rt != null)
			aPrs.setLastModified(rt.getRevision());
		
	}

	public String SaveToVCardFile ()
	{
		String ret = CC.STR_EMPTY;
		
		//tPerson prs = _aut.getPerson();
		try
		{
			//if (_vcard != null)
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
