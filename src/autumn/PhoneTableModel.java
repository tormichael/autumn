package autumn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import autumn.tabella.tVTN;
import JCommonTools.CC;
import net.sourceforge.cardme.vcard.features.TelephoneFeature;
import net.sourceforge.cardme.vcard.types.parameters.TelephoneParameterType;


public class PhoneTableModel extends AbstractTableModel {

	private Autumn 	_aut;
    private ArrayList<tVTN> _contacts;

	public PhoneTableModel(Autumn aut)
	{
		_aut = aut;
		if (_aut != null)
			_contacts = _aut.getPerson().getContactColl();
		else
			_contacts = null;
	}
	
	@Override
	public int getColumnCount() 
	{
		return 3;
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
					/*
					for (TelephoneParameterType tt : _phoneNumbers.get(rowIndex).getTelephoneParameterTypesList())
						ret += tt.getDescription()+ ";";
					if (ret.length() > 1)
						ret = ret.substring(0, ret.length()-1);
					*/
					ret = _contacts.get(rowIndex).getType();
					break;
				case 2:
					ret = _contacts.get(rowIndex).getNote();
					break;
			}				
			
		}
		return ret;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
	{
		super.setValueAt(aValue, rowIndex, columnIndex);
		/*if (_params == null)
			return;
		
		if (_params.size() >= rowIndex)
		{
			if (_params.size() == rowIndex)
				_params.add(new Param());
			
			switch (columnIndex)
			{
				case 0:
					_params.get(rowIndex).Number = Integer.parseInt(aValue.toString());
					break;
				case 1:
					_params.get(rowIndex).Title = aValue.toString();
					break;
				case 2:
					_params.get(rowIndex).Name = aValue.toString();
					break;
				case 3:
					_params.get(rowIndex).Type = eQueryParamType.valueOf(aValue.toString());
					break;
				case 4:
					_params.get(rowIndex).IsInsert = Boolean.parseBoolean(aValue.toString());
					break;
				case 5:
					_params.get(rowIndex).DefaultValue = aValue.toString();
					break;
			}
		}*/
			
		//super.setValueAt(aValue, rowIndex, columnIndex);
	}
	
}
