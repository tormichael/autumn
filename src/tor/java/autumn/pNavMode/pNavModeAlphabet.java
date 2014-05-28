package tor.java.autumn.pNavMode;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		
		_tp.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				JPanel pnl = (JPanel)_tp.getSelectedComponent();
				if (pnl != null)
				{
					JList<tObj> lst = (JList <tObj>) pnl.getComponent(0);
					if (lst != null && lst.getSelectedIndex() >= 0)
						RedrawObjData(lst, lst.getSelectedIndex());
				}
			}
		});
	}

	public void ShowRegister(tObj aObj)
	{
		_tp.removeAll();
		
		tRegister reg = mAut.getRegister();
		reg.SortObjCollection();
		String prevLetter = CC.STR_EMPTY;
		JPanel pnlLetter = null;
		JList<tObj> lstObj = null;
		JPanel pnlSeleted = null;
		JList<tObj> lstSelected = null;
		int indSelected = -1;
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
				lstObj.addListSelectionListener(new ListSelectionListener() 
				{
					@Override
					public void valueChanged(ListSelectionEvent e) 
					{
						RedrawObjData((JList<tObj>) e.getSource(), e.getFirstIndex());
					}
				});
			}
			lmObj.addElement(obj);
			if (obj == aObj)
			{
				indSelected = lmObj.indexOf(obj);
				lstSelected = lstObj;
				pnlSeleted = pnlLetter;
			}
		}
		
		if (pnlSeleted != null && lstSelected != null && indSelected >= 0)
		{
			_tp.setSelectedComponent(pnlSeleted);
			lstSelected.setSelectedIndex(indSelected);
		}
	}
	
	private void RedrawObjData(JList<tObj> lo, int aInd)
	{
		if (mActObjSelected != null && lo != null)
		{
			mActObjSelected.actionPerformed(new ActionEvent(lo.getSelectedValue(), aInd, CC.STR_EMPTY));
		}
	}
}
