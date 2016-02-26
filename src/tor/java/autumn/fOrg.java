package tor.java.autumn;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JToggleButton;

import tor.java.autumn.IntFrame.infAddress;
import tor.java.autumn.IntFrame.infBase;
import tor.java.autumn.IntFrame.infFIO;
import tor.java.autumn.IntFrame.infOrg;
import tor.java.autumn.IntFrame.infPhones;

public class fOrg extends fObject 
{
	private infOrg						_frmOrg;
	private infPhones				_frmPhones;
	private infAddress				_frmAddress;
	
	private JToggleButton 		_btnViewOrg;
	private JToggleButton 		_btnViewPhone;
	private JToggleButton 		_btnViewAddress;

	public fOrg(Autumn aAut)
	{
		super(aAut);
		
		setPreferencePath("fOrg");

		_btnViewOrg = new JToggleButton(actViewOrg);
		actViewOrg.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/source_h.png"));
		mTBar.add(_btnViewOrg, 0);

		_btnViewPhone = new JToggleButton(actViewPhones);
		actViewPhones.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/source_moc.png"));
		mTBar.add(_btnViewPhone, 1);
		
		_btnViewAddress = new JToggleButton(actViewAddress);
		actViewAddress.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/message.png"));
		mTBar.add(_btnViewAddress, 2);
	}

	protected void mCreatInfXXX(String aName)
	{
		if (aName.equals(infBase.getClassNameOnly(infOrg.class)))
		{
			_btnViewOrg.doClick();
		}
		else if (aName.equals(infBase.getClassNameOnly(infAddress.class)))
		{
			_btnViewAddress.doClick();
		}
		else if (aName.equals(infBase.getClassNameOnly(infPhones.class)))
		{
			_btnViewPhone.doClick();
		}
		else
		{
			super.mCreatInfXXX(aName);
		}
	}
	
	Action actViewOrg = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			_frmOrg = (infOrg)showHideFrm(_frmOrg, infOrg.class.getName(), _btnViewOrg);
		}
	};
	
	Action actViewPhones = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			_frmPhones = (infPhones)showHideFrm(_frmPhones, infPhones.class.getName(), _btnViewPhone);
		}
	};
	
	Action actViewAddress = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			_frmAddress = (infAddress)showHideFrm(_frmAddress, infAddress.class.getName(), _btnViewAddress);
		}
	};
	
	protected void UpdateLanguage()
	{
		super.UpdateLanguage();
		
		setTitle(mAut.getString("Titles.wOrg"));
	}
	
}
