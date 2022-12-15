package formAPI.inc;

import javax.swing.JLabel;

public class Mylabel extends JLabel{
    String thetext;
    String nom; // attribute
    public Mylabel(){

    }
    public Mylabel(String name){
        thetext=name;
        nom=name;
        this.setText(thetext);
    }
    public String getThetext() {
        return thetext;
    }public void setThetext(String thetext) {
        this.thetext = thetext;
        setText(thetext);
    }public String getNom() {
        return nom;
    }private void setNom(String nom) {
        this.nom = nom;
    }
    
}
