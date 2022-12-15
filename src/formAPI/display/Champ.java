package formAPI.display;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import formAPI.inc.Dropdownlist;
import formAPI.inc.Mylabel;
import formAPI.inc.Mytextfield;

public class Champ {
    Mylabel label;
    JComponent component;
    boolean visibility=true;

    public Champ(){

    }

    public Champ(Mylabel label, JComponent component){
        this.component=component;
        this.label=label;
    }
    public void setToDropdownlist(String[] options){
        Dropdownlist newcomp=new Dropdownlist(options);
        component=newcomp;
        
    }
    public void setToDropdownlist(String[] options, String[] values) throws Exception{
        Dropdownlist newcomp=new Dropdownlist(options, values);
        component=newcomp;
        
    }
    public void setDefaultText(String text){
        if(component instanceof JTextField){
            component=new Mytextfield(text);
        }
    }
    public void setDefaultDropdownIndex(int index){
        if(component instanceof Dropdownlist){
            Dropdownlist component2=(Dropdownlist) component;
            component2.setSelectedIndex(index);
            component=component2;
        }
    }
    


    public JComponent getComponent() {
        return component;
    }public Mylabel getLabel() {
        return label;
    }public boolean getVisibility(){
        return visibility;
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }public void setLabel(Mylabel label) {
        this.label = label;
    }public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
    
}
