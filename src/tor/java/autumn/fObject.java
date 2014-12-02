package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import tor.java.autumn.tabella.tObj;
import tor.java.autumn.tabella.tPerson;
import JCommonTools.AsRegister;
import JCommonTools.FileNameTools;

public class fObject extends JFrame 
{
	protected Autumn					mAut;
	protected tObj						mObj;

	protected String 					mCurrDir;
	protected String					mPrefPath;
	
	protected JToolBar 					mTBar;
	protected JToggleButton				mBtnViewImage;
	protected JToggleButton 			mBtnViewNote;
	protected JDesktopPane				mDesktop;
	protected JTextField				mTxtObjName;
	protected ArrayList<infBase>		mALInF;
	
	protected infImages					mFrmImages;
	protected infNote					mFrmNote;

	private JPanel						_pnl;
	private JToggleButton 				_btnTransparency;
	private Color						_defaultBackrounfColor;
	
	public Action UpdateRegisterShow;
	
	public fObject(Autumn aAut)
	{
		_defaultBackrounfColor = fObject.this.getBackground();
		
		mAut = aAut;
		mObj = null;
		mCurrDir = null;
		mPrefPath = "fObject";
		UpdateRegisterShow  = null;
	
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(mAut.getImageInRscImg("autumn.png"));
	
		mALInF = new ArrayList<infBase>();
		
		/**
		 *   T O O L S   B A R
		 */
		mTBar = new JToolBar();
		add(mTBar, BorderLayout.NORTH);

		//actCreate.putValue(Action.SMALL_ICON, CreateIcon("new.png", Start.TOOL_BAR_ICON_SIZE));
		//bar.add(actCreate);

		actLoad.putValue(Action.SMALL_ICON, mAut.getImageIcon("open.png"));
		mTBar.add(actLoad);
		
		actSave.putValue(Action.SMALL_ICON, mAut.getImageIcon("save.png"));
		mTBar.add(actSave);
		
		mTBar.addSeparator();
		_btnTransparency = new JToggleButton(actTransparency);
		//actViewFIO.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		mTBar.add(_btnTransparency);
		mTBar.addSeparator();
		
		mBtnViewImage = new JToggleButton(actViewImages);
		actViewImages.putValue(Action.SMALL_ICON, mAut.getImageIcon("new.png"));
		mTBar.add(mBtnViewImage);

		mBtnViewNote = new JToggleButton(actViewNotes);
		actViewNotes.putValue(Action.SMALL_ICON, mAut.getImageIcon("new.png"));
		mTBar.add(mBtnViewNote);
		
		//bar.add(actViewOptions);
		//actViewOptions.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));

		_pnl = new JPanel(new BorderLayout());
		
			JPanel pnlObj = new JPanel(new BorderLayout());
			pnlObj.add(new JLabel(mAut.getString("Label.Object.Title")), BorderLayout.WEST);
			mTxtObjName = new JTextField();
			pnlObj.add(mTxtObjName, BorderLayout.CENTER);
			_pnl.add(pnlObj, BorderLayout.NORTH);
			
			mDesktop = new JDesktopPane();
			_pnl.add(mDesktop, BorderLayout.CENTER);
		
		this.add(_pnl, BorderLayout.CENTER);
		
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
		
		UpdateLanguage();		
	}
	
	Action actLoad = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
		};
		
	};
	
	Action actSave = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
		}
	};

	Action actTransparency = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			//fPerson.this.setVisible(false);
			fObject.this.dispose();
			if (_btnTransparency.isSelected())
			{
				fObject.this.setUndecorated(true);
				//this.setShape(null);
				//this.setOpacity(0.7f);
				fObject.this.setBackground(new Color(0, 0, 0, 0));
				
				_pnl.setOpaque(false);
				//pnl.setBackground(new Color(0, 0, 0, 0));
			
				mDesktop.setOpaque(false);
				//_desktop.setBackground(new Color(0, 0, 0, 0));
			}
			else
			{
				_pnl.setOpaque(true);
				mDesktop.setOpaque(true);
				fObject.this.setBackground(_defaultBackrounfColor);
				fObject.this.setUndecorated(false);
			}
			fObject.this.setVisible(true);
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
	};
	
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
	
	public void Show(tObj aObj)
	{
		mObj = aObj;
		
		mTxtObjName.setText(mObj.getName());
		mDesktop.setToolTipText(Autumn.Calendar2String(mObj.getLastModified()));
	
		for (infBase inf : mALInF)
			inf.Load(mObj);
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
	
	protected void UpdateLanguage()
	{
		//setTitle(_aut.getString("Titles.wObject"));
		actLoad.putValue(Action.SHORT_DESCRIPTION , mAut.getString("ToolsBar.ShortDescription.FileLoad"));
		actSave.putValue(Action.SHORT_DESCRIPTION , mAut.getString("ToolsBar.ShortDescription.FileSave"));
		
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
		
		mSavePreference(node);
	}
	
	protected void mSavePreference(Preferences aNode)
	{
	}
}
