package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import tor.java.autumn.pNavMode.pNavMode;
import tor.java.autumn.pNavMode.pNavModeAlphabet;
import tor.java.autumn.pNavMode.pNavModeList;
import tor.java.thirteen.card.tObj;
import tor.java.thirteen.card.tOrg;
import tor.java.thirteen.card.tPerson;
import tor.java.thirteen.card.tRegister;
import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;
import JCommonTools.FileNameTools;
import JCommonTools.Tools;
import JCommonTools.Dialog.dAbout;
import JCommonTools.Dialog.dPassword;
import JCommonTools.Param.BookParam;
import JCommonTools.Param.fBookParam;

public class fNavigator extends JFrame 
{
	private Autumn _aut;
	
	private JMenu _mnuFile;
	private JMenuItem _mnuFileNew;
	private JMenuItem _mnuFileLoad;
	private JMenuItem _mnuFileAdd;
	private JMenuItem _mnuFileSave;
	private JMenuItem _mnuFileSaveAs;
	private JMenuItem _mnuFileSaveAndCrypt;
	private JMenuItem _mnuFilePrint;
	private JMenuItem _mnuFileProperties;
	private JMenuItem _mnuFileRefbook;
	private JMenuItem _mnuFileExit;
	private JMenu _mnuRecord;
	private JMenuItem _mnuRecordNew;
	private JMenuItem _mnuRecordDelete;
	private JMenu _mnuOptions;
	private JMenuItem _mnuOptReplaceFLName;
	private JMenu _mnuHelp;
	private JMenuItem _mnuHelpAbout;
	private JLabel _lblMode;
	private JLabel _sbiMain;
	private JComboBox<CodeText> _cboMode;
	
	private JPanel _pnlNavigator;
	private pNavMode _currNavMode;
	
	private tObj _currObj;
	private fObject _frm;
	private fObject _frmObject;
	private fObject _frmPerson;
	private fObject _frmOrg;
	
	private String _currFileName;
	private String _lastAddFileName;
	private boolean _isVCard;
	
	public void setCurrentFileName(String aFN)
	{
		_currFileName = aFN;
		if (_currFileName != null && _currFileName.length() > 0)
		{
			this.setTitle(String.format(_aut.getString("Titles.fNavigator.CurrentName"), aFN));
		}
		else
		{
			this.setTitle(_aut.getString("Titles.fNavigator"));
		}
	}
	
