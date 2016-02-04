package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import tor.java.autumn.IntFrame.infAddress;
import tor.java.autumn.IntFrame.infBase;
import tor.java.autumn.IntFrame.infFIO;
import tor.java.autumn.IntFrame.infImages;
import tor.java.autumn.IntFrame.infNote;
import tor.java.autumn.IntFrame.infPhones;
import tor.java.thirteen.card.tObj;
import tor.java.thirteen.card.tPerson;
import JCommonTools.AsRegister;
import JCommonTools.FileNameTools;

public class fPerson extends fObject 
{
	//public final static String FRM_FIO_NAME = "frmFIO";
	//public final static String FRM_PHONES_NAME = "frmPhones";
	//public final static String FRM_ADDRESS_NAME = "frmAddress";
	//public final static String FRM__NAME = "frm";
	
	//private tPerson				_prs;

	private JMenuItem 			_mnuOptReplaceFLName;
	
	private infFIO						_frmFIO;
	private infPhones				_frmPhones;
	private infAddress				_frmAddress;
	
	private JToggleButton 		_btnViewFIO;
	private JToggleButton 		_btnViewPhone;
	private JToggleButton 		_btnViewAddress;
	private JButton 					_btnReplaceFLName;
	
	protected void setPerson(tPerson aPrs)
	{
		mObj = aPrs;
	}
	protected tPerson getPerson()
	{
		if (mObj instanceof tPerson)
			return (tPerson)mObj;
		else
			return null;
	}
	
	public fPerson(Autumn aAut)
	{
		super(aAut);
		
		setPreferencePath("fPerson");
		//_prs = null;
	
		//_mnuFileSave.setAction(actSave);
//		mBtnSave.setAction(actSave);
//		actSave.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/save.png"));
		
		_btnViewFIO = new JToggleButton(actViewFIO);
		actViewFIO.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/pages/source_h.png"));
		mTBar.add(_btnViewFIO, 0);

		_btnViewPhone = new JToggleButton(actViewPhones);
		actViewPhones.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/pages/source_moc.png"));
		mTBar.add(_btnViewPhone, 1);
		
		_btnViewAddress = new JToggleButton(actViewAddress);
		actViewAddress.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/pages/message.png"));
		mTBar.add(_btnViewAddress, 2);

		_mnuOptReplaceFLName = new JMenuItem(actReplaceFLName);
		mMnuOption.add(_mnuOptReplaceFLName);
//		mTBar.add(new JSeparator());
//		_btnReplaceFLName = new JButton(actReplaceFLName);
//		actReplaceFLName.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/pages/tar.png"));
//		mTBar.add(_btnReplaceFLName);
		
	}

	protected void loadFromFile()
	{
		JFileChooser dlg = new JFileChooser();
		if (mCurrDir != null && mCurrDir.length() > 0)
			dlg.setCurrentDirectory(new File(mCurrDir));
		dlg.addChoosableFileFilter(new FileNameExtensionFilter("Person", tPerson.FILE_EXTENTION));
		dlg.addChoosableFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
		//dlg.setFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
		dlg.setMultiSelectionEnabled(false);
		if (dlg.showOpenDialog(fPerson.this) == JFileChooser.APPROVE_OPTION)
		{
			tPerson prs = null;
			if (dlg.getSelectedFile().getName().indexOf(".vcf") > 0)
			{
				PersonalVCard pvc = new PersonalVCard(mAut);
				prs = pvc.LoadOneVCard(dlg.getSelectedFile().getPath());
			}
			else
			{
				try
				{ 
					prs = tPerson.Load(dlg.getSelectedFile().getPath());
				}
				catch (Exception ex )
				{
					mAut.ShowError(ex.getMessage());
				}
			}
			
			if (prs != null)
			{
				if (mAut.getRegister() == null)
					setPerson(prs);
				else if (mAut.getRegister().ReplaceObject(mObj, prs))
				{
					setPerson(prs);
					if (UpdateRegisterShow != null)
						UpdateRegisterShow.actionPerformed(new ActionEvent(prs, 0, null));
				}
			}
			Show(mObj);
			mCurrDir = dlg.getSelectedFile().getParent();
		}
	}
	
	protected void saveToFile()
	{
		super.saveToFile();
		JFileChooser dlg = new JFileChooser();
		if (mCurrDir != null && mCurrDir.length() > 0)
			dlg.setCurrentDirectory(new File(mCurrDir));
		dlg.setFileFilter(new FileNameExtensionFilter("Person", tPerson.FILE_EXTENTION));
		dlg.setMultiSelectionEnabled(false);
		if (dlg.showSaveDialog(fPerson.this) == JFileChooser.APPROVE_OPTION)
		{
			String err = getPerson().Save(FileNameTools.AddExtensionIfNone(dlg.getSelectedFile().getPath(), tPerson.FILE_EXTENTION));
			if (err != null && err.length() > 0)
				mAut.ShowError(err);
			
			mCurrDir = dlg.getSelectedFile().getParent();
		}
	}
	
	protected void mCreatInfXXX(String aName)
	{
		if (aName.equals(infBase.getClassNameOnly(infFIO.class)))
		{
			_btnViewFIO.doClick();
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
	
	Action actViewFIO = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			_frmFIO = (infFIO)showHideFrm(_frmFIO, infFIO.class.getName(), _btnViewFIO);
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
	
	Action actReplaceFLName = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			getPerson().ReplaceFirstLastName();
			if (_frmFIO != null)
				_frmFIO.Load();
		}
	};
	
	protected void UpdateLanguage()
	{
		super.UpdateLanguage();
		
		setTitle(mAut.getString("Titles.wPerson"));
		_mnuOptReplaceFLName.setText(mAut.getString("Menu.Options.ReplaceFLName"));
	}
	
	protected void mLoadPreference(Preferences aNode)
	{
		super.mLoadPreference(aNode);
		
//		if (aNode.getBoolean("isFIOShow", false))
//			_btnViewFIO.doClick();
//		if (aNode.getBoolean("isPhoneShow", false))
//			_btnViewPhone.doClick();
//		if (aNode.getBoolean("isAddressShow", false))
//			_btnViewAddress.doClick();
	}
	
	protected void mSavePreference(Preferences aNode)
	{
		super.mSavePreference(aNode);
		
//		aNode.putBoolean("isFIOShow", _btnViewFIO.isSelected());
//		aNode.putBoolean("isPhoneShow", _btnViewPhone.isSelected());
//		aNode.putBoolean("isAddressShow", _btnViewAddress.isSelected());
	}
}
