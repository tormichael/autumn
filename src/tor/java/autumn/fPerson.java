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
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import tor.java.thirteen.card.tPerson;
import JCommonTools.AsRegister;
import JCommonTools.FileNameTools;

public class fPerson extends fObject 
{
	//private tPerson				_prs;

	private infFIO				_frmFIO;
	private infPhones			_frmPhones;
	private infAddress			_frmAddress;
	
	private JToggleButton 		_btnViewFIO;
	private JToggleButton 		_btnViewPhone;
	private JToggleButton 		_btnViewAddress;
	
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
		
		mPrefPath = "fPerson";
		//_prs = null;
	
		_btnViewFIO = new JToggleButton(actViewFIO);
		actViewFIO.putValue(Action.SMALL_ICON, mAut.getImageIcon("new.png"));
		mTBar.add(_btnViewFIO, 5);

		_btnViewPhone = new JToggleButton(actViewPhones);
		actViewPhones.putValue(Action.SMALL_ICON, mAut.getImageIcon("new.png"));
		mTBar.add(_btnViewPhone, 6);
		
		_btnViewAddress = new JToggleButton(actViewAddress);
		actViewAddress.putValue(Action.SMALL_ICON, mAut.getImageIcon("new.png"));
		mTBar.add(_btnViewAddress, 7);
		
	}
	
	Action actLoad = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e){
			
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
		};
		
	};
	
	Action actSave = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Save();
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
	};

	Action actViewFIO = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (_frmFIO == null)
			{
				_frmFIO = new infFIO(mAut, "frmFIO", getPerson());
				_frmFIO.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				mALInF.add(_frmFIO);
 				_frmFIO.Load();
			}
			if (_btnViewFIO.isSelected())
			{
				mDesktop.add(_frmFIO, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				mDesktop.remove(_frmFIO);
			}
			_frmFIO.setVisible(_btnViewFIO.isSelected());
			mDesktop.repaint();
		}
	};
	
	Action actViewPhones = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (_frmPhones == null)
			{
				_frmPhones = new infPhones(mAut, "frmPhones", getPerson());
				_frmPhones.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				mALInF.add(_frmPhones);
 				_frmPhones.Load();
			}
			if (_btnViewPhone.isSelected())
			{
				mDesktop.add(_frmPhones, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				mDesktop.remove(_frmPhones);
			}
			_frmPhones.setVisible(_btnViewPhone.isSelected());
			mDesktop.repaint();
		}
	};
	
	Action actViewAddress = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (_frmAddress == null)
			{
				_frmAddress = new infAddress(mAut, "frmAddress", getPerson());
				_frmAddress.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				mALInF.add(_frmAddress);
 				_frmAddress.Load();
			}
			if (_btnViewAddress.isSelected())
			{
				mDesktop.add(_frmAddress, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				mDesktop.remove(_frmAddress);
			}
			_frmAddress.setVisible(_btnViewAddress.isSelected());
			mDesktop.repaint();
		}
	};
	
	protected void UpdateLanguage()
	{
		super.UpdateLanguage();
		
		setTitle(mAut.getString("Titles.wPerson"));
	}
	
	protected void mLoadPreference(Preferences aNode)
	{
		super.mLoadPreference(aNode);
		
		if (aNode.getBoolean("isFIOShow", false))
			_btnViewFIO.doClick();
		if (aNode.getBoolean("isPhoneShow", false))
			_btnViewPhone.doClick();
		if (aNode.getBoolean("isAddressShow", false))
			_btnViewAddress.doClick();
	}
	
	protected void mSavePreference(Preferences aNode)
	{
		super.mSavePreference(aNode);
		
		aNode.putBoolean("isFIOShow", _btnViewFIO.isSelected());
		aNode.putBoolean("isPhoneShow", _btnViewPhone.isSelected());
		aNode.putBoolean("isAddressShow", _btnViewAddress.isSelected());
	}
}
