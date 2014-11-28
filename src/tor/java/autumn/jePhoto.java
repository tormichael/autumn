package tor.java.autumn;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import JCommonTools.CC;
import JCommonTools.JPanelWI;
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
			setImage(_photo.getPhoto());
		}
		else
		{
			_img = this.createImage(this.getWidth(), this.getHeight());
			this.repaint();
		}
	}
	
	public void setImage(Image aPhoto)
	{
		if (aPhoto != null)
			_img = aPhoto;
		else
			_img = this.createImage(this.getWidth(), this.getHeight());
		this.repaint();
	}
	public void setImage(byte[] aPhoto)
	{
		if (aPhoto != null)
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
	}
	
	public Image getImage()
	{
		return _img;
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
		
		if (_img != null)
		{
			BufferedImage bi = null;
			if (_img instanceof BufferedImage)
			{
				//double scaleX = (double) this.getWidth()/_img.getWidth(null);
				//double scaleY = (double) this.getHeight()/_img.getHeight(null);
				//AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
				//AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
				//bi = bilinearScaleOp.filter((BufferedImage)_img, new BufferedImage(this.getWidth(), this.getHeight(), ((BufferedImage)_img).getType()));
				bi = JPanelWI.ResizeImage((BufferedImage) _img, this.getWidth(), this.getHeight());
				Point pnt = getCenterImagePoint(bi);
				g.drawImage(bi, pnt.x, pnt.y, this);
			}
			else
			{
				Point pnt = getCenterImagePoint(_img);
				g.drawImage(_img, pnt.x, pnt.y, this);
			}
		}
	}
	
	public void LoadImageFromFile(String aFileName)
	{
		try
		{
			_img = ImageIO.read(new File(aFileName));
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public Point getCenterImagePoint(Image aImg)
	{
		Point pnt = new Point(0, 0);
		if (aImg.getWidth(null) < this.getWidth())
			pnt.x = (this.getWidth()-aImg.getWidth(null))/2;
		if (aImg.getHeight(null) < this.getHeight())
			pnt.y = (this.getHeight()-aImg.getHeight(null))/2;
		
		return pnt;
	}
	
}

