package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tor.java.autumn.IntFrame.infBase;
import tor.java.autumn.IntFrame.infImages;
import tor.java.autumn.IntFrame.infNote;
import tor.java.thirteen.card.tObj;
import JCommonTools.AsRegister;

public class fObject extends JFrame 
{
	protected Autumn						mAut;
	protected tObj							mObj;

	protected String 						mCurrDir;
	protected String						mPrefPath;

	protected JMenuBar 				mMnuBar;	
	protected JMenu 					mMnuFile;
	protected JMenuItem 			mMnuFileLoad;
	protected JMenuItem 			mMnuFileSave;
	protected JMenuItem 			mMnuFileClose;
	protected JMenu 					mMnuView;
	protected JMenuItem 			mMnuViewAddTab;
	protected JMenuItem 			mMnuViewRenameTab;
	protected JMenuItem 			mMnuViewDelTab;
	protected JMenu 					mMnuOption;
	
	protected JToolBar 					mTBar;
	protected JButton						mBtnSave;
	protected JToggleButton			mBtnViewImage;
	protected JToggleButton 			mBtnViewNote;
	protected JDesktopPane			mDesktop;
	protected JTextField					mTxtObjName;
	protected ArrayList<infBase>	mALInF;
	
	protected infImages					mFrmImages;
	protected infNote						mFrmNote;

	private JTabbedPane					_tp;
	private JPanel								_pnl;
	private JToggleButton 				_btnTransparency;
	private Color								_defaultBackrounfColor;
	
	public Action UpdateRegisterShow;
	
	public fObject(Autumn aAut)
	{
		_defaultBackrounfColor = fObject.this.getBackground();
		
		mAut = aAut;
		mObj = null;
		mCurrDir = null;
		mPrefPath = "fObject";
		UpdateRegisterShow  = null;
	
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(mAut.getImageInRscImg("icons/autumn.png"));
	
		mALInF = new ArrayList<infBase>();
		
		mMnuBar = new JMenuBar();
		setJMenuBar(mMnuBar);
		mMnuFile = new JMenu();
		mMnuBar.add(mMnuFile);
		mMnuFileLoad = new JMenuItem(actLoad);
		mMnuFile.add(mMnuFileLoad);
		mMnuFileSave = new JMenuItem(actSave);
		mMnuFile.add(mMnuFileSave);
		mMnuFileClose = new JMenuItem(actClose);
		mMnuFile.add(mMnuFileClose);
		mMnuView = new JMenu();
		mMnuBar.add(mMnuView);
		mMnuViewAddTab = new JMenuItem(actViewAddTab);
		mMnuView.add(mMnuViewAddTab);
		mMnuViewRenameTab = new JMenuItem(actViewRenameTab);
		mMnuView.add(mMnuViewRenameTab);
		mMnuViewDelTab = new JMenuItem(actViewDelTab);
		mMnuView.add(mMnuViewDelTab);
		mMnuOption = new JMenu();
		mMnuBar.add(mMnuOption);
		/**
		 *   T O O L S   B A R
		 */
		mTBar = new JToolBar();
		add(mTBar, BorderLayout.NORTH);

		//actCreate.putValue(Action.SMALL_ICON, CreateIcon("new.png", Start.TOOL_BAR_ICON_SIZE));
		//bar.add(actCreate);

		actLoad.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/open.png"));
		actSave.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/save.png"));
		
		//mTBar.add(actLoad);
		
		//mBtnSave = new JButton();
		//mBtnSave.setIcon(mAut.getImageIcon("save.png"));
		//mTBar.add(mBtnSave);
		
		//actSave.putValue(Action.SMALL_ICON, mAut.getImageIcon("save.png"));
		//mTBar.add(actSave);
		
//		mTBar.addSeparator();
//		_btnTransparency = new JToggleButton(actTransparency);
//		//actViewFIO.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
//		mTBar.add(_btnTransparency);
//		mTBar.addSeparator();
		
		mBtnViewImage = new JToggleButton(actViewImages);
		actViewImages.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/pages/image.png"));
		mTBar.add(mBtnViewImage);

		mBtnViewNote = new JToggleButton(actViewNotes);
		actViewNotes.putValue(Action.SMALL_ICON, mAut.getImageIcon("icons/pages/txt.png"));
		mTBar.add(mBtnViewNote);
		
		//bar.add(actViewOptions);
		//actViewOptions.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));

		_pnl = new JPanel(new BorderLayout());
		
			JPanel pnlObj = new JPanel(new BorderLayout());
			pnlObj.add(new JLabel(mAut.getString("Label.Object.Title")), BorderLayout.WEST);
			mTxtObjName = new JTextField();
			pnlObj.add(mTxtObjName, BorderLayout.CENTER);
			_pnl.add(pnlObj, BorderLayout.NORTH);
			
			_tp = new JTabbedPane();
			mDesktop = new JDesktopPane();
			//_pnl.add(mDesktop, BorderLayout.CENTER);
			_tp.add(mDesktop, mAut.getString("TabPanel.Person.Main"));
			
			_pnl.add(_tp, BorderLayout.CENTER);
		
		this.add(_pnl, BorderLayout.CENTER);	
		_tp.addChangeListener( new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				java.awt.Component cmp = _tp.getSelectedComponent();
				if (cmp instanceof JDesktopPane)
					mDesktop = (JDesktopPane) cmp;
				
				enableDo();
			}
		});
		
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowOpened(WindowEvent e) 
			{
				LoadProgramPreference ();
				UpdateLanguage();
				enableDo();
				super.windowOpened(e);
			}
			@Override
			public void windowClosing(WindowEvent e) 
			{
				SaveProgramPreference();
				super.windowClosing(e);
			}
		});
		
	}
	
	Action actLoad = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			loadFromFile();
		};
		
	};
	
	Action actSave = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			saveToFile();
		}
	};

	Action actClose = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Close();
		}
	};
	
