package autumn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import JCommonTools.CC;

import net.sourceforge.cardme.vcard.features.TelephoneFeature;
import net.sourceforge.cardme.vcard.types.parameters.TelephoneParameterType;


public class PhoneTableModel extends AbstractTableModel {

	private ResourceBundle 	_bnd;
	private ArrayList<TelephoneFeature> _phoneNumbers;
	
	public void setTelephones(Iterator<TelephoneFeature> aitNumbers){
		if (aitNumbers != null){
			_phoneNumbers.clear();
			while (aitNumbers.hasNext()){
				_phoneNumbers.add(aitNumbers.next());
			}
		}
	}
	
	public PhoneTableModel(ResourceBundle aBnd){
		_bnd = aBnd;
		_phoneNumbers = new ArrayList<TelephoneFeature>();
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return _phoneNumbers != null ? _phoneNumbers.size() + 1 : 1;   
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String ret = CC.STR_EMPTY;
		if (_phoneNumbers != null && _phoneNumbers.size() > rowIndex){
			switch (columnIndex){
				case 0:
					ret = _phoneNumbers.get(rowIndex).getTelephone();
					break;
				case 1:
					for (TelephoneParameterType tt : _phoneNumbers.get(rowIndex).getTelephoneParameterTypesList())
						ret += tt.getDescription()+ ";";
					if (ret.length() > 1)
						ret = ret.substring(0, ret.length()-1);
					break;
			}				
		}
		return wPerson.StringFromUTF8(ret);
	}

	@Override
	public String getColumnName(int columnIndex) {
		
		String ret = super.getColumnName(columnIndex);
		
		switch (columnIndex){
			case 0:
				ret = _bnd.getString("Table.Phone.ColName.Num");
				break;
			case 1:
				ret = _bnd.getString("Table.Phone.ColName.Types");
				break;
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