	public fNavigator(Autumn aAut)
	{
		_aut = aAut;
		_currObj = null;
		_frm = null;
		_frmObject = null;
		_frmPerson = null;
		_currFileName = null;
		_lastAddFileName = null;
		_isVCard = false;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setIconImage(_aut.getImageInRscImg("icons/autumn2.png"));

		/**
		 * M E N U
		 */
		JMenuBar mnuBar = new JMenuBar();
		setJMenuBar(mnuBar);
		_mnuFile = new JMenu();
		mnuBar.add(_mnuFile);
		//JMenuItem mnuFileCreate = new JMenuItem(actCreate);
		//mnuFileCreate.setText( _aut.getString("Menu.Person.File.Create"));
		//mnuFile.add(mnuFileCreate);
		_mnuFileNew = new JMenuItem(actNew);
		_mnuFile.add(_mnuFileNew);
		_mnuFile.addSeparator();
		_mnuFileLoad = new JMenuItem(actLoad);
		_mnuFile.add(_mnuFileLoad);
		_mnuFileAdd = new JMenuItem(actAddFiles);
		_mnuFile.add(_mnuFileAdd);
		_mnuFile.addSeparator();
		_mnuFileSave = new JMenuItem(actSave);
		_mnuFile.add(_mnuFileSave);
		_mnuFileSaveAs = new JMenuItem(actSaveAs);
		_mnuFile.add(_mnuFileSaveAs);
		_mnuFileSaveAndCrypt = new JMenuItem(actSaveAndCrypt);
		_mnuFile.add(_mnuFileSaveAndCrypt);
		_mnuFile.addSeparator();
		_mnuFilePrint = new JMenuItem();
		_mnuFile.add(_mnuFilePrint);
		_mnuFile.addSeparator();
		_mnuFileProperties = new JMenuItem(actProperties);
		_mnuFile.add(_mnuFileProperties);
		_mnuFileRefbook = new JMenuItem(actRefbook);
		_mnuFile.add(_mnuFileRefbook);
		_mnuFile.addSeparator();
		_mnuFileExit = new JMenuItem(actExit);
		_mnuFile.add(_mnuFileExit);
		_mnuRecord = new JMenu();
		mnuBar.add(_mnuRecord);
		_mnuRecordNew = new JMenuItem(actRecordNew);
		_mnuRecord.add(_mnuRecordNew);
		_mnuRecordDelete = new JMenuItem(actRecordDelete);
		_mnuRecord.add(_mnuRecordDelete);
		_mnuOptions = new JMenu();
		mnuBar.add(_mnuOptions);
		_mnuHelp = new JMenu();
		_mnuOptReplaceFLName = new JMenuItem(actOptReplaceFLName);
		_mnuOptions.add(_mnuOptReplaceFLName);
		mnuBar.add(_mnuHelp);
		_mnuHelpAbout = new JMenuItem(new ActionAbout());
		_mnuHelp.add(_mnuHelpAbout);
		/**
		 *   T O O L S   B A R
		 */
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.NORTH);
		//actCreate.putValue(Action.SMALL_ICON, CreateIcon("new.png", Start.TOOL_BAR_ICON_SIZE));
		//bar.add(actCreate);
		actLoad.putValue(Action.SMALL_ICON, _aut.getImageIcon("open.png"));
		actLoad.putValue(Action.SHORT_DESCRIPTION , _aut.getString("ToolsBar.ShortDescription.FileLoad"));
		bar.add(actLoad);
		actSave.putValue(Action.SMALL_ICON, _aut.getImageIcon("save.png"));
		actSave.putValue(Action.SHORT_DESCRIPTION , _aut.getString("ToolsBar.ShortDescription.FileSave"));
		bar.add(actSave);
		bar.addSeparator();
		actRecordNew.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		actRecordNew.putValue(Action.SHORT_DESCRIPTION , _aut.getString("ToolsBar.ShortDescription.RecordNew"));
		bar.add(actRecordNew);
		actRecordDelete.putValue(Action.SMALL_ICON, _aut.getImageIcon("delete.png"));
		actRecordDelete.putValue(Action.SHORT_DESCRIPTION , _aut.getString("ToolsBar.ShortDescription.RecordDelete"));
		bar.add(actRecordDelete);
		bar.addSeparator();
		actProperties.putValue(Action.SMALL_ICON, _aut.getImageIcon("configure.png"));
		actRefbook.putValue(Action.SMALL_ICON, _aut.getImageIcon("refbook.png"));
		_lblMode = new JLabel();
		bar.add(_lblMode);
		bar.addSeparator();
		_cboMode = new JComboBox<CodeText>();
		bar.add(_cboMode);
		bar.addSeparator(new Dimension(30, 20));
		/**
		 * C O N T E N T S
		 */
		_currNavMode = null;
		_pnlNavigator = new JPanel(new BorderLayout());
		add(_pnlNavigator, BorderLayout.CENTER);
		
		
		/**
		 * S T A T U S   B A R
		 */
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createRaisedBevelBorder());
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(statusBar, BorderLayout.SOUTH);
		
		_sbiMain = new JLabel();
		_sbiMain.setText("Welcome!");
		_sbiMain.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.add(_sbiMain);

		_reloadParam();		
	
		LoadProgramPreference ();
		
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e) 
			{
				SaveProgramPreference();
				if (_frm != null)
				{
					_frm.Close();
				}
				super.windowClosing(e);
			}
		}); 
		
		_cboMode.addActionListener(new ActionListener() 
		{
						
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				_showMode();
			}
		});

	}

	private void _showMode()
	{
		if (_currNavMode != null)
		{
			_pnlNavigator.remove(_currNavMode);
			_pnlNavigator.revalidate();
			_pnlNavigator.repaint();
			_currNavMode = null;
		}
		if (_cboMode.getSelectedItem() instanceof CodeText)
		{
			switch (((CodeText)_cboMode.getSelectedItem()).getCode())
			{
			case 1: // alphabet
				_currNavMode = new pNavModeAlphabet(_aut);
				break;
			case 2: // list
				_currNavMode = new pNavModeList(_aut);
				break;
	//		case 2: // group
	//			_currNavMode = new pNavModeGroup(_aut);
	//			break;
	//		case 3: // type
	//			_currNavMode = new pNavModeType(_aut);
	//			break;
			}
		}
		if (_currNavMode != null)
		{
			_pnlNavigator.add(_currNavMode, BorderLayout.CENTER);
			_pnlNavigator.revalidate();
			_pnlNavigator.repaint();
			_currNavMode.ShowRegister();
			_currNavMode.setActionObjectSelected(actObjectSelected);
		}
		
	}
	
	private class ActionAbout extends AbstractAction
	{
		public ActionAbout()
		{
			putValue(Action.SMALL_ICON, _aut.getImageIcon("about.png"));
		}
		public void actionPerformed(ActionEvent arg0) 
		{
			dAbout dlg = new dAbout();
			dlg.setIconImage(fNavigator.this.getIconImage());
			dlg.set_txtTitle(_aut.getString("Titles.fNavigator"));
			try
			{
				Random rdm = new Random();
				String name = Autumn.FD_RESOURCE_IMAGE_AUTUMN+"autumn0"+ (rdm.nextInt(37)+1)+ ".png";
				URL url = this.getClass().getClassLoader().getResource(name);
				if (url ==null)
					url = this.getClass().getResource(name);			
	
				if (url != null)
				{
					BufferedImage img = ImageIO.read(url); 
					dlg.setBackrounImage(img);
				}
			}
			catch (Exception ex)
			{
			}
			dlg.setTextColor(Color.WHITE);
			String dt = CC.STR_EMPTY;
			try
			{
				//File jarFile = new File(fOrbita.this.getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm());
				//dt = "build time: "+ jarFile.lastModified();
			}
			catch (Exception ex) {}
			dlg.set_txtBuild("Build id: 0001"+CC.NEW_LINE+dt);
			dlg.setVisible(true);
		}
	}

	private void _repaint()
	{
		_pnlNavigator.revalidate();
		_pnlNavigator.repaint();
		_currNavMode.ShowRegister();
	}
	
	Action actNew = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (_aut.getRegister() != null)
				_aut.getRegister().ClearObjectsCollection();
			
			setCurrentFileName(null);
			_frm.setVisible(false);
			_frm = null;
			_repaint();
			_sbiMain.setText(_aut.getString("Text.Message.Successfully.New"));
		}
	};
	
	Action actLoad = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser dlg = new JFileChooser();
			if (_currFileName != null)
				dlg.setCurrentDirectory(new File(_currFileName));
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("Object register", tRegister.FILE_EXTENTION));
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
			dlg.setMultiSelectionEnabled(false);
			if (dlg.showOpenDialog(fNavigator.this) == JFileChooser.APPROVE_OPTION)
			{
				if (dlg.getSelectedFile() != null && dlg.getSelectedFile().getPath().length() > 0)
				{
					setCurrentFileName(dlg.getSelectedFile().getPath());
					_loadCurrenFileName();
//					for (int ii = 0; ii < dlg.getSelectedFiles().length; ii++)
//					{
//						setCurrentFileName(dlg.getSelectedFiles()[ii].getPath());
//						_loadCurrenFileName();
//					}
				}

//				if (dlg.getSelectedFile().getName().indexOf(".vcf") > 0)
//				{
//					_aut.getRegister().ClearObjectsCollection();
//					PersonalVCard pvc = new PersonalVCard(_aut);
//					pvc.LoadFromVCardFile(_currFileName);
//					_isVCard = true;
//					
//				}
//				else
//				{
//					_aut.setRegister(tRegister.Load(_currFileName));
//					_isVCard = false;
//				}
//				_sbiMain.setText(String.format(_aut.getString("Text.Message.Successfully.Load"), _currFileName));
//				
//				if (_currNavMode != null)
//					_currNavMode.ShowRegister();
			}
		};
	};

	Action actAddFiles = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser dlg = new JFileChooser();
			if (_lastAddFileName != null)
				dlg.setCurrentDirectory(new File(_lastAddFileName));
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("Object register", tRegister.FILE_EXTENTION));
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
			dlg.setMultiSelectionEnabled(true);
			if (dlg.showOpenDialog(fNavigator.this) == JFileChooser.APPROVE_OPTION)
			{
				PersonalVCard pvc = new PersonalVCard(_aut);
				if (dlg.getSelectedFiles() != null && dlg.getSelectedFiles().length > 0)
				{
					for (int ii = 0; ii < dlg.getSelectedFiles().length; ii++)
					{
						String fName =  dlg.getSelectedFiles()[ii].getPath();
						tPerson prs = null;
						if (fName.indexOf(".vcf") > 0)
						{
							prs = pvc.LoadOneVCard(fName);
						}
						else if (fName.indexOf(".per") > 0)
						{
							prs = tPerson.Load(fName);
						}
						
						if (prs != null)
							_aut.getRegister().getObjColl().add(prs);
						
						_lastAddFileName = fName;
					}
					_currNavMode.ShowRegister(null);
				}
				
			}
		};
	};
	
	private void _loadCurrenFileName()
	{
		_isVCard = false;
		if (_currFileName.indexOf(".vcf") > 0)
		{
			if (_aut.getRegister() != null)
				_aut.getRegister().ClearObjectsCollection();
			PersonalVCard pvc = new PersonalVCard(_aut);
			pvc.LoadFromVCardFile(_currFileName);
			_isVCard = true;
			
		}
		else if (_currFileName.endsWith(tRegister.FILE_EXTENTION_CIPHER))
		{
			String dRes =_getPasswordDialog(_currFileName, false);
			if (dRes != null && dRes.length() > 0)
			{
				tRegister reg = tRegister.LoadCipher(_currFileName, dRes);
				if (reg != null)
				{
					_aut.setRegister(reg);
				}
				else
				{
					_sbiMain.setText(_aut.getString("Text.Error.DecryptFile"));
					setCurrentFileName(null);
					return;
				}
			}
			else
			{
				_sbiMain.setText(_aut.getString("Text.Error.DecryptFile"));
				setCurrentFileName(null);
				return;
			}
		}
		else
		{
			_aut.setRegister(tRegister.Load(_currFileName));
		}
				
		_sbiMain.setText(String.format(_aut.getString("Text.Message.Successfully.Load"), _currFileName));
		
		if (_currNavMode != null)
			_currNavMode.ShowRegister();
		
		_repaint();
	}
	
	Action actSaveAs = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setCurrentFileName(_getSavedFileName()); 
			if (_currFileName != null)
			{
				_aut.getRegister().setDateCreated(Calendar.getInstance().getTime());
				_save();
			}
		}
	};

	Action actSaveAndCrypt = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (_currFileName == null || _currFileName.length() > 0)
				setCurrentFileName(_getSavedFileName()); 
			
			String dRes =_getPasswordDialog(_currFileName, true);
			if (dRes != null && dRes.length() > 0)
			{
				String fn = FileNameTools.AddExtensionIfNone(_currFileName, tRegister.FILE_EXTENTION_CIPHER);
				_save(fn, dRes);
			}
			//_sbiMain.setText(dRes);	
		}
	};
	
	Action actSave = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (_isVCard || _currFileName == null || _currFileName.length() == 0)
				setCurrentFileName(_getSavedFileName());

			if (_currFileName != null)
				_save();
		}
	};

	Action actProperties = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
