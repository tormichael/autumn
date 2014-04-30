package tor.java.autumn.IntFrame;

import java.util.prefs.Preferences;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import JCommonTools.CC;
import JCommonTools.TableTools;
import tor.java.autumn.Autumn;
import tor.java.autumn.PhoneTableModel;
import tor.java.autumn.tabella.tPerson;

public class infPhones extends infBase 
{
	private JTable			_tabConnection;
	private PhoneTableModel _tmPhone;

	public infPhones(Autumn aAut, String aName, tPerson aPerson)
	{
		super(aAut, aName, aPerson);
		
		_tmPhone = new PhoneTableModel(aAut, aPerson);
		_tabConnection = new JTable(_tmPhone);
		this.add(new JScrollPane(_tabConnection));
		
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mName);
		TableTools.SetColumnsWidthFromString(_tabConnection, node.get("TabColWidth_Connection", CC.STR_EMPTY));
	}	
	
	public void Load()
	{
		_tmPhone.Reconnect((tPerson)mObj);
		_tabConnection.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	protected void mSave()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mName);
		node.put("TabColWidth_Connection", TableTools.GetColumnsWidthAsString(_tabConnection));
	}
}
