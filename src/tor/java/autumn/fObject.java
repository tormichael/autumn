package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Constructor;
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

import com.sun.corba.se.impl.orbutil.graph.Node;

import tor.java.autumn.IntFrame.infAddress;
import tor.java.autumn.IntFrame.infBase;
import tor.java.autumn.IntFrame.infImages;
import tor.java.autumn.IntFrame.infNote;
import tor.java.thirteen.card.tObj;
import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.Convert;

public class fObject extends JFrame 
{
	//public final static String FRM_BASE_NAME = "frmBase";
	//public final static String FRM_IMAGE_NAME = "frmImages";
	//public final static String FRM_NOTE_NAME = "frmNote";

	protected Autumn					mAut;
	protected tObj						mObj;

	//protected String 					mCurrDir;
	protected File							mCurrFile;
	private String							_prefPath;

	protected JMenuBar 				mMnuBar;	
	protected JMenu 					mMnuFile;
	protected JMenuItem 				mMnuFileLoad;
	protected JMenuItem 				mMnuFileSave;
	protected JMenuItem 				mMnuFileSaveAs;
	protected JMenuItem 				mMnuFileClose;
	protected JMenu 					mMnuView;
	protected JMenuItem 				mMnuViewAddTab;
	protected JMenuItem 				mMnuViewRenameTab;
	protected JMenuItem 				mMnuViewDelTab;
	protected JMenu 					mMnuOption;
	
	protected JToolBar 						mTBar;
	protected JButton						mBtnSave;
	protected JToggleButton				mBtnViewImage;
	protected JToggleButton 			mBtnViewNote;
	protected JDesktopPane				mDesktop;
	protected JTextField					mTxtObjName;
	protected ArrayList<infBase>	mALInF;
	
	protected infImages					mFrmImages;
	protected infNote						mFrmNote;
	
	public tObj	getObject()
	{
		return mObj;
	}

	public void setPreferencePath (String aPrefPath)
	{
		_prefPath = Autumn.PREFERENCE_PATH+"/"+ aPrefPath;
	}
	public String getPreferencePath()
	{
		return _prefPath;
	}
	
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
		//mCurrDir = null;
		mCurrFile = null;
		setPreferencePath("fObject");
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
		mMnuFileSaveAs = new JMenuItem(actSaveAs);
		mMnuFile.add(mMnuFileSaveAs);
		mMnuFile.addSeparator();
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

		actLoad.putValue(Action.SMALL_ICON, mAut.getImageIcon("open.png"));
		actSave.putValue(Action.SMALL_ICON, mAut.getImageIcon("save.png"));
		
		mBtnViewImage = new JToggleButton(actViewImages);
		actViewImages.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/image.png"));
		mTBar.add(mBtnViewImage);

		mBtnViewNote = new JToggleButton(actViewNotes);
		actViewNotes.putValue(Action.SMALL_ICON, mAut.getImageIcon("pages/txt.png"));
		mTBar.add(mBtnViewNote);
		
		_pnl = new JPanel(new BorderLayout());
		
			JPanel pnlObj = new JPanel(new BorderLayout());
			pnlObj.add(new JLabel(mAut.getString("Label.Object.Title")), BorderLayout.WEST);
			mTxtObjName = new JTextField();
			pnlObj.add(mTxtObjName, BorderLayout.CENTER);
			_pnl.add(pnlObj, BorderLayout.NORTH);
			
			_tp = new JTabbedPane();
			mDesktop = new JDesktopPane();
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

	Action actSaveAs = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			saveToFileAs();
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
	
	protected infBase newInfBase (String aFrmName)
	{
		infBase ret = null; 
		try
		{
			Constructor ctor =  Class.forName(aFrmName).getConstructor(Autumn.class, String.class, tObj.class);
			ctor.setAccessible(true);
			Object obj = ctor.newInstance(mAut, getPreferencePath(), getObject());
			if (obj instanceof infBase)
				ret = (infBase) obj;
		}
		catch (Exception ex)
		{
			
		}
		return ret;
	}
	
	protected infBase showHideFrm(infBase aFrm, String aFrmName, JToggleButton aBtn)
	{
		if (aFrm == null)
		{
			aFrm = newInfBase(aFrmName);
			aFrm.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
			aFrm.setVisible(true);
			mALInF.add(aFrm);
			aFrm.Load();
		}
		
		if (aBtn.isSelected())
		{
			mDesktop.add(aFrm, JDesktopPane.PALETTE_LAYER);
		}
		else
		{
			mDesktop.remove(aFrm);
		}
		
		//aFrm.setVisible(aBtn.isSelected());
		mDesktop.repaint();
		
		return aFrm;
	}
	
