package tor.java.autumn.pNavMode;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import tor.java.autumn.Autumn;

public class pNavModeGroup extends pNavMode 
{
	public pNavModeGroup(Autumn aAut)
	{
		super (aAut);
		
		add(new JLabel("DEBUG: pNavModeGroup"), BorderLayout.CENTER);
	}

}
