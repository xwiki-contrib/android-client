package org.xwiki.android.xmodel.xobjects;

public class XDoubleProperty extends XNumberProperty<Double>
{

    private Double val;
    
    public XDoubleProperty()
    {
        fields.put("numberType","double");
    }

    @Override
    public void setValue(Double val)
    {
        this.val = val;
    }

    @Override
    public Double getValue()
    {
        return val;
    }

    @Override
    public void setValueFromString(String val)
    {
      this.val=new Double(val);        
    }

}
