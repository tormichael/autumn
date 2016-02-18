package tor.java.autumn;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JToggleButton;

import tor.java.autumn.IntFrame.infAddress;
import tor.java.autumn.IntFrame.infBase;
import tor.java.autumn.IntFrame.infFIO;
import tor.java.autumn.IntFrame.infPhones;

public class fOrg extends fObject 
{
	//private infFIO						_frmFIO;
	private infPhones				_frmPhones;
	private infAddress				_frmAddress;
	
	//private JToggleButton 		_btnViewFIO;
	private JToggleButton 		_btnViewPhone;
	private JToggleButton 		_btnViewAddress;

	public fOrg(Autumn aAut)
	{
		super(aAut);
		
		setPreferencePath("fOrg");

		//_btnViewFIO = new JToggleButton(actViewFIO);
		//actViewFIO.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/source_h.png"));
		//mTBar.add(_btnViewFIO, 0);

		_btnViewPhone = new JToggleButton(actViewPhones);
		actViewPhones.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/source_moc.png"));
		mTBar.add(_btnViewPhone, 1);
		
		_btnViewAddress = new JToggleButton(actViewAddress);
		actViewAddress.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/message.png"));
		mTBar.add(_btnViewAddress, 2);
	}

	protected void mCreatInfXXX(String aName)
	{
		if (aName.equals(infBase.getClassNameOnly(infFIO.class)))
		{
			//_btnViewFIO.doClick();
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
	
//	Action actViewFIO = new AbstractAction() 
//	{
//		@Override
//		public void actionPerformed(ActionEvent arg0) 
//		{
//			_frmFIO = (infFIO)showHideFrm(_frmFIO, infFIO.class.getName(), _btnViewFIO);
//		}
//	};
	
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
