package tor.java.autumn.IntFrame;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import JCommonTools.CC;
import JCommonTools.TableTools;
import JCommonTools.RefBook.RBComboBoxCellEdit;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;
import tor.java.autumn.Autumn;
import tor.java.autumn.PhoneTableModel;
import tor.java.thirteen.card.tObj;
import tor.java.thirteen.card.tPerson;
import tor.java.thirteen.card.tVTN;

public class infPhones extends infBase 
{
	private JTable			_tabConnection;
	private PhoneTableModel _tmPhone;
	private JComboBox<rbNode> _cboType;
	private JComboBox<rbNode> _cboMode;
	
	public infPhones(Autumn aAut, String aPrefPath, tObj aPerson)
	{
		super(aAut, aPrefPath, aPerson);
		
		_tmPhone = new PhoneTableModel(aAut, aPerson);
		_tabConnection = new JTable(_tmPhone);
		_tabConnection.setRowHeight(_tabConnection.getFont().getSize() + 2);
		this.add(new JScrollPane(_tabConnection));

		DefaultComboBoxModel<rbNode> cbmType = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmType, aAut.getRefbook().getNodeContactType(), true);
		_cboType = new JComboBox<rbNode>(cbmType);
		_tmPhone.setComboBoxTypeModel(cbmType);
		RBComboBoxCellEdit cedit = new RBComboBoxCellEdit (_cboType);
		cedit.setClickCountToStart(2);
		_tabConnection.getColumnModel().getColumn(1).setCellEditor(cedit);
		DefaultComboBoxModel<rbNode> cbmMode = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmMode, aAut.getRefbook().getNodeContactMode(), true);
		_cboMode = new JComboBox<rbNode>(cbmMode);
		_tmPhone.setComboBoxModeModel(cbmMode);
		cedit = new RBComboBoxCellEdit (_cboMode);
		cedit.setClickCountToStart(2);
		_tabConnection.getColumnModel().getColumn(2).setCellEditor(cedit);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		_tabConnection.getColumnModel().getColumn(2).setCellRenderer(renderer);
		
		Preferences node = Preferences.userRoot().node(mPrefPath);
		TableTools.SetColumnsWidthFromString(_tabConnection, node.get("TabColWidth_Connection", CC.STR_EMPTY));
	
		JPopupMenu pum = new JPopupMenu();
		JMenuItem pumiDel = new JMenuItem(actTabRowDelete);
		pumiDel.setText(mAut.getString("PopupMenu.intPhones.Delete"));
		pum.add(pumiDel);
		_tabConnection.setComponentPopupMenu(pum);
	}	
	
	public void Load()
	{
		_tmPhone.Reconnect((tPerson)mObj);
		_tabConnection.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	protected void mSave()
	{
		Preferences node = Preferences.userRoot().node(mPrefPath);
		node.put("TabColWidth_Connection", TableTools.GetColumnsWidthAsString(_tabConnection));
	}

	Action actTabRowDelete = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_tabConnection.getSelectedRowCount() >= 1)
			{
				ArrayList<tVTN> cnts = ((tPerson)mObj).getContactColl();
				int[] iSelRow = _tabConnection.getSelectedRows(); 
				for(int ii=iSelRow.length-1; ii >= 0; ii--)
				{
					cnts.remove(iSelRow[ii]);
				}
				_tabConnection.updateUI();
			}
		}
	};
	
}
