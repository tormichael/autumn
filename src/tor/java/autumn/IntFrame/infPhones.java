package tor.java.autumn.IntFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import JCommonTools.CC;
import JCommonTools.TableTools;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;
import tor.java.autumn.Autumn;
import tor.java.autumn.PhoneTableModel;
import tor.java.autumn.tabella.tPerson;

public class infPhones extends infBase 
{
	private JTable			_tabConnection;
	private PhoneTableModel _tmPhone;
	private JComboBox<rbNode> _cboType;
	private JComboBox<rbNode> _cboMode;
	
	public infPhones(Autumn aAut, String aName, tPerson aPerson)
	{
		super(aAut, aName, aPerson);
		
		_tmPhone = new PhoneTableModel(aAut, aPerson);
		_tabConnection = new JTable(_tmPhone);
		this.add(new JScrollPane(_tabConnection));

		DefaultComboBoxModel<rbNode> cbmType = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmType, aAut.getRefbook().getNodeContactType(), true);
		_cboType = new JComboBox<rbNode>(cbmType);
		_tmPhone.setComboBoxTypeModel(cbmType);
		_tabConnection.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(_cboType));
		DefaultComboBoxModel<rbNode> cbmMode = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmMode, aAut.getRefbook().getNodeContactMode(), true);
		_cboMode = new JComboBox<rbNode>(cbmMode);
		_tmPhone.setComboBoxModeModel(cbmMode);
		_tabConnection.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(_cboMode));
	
		
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
