package tor.java.autumn.tabella;

public class tBin 
{
	private byte[] Bin;
	private String Fmt;
	private String Type;
	private String Note;
	private int NN;
	
	
	
	public byte[] getBin() {
		return Bin;
	}

	public void setBin(byte[] bin) {
		Bin = bin;
	}



	public String getFmt() {
		return Fmt;
	}



	public void setFmt(String fmt) {
		Fmt = fmt;
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



	private void tbin() 
	{
		Bin = null;
		Fmt = null;
		Type = null;
		Note = null;
		NN = 0;
	}

}
