package tor.java.autumn.tabella;

import JCommonTools.CC;

public class tVTN 
{
	private String Value;
	private String Type;
	private String Note;
	private int NN;

	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public int getNN() {
		return NN;
	}
	public void setNN(int nN) {
		NN = nN;
	}
	public tVTN(String aVal, String aType, String aNote, int aNN)
	{
		Value = aVal;
		Type = aType;
		Note = aNote;
		NN = aNN;
	}
	public  tVTN(String aVal, String aType)
	{
		this(aVal, aType, CC.STR_EMPTY, 0);
	}
	public  tVTN()
	{
		this(CC.STR_EMPTY, CC.STR_EMPTY, CC.STR_EMPTY, 0);
	}

	
}
