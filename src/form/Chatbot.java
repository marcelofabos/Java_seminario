
package form;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Chatbot extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea chatArea = new JTextArea(15, 40);
    private JTextField inputField = new JTextField(30);
    private JButton sendButton = new JButton("Enviar");

    public Chatbot() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BotFierron");
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.PINK);

        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.PINK);
        inputPanel.add(inputField);
        inputPanel.add(sendButton);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void sendMessage() {
        String message = inputField.getText().trim().toLowerCase(); 
        chatArea.append("Tú --> " + message + "\n");
        inputField.setText("");

        // Lógica de respuesta 
        String reply = getReply(message);
        if (reply != null) {
            replyMessage(reply);
        } else {
            replyMessage("Lo siento, no puedo ayudarte con este inconveniente. Por favor, comunícate con el administrador.");
        }
    }

    private String getReply(String message) {
        // Respuestas y Consultas predefinidas
        String[][] responses = {
            {"hi", "¡Hola! Soy BotFierron."},
            {"libros disponibles", "Los libros disponibles son: \nCien años de soledad\nOrgullo y prejuicio\nEl alquimista\nTriap"},
            {"autor del libro cien años de soledad", "El autor es Gabriel García Márquez."},
            {"nacionalidad del autor gabriel garcia marquez", "Su nacionalidad es colombiana."},
            {"categoria mas popular en senati", "La categoría más popular en SENATI es 'AUTOAYUDA'."},
            {"instructor favorito de senati", "El instructor favorito de SENATI es el instructor JOE <3."}
        };

        // Buscar coincidencia en las respuestas 
        for (String[] pair : responses) {
            if (message.contains(pair[0])) {
                return pair[1];
            }
        }

        // Si no se encuentra una respuesta, devolver null
        return null;
    }

    private void replyMessage(String message) {
        chatArea.append("ChatBot --> " + message + "\n"); // Agrega la respuesta del ChatBot al área de chat
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Chatbot().setVisible(true);
            }
        });
    }
}