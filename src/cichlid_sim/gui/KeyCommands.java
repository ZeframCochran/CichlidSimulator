package cichlid_sim.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class KeyCommands extends JDialog implements ActionListener {

    /**
     * Launch the application.
     */
    public void init() {
        KeyCommands dialog = new KeyCommands();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    /**
     * Create the dialog.
     */
    public KeyCommands() {
        setModal(true);
        //setResizable(false)
        setLocationRelativeTo(null);
        setTitle("Key Commands");
        setBounds(100, 100, 600, 450);
        getContentPane().setLayout(new BorderLayout());
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                okButton.addActionListener(this);
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }
        {
            JScrollPane scrollPane = new JScrollPane();
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            {
                JTextPane text = new JTextPane();
                scrollPane.setViewportView(text);
                text.setEditable(false);
                text.setText("Moving The Camera\n"
                        + "-----------------------\n"
                        + "Move Camera Up: Q\n"
                        + "Move Camera Down: Z\n"
                        + "Move Camera Forward: W\n"
                        + "Move Camera Back: S\n"
                        + "Move Camera Right: D\n"
                        + "Move Camera Left: A\n");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if ("OK".equals(e.getActionCommand())) {
            this.setVisible(false);
        }
    }
}
