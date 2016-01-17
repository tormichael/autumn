package tor.java.autumn.IntFrame;

import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import JCommonTools.GBC;

import com.toedter.calendar.JDateChooser;

import tor.java.autumn.Autumn;
import tor.java.thirteen.card.tPerson;

public class infFIO extends infBase 
{
	private JTextField 	_txtFirstName;
	private JTextField 	_txtPatronymicName;
	private JTextField 	_txtLastName;
	private JTextFieldUDateEditor _txtUDate;
	private JDateChooser	_dtBirthday;

	public infFIO(Autumn aAut, String aName, tPerson aPerson)
	{
		super(aAut, aName, aPerson);
		mObj = aPerson;
		
		GridBagLayout gbl = new GridBagLayout();
		GBC gbc = new GBC(0,0);
		gbc.setInsets(2, 2, 2, 2);
		gbc.setFill(GBC.HORIZONTAL);
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(gbl);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// first row
		JLabel lblLastName = new JLabel(mAut.getString("Label.Person.LastName"));
		gbl.setConstraints(lblLastName, gbc.setGridXY(0,0).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblLastName);
		_txtLastName = new JTextField(); 
		gbl.setConstraints(_txtLastName, gbc.setGridXY(1,0).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_txtLastName);
		// second row
		JLabel lblFirstName = new JLabel(mAut.getString("Label.Person.FirstName"));
		gbl.setConstraints(lblFirstName, gbc.setGridXY(0,1).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblFirstName);
		_txtFirstName = new JTextField(); 
		gbl.setConstraints(_txtFirstName, gbc.setGridXY(1,1).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_txtFirstName);
		// third row
		JLabel lblPatronymicName = new JLabel(mAut.getString("Label.Person.PatronymicName"));
		gbl.setConstraints(lblPatronymicName, gbc.setGridXY(0,2).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblPatronymicName);
		_txtPatronymicName = new JTextField(); 
		gbl.setConstraints(_txtPatronymicName, gbc.setGridXY(1,2).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_txtPatronymicName);
		// fourth row
		JLabel lblBirthday = new JLabel(mAut.getString("Label.Person.Birthday"));
		gbl.setConstraints(lblBirthday, gbc.setGridXY(0,3).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblBirthday);
		_txtUDate = new JTextFieldUDateEditor(); 
		_dtBirthday = new JDateChooser(_txtUDate);
		//_txtBirthday = new JTextField(); 
		gbl.setConstraints(_dtBirthday, gbc.setGridXY(1,3).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_dtBirthday);
		
		//_dtBirthday.setDateFormatString("");

		//_dtBirthday;
		
		this.add(pnlMain);
	}
	
	
	public void Load()
	{
		tPerson prs = (tPerson)mObj;
		if (prs != null)
		{
			_txtLastName.setText(prs.getLName());
			_txtFirstName.setText(prs.getFName());
			_txtPatronymicName.setText(prs.getPName());
			//_dtBirthday.setCalendar(prs.getDBCalendar());
			//_txtUDate.setDate(prs.getDBDate());
			_txtUDate.setDate(prs.getBDay());
		}
	}
	
	protected void mSave()
	{
		tPerson prs = (tPerson)mObj;
		if (prs != null)
		{
			prs.setLName(_txtLastName.getText());
			prs.setFName(_txtFirstName.getText());
			prs.setPName(_txtPatronymicName.getText());
			//prs.setBDDate(_dtBirthday.getDate());
			//prs.setBDDate(_txtUDate.getDate());
			prs.setBDay(_txtUDate.getDateYYYMMDD());
		}
	}
}
