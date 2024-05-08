
package form;

import fuente.Categoria;
import fuente.ConexionMySQL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

public class AppCategoria extends JFrame implements ActionListener {

    private JLabel lbl_titulo, lbl_1, lbl_2, lbl_3, lbl_4, lbl_5, lbl_6, lbl_7;

    private JTextField txt_id, txt_nombre;

    private JDateChooser dc_fn;

    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;

    private JTable categorias;
    private JScrollPane scr_personal;

    private ConexionMySQL cn = new ConexionMySQL();

    public AppCategoria() {
        super();

        ConfigurarVentana();
        IniciarControles();
        LimpiarDatos();
        MostrarDatos();
    }

    private void ConfigurarVentana() {
        this.setTitle("CATEGORIA");
        this.setSize(430, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel();
        lbl_titulo.setText("MANTENIMIENTO DE CATEGORIA");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 20));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_1 = new JLabel();
        lbl_1.setText("ID");
        lbl_1.setBounds(135, 50, 120, 25);

        txt_id = new JTextField();
        txt_id.setBounds(150, 50, 80, 25);

        lbl_2 = new JLabel();
        lbl_2.setText("Nombre Categoria");
        lbl_2.setBounds(20, 80, 110, 25);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(20, 110, 110, 25);

        
        btn_nuevo = new JButton();
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setBounds(10, 210, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton();
        btn_agregar.setText("AGREGAR");
        btn_agregar.setBounds(110, 210, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton();
        btn_editar.setText("EDITAR");
        btn_editar.setBounds(210, 210, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton();
        btn_borrar.setText("BORRAR");
        btn_borrar.setBounds(310, 210, 90, 25);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton();
        btn_cerrar.setText("CERRAR");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 420, 180, 30);
        btn_cerrar.addActionListener(this);

        categorias = new JTable();

        categorias.setRowHeight(20);
        categorias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scr_personal = new JScrollPane(categorias);
        scr_personal.setBounds(10, 250, 390, 150);

        // Agregar los controles al JFrame
        this.add(lbl_titulo);
        this.add(lbl_1);
        this.add(txt_id);
        this.add(lbl_2);
        this.add(txt_nombre);
    
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scr_personal);

        ControladorTxt ctxt = new ControladorTxt();

        txt_id.addKeyListener(ctxt);
        txt_nombre.addKeyListener(ctxt);

        ControladorClick click = new ControladorClick();

        categorias.addMouseListener(click);
    }

    private void MostrarDatos() {
        DefaultTableModel md_tabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        md_tabla.setRowCount(0);

        Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();
            
            rs = stm.executeQuery("select * from categorias");

            int nc = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= nc; i++) {
                md_tabla.addColumn(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                Object[] arr_filas = new Object[nc];

                for (int i = 0; i < nc; i++) {
                    arr_filas[i] = rs.getObject(i + 1);
                }

                md_tabla.addRow(arr_filas);
            }

        } catch (SQLException e1) {

        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e2) {

            }
        }

        categorias.setModel(md_tabla);
    }

    private class ControladorTxt implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getSource() == txt_id && txt_id.getText().length() == 5) {
                e.consume();
            } else if (e.getSource() == txt_nombre && txt_nombre.getText().length() == 25) {
                e.consume();
            } 
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class ControladorClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int num_fila = categorias.getSelectedRow();
            
            txt_id.setEditable(false);

            String dni = (String) categorias.getValueAt(num_fila, 0);
            String app = (String) categorias.getValueAt(num_fila, 1);
            txt_id.setText(dni);
            txt_nombre.setText(app);
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn_cerrar) {
            dispose();
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else {
            if (txt_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese un ID");
                txt_id.requestFocus();
                return;
            }
            
            if (txt_nombre.getText().length() == 0){
                JOptionPane.showMessageDialog(null, "Ingrese una categoria");
                txt_nombre.requestFocus();
                return;
            }
            
            Categoria personal = new Categoria();

            personal.setId_categoria(txt_id.getText());
            personal.setNombre_categoria(txt_nombre.getText());
            
            
            Connection cnx = null;
            
            java.sql.PreparedStatement pstm = null;
            
            try {
                cnx = cn.Conectar();

                String cad_sql = "";

                if (e.getSource() == btn_agregar) {
                    cad_sql = "insert into categorias values (?, ?)";
                    
                    pstm = cnx.prepareStatement(cad_sql);

                    pstm.setString(1, personal.getId_categoria());
                    pstm.setString(2, personal.getNombre_categoria());
                    
                    pstm.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Categoria Registrado");
                } else if (e.getSource() == btn_editar) {
                    cad_sql = "update categorias set nombre_categoria = ? where id_categoria = ?";

                    pstm = cnx.prepareStatement(cad_sql);
                    
                    
                    pstm.setString(1, personal.getNombre_categoria());
                    pstm.setString(2, personal.getId_categoria());

                    pstm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Categoria Actualizado");
                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(null,
                            " Seguro de borrar el registro?",
                            "SENATI", JOptionPane.YES_NO_OPTION);

                    if (opc == JOptionPane.YES_OPTION) {
                        cad_sql = "delete from categorias where id_categoria = ?";

                        pstm = cnx.prepareStatement(cad_sql);

                        pstm.setString(1, personal.getId_categoria());

                        pstm.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Categoria Eliminado");
                    }
                }

                MostrarDatos();
            } catch (SQLException e1) {

            } finally {
                try {
                    if (pstm != null) pstm.close();
                    if (cnx != null) cnx.close();

                } catch (SQLException e2) {

                }
            }

            LimpiarDatos();
        }
    }

    private void LimpiarDatos() {
        txt_id.setEditable(true);

        categorias.clearSelection();

        txt_id.setText("");
        txt_nombre.setText("");
        
        txt_id.requestFocus();
    }
    
    public static void main(String[] args) {
        AppCategoria app_cat = new AppCategoria();
        app_cat.setVisible(true);
    }
}
