package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

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
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import tor.java.autumn.pNavMode.pNavMode;
import tor.java.autumn.pNavMode.pNavModeAlphabet;
import tor.java.autumn.pNavMode.pNavModeGroup;
import tor.java.autumn.pNavMode.pNavModeType;
import tor.java.autumn.tabella.tObj;
import tor.java.autumn.tabella.tPerson;
import tor.java.autumn.tabella.tRegister;
import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.CodeText;

public class fNavigator extends JFrame 
{
	private Autumn _aut;
	
	private JMenu _mnuFile;
	private JMenuItem _mnuFileLoad;
	private JMenuItem _mnuFileSave;
	private JMenuItem _mnuFileSaveAs;
	private JMenuItem _mnuFilePrint;
	private JMenuItem _mnuFileProperties;
	private JMenuItem _mnuFileExit;
	private JMenu _mnuRecord;
	private JMenuItem _mnuRecordNew;
	private JMenuItem _mnuRecordDelete;
	private JLabel _lblMode;
	private JLabel _sbiMain;
	private JComboBox<CodeText> _cboMode;
	
	private pNavMode _currNavMode;
	
	private fPerson _fprs;
	
	private String _currFileName;
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
		_fprs = null;
		_currFileName = null;
		_isVCard = false;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setIconImage(_aut.getImage("autumn2.png"));

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
		_mnuFileLoad = new JMenuItem(actLoad);
		_mnuFile.add(_mnuFileLoad);
		_mnuFile.addSeparator();
		_mnuFileSave = new JMenuItem(actSave);
		_mnuFile.add(_mnuFileSave);
		_mnuFileSaveAs = new JMenuItem(actSaveAs);
		_mnuFile.add(_mnuFileSaveAs);
		_mnuFile.addSeparator();
		_mnuFilePrint = new JMenuItem();
		_mnuFile.add(_mnuFilePrint);
		_mnuFile.addSeparator();
		_mnuFileProperties = new JMenuItem();
		_mnuFile.add(_mnuFileProperties);
		_mnuFile.addSeparator();
		_mnuFileExit = new JMenuItem(actExit);
		_mnuFile.add(_mnuFileExit);
		_mnuRecord = new JMenu();
		mnuBar.add(_mnuRecord);
		_mnuRecordNew = new JMenuItem(actRecordNew);
		_mnuRecord.add(_mnuRecordNew);
		_mnuRecordDelete = new JMenuItem(actRecordDelete);
		_mnuRecord.add(_mnuRecordDelete);
		
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

		UpdateLanguage();		
	
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
		
