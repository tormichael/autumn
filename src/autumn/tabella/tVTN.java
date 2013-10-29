package autumn.tabella;

import JCommonTools.CC;

public class tVTN 
{
	public String Value;
	public String Type;
	public String Note;
	public int NN;

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
