package tor.java.autumn;

import java.io.File;
import java.io.FileInputStream;

import org.mozilla.universalchardet.UniversalDetector;

/**
 *  https://code.google.com/p/juniversalchardet/
 *  
 * @author tor
 *
 */
public class DetectCharset 
{
	
	public DetectCharset()
	{
	}
	
	
	public static String DetectFile(String aFileName)
	{
		return DetectCharset.DetectFile(new File(aFileName));
	}
	public static String DetectFile(File aFile)
	{
		String ret = null;
		UniversalDetector _det = new UniversalDetector(null);
		try
		{
			FileInputStream  fis = new FileInputStream(aFile);
		
	        byte[] buf = new byte[1024] ;
	        int len;

	        while( (len=fis.read(buf,0,buf.length)) != -1 && !_det.isDone()) 
	        {
	        	_det.handleData(buf, 0, len);
	        }
	        _det.dataEnd();

	        ret = _det.getDetectedCharset();
	        
	        fis.close();
	        _det.reset();
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
		}
		
		return ret;
	}
}
