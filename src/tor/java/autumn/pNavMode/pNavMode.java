package tor.java.autumn.pNavMode;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JPanel;

import tor.java.autumn.Autumn;
import tor.java.autumn.tabella.tObj;

public class pNavMode extends JPanel 
{
	protected Autumn mAut;
	
	protected Action mActObjSelected;

	public void setActionObjectSelected (Action aAct)
	{
		mActObjSelected = aAct;
	}
	
	
	public pNavMode(Autumn aAut)
	{
		mAut = aAut;
		
		this.setLayout(new BorderLayout());
	}
	
	public void ShowRegister()
	{
		ShowRegister(null);
	}
	public void ShowRegister(tObj aObj)
	{
		
	}
	
}
