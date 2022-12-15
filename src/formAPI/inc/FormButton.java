package formAPI.inc;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import formAPI.display.Formulaire;
import formAPI.listener.FormButtonListener;

public class FormButton extends JButton{
    Formulaire form;
    MouseListener listener;

    public FormButton(String text) throws Exception{
        super(text);
    }
    public FormButton(Formulaire form ,String text) throws Exception{
        setForm(form);
        setListener(new FormButtonListener(form));
        setText(text);
        addMouseListener(getListener());
    }

    public MouseListener getListener() {
        return listener;
    }
    public void setListener(MouseListener listener) {
        this.listener = listener;
    }
    public Formulaire getForm() {
        return form;
    }public void setForm(Formulaire form) {
        this.form = form;
    }
}