package formAPI.display;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Vector;
import formAPI.staticFunction.Function;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import formAPI.fileManager.MyFilemanager;
import formAPI.inc.Dropdownlist;
import formAPI.inc.FormButton;
import formAPI.inc.Mylabel;
import formAPI.inc.Mytextfield;
import formAPI.listener.NumberListener;

import java.awt.*;
import java.io.IOException;

public class Formulaire extends JPanel{
    Vector<Champ> champs=new Vector<Champ>();
    Object objet;
    int[] forminset = {5,10,15,10};
    Fenetre window;
    JButton button;
    MyFilemanager filemanager;

    public Formulaire(Object objet) throws Exception{
        super();
        button=new JButton("OK");
        
        this.objet=objet;
        setBackground(Color.white);
        drawForm(objet);
    }

    public Formulaire(Object objet, FormButton button) throws Exception{
        setButton(button);
        writeAll();
        setObjet(objet);
        setBackground(Color.white);
        drawForm(objet); 

    }

    public void drawForm(Object objet){
        this.objet=objet;
        defineObject(objet);
        writeForm();
        
    }

    public void reloadChamp(){
        removeAll();
        writeForm();
    }

    public void writeAll() throws Exception{
        filemanager.writeAll(champs);
    }
    public void writeObject() throws Exception{
        filemanager.getOos().writeObject(translateObject());
        filemanager.write("", "\n");
    }

    //définir l'objet du formulaire
    public void defineObject(Object objet){
        Field[] fields=objet.getClass().getDeclaredFields();
        Vector<Champ> definedChamps=new Vector<Champ>();

        for (Field field : fields) {
            Champ champ = Function.getChamp(field);
            if(field.getType()==Integer.class || field.getType()==Double.class){
                System.out.println("niditra");
                champ.getComponent().addKeyListener(new NumberListener());
            }
            // champ.getComponent().addKeyListener(new NumberListener());
            definedChamps.add(champ);

        }
        setChamps(definedChamps);
        
    }

    //modification champs
    public void setToDropdownlist(int index, String... args){
        champs.get(index).setToDropdownlist(args);
        reloadChamp();
    }

    public void setToDropdownlist(int index, String[] options, String[] values) throws Exception{
        champs.get(index).setToDropdownlist(options, values);
        reloadChamp();
    }

    public void modifyLabel(int index, String text){
        champs.get(index).getLabel().setThetext(text);

        reloadChamp();
    }
    public void modifyChamp(int index, JComponent newcomp){
        champs.get(index).setComponent(newcomp);
        reloadChamp();
    }
    public void setDefaultText(int index, String text){
        champs.get(index).setDefaultText(text);
        reloadChamp();
    }
    public void setDefaultDropdownIndex(int index, int anIndex){
        champs.get(index).setDefaultDropdownIndex(anIndex);
        reloadChamp();
    }
    //écrit le formulaire
    public void writeForm(){
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setLayout(gridbag);
        c.fill=GridBagConstraints.BOTH;
        c.gridwidth=1;
        c.insets=new Insets(forminset[0],forminset[1],forminset[2],forminset[3]);
        c.weightx=0.5;
        c.weighty=0.1;
        for (Champ champ : champs) {
            if(champ.getVisibility()){
                //c.gridx=1;
                c.gridheight=champs.size();
                gridbag.setConstraints(champ.getLabel(), c);
                add(champ.getLabel());
                
                c.gridwidth=GridBagConstraints.REMAINDER;
                c.weightx=3.0;
                gridbag.setConstraints(champ.getComponent(), c);
                add(champ.getComponent());
                c.gridwidth=1;
                c.weightx=0.5;
            }
        }
        c.gridx=1;
        if(getButton()!=null){
            gridbag.setConstraints(getButton(), c);
            add(getButton());
        }
    }

    //changement du rang des champs
    public void setOrder(int[] index){
        Vector<Champ> newChamps=new Vector<>();
        for (int i=0; i<champs.size(); ++i) {
            newChamps.add(champs.get(index[i]));
            if(i==index.length-1){
                break;
            }
        }
        setChamps(newChamps);
    }

    public void moveUp(int index, int nbup){
        if(nbup<0){
            nbup*=-1;

        }
        for(int i=0; i<nbup;++i){
            moveUp(index);
        }
    }
    public void moveDown(int index, int nbdown){
        if(nbdown<0){
            nbdown*=-1;

        } 
        for(int i=0; i<nbdown;++i){
            moveDown(index);
        }
    }
    public void move(int index, int nb){
        if(nb<0){
            moveDown(index, nb);
        }else{
            moveUp(index, nb);
        }
    }

    public void moveUp(int index){
        if(index>0){
            Champ tmp = champs.get(index);
            champs.set(index, champs.get(index-1));
            champs.set(index-1, tmp);
            reloadChamp();
        }
    }

    public void moveDown(int index){
        if(index < champs.size()){
            Champ tmp= champs.get(index);
            champs.set(index, champs.get(index+1));
            champs.set(index+1, tmp);
            reloadChamp();
        }
    }

    //récupération des valeurs

    public Object translateObject() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException{
        Field[] fields = objet.getClass().getDeclaredFields();
        Object val = objet.getClass().getConstructor().newInstance();
        int c=0;
        for (Champ champ : champs) {
            if(champ.getComponent() instanceof JTextField){
                Mytextfield tmp = (Mytextfield)champ.getComponent();

                System.out.println(tmp.getText());
                val.getClass().getDeclaredMethod("set"+ champ.getLabel().getNom(),String.class).invoke(val, (Object)tmp.getText());
            }
            if(champ.getComponent() instanceof JComboBox){
                JComboBox<String> tmp = (JComboBox)champ.getComponent();

                String forMethod=Function.toUpperFirstChar(champ.getLabel().getNom());
                val.getClass().getDeclaredMethod("set"+ champ.getLabel().getNom(),String.class).invoke(val, (Object) tmp.getSelectedItem());
            }
            c++;
        }
        return val;
    }

    public void changeLabel(int index, String newLabel){
        getChamps().get(index).getLabel().setThetext(newLabel);

    }
    public String getLabelAttribute(int index){
        String val = getChamps().get(index).getLabel().getNom();
        return val;
    }
    public boolean isDropdownLists(int index){
        if(getChamps().get(index).getComponent().getClass()==Dropdownlist.class){
            return true;
        };
        return false;
    }
    public void changeVisibility(int index, boolean boo){
        getChamps().get(index).setVisibility(boo);
        reloadChamp();
    }
    public void changeDefaultText(int index, String defaultValue){
        getChamps().get(0).setDefaultText("null");
        reloadChamp();
    }

    //getter & setter
    public Vector<Champ> getChamps() {
        return champs;
    }
    public void setChamps(Vector<Champ> champs) {
        this.champs = champs;
    }
    public void setForminset(int[] forminset) {
        this.forminset = forminset;
    }public void setPadTop(int arg){
        forminset[0]=arg;
    }public void setPadLeft(int arg){
        forminset[1]=arg;
    }public void setPadBot(int arg){
        forminset[2]=arg;
    }public void setPadRight(int arg){
        forminset[3]=arg;
    }
    public int[] getForminset() {
        return forminset;
    }public MyFilemanager getFilemanager() {
        return filemanager;
    }public void setFilemanager(MyFilemanager filemanager) {
        this.filemanager = filemanager;
    }
    public JButton getButton() {
        return button;
    }
    public void setButton(JButton button) {
        this.button = button;
        reloadChamp();
    }
    public void setObjet(Object objet) {
        this.objet = objet;
    }public Object getObjet() {
        return objet;
    }
    

    
}
