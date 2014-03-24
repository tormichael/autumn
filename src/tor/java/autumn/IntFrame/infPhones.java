package tor.java.autumn.IntFrame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import tor.java.autumn.Autumn;
import tor.java.autumn.PhoneTableModel;

public class infPhones extends infBase 
{
	private JTable			_tabConnection;
	private PhoneTableModel _tmPhone;

	public infPhones(Autumn aAut, String aName)
	{
		super(aAut, aName);
		
		_tmPhone = new PhoneTableModel(aAut);
		_tabConnection = new JTable(_tmPhone);
		this.add(new JScrollPane(_tabConnection));
	}	
	
}
