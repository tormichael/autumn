package tor.java.autumn.pNavMode;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import JCommonTools.CC;
import tor.java.autumn.Autumn;
import tor.java.autumn.tabella.tObj;
import tor.java.autumn.tabella.tRegister;

public class pNavModeList extends pNavMode 
{
	private JList<tObj> _lst;
	private DefaultListModel<tObj> _lmObj;
	
	public pNavModeList(Autumn aAut)
	{
		super (aAut);
		
		_lmObj = new DefaultListModel<tObj>();
		_lst = new JList<tObj>(_lmObj);
		
		add(new JScrollPane(_lst), BorderLayout.CENTER);
		
		_lst.addListSelectionListener(new ListSelectionListener() 
		{
			@Override
			public void valueChanged(ListSelectionEvent e) 
			{
				JList<tObj> lo = (JList<tObj>) e.getSource();
				if (mActObjSelected != null &&  lo != null)
				{
					mActObjSelected.actionPerformed(new ActionEvent(lo.getSelectedValue(),  e.getFirstIndex(), CC.STR_EMPTY));
				}
			}
		});

	}

	public void ShowRegister(tObj aObj)
	{
		_lmObj.clear();
		tRegister reg = mAut.getRegister();
		reg.SortObjCollection();
		for (tObj obj : reg.getObjColl())
		{
			_lmObj.addElement(obj);
		}
		
	}	
	
}
