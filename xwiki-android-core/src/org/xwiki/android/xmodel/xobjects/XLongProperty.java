package org.xwiki.android.xmodel.xobjects;

public class XLongProperty extends XNumberProperty<Long>
{
	private Long val;

	public XLongProperty()
	{
		fields.put("numberType", "long");
	}

	@Override
	public void setValue(Long val)
	{
		this.val = val;
	}

	@Override
	public Long getValue()
	{
		return val;
	}

	@Override
	public void setValueFromString(String val)
	{
		this.val = new Long(val);
	}

	@Override
	public String toString()
	{
		return val + "";
	}
}
