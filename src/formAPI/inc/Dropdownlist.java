package formAPI.inc;

import java.awt.Color;
import java.lang.StackWalker.Option;

import javax.swing.JComboBox;
import javax.swing.JComponent;

public class Dropdownlist extends JComboBox{
    String[] options;
    String[] values=options;

    public Dropdownlist(String[] options){
        super(options);

        this.options=options;
        setBackground(Color.white);
    }
    public Dropdownlist(String[] options, String[] values) throws Exception{
        super(options);
        
        if(options.length != values.length){
            throw new Exception("options and values length don't match");
        }
        setOptions(options);
        setValues(values);
        setBackground(Color.white);
    }
    public void changeValue(int index, String str){
        values[index]=str;
    }
    public void setValues(String[] values) {
        this.values = values;
    }public String[] getValues() {
        return values;
    }public void setOptions(String[] options) {
        this.options = options;
    }public String[] getOptions() {
        return options;
    }

}