		_cboMode.addActionListener(new ActionListener() 
		{
						
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				_showMode();
			}
		});

		if (_currFileName.length() > 0)
			_loadCurrenFileName();
	}

	private void _showMode()
	{
		if (_currNavMode != null)
			fNavigator.this.remove(_currNavMode);
		
		switch (((CodeText)_cboMode.getSelectedItem()).getCode())
		{
		case 1: // alphabet
			_currNavMode = new pNavModeAlphabet(_aut);
			break;
		case 2: // group
			_currNavMode = new pNavModeGroup(_aut);
			break;
		case 3: // type
			_currNavMode = new pNavModeType(_aut);
			break;
		}
		
		if (_currNavMode != null)
		{
			fNavigator.this.add(_currNavMode, BorderLayout.CENTER);
			_currNavMode.setActionObjectSelected(actObjectSelected);
		}
		
	}
	
	Action actLoad = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser dlg = new JFileChooser();
			if (_currFileName != null)
				dlg.setCurrentDirectory(new File(_currFileName));
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("Object register", "ore"));
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
			dlg.setMultiSelectionEnabled(false);
			if (dlg.showOpenDialog(fNavigator.this) == JFileChooser.APPROVE_OPTION)
			{
				setCurrentFileName(dlg.getSelectedFile().getPath());
				_loadCurrenFileName();
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
	
	private void _loadCurrenFileName()
	{
		if (_currFileName.indexOf(".vcf") > 0)
		{
			_aut.getRegister().ClearObjectsCollection();
			PersonalVCard pvc = new PersonalVCard(_aut);
			pvc.LoadFromVCardFile(_currFileName);
			_isVCard = true;
			
		}
		else
		{
			_aut.setRegister(tRegister.Load(_currFileName));
			_isVCard = false;
		}
		
		_sbiMain.setText(String.format(_aut.getString("Text.Message.Successfully.Load"), _currFileName));
		
		if (_currNavMode != null)
			_currNavMode.ShowRegister();
	}
	
	Action actSaveAs = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setCurrentFileName(_getSavedFileName()); 
			if (_currFileName != null)
				_save();
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
	
	Action actExit = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			setVisible(false);
		}
	};

	Action actObjectSelected = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (_fprs == null)
				_fprs= new fPerson(_aut);
			
			if (!_fprs.isVisible())
				_fprs.setVisible(true);
			
			_fprs.SavePerson();
			
			tPerson prs = (tPerson) e.getSource();
			if (prs != null)
				_fprs.ShowPerson(prs);
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
			switch(dlg.getObjectType())
			{
				case 0:
					obj = new tObj();
					break;
				case 1:
					obj = new tPerson();
					break;
				default:
					break;
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

	
	private String _getSavedFileName()
	{
		String ret = null;
		JFileChooser dlg = new JFileChooser();
		if (_currFileName != null)
			dlg.setCurrentDirectory(new File(_currFileName));
		dlg.addChoosableFileFilter(new FileNameExtensionFilter("Object register", "ore"));
		dlg.setMultiSelectionEnabled(false);
		if (dlg.showOpenDialog(fNavigator.this) == JFileChooser.APPROVE_OPTION)
		{
			ret = dlg.getSelectedFile().getPath();
		}
		return ret;
	}
	
	private void _save()
	{
		if (_fprs != null)
			_fprs.SavePerson();

		String strErr = _aut.getRegister().Save(_currFileName);
		if (strErr == null)
			_sbiMain.setText(String.format(_aut.getString("Text.Message.Successfully.Save"), _currFileName));
		else
			_sbiMain.setText(String.format(_aut.getString("Text.Error"), strErr));
	}
	
	private void UpdateLanguage()
	{
		setCurrentFileName(_currFileName);
		
		_mnuFile.setText(_aut.getString("Menu.Person.File"));
		_mnuFileLoad.setText( _aut.getString("Menu.Person.File.Load"));
		_mnuFileSave.setText( _aut.getString("Menu.Person.File.Save"));
		_mnuFileSaveAs.setText( _aut.getString("Menu.Person.File.SaveAs"));
		_mnuFilePrint.setText( _aut.getString("Menu.Person.File.Print"));
		_mnuFileProperties.setText( _aut.getString("Menu.Person.File.Properties"));
		_mnuFileExit.setText( _aut.getString("Menu.Person.File.Exit"));
		_mnuRecord.setText(_aut.getString("Menu.Record"));
		_mnuRecordNew.setText(_aut.getString("Menu.Record.New"));
		_mnuRecordDelete.setText(_aut.getString("Menu.Record.Delete"));
		_lblMode.setText(_aut.getString("Label.Navigator.Mode"));
		
		_cboMode.removeAllItems();
		_cboMode.addItem(new CodeText(1, _aut.getString("Text.Navigator.Mode.Alphabet")));
		_cboMode.addItem(new CodeText(2, _aut.getString("Text.Navigator.Mode.Group")));
		_cboMode.addItem(new CodeText(3, _aut.getString("Text.Navigator.Mode.Type")));
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
		
		setCurrentFileName(node.get("LastOpenedFileName", CC.STR_EMPTY));
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/Navigator");
		AsRegister.SaveFrameStateSizeLocation(node, this);
		
		node.putInt("SelectedMode", ((CodeText) _cboMode.getSelectedItem()).getCode());
		node.put("LastOpenedFileName", _currFileName);
	}	
}
