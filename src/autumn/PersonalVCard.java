package autumn;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import autumn.tabella.tPerson;
import autumn.tabella.tVTN;

import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.io.CompatibilityMode;
import net.sourceforge.cardme.io.VCardWriter;
import net.sourceforge.cardme.util.StringUtil;
import net.sourceforge.cardme.vcard.VCardImpl;
import net.sourceforge.cardme.vcard.errors.VCardError;
import net.sourceforge.cardme.vcard.features.NameFeature;
import net.sourceforge.cardme.vcard.features.TelephoneFeature;
import JCommonTools.CC;

public class PersonalVCard 
{
	private Autumn			_aut;
	private VCardImpl 		_vcard;
	private	Charset			_workcharset;
	private String 			_filename;
	

	public PersonalVCard(Autumn aut)
	{
		_aut = aut;
		_vcard = null;
		_workcharset = Charset.defaultCharset();
		
	}
	
	public void LoadFromVCardFile(String aFileName)
	{
		tPerson prs = _aut.getPerson();
		
		VCardEngine vcardEngine = new VCardEngine();
		vcardEngine.setCompatibilityMode(CompatibilityMode.MS_OUTLOOK);
		//vcardEngine.setForcedCharset("UTF-8");
		try
		{
			_vcard = (VCardImpl)vcardEngine.parse(new File(aFileName));
			if (vcardEngine.isCharsetForced())
				_workcharset = vcardEngine.getForcedCharset(); 
		}
		catch (IOException ioe)
		{
			System.err.print(_aut.getString("Text.Error.NotParseVCardFile")+aFileName);
			ioe.printStackTrace();
		}
	
		if (_vcard != null)
		{
			NameFeature name = _vcard.getName(); 
			if (name != null)
			{
				prs.setFName(ConvertStringFrom(name.getGivenName(), name.getCharset()));
				if (name.getAdditionalNames().hasNext())
					prs.setPName(ConvertStringFrom(name.getAdditionalNames().next(), name.getCharset()));
				else
					prs.setPName(CC.STR_EMPTY);
				prs.setLName(ConvertStringFrom(name.getFamilyName(), name.getCharset()));
				if (_vcard.getBirthDay() != null)
					prs.setBDDate(_vcard.getBirthDay().getBirthday().getTime());
					//_txtBirthday.setText(DisplayCalendar(vcard.getBirthDay().getBirthday()));
				else
					prs.setBDDate(Calendar.getInstance().getTime());
					//_txtBirthday.setText(CC.STR_EMPTY);
				
				while (_vcard.getPhotos().hasNext())
				{
					prs.addImageAsBytes(_vcard.getPhotos().next().getPhoto());
				}
			}
			
			if (_vcard.getNotes().hasNext())
				prs.setNotes(_vcard.getNotes().next().getNote());
			else
				prs.setNotes(CC.STR_EMPTY);
			
			while (_vcard.getTelephoneNumbers().hasNext())
			{
				TelephoneFeature vtel = _vcard.getTelephoneNumbers().next();
				tVTN tel = new tVTN(vtel.getTelephone(), vtel.getTypeString());
				prs.getContactColl().add(tel);
			}
		}
		
		_filename = aFileName;
	}

	public String SaveToVCardFile ()
	{
		String ret = CC.STR_EMPTY;
		
		tPerson prs = _aut.getPerson();
		try
		{
			if (_vcard != null){

				_vcard.getErrors().clear();	
				
				NameFeature name = _vcard.getName(); 
				name.setCharset("UTF-8");
				//name.setEncodingType(EncodingType.)
				name.setGivenName(Autumn.String2UTF8(prs.getFName()));
				name.setFamilyName(Autumn.String2UTF8(prs.getLName()));
				name.clearAdditionalNames();
				if (prs.getLName().length() > 0){
					name.addAdditionalName(Autumn.String2UTF8(prs.getPName()));
				}
				
				//if (_vcard.hasErrors())
				VCardWriter writer = new VCardWriter();
				writer.setVCard(_vcard);
				String vstr = writer.buildVCardString();
				if (writer.hasErrors()){
					List<VCardError> errs = _vcard.getErrors();
					for (int jj = 0; jj < errs.size(); jj++){
						System.err.println(StringUtil.formatException(errs.get(jj).getError()));
					}
					ret = "error vcard [buildVCardString()]";
				}
				else{
					if (_filename != null && _filename.length() > 0){
						FileWriter file = new FileWriter(new File(_filename));
						file.write(vstr);
						file.close();
					}
				}
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
				ret = new String(aTxt.getBytes(), csName);
			}
			catch (UnsupportedEncodingException ex)
			{
				ex.printStackTrace();
			}
		}
		return ret;
	}
	

}