//			BookParam bp = BookParam.Load(_aut.getRegister().getBookParamFileName());
//			if (bp == null)
//			{
//				bp = new BookParam();
//				bp.getBookPage().setPrefsNode(Autumn.PREFERENCE_PATH);
//			}
			final fBookParam frm = new fBookParam(_aut.getBookParam());
			frm.setAppPreferencePath(Autumn.PREFERENCE_PATH);
			frm.setTitle(_aut.getString("BookParam.Title"));
			frm.setIconImage(_aut.getImageIcon("configure.png").getImage());
	
			frm.setEditable(true);
			frm.actPageAdd.putValue(Action.SMALL_ICON, _aut.getImageIcon("page_add.png"));
			frm.actPageEdit.putValue(Action.SMALL_ICON, _aut.getImageIcon("page_edit.png"));
			frm.actPageRemove.putValue(Action.SMALL_ICON, _aut.getImageIcon("page_remove.png"));
			frm.actParamsEdit.putValue(Action.SMALL_ICON, _aut.getImageIcon("params_edit.png"));
		
//			frm.addWindowListener(new WindowAdapter() 
//			{
//				@Override
//				public void windowClosing(WindowEvent e) 
//				{
//					frm.removeWindowListener(this);
//					if (frm.getCurrentFileName() != null && frm.getCurrentFileName().length()>0) 
//						_aut.getRegister().setBookParamFileName(frm.getCurrentFileName());
//					
//					super.windowClosing(e);
//				};
//			});
			
			frm.setVisible(true);
			if (frm.IsOk())
			{
				_reloadParam();
			}
		}
	};
	
	Action actRefbook = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			_aut.getRefbook().FormShow();
		}
	};

	Action actExit = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			fNavigator.this.dispatchEvent(new WindowEvent(fNavigator.this, WindowEvent.WINDOW_CLOSING));
			//System.exit(0);
		}
	};

	Action actObjectSelected = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (_frm != null)
			{
				_frm.Save();
				_frm.setVisible(false);
			}

			
			if (e.getSource() instanceof tPerson)
			{
				if (_frmPerson == null)
				{
					_frmPerson= new fPerson(_aut);
					_frmPerson.UpdateRegisterShow = actRefreshNavMode;
				}
				_frm = _frmPerson;
			}
			else if (e.getSource() instanceof tOrg)
			{
				if (_frmOrg == null)
				{
					_frmOrg= new fOrg(_aut);
					_frmOrg.UpdateRegisterShow = actRefreshNavMode;
				}
				_frm = _frmOrg;
			}
			else if (e.getSource() instanceof tObj)
			{
				if (_frmObject == null)
				{
					_frmObject= new fObject(_aut);
					_frmObject.UpdateRegisterShow = actRefreshNavMode;
				}
				_frm = _frmObject;
			}
			else
			{
				return;
			}
				
			//if (!_frm.isVisible())
			_currObj = (tObj) e.getSource();
			_frm.setVisible(true);
			_frm.Show(_currObj);
		}
	};
	
	Action actRefreshNavMode = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			tObj obj =  (tObj) e.getSource();
			if (obj != null)
				_currNavMode.ShowRegister(obj);
		}
	};

	Action actRecordNew = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dNewObj dlg = new dNewObj(_aut);
			dlg.setActionOk(actRecordNewOk);
			dlg.setVisible(true);
		}
	};
	
	Action actRecordNewOk = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dNewObj dlg = (dNewObj) e.getSource();
			tObj obj = null;
			if (dlg.getObjectType() == tObj.getType())
			{	
					obj = new tObj();
			}
			else if (dlg.getObjectType() == tPerson.getType())
			{
					obj = new tPerson();
			}
			else if (dlg.getObjectType() == tOrg.getType())
			{
					obj = new tOrg();
			}
			
			if (obj != null)
			{
				obj.setName(dlg.getObjectName());
				_aut.getRegister().getObjColl().add(obj);
				
				if (_currNavMode != null)
					_currNavMode.ShowRegister(obj);
			}
		}
	};

	Action actRecordDelete = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
	};

	Action actOptReplaceFLName = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			for (tObj to : _aut.getRegister().getObjColl())
			{
				if (to instanceof tPerson)
				{
					((tPerson) to).ReplaceFirstLastNameIf();
				}
			}
			_frm.Show(_currObj);
		}
	};
	
	private String _getSavedFileName()
	{
		String ret = null;
		JFileChooser dlg = new JFileChooser();
		if (_currFileName != null)
			dlg.setCurrentDirectory(new File(_currFileName));
		dlg.setFileFilter(new FileNameExtensionFilter("Object register", tRegister.FILE_EXTENTION));
		dlg.setMultiSelectionEnabled(false);
		if (dlg.showSaveDialog(fNavigator.this) == JFileChooser.APPROVE_OPTION)
		{
			ret = FileNameTools.AddExtensionIfNone(dlg.getSelectedFile().getPath(), tRegister.FILE_EXTENTION);
		}
		return ret;
	}
	
	private void _save()
	{
		_save (_currFileName, null);
	}
	private void _save(String aFN, String aKey)
	{
		if (_frm != null)
		{
			_frm.Save();
		}

		String strErr = null;
		if (aKey != null && aKey.length() > 0)
		{
			strErr = _aut.getRegister().SaveCipher(aFN, aKey);
		}
		else
		{
			strErr = _aut.getRegister().Save(aFN);
		}

		if (strErr == null)
		{
			_sbiMain.setText(String.format(_aut.getString("Text.Message.Successfully.Save"), _currFileName));
		}
		else
		{
			_sbiMain.setText(String.format(_aut.getString("Text.Error"), strErr));
		}
	}
	
	private String _getPasswordDialog(String aFileName, boolean aIsPutNewPassword)
	{
		dPassword dlg = new dPassword(_aut.getString("Titles.DlgPassword"), aIsPutNewPassword);
		dlg.setVisible(true);
		String ret = dlg.getPassword();
		return ret;
		
//		return JOptionPane.showInputDialog( 
//				fNavigator.this, 
//				_aut.getString("Label.DlgPassword.Password"),
//				aFileName, // _aut.getString("Titles.DlgPassword"),
//				JOptionPane.PLAIN_MESSAGE // .OK_CANCEL_OPTION
//				 //_aut.getImageIcon("password.png")
//		); 
	}
	
	private void _reloadParam()
	{
		Preferences nodeGeneral = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/general");
		String fntSet = nodeGeneral.get("GlobalFont", CC.STR_EMPTY);
		if (fntSet.length() > 0)
		{
			Font fnt = Font.decode(fntSet);
			Tools.setUIFont (new javax.swing.plaf.FontUIResource(fnt));
		}
		
		UpdateLanguage();
	}
	
	private void UpdateLanguage()
	{
		setCurrentFileName(_currFileName);
		
		_mnuFile.setText(_aut.getString("Menu.Person.File"));
		_mnuFileNew.setText( _aut.getString("Menu.Person.File.New"));
		_mnuFileLoad.setText( _aut.getString("Menu.Person.File.Load"));
		_mnuFileAdd.setText( _aut.getString("Menu.Person.File.Add"));
		_mnuFileSave.setText( _aut.getString("Menu.Person.File.Save"));
		_mnuFileSaveAs.setText( _aut.getString("Menu.Person.File.SaveAs"));
		_mnuFileSaveAndCrypt.setText( _aut.getString("Menu.Person.File.SaveAndCrypt"));
		_mnuFilePrint.setText( _aut.getString("Menu.Person.File.Print"));
		_mnuFileProperties.setText( _aut.getString("Menu.Person.File.Properties"));
		_mnuFileRefbook.setText(_aut.getString("Menu.Person.File.Refbook"));
		_mnuFileExit.setText( _aut.getString("Menu.Person.File.Exit"));
		_mnuRecord.setText(_aut.getString("Menu.Record"));
		_mnuRecordNew.setText(_aut.getString("Menu.Record.New"));
		_mnuRecordDelete.setText(_aut.getString("Menu.Record.Delete"));
		_mnuHelp.setText(_aut.getString("Menu.Help"));
		_mnuHelpAbout.setText(_aut.getString("Menu.Help.About"));
		_mnuOptions.setText(_aut.getString("Menu.Options"));
		_mnuOptReplaceFLName.setText(_aut.getString("Menu.Options.ReplaceFLName"));
		_lblMode.setText(_aut.getString("Label.Navigator.Mode"));
		
		_cboMode.removeAllItems();
		_cboMode.addItem(new CodeText(1, _aut.getString("Text.Navigator.Mode.Alphabet")));
		_cboMode.addItem(new CodeText(2, _aut.getString("Text.Navigator.Mode.List")));
		//_cboMode.addItem(new CodeText(2, _aut.getString("Text.Navigator.Mode.Group")));
		//_cboMode.addItem(new CodeText(3, _aut.getString("Text.Navigator.Mode.Type")));
	}
	
	private void LoadProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/Navigator");
		AsRegister.LoadFrameStateSizeLocation(node, this);
		
		int sm = node.getInt("SelectedMode", 1);
		for (int ii = 0; ii < _cboMode.getItemCount(); ii++)
				if (((CodeText) _cboMode.getItemAt(ii)).getCode() == sm)
				{
					_cboMode.setSelectedIndex(ii);
					_showMode();
				}
		
		String cfn = node.get("LastOpenedFileName", CC.STR_EMPTY);
		if (cfn.length() > 0 && new File(cfn).isFile())
		{
			setCurrentFileName(cfn);
			_loadCurrenFileName();
		}
		cfn = node.get("LastAddedFileName", CC.STR_EMPTY);
		if (cfn.length() > 0 && new File(cfn).isFile())
		{
			_lastAddFileName = cfn;
		}
		
		//_aut.getRefbook().Load(node.get("RefBookFileName", CC.STR_EMPTY));
	}

	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/Navigator");
		AsRegister.SaveFrameStateSizeLocation(node, this);
		
		if (_cboMode != null && _cboMode.getSelectedItem() != null)
			node.putInt("SelectedMode", ((CodeText) _cboMode.getSelectedItem()).getCode());
		
		if (_currFileName != null && _currFileName.length() > 0)
			node.put("LastOpenedFileName", _currFileName);
		
		if (_lastAddFileName != null && _lastAddFileName.length() > 0)
			node.put("LastAddedFileName", _lastAddFileName);
		
		//if (_aut.getRefbook().getFileName() != null)
		//	node.put("RefBookFileName", _aut.getRefbook().getFileName());
	}	
}
