package tor.java.autumn;

import java.awt.BorderLayout;
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import tor.java.autumn.IntFrame.infBase;
import tor.java.autumn.IntFrame.infFIO;
import tor.java.autumn.IntFrame.infImages;
import tor.java.autumn.IntFrame.infPhones;
import JCommonTools.AsRegister;
import JCommonTools.CC;
import JCommonTools.TableTools;

public class fPerson extends JFrame 
{
	private Autumn			_aut;
	
	private JTextField		_txtObjName;
	private JDesktopPane	_desktop;
	private ArrayList<infBase>	_alInF;
	private infFIO			_frmFIO;
	private infPhones		_frmPhones;
	private JInternalFrame	_frmAddress;
	private infImages		_frmImages;
	
	private JToggleButton _btnViewFIO;
	private JToggleButton _btnViewPhone;
	private JToggleButton _btnViewImage;
	
	public fPerson(Autumn aAut)
	{
		_aut = aAut;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(_aut.getImage("autumn.png"));
	
		_alInF = new ArrayList<infBase>();
		
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
		
		_btnViewFIO = new JToggleButton(actViewFIO);
		actViewFIO.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		bar.add(_btnViewFIO);

		_btnViewPhone = new JToggleButton(actViewPhones);
		actViewPhones.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		bar.add(_btnViewPhone);
		
		bar.add(actViewAddress);
		actViewAddress.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		
		_btnViewImage = new JToggleButton(actViewImages);
		actViewImages.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		bar.add(_btnViewImage);
		
		bar.add(actViewNotes);
		actViewNotes.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		bar.add(actViewOptions);
		actViewOptions.putValue(Action.SMALL_ICON, _aut.getImageIcon("new.png"));
		
		JPanel pnl = new JPanel(new BorderLayout());
		
			JPanel pnlObj = new JPanel(new BorderLayout());
			pnlObj.add(new JLabel(_aut.getString("Label.Object.Title")), BorderLayout.WEST);
			_txtObjName = new JTextField();
			pnlObj.add(_txtObjName, BorderLayout.CENTER);
			pnl.add(pnlObj, BorderLayout.NORTH);
			
			_desktop = new JDesktopPane();
			pnl.add(_desktop, BorderLayout.CENTER);
		
		this.add(pnl, BorderLayout.CENTER);
		
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
	
	Action actLoad = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			
			JFileChooser dlg = new JFileChooser();
			dlg.setCurrentDirectory(new File("C:\\temp\\vcards\\"));
			dlg.setFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
			dlg.setMultiSelectionEnabled(false);
			if (dlg.showOpenDialog(fPerson.this) == JFileChooser.APPROVE_OPTION)
			{
				if (dlg.getSelectedFile().getName().indexOf(".vcf") > 0)
				{
					//LoadFromVCardFile(dlg.getSelectedFile().getPath());
					PersonalVCard pvc = new PersonalVCard(_aut);
					pvc.LoadFromVCardFile(dlg.getSelectedFile().getPath());
					
				}
				
				_txtObjName.setText(_aut.getPerson().Name);
				for (infBase inf : _alInF)
					inf.Load();
			}
		};
		
	};
	
	Action actSave = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			_aut.getPerson().Name = _txtObjName.getText();
			for (infBase inf: _alInF)
				inf.Save();
		}
	};

	Action actViewFIO = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (_frmFIO == null)
			{
				_frmFIO = new infFIO(_aut, "frmFIO");
				_frmFIO.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				_alInF.add(_frmFIO);
 				_frmFIO.Load();
			}
			if (_btnViewFIO.isSelected())
			{
				_desktop.add(_frmFIO, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				_desktop.remove(_frmFIO);
			}
			_frmFIO.setVisible(_btnViewFIO.isSelected());
			_desktop.repaint();
		}
	};
	
	Action actViewPhones = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (_frmPhones == null)
			{
				_frmPhones = new infPhones(_aut, "frmPhones");
				_frmPhones.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				_alInF.add(_frmPhones);
 				_frmPhones.Load();
			}
			if (_btnViewPhone.isSelected())
			{
				_desktop.add(_frmPhones, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				_desktop.remove(_frmPhones);
			}
			_frmPhones.setVisible(_btnViewPhone.isSelected());
			_desktop.repaint();
		}
	};
	
	Action actViewAddress = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			
		}
	};
	
	Action actViewImages = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			if (_frmImages == null)
			{
				_frmImages = new infImages(_aut, "frmImages");
				_frmImages.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
 				_alInF.add(_frmImages);
 				_frmImages.Load();
			}
			if (_btnViewImage.isSelected())
			{
				_desktop.add(_frmImages, JDesktopPane.PALETTE_LAYER);
			}
			else
			{
				_desktop.remove(_frmImages);
			}
			_frmImages.setVisible(_btnViewImage.isSelected());
			_desktop.repaint();	
		}
	};
	
	Action actViewNotes = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			
		}
	};
	
	Action actViewOptions = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			
		}
	};
	
	private void UpdateLanguage()
	{
		setTitle(_aut.getString("Titles.wPerson"));
		
	}
	
	private void LoadProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/main");
		AsRegister.LoadFrameStateSizeLocation(node, this);
		//TableTools.SetColumnsWidthFromString(_tabOp, node.get("TabColWidth_Operation", CC.STR_EMPTY));
	}
	
	private void SaveProgramPreference()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/main");
		AsRegister.SaveFrameStateSizeLocation(node, this);
		//node.put("TabColWidth_Operation", TableTools.GetColumnsWidthAsString(_tabOp));
	}
}
