package tor.java.autumn.pNavMode;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import tor.java.autumn.Autumn;

public class pNavModeType extends pNavMode 
{

	public pNavModeType(Autumn aAut)
	{
		super(aAut);
		
		add(new JLabel("DEBUG: pNavModeType"), BorderLayout.CENTER);
	}
	
}
