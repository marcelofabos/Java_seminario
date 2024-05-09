
package form;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class PanelForm extends JFrame implements ActionListener {
    
    private JMenuBar mb_principal;
    private JMenu mn_casos;
    private JMenuItem mi_caso1, mi_salir;

    public PanelForm() {
        super();
        Configurar();
        Iniciar();
    }

    private void Configurar() {
        this.setSize(400, 400);
        this.setTitle("Aplicación Chatbot");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void Iniciar(){
        mb_principal = new JMenuBar();
        mn_casos = new JMenu("CASOS");
        
        mi_caso1 = new JMenuItem("Abrir Chatbot");
        mi_caso1.addActionListener(this);
        
        mi_salir = new JMenuItem("Salir de la Aplicacion");
        mi_salir.addActionListener(this);
        
        mn_casos.add(mi_caso1);
        mn_casos.add(mi_salir);
        
        mb_principal.add(mn_casos);
        this.setJMenuBar(mb_principal);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mi_caso1) {
            Chatbot chatbot = new Chatbot();
            chatbot.setVisible(true);
        } else if (e.getSource() == mi_salir) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        PanelForm panelform = new PanelForm();
        panelform.setVisible(true);
    }
}