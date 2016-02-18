package tor.java.autumn;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;

import JCommonTools.RefBook.RefBook;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;

/***
 * Wrapper for RefBook. Defined specificity Autumn project. 
 * @author M.Tor
 * 11.09.2014
 */
public class RefBookAutumn
{
	public final static String RB_DEFAUL_FILENAME = "autumn.rfb";
	
	public final static String RB_ALIAS_VTN_TYPE = "vtn_type";
	public final static String RB_ALIAS_VTN_TYPE_EMAIL = "EMAIL";
	public final static String RB_ALIAS_VTN_MODE = "vtn_mode";
	public final static String RB_ALIAS_ADDRESS_TYPE = "address_type";
	public final static String RB_ALIAS_COUNTRY = "country";
	public final static String RB_ALIAS_OWNERSHIP = "ownership";
	public final static String RB_ALIAS_CODE_ID = "CODE_ID";
	
	private RefBook _rb;
	private Autumn _aut;

	private String _curFileName;
	
	private rbNode _rbnContactType;
	private rbNode _rbnContactMode;
	private rbNode _rbnAddressType;
	private rbNode _rbnCountry;
	private rbNode _rbnOwnership;
	private rbNode _rbnCodeID;
	
	public RefBook getRB()
	{
		return _rb;
	}
	
	public rbNode getRefBookNode()
	{
		return _rb.getRefBookNode();
	}
	public rbNode getNodeContactType()
	{
		if (_rbnContactType == null)
			_rbnContactType = _rb.getRefBookNode().findByAlias(RB_ALIAS_VTN_TYPE);		

		return _rbnContactType;
	}
	public rbNode getNodeContactMode()
	{
		if (_rbnContactMode == null)
			_rbnContactMode =_rb.getRefBookNode().findByAlias(RB_ALIAS_VTN_MODE);

		return _rbnContactMode;
	}
	public rbNode getNodeAddressType()
	{
		if (_rbnAddressType == null)
			_rbnAddressType = _rb.getRefBookNode().findByAlias(RB_ALIAS_ADDRESS_TYPE);		

		return _rbnAddressType;
	}
	public rbNode getNodeCountry()
	{
		if (_rbnCountry == null)
			_rbnCountry = _rb.getRefBookNode().findByAlias(RB_ALIAS_COUNTRY);		

		return _rbnCountry;
	}
	public rbNode getNodeOwnership()
	{
		if (_rbnOwnership == null)
			_rbnOwnership = _rb.getRefBookNode().findByAlias(RB_ALIAS_OWNERSHIP);		

		return _rbnOwnership;
	}
	public rbNode getNodeCodeID()
	{
		if (_rbnCodeID == null)
			_rbnCodeID = _rb.getRefBookNode().findByAlias(RB_ALIAS_CODE_ID);		

		return _rbnCodeID;
	}
//	public rbNode getNodeXXX()
//	{
//		if (_rbnXXX == null)
//			_rbnXXX = _rb.getRefBookNode().findByAlias(RB_ALIAS_XXX);		
//
//		return _rbnXXX;
//	}

	public String getFileName()
	{
		return _curFileName;
	}
	
	public RefBookAutumn(Autumn aA)
	{
		_rb = new RefBook();
		_aut = aA;
		_curFileName = null;
		_initRBN();
	}
	
	private void _initRBN()
	{
		_rbnContactType = null;
		_rbnContactMode = null;
		_rbnAddressType = null;
		_rbnCountry = null;
		_rbnOwnership = null;
		_rbnCodeID = null;
	}

	public void LoadDefault()
	{
		Load(RB_DEFAUL_FILENAME);
	}
	public void Load(String aFileName)
	{
		if (aFileName != null && aFileName.length() > 0)
		{
			_rb = RefBook.Load(aFileName);
			if (_rb != null)
				_curFileName = aFileName;
			else
				_rb = new RefBook();
		}
	}
	
	public void FormShow() 
	{
		final fRefBook frb = new fRefBook(_rb) ;
		frb.setTitle(_aut.getString("Refbook.Title"));
		frb.setIconImage(_aut.getImageIcon("refbook.png").getImage());
		frb.ActLoad.putValue(Action.SMALL_ICON, _aut.getImageIcon("open.png"));
		frb.ActSave.putValue(Action.SMALL_ICON, _aut.getImageIcon("save.png"));
		frb.ActNew.putValue(Action.SMALL_ICON, _aut.getImageIcon("plus.png"));
		frb.ActEdit.putValue(Action.SMALL_ICON, _aut.getImageIcon("edit.png"));
		frb.ActDelete.putValue(Action.SMALL_ICON, _aut.getImageIcon("trash.png"));
		frb.ActRefresh.putValue(Action.SMALL_ICON, _aut.getImageIcon("shredder.png"));
		frb.setPreferencePath(Autumn.PREFERENCE_PATH+"/refbook");
		if (_curFileName != null && _curFileName.length() > 0)
		{
			frb.setStatusText(String.format(_aut.getString("Refbook.LoadedFile"), _curFileName));
			frb.setCurrentFileName(_curFileName);
		}
		else
			frb.setStatusText(_aut.getString("Refbook.OpenNew"));
		
		frb.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				frb.removeWindowListener(this);
				_curFileName = frb.getCurrentFileName();
				_aut.getRegister().setRefBookFileName(_curFileName);
				_initRBN();
				super.windowClosing(e);
			}
		});
		
		frb.setVisible(true);
	}

}
