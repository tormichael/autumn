package tor.java.autumn.IntFrame;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tor.java.autumn.Autumn;
import tor.java.thirteen.card.tObj;
import JCommonTools.GBC;

import com.toedter.calendar.JDateChooser;

public class infOrg extends infBase 
{

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

//		// first row
//		JLabel lblLastName = new JLabel(mAut.getString("Label.Person.LastName"));
//		gbl.setConstraints(lblLastName, gbc.setGridXY(0,0).setGridSpan(1, 1).setWeight(0.0, 0.0));
//		pnlMain.add(lblLastName);
//		_txtLastName = new JTextField(); 
//		gbl.setConstraints(_txtLastName, gbc.setGridXY(1,0).setGridSpan(1, 1).setWeight(0.3, 0.0));
//		pnlMain.add(_txtLastName);
//		// second row
//		JLabel lblFirstName = new JLabel(mAut.getString("Label.Person.FirstName"));
//		gbl.setConstraints(lblFirstName, gbc.setGridXY(0,1).setGridSpan(1, 1).setWeight(0.0, 0.0));
//		pnlMain.add(lblFirstName);
//		_txtFirstName = new JTextField(); 
//		gbl.setConstraints(_txtFirstName, gbc.setGridXY(1,1).setGridSpan(1, 1).setWeight(0.3, 0.0));
//		pnlMain.add(_txtFirstName);
//		// third row
//		JLabel lblPatronymicName = new JLabel(mAut.getString("Label.Person.PatronymicName"));
//		gbl.setConstraints(lblPatronymicName, gbc.setGridXY(0,2).setGridSpan(1, 1).setWeight(0.0, 0.0));
//		pnlMain.add(lblPatronymicName);
//		_txtPatronymicName = new JTextField(); 
//		gbl.setConstraints(_txtPatronymicName, gbc.setGridXY(1,2).setGridSpan(1, 1).setWeight(0.3, 0.0));
//		pnlMain.add(_txtPatronymicName);
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
