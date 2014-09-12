package tor.java.autumn;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.omg.CORBA.ExceptionList;

import com.sun.istack.internal.logging.Logger;

import sun.java2d.loops.ScaledBlit;

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
				bi = resizeImage((BufferedImage) _img, this.getWidth(), this.getHeight());
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
	
	public static BufferedImage resizeImage(BufferedImage aSrcImg, int aWidth, int aHeight) 
	{
		BufferedImage resizedImage = null;		
        try 
        {
            int type = aSrcImg.getType() == 0? BufferedImage.TYPE_INT_ARGB : aSrcImg.getType();

            //*Special* if the width or height is 0 use image src dimensions
            if (aWidth == 0) {
                aWidth = aSrcImg.getWidth();
            }
            if (aHeight == 0) {
                aHeight = aSrcImg.getHeight();
            }

            int fHeight = aHeight;
            int fWidth = aWidth;

            //Work out the resized width/height
            if (aSrcImg.getHeight() > aHeight || aSrcImg.getWidth() > aWidth) {
                fHeight = aHeight;
                int wid = aWidth;
                float sum = (float)aSrcImg.getWidth() / (float)aSrcImg.getHeight();
                fWidth = Math.round(fHeight * sum);

                if (fWidth > wid) {
                    //resize again for the width this time
                    fHeight = Math.round(wid/sum);
                    fWidth = wid;
                }
            }
            else
            {
            	fWidth = aSrcImg.getWidth();
            	fHeight = aSrcImg.getHeight();
            	float sc =  aHeight/fHeight > aWidth/fWidth ? aWidth/fWidth : aHeight/fHeight; 
            	fWidth = Math.round(fWidth * sc);
            	fHeight = Math.round(fHeight * sc);
            }

            resizedImage = new BufferedImage(fWidth, fHeight, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(aSrcImg, 0, 0, fWidth, fHeight, null);
            g.dispose();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            return null;
        }

        return resizedImage;
    }
}

