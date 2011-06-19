package org.xwiki.android.resources;

import java.lang.reflect.Type;

import sun.util.calendar.ZoneInfo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TimeZoneDeserializer implements JsonDeserializer<ZoneInfo>
{

    @Override
    public ZoneInfo deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
    {
        // TODO Auto-generated method stub
        ZoneInfo zi = new ZoneInfo("Asia/Colombo",19800000);
        return zi;
    }

}
