package tor.java.autumn.IntFrame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tor.java.autumn.Autumn;
import tor.java.autumn.jePhoto;
import tor.java.autumn.tabella.tBin;

public class pnlImage extends JPanel 
{
	private Autumn _aut;
	private tBin	_bin;
	private JTextField _txtNote;
	private jePhoto	_pnlImg;

	public pnlImage(Autumn aAut)
	{
		super(new BorderLayout());
	
		_aut = aAut;
		
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.add(new JLabel(aAut.getString("Label.Image.Note")), BorderLayout.WEST);
		_txtNote = new JTextField();
		pnl.add(_txtNote, BorderLayout.CENTER);
		this.add(pnl, BorderLayout.NORTH);

		_pnlImg = new jePhoto();
		this.add(_pnlImg, BorderLayout.CENTER);
		
	}

	public void setBin (tBin aBin)
	{
		_bin = aBin;
		_txtNote.setText(aBin.getNote());
		_pnlImg.setPhoto(aBin.getBin());
	}
	
	public String getTitle()
	{
		if (_txtNote.getText().length() > 0)
		{
			// later DoIt more intellectually .... example, to first space, but not more 8 characters
			return _txtNote.getText().substring(0, 8);
		}
		else
			return null;
	}
	
	public void Save()
	{
		_bin.setNote(_txtNote.getText());
	}
	

}
