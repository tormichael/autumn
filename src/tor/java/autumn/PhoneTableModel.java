package tor.java.autumn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

import tor.java.thirteen.card.tPerson;
import tor.java.thirteen.card.tVTN;
import JCommonTools.CC;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;


public class PhoneTableModel extends AbstractTableModel {

	private Autumn 	_aut;
    private ArrayList<tVTN> _contacts;

	private DefaultComboBoxModel<rbNode> _modCboType;
	private DefaultComboBoxModel<rbNode> _modCboMode;
   
	public void setComboBoxTypeModel (DefaultComboBoxModel<rbNode> aCBM)
	{
		_modCboType = aCBM;
	}
	public void setComboBoxModeModel (DefaultComboBoxModel<rbNode> aCBM)
	{
		_modCboMode = aCBM;
	}
	
	public PhoneTableModel(Autumn aut, tPerson aPerson)
	{
		_aut = aut;
		Reconnect(aPerson);
	}
	
	public void Reconnect(tPerson aPerson)
	{
		if (aPerson != null)
			_contacts = aPerson.getContactColl();
		else
			_contacts = null;
	}
	
	@Override
	public int getColumnCount() 
	{
		return 4;
	}

	@Override
	public int getRowCount() 
	{
		return _contacts != null ? _contacts.size() + 1 : 1;
	}

	@Override
	public String getColumnName(int columnIndex) 
	{
		String ret = super.getColumnName(columnIndex);
		
		switch (columnIndex){
			case 0:
				ret = _aut.getString("Table.Phone.ColName.Num");
				break;
			case 1:
				ret = _aut.getString("Table.Phone.ColName.Types");
				break;
			case 2:
				ret = _aut.getString("Table.Phone.ColName.Mode");
				break;
			case 3:
				ret = _aut.getString("Table.Phone.ColName.Notes");
				break;
		}
		return ret;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		String ret = CC.STR_EMPTY;
		if (_contacts != null && _contacts.size() > rowIndex)
		{
			switch (columnIndex){
				case 0:
					ret = _contacts.get(rowIndex).getValue();
					break;
				case 1:
					ret = fRefBook.FindRBNodeByIDInComModel(_modCboType, _contacts.get(rowIndex).getType());
					break;
				case 2:
					ret = fRefBook.FindRBNodeByIDInComModel(_modCboMode, _contacts.get(rowIndex).getMode());
					break;
				case 3:
					ret = _contacts.get(rowIndex).getNote();
					break;
			}				
			
		}
		return ret;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		//return super.isCellEditable(rowIndex, columnIndex);
		return true;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
	{
		if (rowIndex == _contacts.size())
		{
			_contacts.add(new tVTN());
		}
		
		switch (columnIndex)
		{
			case 0:
				_contacts.get(rowIndex).setValue(aValue.toString());
				break;
			case 1:
				_contacts.get(rowIndex).setType(fRefBook.FindRBNodeByNameInComModel(_modCboType, aValue.toString()));
				break;
			case 2:
				_contacts.get(rowIndex).setMode(fRefBook.FindRBNodeByNameInComModel(_modCboMode, aValue.toString()));
				break;
			case 3:
				_contacts.get(rowIndex).setNote(aValue.toString());
				break;
			default:
				super.setValueAt(aValue, rowIndex, columnIndex);
				break;
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	
	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
//		if (columnIndex == 1)
//			return _modCboType.getClass();
//		if (columnIndex == 2)
//			return _modCboMode.getClass();
//		else
			return getValueAt(0, columnIndex).getClass();
	}
}
