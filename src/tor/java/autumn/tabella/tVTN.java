package tor.java.autumn.tabella;

import JCommonTools.CC;

public class tVTN 
{
	private String _val;
	private int _type;
	private int _mode;
	private String _note;
	private int _nn;

	public String getValue() {
		return _val;
	}
	public void setValue(String value) {
		_val = value;
	}
	public int getType() {
		return _type;
	}
	public void setType(int type) {
		_type = type;
	}
	public int getMode() {
		return _mode;
	}
	public void setMode(int mode) {
		_mode = mode;
	}
	public String getNote() {
		return _note;
	}
	public void setNote(String note) {
		_note = note;
	}
	public int getNN() {
		return _nn;
	}
	public void setNN(int nN) {
		_nn = nN;
	}
	public tVTN(String aVal, int aType, int aMode, String aNote, int aNN)
	{
		_val = aVal;
		_type = aType;
		_mode = aMode;
		_note = aNote;
		_nn = aNN;
	}
	public  tVTN(String aVal, int aType, int aMode)
	{
		this(aVal, aType, aMode, CC.STR_EMPTY, 0);
	}
	public  tVTN()
	{
		this(CC.STR_EMPTY, 0, 0, CC.STR_EMPTY, 0);
	}

	
}
