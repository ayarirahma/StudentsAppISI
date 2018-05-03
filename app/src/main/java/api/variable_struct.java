package api;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Geek Hamza on 03/05/2018.
 */

public class variable_struct
{
    public  String type, value;
    HashMap<String,String> valueInfo;
    variable_struct()
    {
        this.type="String";
        this.valueInfo=new  HashMap<String,String>();

    }
};
