package tor.java.autumn.pNavMode;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;

import JCommonTools.CC;
import tor.java.autumn.Autumn;
import tor.java.autumn.tabella.tObj;
import tor.java.autumn.tabella.tRegister;

public class pNavModeAlphabet extends pNavMode 
{
	private JTabbedPane _tp; 
	
	public pNavModeAlphabet(Autumn aAut)
	{
		super (aAut);
		
		_tp = new JTabbedPane();
		_tp.setTabPlacement(JTabbedPane.LEFT);
		
		add(_tp, BorderLayout.CENTER);
		
	}
	
	public void ShowRegister()
	{
		_tp.removeAll();
		
		tRegister reg = mAut.getRegister();
		reg.SortObjCollection();
		String prevLetter = CC.STR_EMPTY;
		JPanel pnlLetter = null;
		JList<tObj> lstObj = null;
		DefaultListModel<tObj> lmObj = null;
		for (tObj obj : reg.getObjColl())
		{
			String letter = obj.getName().substring(0, 1).toUpperCase();
			if (!letter.equals(prevLetter))
			{
				pnlLetter = new JPanel(new BorderLayout());
				lmObj = new DefaultListModel<tObj>();
				lstObj = new JList<tObj>(lmObj);
				pnlLetter.add(lstObj, BorderLayout.CENTER);
				_tp.add(pnlLetter,letter);
				prevLetter = letter;
			}
			lmObj.addElement(obj);
		}
		
	}
}
