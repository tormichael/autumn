package tor.java.autumn.IntFrame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import JCommonTools.RefBook.RBComboBoxCellEdit;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;
import tor.java.autumn.Autumn;
import tor.java.thirteen.card.tAdr;
import tor.java.thirteen.card.tObj;
import tor.java.thirteen.card.tPerson;

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
	
	public infAddress(Autumn aAut, String aPrefPath, tObj aPerson)
	{
		super (aAut, aPrefPath, aPerson);
		
		_prevAdrInd = -1;
		
		DefaultComboBoxModel<rbNode> cbmType = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmType, aAut.getRefbook().getNodeAddressType(), true);
		_cboType = new JComboBox<rbNode>(cbmType);
		if(aPerson != null && aPerson instanceof tPerson)
			_tabMod = new tmAddress(aAut, ((tPerson)aPerson).getAddrColl(), cbmType);
		else
			_tabMod = new tmAddress(aAut, null, cbmType);
		_tab =  new JTable(_tabMod);
		_tab.setRowHeight(_tab.getFont().getSize() + 2);
		_cboType.setRequestFocusEnabled(false);
		RBComboBoxCellEdit edit = new RBComboBoxCellEdit(_cboType);
		edit.setClickCountToStart(2);
		_tab.getColumnModel().getColumn(0).setCellEditor(edit);
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnlAdr = new JPanel(gbl);
		JLabel lbl = new JLabel(mAut.getString("Label.Address.Index"));
		gbl.setConstraints(lbl, new GBC(0,0).setIns(2).setAnchor(GBC.EAST));
		pnlAdr.add(lbl);
		_txfIndex = new JTextField(8);
		_txfIndex.setMinimumSize(new Dimension(_txfIndex.getPreferredSize().width, _txfIndex.getPreferredSize().height));
		gbl.setConstraints(_txfIndex, new GBC(1,0).setIns(2)); //.setFill(GBC.HORIZONTAL));
		pnlAdr.add(_txfIndex);
		DefaultComboBoxModel<rbNode> cbmCountry = new DefaultComboBoxModel<rbNode>();
		fRefBook.LoadComboModel(cbmCountry, aAut.getRefbook().getNodeCountry(), true);
		_cboCountry = new JComboBox<rbNode>(cbmCountry);
		gbl.setConstraints(_cboCountry, new GBC(2,0).setIns(2).setFill(GBC.HORIZONTAL).setAnchor(GBC.EAST).setWeight(0.3, 0.));
		pnlAdr.add(_cboCountry);
		lbl = new JLabel(mAut.getString("Label.Address.Location"));
		gbl.setConstraints(lbl, new GBC(0,1).setIns(2).setAnchor(GBC.EAST));
		pnlAdr.add(lbl);
		_txfLocality = new JTextField();
		gbl.setConstraints(_txfLocality, new GBC(1,1).setIns(2).setGridSpan(2,1).setFill(GBC.HORIZONTAL).setWeight(1., 0.));
		pnlAdr.add(_txfLocality);
		lbl = new JLabel(mAut.getString("Label.Address.Region"));
		gbl.setConstraints(lbl, new GBC(0,2).setIns(2).setAnchor(GBC.EAST));
		pnlAdr.add(lbl);
		_txfRegion = new JTextField();
		gbl.setConstraints(_txfRegion, new GBC(1,2).setIns(2).setGridSpan(2,1).setFill(GBC.HORIZONTAL).setWeight(1., 0.));
		pnlAdr.add(_txfRegion);
		lbl = new JLabel(mAut.getString("Label.Address.Place"));
		gbl.setConstraints(lbl, new GBC(0,3).setIns(2).setAnchor(GBC.EAST));
		pnlAdr.add(lbl);
		_txaAddress = new JTextArea();
		JScrollPane sp = new JScrollPane(_txaAddress);
		gbl.setConstraints(sp, new GBC(1,3).setIns(2).setGridSpan(2,1).setFill(GBC.BOTH) .setWeight(1., 0.5));
		pnlAdr.add(sp);
		lbl = new JLabel(mAut.getString("Label.Address.Note"));
		gbl.setConstraints(lbl, new GBC(0,4).setIns(2).setAnchor(GBC.EAST));
		pnlAdr.add(lbl);
		_txaNote = new JTextArea();
		sp = new JScrollPane(_txaNote);
		gbl.setConstraints(sp, new GBC(1,4).setIns(2).setGridSpan(2,1).setFill(GBC.BOTH).setWeight(1., 0.5));
		pnlAdr.add(sp);
		
		_spn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(_tab), pnlAdr);
		this.add(_spn);
		
		_tab.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_tab.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				DefaultListSelectionModel dlsm = (DefaultListSelectionModel) e.getSource();
				if (e.getValueIsAdjusting() && dlsm != null)
				{
					ArrayList<tAdr> aadr = ((tPerson)mObj).getAddrColl();
					if (_prevAdrInd >= 0 && _prevAdrInd < aadr.size())
						_getData(aadr.get(_prevAdrInd));
					_prevAdrInd = dlsm.getLeadSelectionIndex();
					if (_prevAdrInd < aadr.size())
					{
						_setData(aadr.get(_prevAdrInd));
						//_prevAdrInd = sr;
					}
					else
					{
						_setData(null);
					}
				}
			}
		});
	}

	private void _setData(tAdr aAdr)
	{
		if (aAdr != null)
		{
			_txfIndex.setText(aAdr.getIndex());
			_cboCountry.setSelectedItem(
					fRefBook.FindRBNodeByIDInComboBoxModel(
							(DefaultComboBoxModel<rbNode>)_cboCountry.getModel(), 
							aAdr.getCountry()
			));
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
		rbNode nd = (rbNode)_cboCountry.getSelectedItem();
		if (nd != null)
			aAdr.setCountry(nd.getId());
		else
			aAdr.setCountry(0);
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
		{
			_tab.getSelectionModel().setSelectionInterval(0, 0);
			_setData(prs.getAddrColl().get(0));
			_prevAdrInd = 0;
		}
		else
			_setData(null);
		
		_tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Preferences node = Preferences.userRoot().node(mPrefPath);
		TableTools.SetColumnsWidthFromString(_tab, node.get("TabColWidth_Address", CC.STR_EMPTY));
		_spn.setDividerLocation(node.getInt("SplitAddress", 200));
	}
	
	protected void mSave()
	{
		ArrayList<tAdr> aadr = ((tPerson)mObj).getAddrColl();
		if (_prevAdrInd >= 0 && _prevAdrInd < aadr.size())
			_getData(aadr.get(_prevAdrInd));	
		Preferences node = Preferences.userRoot().node(mPrefPath);
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
