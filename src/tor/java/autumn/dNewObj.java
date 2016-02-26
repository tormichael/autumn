package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//import sun.awt.HorizBagLayout;
import tor.java.thirteen.card.tObj;
import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;
import JCommonTools.GBC;

public class dNewObj extends JDialog 
{
	private Autumn _aut;
	
	private boolean _isResultOk;
	
	private JTextField	_txtName;
	private JComboBox<CodeText>	_cboType;
	
	private Action	_actOk;
	public void setActionOk(Action aAct)
	{
		_actOk = aAct;
	}
	
	public String getObjectName()
	{
		return _txtName.getText();
	}
	
	public int getObjectType()
	{
		return ((CodeText)_cboType.getSelectedItem()).getCode();
	}
	
	public dNewObj (Autumn aAut)
	{
		_aut  = aAut;
		_isResultOk = false;
		_actOk = null;
		
		this.setTitle(_aut.getString("Titles.dNewObj"));
		this.setModal(true);
		//this.setModalityType(ModalityType.TOOLKIT_MODAL);
		this.setSize(350, 180);
		
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnl =new JPanel(gbl);
		// first row
		JLabel lbl = new JLabel(_aut.getString("Label.NewObj.Name"));
		gbl.setConstraints(lbl, new GBC(0,0).setIns(5).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_txtName = new JTextField();
		gbl.setConstraints(_txtName, new GBC(1,0).setIns(5).setGridSpan(2, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_txtName);
		// next row
		lbl = new JLabel(_aut.getString("Label.NewObj.Type"));
		gbl.setConstraints(lbl, new GBC(0,1).setIns(5).setAnchor(GBC.EAST));
		pnl.add(lbl);
		_cboType = new JComboBox<CodeText>();
		gbl.setConstraints(_cboType, new GBC(1,1).setIns(5).setGridSpan(2, 1).setFill(GBC.HORIZONTAL).setWeight(1.0, 0.0));
		pnl.add(_cboType);
		
		JButton cmdOk = new JButton(actOk);
		cmdOk.setText(_aut.getString("Button.NewObj.Ok"));
		gbl.setConstraints(cmdOk, new GBC(1,2).setIns(10).setWeight(0.7,0.0).setAnchor(GBC.EAST));
		pnl.add(cmdOk);
		JButton cmdCancel = new JButton(actCancel);
		cmdCancel.setText(_aut.getString("Button.NewObj.Cancel"));
		gbl.setConstraints(cmdCancel, new GBC(2,2).setIns(10).setAnchor(GBC.EAST));
		pnl.add(cmdCancel);
		
		this.add(pnl, BorderLayout.CENTER);		
	
		DefaultComboBoxModel<CodeText> cbm = new DefaultComboBoxModel<CodeText>();
		for (CodeText ct : _aut.getArrObjType() )
			cbm.addElement(ct);
		_cboType.setModel(cbm);
	
		LoadProgramPreference ();
		
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				SaveProgramPreference();
				super.windowClosing(e);
			}
		});
		
	}
	
	public Action actOk = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_actOk != null)
			{
				_actOk.actionPerformed(new ActionEvent(dNewObj.this, 0, CC.STR_EMPTY));
				//setVisible(false);
				dNewObj.this.dispatchEvent(new WindowEvent(dNewObj.this, WindowEvent.WINDOW_CLOSING));
			}
			else
			{
				_txtName.setText("ERROR! - action Ok undefined!");
			}
		}
	}; 
	
	public Action actCancel = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			setVisible(false);
		}
	}; 

	
	private void LoadProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/dNewObj");
		AsRegister.LoadWindowLocation(node, this);
		AsRegister.LoadWindowSize(node, this);
		int lastTypeCode = node.getInt("LastType", tObj.getType());
		for (int ii=0; ii < _cboType.getModel().getSize(); ii++ )
		{
			if (_cboType.getModel().getElementAt(ii).getCode() == lastTypeCode)
			{
				_cboType.getModel().setSelectedItem(_cboType.getModel().getElementAt(ii));
				break;
			}
		}
	}

	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/dNewObj");
		AsRegister.SaveWindowLocation(node, this);
		AsRegister.SaveWindowSize(node, this);
		CodeText ct = (CodeText)_cboType.getSelectedItem();
		if (ct != null)
		{
			node.putInt("LastType", ct.getCode());
		}
	}
		
}
