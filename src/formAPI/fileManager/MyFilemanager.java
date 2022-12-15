package formAPI.fileManager;

import java.io.*;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import formAPI.display.Champ;
import formAPI.inc.Mytextfield;

public class MyFilemanager {
    File file;
    FileOutputStream out;
    FileInputStream inp;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public MyFilemanager(String name) throws IOException{
        this.file=new File(name);
        out=new FileOutputStream(file);
        oos=new ObjectOutputStream(out);

        inp=new FileInputStream(file);
        ois=new ObjectInputStream(inp);
    }
    public void write(String toWrite,String separation) throws Exception{
        out.write(toWrite.concat(separation).getBytes());
    }
    public void writeAll(Vector<Champ> champs) throws Exception{
        // System.out.println("test");
        for (Champ champ : champs) {
            write(champ.getLabel().getNom(),"=");
            if(champ.getComponent() instanceof JComboBox){
                JComboBox<String> tmp=(JComboBox<String>) champ.getComponent();
                write((String) tmp.getSelectedItem(),";;");
            }else if(champ.getComponent() instanceof JTextField){
                Mytextfield tmp = (Mytextfield) champ.getComponent();
                write(tmp.getText(),";;");
            }
        }
        out.write("\n".getBytes());
    }

    public File getFile() {
        return file;
    }public FileInputStream getInp() {
        return inp;
    }public ObjectInputStream getOis() {
        return ois;
    }public ObjectOutputStream getOos() {
        return oos;
    }public FileOutputStream getOut() {
        return out;
    }public void setFile(File file) {
        this.file = file;
    }public void setInp(FileInputStream inp) {
        this.inp = inp;
    }public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }public void setOut(FileOutputStream out) {
        this.out = out;
    }

}
