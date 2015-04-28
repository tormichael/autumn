package tor.java.autumn.IntFrame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.RuleBasedCollator;
import java.util.prefs.Preferences;

import javax.swing.CellRendererPane;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import JCommonTools.CC;
import JCommonTools.TableTools;
import JCommonTools.RefBook.RBComboBoxCellEdit;
import JCommonTools.RefBook.RefBook;
import JCommonTools.RefBook.fRefBook;
import JCommonTools.RefBook.rbNode;
import tor.java.autumn.Autumn;
import tor.java.autumn.PhoneTableModel;
import tor.java.thirteen.card.tPerson;

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
