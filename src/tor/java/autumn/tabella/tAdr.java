package tor.java.autumn.tabella;


public class tAdr 
{
	private int _type;
	private int _nn;
	private String _index;
	private String _country;
	private String _locality;
	private String _region;
	private String _houseStreet;
	private String _note;


	public String getHouseStreet() 
	{
		return _houseStreet;
	}
	public void setHouseStreet(String houseStreet) 
	{
		_houseStreet = houseStreet;
	}

	public String getLocality()
	{
		return _locality;
	}
	public void setLocality(String locality) 
	{
		_locality = locality;
	}

	public String getRegion()
	{
		return _region;
	}
	public void setRegion(String region) 
	{
		_region = region;
	}

	public String getIndex() 
	{
		return _index;
	}
	public void setIndex(String index)
	{
		_index = index;
	}

	public String getCountry() 
	{
		return _country;
	}
	public void setCountry(String country) 
	{
		_country = country;
	}

	public int getType() 
	{
		return _type;
	}
	public void setType(int type) 
	{
		_type = type;
	}

	public String getNote() 
	{
		return _note;
	}
	public void setNote(String note) 
	{
		_note = note;
	}

	public int getNN() 
	{
		return _nn;
	}
	public void setNN(int nN) 
	{
		_nn = nN;
	}

	public tAdr()
	{
		
	}
}