	Action actViewImages = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			mFrmImages = (infImages)showHideFrm(mFrmImages, infImages.class.getName(), mBtnViewImage);
		}
	};
	
	Action actViewNotes = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			mFrmNote = (infNote)showHideFrm(mFrmNote, infNote.class.getName(), mBtnViewNote);
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
			addNewTab(GetTabbleName());
		}
	};
	
	protected void addNewTab(String aName)
	{
		JDesktopPane pnl = new JDesktopPane(); 
		_tp.add(pnl, aName);
		_tp.setSelectedComponent(pnl);
	}
	
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
		
		if (mObj != null)
		{
			mTxtObjName.setText(mObj.getName());
			mDesktop.setToolTipText(Autumn.Calendar2String(mObj.getLastModified()));
		
			for (infBase inf : mALInF)
				inf.Load(mObj);
		}
	}

	public void Close ()
	{
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

	protected void saveToFileAs()
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
		mMnuFile.setText(mAut.getString("Menu.Person.File"));
		mMnuFileLoad.setText(mAut.getString("Menu.Person.File.Load"));
		mMnuFileSave.setText(mAut.getString("Menu.Person.File.Save"));
		mMnuFileSaveAs.setText(mAut.getString("Menu.Person.File.SaveAs"));
		mMnuFileClose.setText(mAut.getString("Menu.Person.File.Close"));
		mMnuView.setText(mAut.getString("Menu.Config"));
		mMnuViewAddTab.setText(mAut.getString("Menu.Config.AddTab"));
		mMnuViewRenameTab.setText(mAut.getString("Menu.Config.RenameTab"));
		mMnuViewDelTab.setText(mAut.getString("Menu.Config.DelTab"));
		mMnuOption.setText(mAut.getString("Menu.Options"));
	}
	
	private void LoadProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(getPreferencePath());
		AsRegister.LoadFrameStateSizeLocation(node, this);
		
		String currDir = node.get("CurrentDir", null);
		if (currDir != null && currDir.length()>0 && mCurrFile == null)
		{
			mCurrFile = new File(currDir, CC.STR_EMPTY);
		}
		String tn = node.get("TabNames", null);

		try
		{
			String ss[] = null;
			if (tn != null && tn.length() > 0)
			{
				ss = tn.split(";", -1);
			}
			else
			{
				ss = new String[]{CC.STR_EMPTY};
			}
			

			for (int ii=0; ii < ss.length; ii++)
			{
				if (ii > 0)
					addNewTab(ss[ii]);
				
				for (String ndn : node.keys())
				{
					if (ndn.indexOf("tbnm") == 0)
					{
						int ci = Convert.ToIntegerOrZero(ndn.substring(4));
						if (ci == ii)
						{
							String tt[] =  node.get(String.format("tbnm%1$02d", ii), CC.STR_EMPTY).split(";", -1);
							for (int jj=0; jj < tt.length; jj++)
								mCreatInfXXX(tt[jj]);
						}
					}
				}
//				for (String ndn : node.childrenNames())
//				{
//					if (node.nodeExists(getPreferencePath()+"/"+ndn))
//					{
//						Preferences nd = Preferences.userRoot().node(getPreferencePath()+"/"+ndn);
//						if (nd.getInt("tabIndex", 0)==ii )
//							mCreatInfXXX(ndn);
//					}
//				}
				
			}
		}
		catch (Exception ex)
		{
			
		}
		
		if (_tp.getComponentCount() > 1)
			_tp.setSelectedIndex(0);
		
		mLoadPreference(node);
	}
	
	protected void mCreatInfXXX(String aName)
	{
		if (aName.equals(infBase.getClassNameOnly(infImages.class)))
		{
			mBtnViewImage.doClick();
		}
		else if (aName.equals(infBase.getClassNameOnly(infNote.class)))
		{
			mBtnViewNote.doClick();
		}
	}
	
	protected void mLoadPreference(Preferences aNode)
	{
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(getPreferencePath());
		AsRegister.SaveFrameStateSizeLocation(node, this);
		if (mCurrFile != null && mCurrFile.getParent() !=null && mCurrFile.getParent().length() > 0)
			node.put("CurrentDir", mCurrFile.getParent());
		
		int tabIndex =0;
		String tabNames = CC.STR_EMPTY;
		for (Component cmp : _tp.getComponents())
		{
			if (cmp instanceof JDesktopPane)
			{
				String tbNm = CC.STR_EMPTY;
				tabNames += _tp.getTitleAt(tabIndex) + ";";
				JDesktopPane dp =(JDesktopPane) cmp;
				for (Component comp : dp.getComponents())
				{
					if (comp instanceof infBase)
					{
						infBase ib = (infBase) comp;
						tbNm += ib.getClassNameOnly() + ";";
						ib.SavePreference(tabIndex);
					}
				}
				if (tbNm.length() > 0)
				{
					tbNm = tbNm.substring(0, tbNm.length() -1);
					node.put(String.format("tbnm%1$02d", tabIndex), tbNm);
				}
				tabIndex ++;
			}
		}
		
		if (tabNames.length() > 1)
		{
			tabNames = tabNames.substring(0, tabNames.length() -1);
			node.put("TabNames", tabNames);
		}
		
		mSavePreference(node);
	}
	
	protected void mSavePreference(Preferences aNode)
	{
	}
}
