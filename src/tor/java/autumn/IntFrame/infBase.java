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

import tor.java.autumn.Autumn;
import tor.java.thirteen.card.tObj;

public class infBase extends JInternalFrame 
{
	protected Autumn	mAut;
	protected String	mPrefPath;
	protected String	mName;
	protected tObj mObj;

	public infBase(Autumn aAut, String aPrefPath, tObj aObj)
	{
		super("", true, false, false, false);
		mAut = aAut;
		mPrefPath = aPrefPath;
		
		mName = this.getClass().getName();
		int ind = mName.lastIndexOf(".");
		if (ind >= 0)
			mName = mName.substring(ind+1);
		mObj = aObj;
		
		if (mPrefPath != null && mPrefPath.length() > 0)
		{
			mPrefPath += "/"+mName;
			Preferences node = Preferences.userRoot().node(mPrefPath);
			
			this.setBounds(
				node.getInt("infX", 150), node.getInt("infY", 150), 
				node.getInt("infWidth", 100), node.getInt("infHeight", 100));
		}
		else
		{
			this.setBounds(150, 150, 100, 100);
		}
		this.addInternalFrameListener( new InternalFrameAdapter() 
		{
			@Override
			public void internalFrameClosing(InternalFrameEvent e) 
			{
				super.internalFrameClosing(e);
			}
		});
		
	}
	
	public void Load(tObj aObj)
	{
		mObj = aObj;
		Load();
	}
	
	public void Load()
	{
		
	}
	
	public void Save()
	{
		if (mPrefPath != null && mPrefPath.length() > 0)
		{
			Preferences node = Preferences.userRoot().node(mPrefPath+"/"+mName);
			node.putInt("infX", this.getX());
			node.putInt("infY", this.getY());
			node.putInt("infWidth", this.getWidth());
			node.putInt("infHeight", this.getHeight());
		}		
		mSave();
	}
	
	protected void mSave()
	{
		
	}
}
