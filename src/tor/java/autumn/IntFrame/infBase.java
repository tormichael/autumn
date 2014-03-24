package tor.java.autumn.IntFrame;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import sun.awt.WindowClosingListener;
import tor.java.autumn.Autumn;

public class infBase extends JInternalFrame 
{
	protected Autumn	mAut;
	protected String	mName;

	public infBase(Autumn aAut, String aName)
	{
		super("", true, false, false, false);
		mAut = aAut;
		mName = aName;
		
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mName);
		
		this.setBounds(
			node.getInt("infX", 150), node.getInt("infY", 150), 
			node.getInt("infWidth", 100), node.getInt("infHeight", 100));
		
		this.addInternalFrameListener( new InternalFrameAdapter() 
		{
			@Override
			public void internalFrameClosing(InternalFrameEvent e) 
			{
				super.internalFrameClosing(e);
			}
		});
		
	}
	
	public void Load()
	{
		
	}
	
	public void Save()
	{
		Preferences node = Preferences.userRoot().node(Autumn.PREFERENCE_PATH+"/"+mName);
		node.putInt("infX", this.getX());
		node.putInt("infY", this.getY());
		node.putInt("infWidth", this.getWidth());
		node.putInt("infHeight", this.getHeight());
		
		mSave();
	}
	
	protected void mSave()
	{
		
	}
}
