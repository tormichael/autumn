package tor.java.autumn.IntFrame;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tor.java.autumn.Autumn;
import tor.java.autumn.tabella.tBin;
import tor.java.autumn.tabella.tObj;

public class infImages extends infBase 
{
	private JTabbedPane	_tp;
	private boolean 		_isLockTabChange;
	
	public infImages(Autumn aAut, String aName, tObj aObj)
	{
		super(aAut, aName, aObj);
		
		_tp = new JTabbedPane();
		
		this.add(_tp, BorderLayout.CENTER); 
		
		_isLockTabChange = false;
		
		_tp.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				if (_tp.getTabCount() > 0
					&& _tp.getSelectedIndex() >= 0	
					&& _tp.getTitleAt(_tp.getSelectedIndex()).equals("+")
					&& !_isLockTabChange
				)
				{
					_isLockTabChange = true;
					//_addNewTab();
					int ii = _tp.getTabCount()-1;
					tBin bin  = new tBin();
					mObj.getImageCollection().add(bin);
					pnlImage pi = new pnlImage(mAut);
					pi.setBin(bin);
					if (ii > 0)
					{
						_tp.add(pi, mAut.getString("TabPanel.Image.TitleDefault") + " " +(ii+1), ii);
					}
					else
					{
						_tp.removeAll();
						_tp.add(pi, mAut.getString("TabPanel.Image.TitleDefault"));
						_tp.add(new JPanel(), "+");
					}
					_isLockTabChange = false;
					_tp.setSelectedIndex(_tp.getTabCount()-2);
				}
				
			}
		});	

		//_tp.add(new JPanel(), "+");
	}
	
	public void Load()
	{
		
		_isLockTabChange = true;		
		
		_tp.removeAll();
		
		if (mObj == null)
			return;
		
		int ii = 1;
		for(tBin bin : mObj.getImageCollection())
		{
			pnlImage pi = new pnlImage(mAut);
			pi.setBin(bin);
			String title = pi.getTitle();
			if (title == null)
				title = mAut.getString("TabPanel.Image.TitleDefault") + " " + (ii++);
			_tp.add(pi, title);
		}
		
		//boolean isEmpty = _tp.getTabCount() == 0; 
		//if (isEmpty)
		//	_addNewTab();
		
		_isLockTabChange = false;
		
		//if (!isEmpty)
			_tp.add(new JPanel(), "+");
		
	}
	
//	private void _addNewTab()
//	{
//		int ii = _tp.getTabCount()-1;
//		tBin bin  = new tBin();
//		mAut.getPerson().getImgColl().add(bin);
//		pnlImage pi = new pnlImage(mAut);
//		pi.setBin(bin);
//		if (ii > 0)
//		{
//			_tp.add(pi, mAut.getString("TabPanel.Image.TitleDefault") + " " +(ii+1), ii);
//		}
//		else
//		{
//			_tp.removeAll();
//			_tp.add(pi, mAut.getString("TabPanel.Image.TitleDefault"));
//			_tp.add(new JPanel(), "+");
//		}
//	}
}
