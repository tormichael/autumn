package tor.java.autumn.IntFrame;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.w3c.dom.views.AbstractView;

import JCommonTools.CC;
import JCommonTools.GBC;
import JCommonTools.TableTools;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;

import tor.java.autumn.Autumn;
import tor.java.autumn.tabella.tAdr;
import tor.java.autumn.tabella.tPerson;

public class infAddress extends infBase 
{
	private JSplitPane _spn;
	private JTable _tab;
	private tmAddress _tabMod;
	private JComboBox<rbNode> _cboType;
	
	private JTextField _txfIndex;
	private JComboBox<rbNode> _cboCountry;
	private JTextField _txfLocality;
	private JTextField _txfRegion;
	private JTextArea _txaAddress;
	private JTextArea _txaNote;
	
	private int _prevAdrInd;
	
	public infAddress(Autumn aAut, String aName, tPerson aPerson)
	{
		super (aAut, aName, aPerson);
		
		_prevAdrInd = -1;
		
		DefaultComboBoxModel<rbNode> cbmType = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmType, aAut.getRefbook().getNodeAddressType(), true);
		_cboType = new JComboBox<rbNode>(cbmType);
		if(aPerson != null)
			_tabMod = new tmAddress(aAut, aPerson.getAddrColl(), cbmType);
		else
			_tabMod = new tmAddress(aAut, null, cbmType);
		_tab =  new JTable(_tabMod);
		_tab.setRowHeight(_tab.getFont().getSize() + 2);
		_tab.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(_cboType));
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnlAdr = new JPanel(gbl);
		_txfIndex = new JTextField(10);		
		gbl.setConstraints(_txfIndex, new GBC(0,0).setIns(2).setFill(GBC.HORIZONTAL));
		pnlAdr.add(_txfIndex);
		DefaultComboBoxModel<rbNode> cbmCountry = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmCountry, aAut.getRefbook().getNodeCountry(), true);
		_cboCountry = new JComboBox<rbNode>(cbmCountry);
		gbl.setConstraints(_cboCountry, new GBC(1,0).setIns(2).setFill(GBC.HORIZONTAL).setAnchor(GBC.EAST).setWeight(1., 0.));
		pnlAdr.add(_cboCountry);
		_txfLocality = new JTextField();
		gbl.setConstraints(_txfLocality, new GBC(0,1).setIns(2).setGridSpan(2,1).setFill(GBC.HORIZONTAL).setWeight(1., 0.));
		pnlAdr.add(_txfLocality);
		_txfRegion = new JTextField();
		gbl.setConstraints(_txfRegion, new GBC(0,2).setIns(2).setGridSpan(2,1).setFill(GBC.HORIZONTAL).setWeight(1., 0.));
		pnlAdr.add(_txfRegion);
		_txaAddress = new JTextArea();
		JScrollPane sp = new JScrollPane(_txaAddress);
		gbl.setConstraints(sp, new GBC(0,3).setIns(2).setGridSpan(2,1).setFill(GBC.BOTH) .setWeight(1., 0.5));
		pnlAdr.add(sp);
		_txaNote = new JTextArea();
		sp = new JScrollPane(_txaNote);
		gbl.setConstraints(sp, new GBC(0,4).setIns(2).setGridSpan(2,1).setFill(GBC.BOTH).setWeight(1., 0.5));
		pnlAdr.add(sp);
		
		_spn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(_tab), pnlAdr);
		this.add(_spn);
		
		_tab.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_tab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				ArrayList<tAdr> aadr = ((tPerson)mObj).getAddrColl();
				if (_prevAdrInd >= 0)
					_getData(aadr.get(_prevAdrInd));
				int sr = e.getFirstIndex();
				if (sr < aadr.size())
				{
					_setData(aadr.get(sr));
					_prevAdrInd = sr;
				}
				else
				{
					_setData(null);
				}
			}
		});
	}

	private void _setData(tAdr aAdr)
	{
		if (aAdr != null)
		{
			_txfIndex.setText(aAdr.getIndex());
			fRefBook.FindRBNodeByIDInComModel((DefaultComboBoxModel<rbNode>)_cboCountry.getModel(), aAdr.getType());
			_txfLocality.setText(aAdr.getLocality());
			_txfRegion.setText(aAdr.getRegion());
			_txaAddress.setText(aAdr.getHouseStreet());
			_txaNote.setText(aAdr.getNote());
		}
		else
		{
			_txfIndex.setText(CC.STR_EMPTY);
			_cboCountry.setSelectedIndex(0);
			_txfLocality.setText(CC.STR_EMPTY);
			_txfRegion.setText(CC.STR_EMPTY);
			_txaAddress.setText(CC.STR_EMPTY);
			_txaNote.setText(CC.STR_EMPTY);
		}
	}
	private void _getData(tAdr aAdr)
	{
		aAdr.setIndex(_txfIndex.getText());
		aAdr.setCountry(((rbNode)_cboCountry.getSelectedItem()).getId());
		aAdr.setLocality(_txfLocality.getText());
		aAdr.setRegion(_txfRegion.getText());
		aAdr.setHouseStreet(_txaAddress.getText());
		aAdr.setNote(_txaNote.getText());
	}
	
	public void Load()
	{
		tPerson prs = (tPerson)mObj;
		_tabMod.Reconnect(prs);
		if (prs != null && prs.getAddrColl().size()>0)
			_tab.getSelectionModel().setSelectionInterval(0, 0);
		else
			_setData(null);
		
		_tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mName);
		_spn.setDividerLocation(node.getInt("SplitAddress", 200));
	}
	
	protected void mSave()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mName);
		node.put("TabColWidth_Address", TableTools.GetColumnsWidthAsString(_tab));
		node.putInt("SplitAddress", _spn.getDividerLocation());
	}
	
	class tmAddress extends AbstractTableModel
	{
		private Autumn _aut; 
		private ArrayList<tAdr> _alAdr;
		private DefaultComboBoxModel<rbNode> _cboMod;
		
		public tmAddress(Autumn aAut, ArrayList<tAdr> aALAdr, DefaultComboBoxModel<rbNode> aCboMod)
		{
			_aut = aAut;
			_alAdr = aALAdr;
			_cboMod = aCboMod;
		}
		
		public void Reconnect(tPerson aPerson)
		{
			if (aPerson != null)
				_alAdr = aPerson.getAddrColl();
			else
				_alAdr = null;
		}
		
		@Override
		public int getColumnCount() 
		{
			return 1;
		}

		@Override
		public int getRowCount() 
		{
			return _alAdr != null ? _alAdr.size() + 1 : 1;
		}

		@Override
		public String getColumnName(int columnIndex) 
		{
			return _aut.getString("Table.Address.ColName.Type");
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) 
		{
			if (_alAdr != null && rowIndex < _alAdr.size())
				return fRefBook.FindRBNodeByIDInComModel(_cboMod, _alAdr.get(rowIndex).getType());
			else
				return CC.STR_EMPTY;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) 
		{
			return _alAdr != null;
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
		{
			if (rowIndex == _alAdr.size())
			{
				_alAdr.add(new tAdr());
			}
			
			if (rowIndex < _alAdr.size())
				_alAdr.get(rowIndex).setType(fRefBook.FindRBNodeByNameInComModel(_cboMod, aValue.toString()));
		
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
		
		@Override
		public Class<rbNode> getColumnClass(int columnIndex) 
		{
			return rbNode.class;
		}
	}
	
}
