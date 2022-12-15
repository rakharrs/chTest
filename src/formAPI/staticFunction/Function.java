package formAPI.staticFunction;

import java.lang.reflect.Field;

import formAPI.display.Champ;
import formAPI.fileManager.MyFilemanager;
import formAPI.inc.Mylabel;
import formAPI.inc.Mytextfield;

public class Function {

    public static Champ getChamp(Field field){
        Champ val = new Champ();
        String defaultName = field.getName();
        Mytextfield textfield = new Mytextfield();
        val.setComponent(textfield);
        val.setLabel(new Mylabel(defaultName));
        
        return val;
    }

    public static String toUpperFirstChar(String str){
        String tmp=str.substring(0, 1);
        str=str.substring(1);
        tmp.toUpperCase();
        return tmp.concat(str);
    }
    
}
