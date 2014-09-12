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
	public final static String RB_ALIAS_VTN_TYPE = "vtn_type";
	public final static String RB_ALIAS_VTN_TYPE_EMAIL = "EMAIL";
	public final static String RB_ALIAS_VTN_MODE = "vtn_mode";
	
	private RefBook _rb;
	private Autumn _aut;

	private String _curFileName;
	
	private rbNode _rbnContactType;
	private rbNode _rbnContactMode;
	
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
	}

	public void Load(String aFileName)
	{
		if (aFileName != null && aFileName.length() > 0)
		{
			_rb = RefBook.Load(aFileName);
			_curFileName = aFileName;
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
				_initRBN();
				super.windowClosing(e);
			}
		});
		
		frb.setVisible(true);
	}

}
