package tor.java.autumn;

import java.io.File;
import java.io.FileInputStream;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

/**
 *  see http://jchardet.sourceforge.net/
 *  
 * @author tor
 *
 */
public class DetectCharset 
{
	private nsDetector _det;
	private Boolean _found;
	private String _charsetName;
	
	public Boolean IsFound()
	{
		return _found;
	}
	
	public String GetCharsetName()
	{
		return _charsetName;
	}
	
	public DetectCharset()
	{
		_init();
		
		_det = new nsDetector();
		_det.Init(new nsICharsetDetectionObserver() 
		{
			@Override
			public void Notify(String aCharset) 
			{
				_found = true;
				_charsetName = aCharset;
			}
		});
		
	}
	
	private void _init()
	{
		_found = false;
		_charsetName = null;
	}
	
	public void DetectFile(String aFileName)
	{
		DetectFile(new File(aFileName));
	}
	public void DetectFile(File aFile)
	{
		_init ();
		try
		{
			FileInputStream  fis = new FileInputStream(aFile);
		
	        byte[] buf = new byte[1024] ;
	        int len;
	        boolean done = false ;
	        boolean isAscii = true ;

	        while( (len=fis.read(buf,0,buf.length)) != -1) 
	        {
	                // Check if the stream is only ascii.
	                if (isAscii)
	                    isAscii = _det.isAscii(buf,len);

	                // DoIt if non-ascii and not done yet.
	                if (!isAscii && !done)
	                    done = _det.DoIt(buf,len, false);
	        }
	        _det.DataEnd();

	        fis.close();

	        if (isAscii) {
	           _charsetName =  "ASCII";
	           _found = true ;
	        }
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
		}
	}
}
