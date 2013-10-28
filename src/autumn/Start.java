package autumn;

import javax.security.auth.login.AppConfigurationEntry;
import javax.swing.JFrame;

import org.omg.CORBA.Environment;

public class Start {

	public final static String FN_RESOURCE_TEXT = "autumn/rsc/autumnText";
	public final static String FD_RESOURCE_ICONS = "rsc/icons/";

	public final static int TOOL_BAR_ICON_SIZE = 24;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		wPerson prsn = new wPerson();
		
		if (args != null && args.length > 0)
			prsn.setFileName(args[0]);
		
		prsn.setVisible(true);
		
		
	}

}
