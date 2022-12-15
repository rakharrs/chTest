package formAPI.listener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NumberListener implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        char ch=e.getKeyChar();
        if((ch <'0' || ch > '9') && ch != '.' && ch != KeyEvent.VK_BACK_SPACE){
            e.consume();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char ch=e.getKeyChar();
        if((ch <'0' || ch > '9') && ch != '.' && ch != KeyEvent.VK_BACK_SPACE){
            e.consume();
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
