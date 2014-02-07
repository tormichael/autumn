package autumn;

import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.omg.CORBA.ExceptionList;

import JCommonTools.CC;

import net.sourceforge.cardme.vcard.features.PhotoFeature;

public class jePhoto extends JPanel {

	private PhotoFeature 	_photo;
	private Image			_img;
	//private Point			_size;
	private Exception		_exc;

	public PhotoFeature getPhoto(){
		return _photo;
	}
	public void setPhoto(PhotoFeature aPhoto)
	{
		_photo = aPhoto;
		if (_photo != null)
		{
			setPhoto(_photo.getPhoto());
		}
		else
		{
			_img = this.createImage(this.getWidth(), this.getHeight());
			this.repaint();
		}
	}
	public void setPhoto(byte[] aPhoto)
	{
		ByteArrayInputStream baiStream = new ByteArrayInputStream(aPhoto);
		try
		{
			_img = ImageIO.read(baiStream);
			this.repaint();
		}
		catch (IOException ex)
		{
			
		}
	}
		
	public Boolean isError(){
		return _exc != null;
	}
	public String getError(){
		String ret = CC.STR_EMPTY;
		if (_exc != null)
			_exc.getMessage();
		return ret;
	}
	
	public jePhoto (){
		super();
		
		_photo = null;
		_exc = null;
		//this.setSize(100, 100);
		_img = this.createImage(this.getWidth(), this.getHeight());
		setBorder(BorderFactory.createRaisedBevelBorder());
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		if (_img != null){
			int x = 0;
			if (_img.getWidth(null) < this.getWidth())
				x = (this.getWidth()-_img.getWidth(null))/2;
			int y = 0;
			if (_img.getHeight(null) < this.getHeight())
				y = (this.getHeight()-_img.getHeight(null))/2;
			g.drawImage(_img, x, y, this);
		}
	}
	
	public void LoadImageFromFile(String aFileName){
		try{
		_img = ImageIO.read(new File(aFileName));
		}
		catch (IOException ex){
			
		}

	}
}