//	Action actTransparency = new AbstractAction(){
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//			//fPerson.this.setVisible(false);
//			fObject.this.dispose();
//			if (_btnTransparency.isSelected())
//			{
//				fObject.this.setUndecorated(true);
//				//this.setShape(null);
//				//this.setOpacity(0.7f);
//				fObject.this.setBackground(new Color(0, 0, 0, 0));
//				
//				_pnl.setOpaque(false);
//				//pnl.setBackground(new Color(0, 0, 0, 0));
//			
//				mDesktop.setOpaque(false);
//				//_desktop.setBackground(new Color(0, 0, 0, 0));
//			}
//			else
//			{
//				_pnl.setOpaque(true);
//				mDesktop.setOpaque(true);
//				fObject.this.setBackground(_defaultBackrounfColor);
//				fObject.this.setUndecorated(false);
//			}
//			fObject.this.setVisible(true);
//			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		}
//	};
	
	Action actViewImages = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (mFrmImages == null)
			{
				mFrmImages = new infImages(mAut, "frmImages", mObj);
				mFrmImages.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				mALInF.add(mFrmImages);
 				mFrmImages.Load();
			}
			if (mBtnViewImage.isSelected())
			{
				mDesktop.add(mFrmImages, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				mDesktop.remove(mFrmImages);
			}
			mFrmImages.setVisible(mBtnViewImage.isSelected());
			mDesktop.repaint();	
		}
	};
	
	Action actViewNotes = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (mFrmNote == null)
			{
				mFrmNote = new infNote(mAut, "frmNote", mObj);
				mFrmNote.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				mALInF.add(mFrmNote);
 				mFrmNote.Load();
			}
			if (mBtnViewNote.isSelected())
			{
				mDesktop.add(mFrmNote, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				mDesktop.remove(mFrmNote);
			}
			mFrmNote.setVisible(mBtnViewNote.isSelected());
			mDesktop.repaint();
		}
	};
	
	Action actViewOptions = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
		}
	};

	Action actViewAddTab  = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JDesktopPane pnl = new JDesktopPane(); 
			_tp.add(pnl, GetTabbleName());
			_tp.setSelectedComponent(pnl);
		}
	};
	Action actViewRenameTab = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			int ii = _tp.getSelectedIndex();
			if (ii >= 0)
				_tp.setTitleAt(ii, GetTabbleName());
		}
	};
	Action actViewDelTab  = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int ii = _tp.getSelectedIndex();
			if (ii > 0)
				_tp.remove(mDesktop);
		}
	};
	
	protected String GetTabbleName()
	{
		JOptionPane dlg = new JOptionPane();
		String ret = dlg.showInputDialog(
				this, 
				mAut.getString("Label.InputName"),
				mAut.getString("Titles.DlgInputName"), 
				JOptionPane.OK_OPTION
		);
		if (ret != null && ret.length() > 0)
			return ret;
		else
			return "???";
	}
	
	public void Show(tObj aObj)
	{
		mObj = aObj;
		
		mTxtObjName.setText(mObj.getName());
		mDesktop.setToolTipText(Autumn.Calendar2String(mObj.getLastModified()));
	
		for (infBase inf : mALInF)
			inf.Load(mObj);
	}

	public void Close ()
	{
		//SaveProgramPreference();
		//setVisible(false);
		fObject.this.dispatchEvent(new WindowEvent(fObject.this, WindowEvent.WINDOW_CLOSING));
	}
	
	public tObj Save()
	{
		if (mObj != null)
		{
			mObj.setName(mTxtObjName.getText());
			for (infBase inf: mALInF)
				inf.Save();
		}
		return mObj;
	}
	
	protected void loadFromFile()
	{
		
	}
	
	protected void saveToFile()
	{
		Save();
	}
	
	protected void enableDo()
	{
		boolean isNotMain = _tp.getSelectedIndex() > 0;
		mMnuViewRenameTab.setEnabled(isNotMain);
		mMnuViewDelTab.setEnabled(isNotMain);
	}
	
	protected void UpdateLanguage()
	{
		//setTitle(_aut.getString("Titles.wObject"));
		
		mMnuFile.setText(mAut.getString("Menu.Person.File"));
		mMnuFileLoad.setText(mAut.getString("Menu.Person.File.Load"));
		mMnuFileSave.setText(mAut.getString("Menu.Person.File.Save"));
		mMnuFileClose.setText(mAut.getString("Menu.Person.File.Close"));
		mMnuView.setText(mAut.getString("Menu.Config"));
		mMnuViewAddTab.setText(mAut.getString("Menu.Config.AddTab"));
		mMnuViewRenameTab.setText(mAut.getString("Menu.Config.RenameTab"));
		mMnuViewDelTab.setText(mAut.getString("Menu.Config.DelTab"));
		mMnuOption.setText(mAut.getString("Menu.Options"));
		//actLoad.putValue(Action.SHORT_DESCRIPTION , mAut.getString("ToolsBar.ShortDescription.FileLoad"));
		//actSave.putValue(Action.SHORT_DESCRIPTION , mAut.getString("ToolsBar.ShortDescription.FileSave"));
		
	}
	
	private void LoadProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mPrefPath);
		AsRegister.LoadFrameStateSizeLocation(node, this);
		//TableTools.SetColumnsWidthFromString(_tabOp, node.get("TabColWidth_Operation", CC.STR_EMPTY));
		if (node.getBoolean("isImageShow", false))
			mBtnViewImage.doClick();
		if (node.getBoolean("isNoteShow", false))
			mBtnViewNote.doClick();
		mCurrDir = node.get("CurrentDir", null);
		
		mLoadPreference(node);
	}
	
	protected void mLoadPreference(Preferences aNode)
	{
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mPrefPath);
		AsRegister.SaveFrameStateSizeLocation(node, this);
		//node.put("TabColWidth_Operation", TableTools.GetColumnsWidthAsString(_tabOp));
		node.putBoolean("isImageShow", mBtnViewImage.isSelected());
		node.putBoolean("isNoteShow", mBtnViewNote.isSelected());
		if (mCurrDir != null && mCurrDir.length() > 0)
			node.put("CurrentDir", mCurrDir);
		
		
		for (Component cmp : _tp.getComponents())
		{
			if (cmp instanceof JDesktopPane)
			{
				JDesktopPane dp =(JDesktopPane) cmp;
				for (Component comp : dp.getComponents())
				{
					if (comp instanceof infBase)
					{
						infBase ib = (infBase) comp;
						
					}
				}
			}
		}
		
		mSavePreference(node);
	}
	
	protected void mSavePreference(Preferences aNode)
	{
	}
}
