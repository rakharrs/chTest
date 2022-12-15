package formAPI.inc;

import javax.swing.JTextField;

public class Mytextfield extends JTextField{
    String defaultvalue;

    public Mytextfield(){

    }
    public Mytextfield(String defaultvalue){
        this.defaultvalue=defaultvalue;
        setText(defaultvalue);
    }
}
