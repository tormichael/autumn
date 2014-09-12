package tor.java.autumn.IntFrame;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import tor.java.autumn.Autumn;
import tor.java.autumn.fNavigator;
import tor.java.autumn.jePhoto;
import tor.java.autumn.tabella.tBin;

public class pnlImage extends JPanel 
{
	private Autumn _aut;
	private tBin	_bin;
	private JTextField _txtNote;
	private jePhoto	_pnlImg;
	private static String _lastPath = null;

	public Action RemovePanel;
	
	public pnlImage(Autumn aAut)
	{
		super(new BorderLayout());
	
		_aut = aAut;
		
		RemovePanel = null;
		
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(new JLabel(aAut.getString("Label.Image.Note")), BorderLayout.WEST);
		_txtNote = new JTextField();
		pnl.add(_txtNote, BorderLayout.CENTER);
		this.add(pnl, BorderLayout.NORTH);

		_pnlImg = new jePhoto();
		this.add(_pnlImg, BorderLayout.CENTER);
		
		JPopupMenu pppImage = new JPopupMenu();
		JMenuItem mnuLoadImage = new JMenuItem(actLoadImage);
		mnuLoadImage.setText(_aut.getString("PopupMenu.pnlImage.Load"));
		pppImage.add(mnuLoadImage);
		JMenuItem mnuPasteImage = new JMenuItem(actPasteImage);
		mnuPasteImage.setText(_aut.getString("PopupMenu.pnlImage.Paste"));
		pppImage.add(mnuPasteImage);
		JMenuItem mnuDeleteImage = new JMenuItem(actDeleteImage);
		mnuDeleteImage.setText(_aut.getString("PopupMenu.pnlImage.Delete"));
		pppImage.add(mnuDeleteImage);
		_pnlImg.setComponentPopupMenu(pppImage);
	}

	public tBin getBin()
	{
		return _bin;
	}
	public void setBin (tBin aBin)
	{
		_bin = aBin;
		_txtNote.setText(aBin.getNote());
		_pnlImg.setImage(aBin.getBin());
	}
	
	public String getTitle()
	{
		if (_txtNote.getText().length() > 0)
		{
			// later DoIt more intellectually .... example, to first space, but not more 8 characters
			if (_txtNote.getText().length() > 8)
				return _txtNote.getText().substring(0, 8);
			else
				return _txtNote.getText();
		}
		else
			return null;
	}
	
	public void Save()
	{
		_bin.setNote(_txtNote.getText());
		_bin.SaveImageAsBin(_pnlImg.getImage());
	}
	
	Action actLoadImage = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JFileChooser dlg = new JFileChooser();
			if (_lastPath != null)
				dlg.setCurrentDirectory(new File(_lastPath));
			dlg.setAcceptAllFileFilterUsed(false);
			dlg.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg","png","gif","tiff"));
			//dlg.addChoosableFileFilter(new FileNameExtensionFilter("All files"));
			dlg.setMultiSelectionEnabled(false);
			if (dlg.showOpenDialog(pnlImage.this) == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					_lastPath = dlg.getSelectedFile().getPath();
					BufferedImage bi = ImageIO.read(new File(_lastPath));
					_pnlImg.setImage(bi);
					_txtNote.setText(dlg.getSelectedFile().getName());
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
			
		}
	};

	Action actPasteImage = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Image img = getImageFromClipboard();
			if (img != null)
			{
				_pnlImg.setImage(img);
			}
		}
	};
	
	Action actDeleteImage = new AbstractAction() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (RemovePanel != null)
			{
				e.setSource(pnlImage.this);
				RemovePanel.actionPerformed(e);
			}
		}
	};
	
	public Image getImageFromClipboard()
	{
		Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor))
		{
			try
			{
				return (Image) transferable.getTransferData(DataFlavor.imageFlavor);
			}
			catch (UnsupportedFlavorException ex)
			{
				ex.printStackTrace();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
}
