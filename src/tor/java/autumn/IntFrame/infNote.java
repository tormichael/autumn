package tor.java.autumn.IntFrame;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tor.java.autumn.Autumn;

public class infNote extends infBase 
{
	private JTextArea 		_txtNote;

	public infNote(Autumn aAut, String aName)
	{
		super(aAut, aName);
		
		_txtNote = new JTextArea();
		this.add(new JScrollPane(_txtNote));
		_txtNote.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	public void Load()
	{
		_txtNote.setText(mAut.getPerson().getNotes());
	}
	
	protected void mSave()
	{
		mAut.getPerson().setNotes(_txtNote.getText());
	}
	
}
