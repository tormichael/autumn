package tor.java.autumn.IntFrame;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tor.java.autumn.Autumn;
import tor.java.autumn.tabella.tObj;

public class infNote extends infBase 
{
	private JTextArea 		_txtNote;

	public infNote(Autumn aAut, String aName, tObj aObj)
	{
		super(aAut, aName, aObj);
		
		_txtNote = new JTextArea();
		this.add(new JScrollPane(_txtNote));
		_txtNote.setBorder(BorderFactory.createLoweredBevelBorder());
	}

	public void Load()
	{
		if (mObj != null)
			_txtNote.setText(mObj.getNote());
	}
	
	protected void mSave()
	{
		mObj.setNote(_txtNote.getText());
	}
	
}
