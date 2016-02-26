package tor.java.autumn.IntFrame;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tor.java.autumn.Autumn;
import tor.java.thirteen.card.tObj;
import JCommonTools.CodeText;
import JCommonTools.GBC;

import com.toedter.calendar.JDateChooser;

public class infOrg extends infBase 
{
	private JTextField 						_txtSName;
	private JComboBox<CodeText> _cboOwnership;
	private JTextArea 						_txtFName;
	
	public infOrg(Autumn aAut, String aPrefPath, tObj aOrg)
	{
		super(aAut, aPrefPath, aOrg);
		mObj = aOrg;
		
		GridBagLayout gbl = new GridBagLayout();
		GBC gbc = new GBC(0,0);
		gbc.setInsets(2, 2, 2, 2);
		gbc.setFill(GBC.HORIZONTAL);
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(gbl);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// first row
		JLabel lblLastName = new JLabel(mAut.getString("Label.Org.ShortName"));
		gbl.setConstraints(lblLastName, gbc.setGridXY(0,0));
		pnlMain.add(lblLastName);
		_txtSName = new JTextField(); 
		gbl.setConstraints(_txtSName, gbc.setGridXY(1,0));
		pnlMain.add(_txtSName);
		// second row
		JLabel lblFirstName = new JLabel(mAut.getString("Label.Org.Ownership"));
		gbl.setConstraints(lblFirstName, gbc.setGridXY(0,1));
		pnlMain.add(lblFirstName);
		_cboOwnership = new JComboBox<CodeText>(); 
		gbl.setConstraints(_cboOwnership, gbc.setGridXY(1,1));
		pnlMain.add(_cboOwnership);
		// third row
		JLabel lblPatronymicName = new JLabel(mAut.getString("Label.Org.FullName"));
		gbl.setConstraints(lblPatronymicName, gbc.setGridXY(0,2));
		pnlMain.add(lblPatronymicName);
		_txtFName = new JTextArea();
		JScrollPane jsp = new JScrollPane(_txtFName); 
		gbl.setConstraints(jsp, gbc.setGridXY(1,2).setFill(GBC.BOTH).setWeight(1.0, 1.0));
		pnlMain.add(jsp);
//		// fourth row
//		JLabel lblBirthday = new JLabel(mAut.getString("Label.Person.Birthday"));
//		gbl.setConstraints(lblBirthday, gbc.setGridXY(0,3).setGridSpan(1, 1).setWeight(0.0, 0.0));
//		pnlMain.add(lblBirthday);
//		_txtUDate = new JTextFieldUDateEditor(); 
//		_dtBirthday = new JDateChooser(_txtUDate);
//		//_txtBirthday = new JTextField(); 
//		gbl.setConstraints(_dtBirthday, gbc.setGridXY(1,3).setGridSpan(1, 1).setWeight(0.3, 0.0));
//		pnlMain.add(_dtBirthday);
		
		
		this.add(pnlMain);
	}

}
