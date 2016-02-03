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
	protected Autumn		mAut;
	protected String		mPrefPath;
	protected tObj 			mObj;
	protected int				tabIndex;

	
	public static String	getClassNameOnly(Class aClass )
	{
		String clName = aClass.getName();
		int ind = clName.lastIndexOf(".");
		if (ind >= 0)
			clName = clName.substring(ind+1);
		
		return clName;
	}
	
	public String	getClassNameOnly()
	{
		return getClassNameOnly(this.getClass());
	}
	
	public infBase(Autumn aAut, String aPrefPath, tObj aObj)
	{
		super("", true, false, false, false);
		mAut = aAut;
		mPrefPath = null;
		
		mObj = aObj;
		
		LoadPreference(aPrefPath);
		if (mPrefPath == null)
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
		//SavePreference();
		mSave();
	}
	
	protected void mSave()
	{
		
	}
	
	public void LoadPreference(String aPrefPath)
	{
		mPrefPath = aPrefPath;
		if (mPrefPath != null && mPrefPath.length() > 0)
		{
			mPrefPath += "/"+getClassNameOnly();
			Preferences node = Preferences.userRoot().node(mPrefPath);
			
			this.setBounds(
				node.getInt("infX", 150), node.getInt("infY", 150), 
				node.getInt("infWidth", 100), node.getInt("infHeight", 100)
			);
			tabIndex = node.getInt("tabIndex", 0);
		}
	}
	
	public void SavePreference(int indexTab)
	{
		if (mPrefPath != null && mPrefPath.length() > 0)
		{
			Preferences node = Preferences.userRoot().node(mPrefPath);
			node.putInt("infX", this.getX());
			node.putInt("infY", this.getY());
			node.putInt("infWidth", this.getWidth());
			node.putInt("infHeight", this.getHeight());
			node.putInt("tabIndex", indexTab);
		}
		
	}
}
