package org.xwiki.android.xmodel.xobjects;

public class XFloatProperty extends XNumberProperty<Float>
{

    private Float val;

    public XFloatProperty()
    {
       //type="com.xpn.xwiki.objects.classes.NumberClass";// in super()
       fields.put("numberType","float");
    }
    
    public XFloatProperty(Float val)
    {
       this();
       this.val=val;
    }

    @Override
    public void setValue(Float val)
    {
        this.val = val;
    }

    @Override
    public Float getValue()
    {
        return val;
    }

    @Override
    public void setValueFromString(String val)
    {
        this.val=new Float(val);
    }
    
    @Override
    public String toString()
    {    	
    	return val+"";
    }

}
