package form;

import fuente.ConexionMySQL;
import fuente.Editorial;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AppEditorial extends JFrame implements ActionListener {

    private JLabel lbl_titulo, lbl_1, lbl_2;
    private JTextField txt_id, txt_nombre, txt_pais;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_editorial;
    private JScrollPane scr_editorial;
    private DefaultTableModel model;
    private final ConexionMySQL conexion;

    public AppEditorial(ConexionMySQL conexion) {
        super();
        this.conexion = conexion;
        ConfigurarVentana();
        IniciarControles();
        LimpiarDatos();
        MostrarDatos();
    }

    private void ConfigurarVentana() {
        setTitle("SENATI - Editorial");
        setSize(430, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel("Seccion de editorial");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 20));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_1 = new JLabel("ID Editorial:");
        lbl_1.setBounds(20, 50, 80, 25);

        txt_id = new JTextField();
        txt_id.setBounds(110, 50, 160, 25);

        lbl_2 = new JLabel("Nombre:");
        lbl_2.setBounds(20, 80, 80, 25);

        txt_nombre = new JTextField();
        txt_nombre.setBounds(110, 80, 160, 25);

        JLabel lbl_3 = new JLabel("País:");
        lbl_3.setBounds(20, 110, 80, 25);

        txt_pais = new JTextField();
        txt_pais.setBounds(110, 110, 160, 25);

        btn_nuevo = new JButton("NUEVO");
        btn_nuevo.setBounds(10, 210, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton("AGREGAR");
        btn_agregar.setBounds(110, 210, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton("EDITAR");
        btn_editar.setBounds(210, 210, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton("BORRAR");
        btn_borrar.setBounds(310, 210, 90, 25);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton("CERRAR");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 420, 180, 30);
        btn_cerrar.addActionListener(this);

        tb_editorial = new JTable();
        tb_editorial.setRowHeight(20);
        tb_editorial.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tb_editorial.getSelectionModel().addListSelectionListener(new SelectionListener());

        scr_editorial = new JScrollPane(tb_editorial);
        scr_editorial.setBounds(10, 250, 390, 150);

        add(lbl_titulo);
        add(lbl_1);
        add(txt_id);
        add(lbl_2);
        add(txt_nombre);
        add(lbl_3);
        add(txt_pais);
        add(btn_nuevo);
        add(btn_agregar);
        add(btn_editar);
        add(btn_borrar);
        add(btn_cerrar);
        add(scr_editorial);

        txt_nombre.addKeyListener(new ControladorTxt());
        txt_pais.addKeyListener(new ControladorTxt());
    }

    private void MostrarDatos() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID Editorial");
        model.addColumn("Nombre");
        model.addColumn("País");

        try {
            Connection cnx = conexion.Conectar();

            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM editoriales ORDER BY id_editorial ASC");

            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("id_editorial"), rs.getString("nombre"), rs.getString("pais")});
            }

            tb_editorial.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void LimpiarDatos() {
        txt_id.setText("");
        txt_nombre.setText("");
        txt_pais.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else if (e.getSource() == btn_agregar) {
            if (ValidarCampos()) {
                Editorial nuevaEditorial = ObtenerDatosEditorial();
                if (InsertarEditorial(nuevaEditorial)) {
                    JOptionPane.showMessageDialog(this, "Editorial agregada correctamente.");
                    LimpiarDatos();
                    MostrarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la editorial.");
                }
            }
        } else if (e.getSource() == btn_editar) {
            if (ValidarCampos()) {
                Editorial editorialEditada = ObtenerDatosEditorial();
                if (EditarEditorial(editorialEditada)) {
                    JOptionPane.showMessageDialog(this, "Editorial editada correctamente.");
                    LimpiarDatos();
                    MostrarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al editar la editorial.");
                }
            }
        } else if (e.getSource() == btn_borrar) {
            String id = txt_id.getText();
            if (!id.isEmpty()) {
                if (EliminarEditorial(id)) {
                    JOptionPane.showMessageDialog(this, "Editorial eliminada correctamente.");
                    LimpiarDatos();
                    MostrarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la editorial.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una editorial de la tabla.");
            }
        }
    }

    private boolean ValidarCampos() {
        return !txt_id.getText().isEmpty() && !txt_nombre.getText().isEmpty() && !txt_pais.getText().isEmpty();
    }

    private Editorial ObtenerDatosEditorial() {
        String id = txt_id.getText();
        String nombre = txt_nombre.getText();
        String pais = txt_pais.getText();
        return new Editorial(id, nombre, pais);
    }

    private boolean InsertarEditorial(Editorial editorial) {
        try (Connection cnx = conexion.Conectar()) {
            String query = "INSERT INTO editoriales (id_editorial, nombre, pais) VALUES (?, ?, ?)";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, editorial.getId_editorial());
            pstmt.setString(2, editorial.getNombre());
            pstmt.setString(3, editorial.getPais());
            int filasInsertadas = pstmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean EditarEditorial(Editorial editorial) {
        try (Connection cnx = conexion.Conectar()) {
            String query = "UPDATE editoriales SET nombre = ?, pais = ? WHERE id_editorial = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, editorial.getNombre());
            pstmt.setString(2, editorial.getPais());
            pstmt.setString(3, editorial.getId_editorial());
            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean EliminarEditorial(String id) {
        try (Connection cnx = conexion.Conectar()) {
            String query = "DELETE FROM editoriales WHERE id_editorial = ?";
            PreparedStatement pstmt = cnx.prepareStatement(query);
            pstmt.setString(1, id);
            int filasEliminadas = pstmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private class ControladorTxt implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class SelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && tb_editorial.getSelectedRow() != -1) {
                int selectedRow = tb_editorial.getSelectedRow();
                txt_id.setText(tb_editorial.getValueAt(selectedRow, 0).toString());
                txt_nombre.setText(tb_editorial.getValueAt(selectedRow, 1).toString());
                txt_pais.setText(tb_editorial.getValueAt(selectedRow, 2).toString());
            }
        }
    }

    public static void main(String[] args) {
        ConexionMySQL conexion = new ConexionMySQL();
        AppEditorial app = new AppEditorial(conexion);
        app.setVisible(true);
    }
}
