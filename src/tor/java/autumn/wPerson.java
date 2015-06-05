package tor.java.autumn;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CORBA.Environment;

import tor.java.autumn.Autumn;
import tor.java.autumn.PhoneTableModel;
import tor.java.autumn.jePhoto;
import tor.java.thirteen.card.tPerson;
import tor.java.thirteen.card.tRegister;

import com.toedter.calendar.JDateChooser;

import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.io.CompatibilityMode;
import net.sourceforge.cardme.io.VCardWriter;
import net.sourceforge.cardme.util.StringUtil;
import net.sourceforge.cardme.vcard.VCardImpl;
import net.sourceforge.cardme.vcard.errors.VCardError;
import net.sourceforge.cardme.vcard.features.NameFeature;
import JCommonTools.CC;
import JCommonTools.FileNameTools;
import JCommonTools.GBC;

public class wPerson extends JFrame 
{
	private Autumn			_aut;
	private tPerson			_prs;

	private JLabel 			_sbiMain;
	private JTextField 		_txtFirstName;
	private JTextField 		_txtPatronymicName;
	private JTextField 		_txtLastName;
	private JDateChooser	_dtBirthday;
	//private JTextField 		_txtBirthday;
	private jePhoto 		_pnlPhoto;
	private JTextArea 		_txtNote;
	private JTable			_tabConnection;
	private PhoneTableModel _tmPhone;
	
