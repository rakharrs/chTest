package formAPI.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import formAPI.display.Formulaire;
import formAPI.fileManager.MyFilemanager;

public class FormButtonListener implements MouseListener{
    Formulaire form;
    
    public FormButtonListener(Formulaire form) throws Exception{
        this.form=form;

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
        try {
            // Olona test = (Olona) form.translateObject();
            // System.out.println(test.getheight());
            // form.writeAll();
            form.writeObject();
            
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
