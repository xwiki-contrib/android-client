package org.xwiki.android.xmodel.xobjects;

public class XIntegerProperty extends XNumberProperty<Integer>
{
	private Integer val;

	public XIntegerProperty()
	{
		fields.put("numberType", "integer");
	}

	@Override
	public void setValue(Integer val)
	{
		this.val = val;
	}

	@Override
	public Integer getValue()
	{
		return val;
	}

	@Override
	public void setValueFromString(String val)
	{
		this.val = new Integer(val);
	}

	@Override
	public String toString()
	{		
		return val+"";
	}

}