	public void setFileName(String aFN)
	{
		//if (aFN != null && aFN.indexOf(".vcf") > 0)
		//	LoadFromVCardFile(aFN);
	}
	
	
	public wPerson(Autumn aut, tPerson aPrs)
	{
		_aut = aut;
		_prs = aPrs;
		Dimension szScreen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(szScreen.width/2, szScreen.height/2);
		setLocation((int)(szScreen.width/2*Math.random()), (int)(szScreen.height/3*Math.random()));

		this.setTitle(_aut.getString("Titles.wPerson"));
		this.setIconImage(CreateIcon("automn2.png", Autumn.TOOL_BAR_ICON_SIZE).getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		/**
		 * M E N U
		 */
		
		JMenuBar mnuBar = new JMenuBar();
		setJMenuBar(mnuBar);

		JMenu mnuFile= new JMenu(_aut.getString("Menu.Person.File"));
		mnuBar.add(mnuFile);
		
		//JMenuItem mnuFileCreate = new JMenuItem(actCreate);
		//mnuFileCreate.setText( _aut.getString("Menu.Person.File.Create"));
		//mnuFile.add(mnuFileCreate);

		JMenuItem mnuFileLoad = new JMenuItem(actLoad);
		mnuFileLoad.setText( _aut.getString("Menu.Person.File.Load"));
		mnuFile.add(mnuFileLoad);

		mnuFile.addSeparator();
		
		JMenuItem mnuFileSave = new JMenuItem(actSave);
		mnuFileSave.setText( _aut.getString("Menu.Person.File.Save"));
		mnuFile.add(mnuFileSave);
		
		JMenuItem mnuFileSaveAs = new JMenuItem();//actSave);
		mnuFileSaveAs.setText( _aut.getString("Menu.Person.File.SaveAs"));
		mnuFile.add(mnuFileSaveAs);
		
		mnuFile.addSeparator();

		JMenuItem mnuFilePrint = new JMenuItem();//actSave);
		mnuFilePrint.setText( _aut.getString("Menu.Person.File.Print"));
		mnuFile.add(mnuFilePrint);
		
		mnuFile.addSeparator();
		
		JMenuItem mnuFileProperties = new JMenuItem();//actSave);
		mnuFileProperties.setText( _aut.getString("Menu.Person.File.Properties"));
		mnuFile.add(mnuFileProperties);
		
		mnuFile.addSeparator();

		JMenuItem mnuFileExit = new JMenuItem(actExit);
		mnuFileExit.setText( _aut.getString("Menu.Person.File.Exit"));
		mnuFile.add(mnuFileExit);

		/**
		 *   T O O L S   B A R
		 */
		JToolBar bar = new JToolBar();
		add(bar, BorderLayout.NORTH);

		//actCreate.putValue(Action.SMALL_ICON, CreateIcon("new.png", Start.TOOL_BAR_ICON_SIZE));
		//bar.add(actCreate);

		actLoad.putValue(Action.SMALL_ICON, CreateIcon("open.png", Autumn.TOOL_BAR_ICON_SIZE));
		actLoad.putValue(Action.SHORT_DESCRIPTION , _aut.getString("ToolsBar.ShortDescription.FileLoad"));
		bar.add(actLoad);
		
		actSave.putValue(Action.SMALL_ICON, CreateIcon("save.png", Autumn.TOOL_BAR_ICON_SIZE));
		actSave.putValue(Action.SHORT_DESCRIPTION , _aut.getString("ToolsBar.ShortDescription.FileSave"));
		bar.add(actSave);
		
		/**
		 * C O N T E N T S
		 */
		JTabbedPane tpPerson = new JTabbedPane();
		add(tpPerson, BorderLayout.CENTER);

		// Tab page 1 (Main)
		JPanel pnlMain = new JPanel();
		tpPerson.addTab(_aut.getString("TabPanel.Person.Main"), pnlMain);
		
		GridBagLayout gbl = new GridBagLayout();
		GBC gbc = new GBC(0,0);
		gbc.setInsets(2, 2, 2, 2);
		gbc.setFill(GBC.HORIZONTAL);
		pnlMain.setLayout(gbl);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// first row
		JLabel lblLastName = new JLabel(_aut.getString("Label.Person.LastName"));
		gbl.setConstraints(lblLastName, gbc.setGridXY(0,0).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblLastName);
		_txtLastName = new JTextField(); 
		gbl.setConstraints(_txtLastName, gbc.setGridXY(1,0).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_txtLastName);
		// second row
		JLabel lblFirstName = new JLabel(_aut.getString("Label.Person.FirstName"));
		gbl.setConstraints(lblFirstName, gbc.setGridXY(0,1).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblFirstName);
		_txtFirstName = new JTextField(); 
		gbl.setConstraints(_txtFirstName, gbc.setGridXY(1,1).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_txtFirstName);
		// third row
		JLabel lblPatronymicName = new JLabel(_aut.getString("Label.Person.PatronymicName"));
		gbl.setConstraints(lblPatronymicName, gbc.setGridXY(0,2).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblPatronymicName);
		_txtPatronymicName = new JTextField(); 
		gbl.setConstraints(_txtPatronymicName, gbc.setGridXY(1,2).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_txtPatronymicName);
		// fourth row
		JLabel lblBirthday = new JLabel(_aut.getString("Label.Person.Birthday"));
		gbl.setConstraints(lblBirthday, gbc.setGridXY(0,3).setGridSpan(1, 1).setWeight(0.0, 0.0));
		pnlMain.add(lblBirthday);
		_dtBirthday = new JDateChooser();
		//_txtBirthday = new JTextField(); 
		gbl.setConstraints(_dtBirthday, gbc.setGridXY(1,3).setGridSpan(1, 1).setWeight(0.3, 0.0));
		pnlMain.add(_dtBirthday);
		
		_pnlPhoto = new jePhoto();
		//_pnlPhoto.setSize(70, 100);
		gbl.setConstraints(_pnlPhoto, gbc.setGridXY(2,0).setGridSpan(1, 4).setWeight(0.7, 0.3).setFill(GBC.BOTH));
		pnlMain.add(_pnlPhoto);
		_pnlPhoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2)
					LoadNewPhoto();
			}
		});

		_tmPhone = new PhoneTableModel(_aut, _prs);
		_tabConnection = new JTable(_tmPhone);
		JScrollPane sp = new JScrollPane(_tabConnection);
		gbl.setConstraints(sp, gbc.setGridXY(0,4).setGridSpan(2, 1).setWeight(0.3, 0.7).setFill(GBC.BOTH));
		pnlMain.add(sp);
		_tabConnection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sp.setBorder(BorderFactory.createLoweredBevelBorder());

		_txtNote = new JTextArea();
		sp = new JScrollPane(_txtNote);
		gbl.setConstraints(sp, gbc.setGridXY(2,4).setGridSpan(1, 1).setWeight(0.7, 0.7).setFill(GBC.BOTH));
		pnlMain.add(sp);
		_txtNote.setBorder(BorderFactory.createLoweredBevelBorder());
		

		JPanel pnlOptional = new JPanel();
		tpPerson.addTab(_aut.getString("TabPanel.Person.Optional"), pnlOptional);
		
		/**
		 * S T A T U S   B A R
		 */
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createRaisedBevelBorder());
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(statusBar, BorderLayout.SOUTH);
		//statusBar.set
		
		_sbiMain = new JLabel();
		_sbiMain.setText("Welcome!");
		_sbiMain.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.add(_sbiMain);
		
	}
	
	public static ImageIcon CreateIcon(String aFName, int aSize)
	{
		URL url = wPerson.class.getResource(Autumn.FD_RESOURCE_ICONS+aFName);
		ImageIcon ico = new ImageIcon(url);
		return new ImageIcon(ico.getImage().getScaledInstance(aSize, aSize, Image.SCALE_SMOOTH));
	}
	

	Action actCreate = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			try
			{
			}
			catch (Exception ex)
			{
			}
		}
	};
	
	Action actLoad = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e){
			
			JFileChooser dlg = new JFileChooser();
			dlg.setCurrentDirectory(new File("C:\\temp\\vcards\\"));
			dlg.setFileFilter(new FileNameExtensionFilter("vCard", "vcf"));
			dlg.setMultiSelectionEnabled(false);
			if (dlg.showOpenDialog(wPerson.this) == JFileChooser.APPROVE_OPTION)
			{
				if (dlg.getSelectedFile().getName().indexOf(".vcf") > 0)
				{
					//LoadFromVCardFile(dlg.getSelectedFile().getPath());
					PersonalVCard pvc = new PersonalVCard(_aut);
					pvc.LoadFromVCardFile(dlg.getSelectedFile().getPath());
					
				}
				_showPersonDate();
			}
		};
		
	};
	
	Action actSave = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
		}
	};
	
	Action actExit = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			setVisible(false);
			
		}
	};
	
	public void LoadFromFile()
	{
	}

	private void _showPersonDate()
	{
		//tPerson prs = _aut.getPerson();
		_txtLastName.setText(_prs.getLName());
		_txtFirstName.setText(_prs.getFName());
		_txtPatronymicName.setText(_prs.getPName());
		_dtBirthday.setCalendar(_prs.getDBCalendar());
		_pnlPhoto.setImage(_prs.getMainImageAsBytes());
		_txtNote.setText(_prs.getNote());
	}
	

	public String ConvertStringFrom(String aTxt, Charset aCS){
		
		String ret = aTxt;
		String csName = (aCS != null && aCS.displayName().length() > 0) ? aCS.displayName() : Charset.defaultCharset().displayName();  
	
		if (aTxt != null && aTxt.length() > 0){
			
			try
			{
				ret = new String(aTxt.getBytes(), csName);
			}
			catch (UnsupportedEncodingException ex)
			{
				ex.printStackTrace();
			}
		}
		return ret;
	}
	
	
	
	private String DisplayCalendar(Calendar aCalendar){
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		return df.format(aCalendar.getTime());
	}

	private void LoadNewPhoto(){
		JFileChooser dlg = new JFileChooser();
		//dlg.setCurrentDirectory(new File("V:\\temp\\vcards\\"));
		dlg.setFileFilter(new FileNameExtensionFilter("Image", "jpg"));
		dlg.setMultiSelectionEnabled(false);
		if (dlg.showOpenDialog(wPerson.this) == JFileChooser.APPROVE_OPTION)
		{
			_pnlPhoto.LoadImageFromFile(dlg.getSelectedFile().getPath());
		}

	}
	
}
