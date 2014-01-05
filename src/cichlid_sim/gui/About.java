package cichlid_sim.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class About extends JDialog implements ActionListener, MouseListener{

	/**
	 * Launch the application.
	 */
        
         private JLabel website,email,label;
         private JPanel panel;
	public void init() {
			About dialog = new About();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
	}
	/**
	 * Create the dialog.
	 */
	public About() {
		setResizable(false);
		//setModal(true);
		setTitle("About");
		setBounds(100, 100, 634, 688);
		getContentPane().setLayout(null);
        
                JPanel buttonPane = new JPanel();
                buttonPane.setBorder(null);
                buttonPane.setBounds(10, 590, 598, 48);
                getContentPane().add(buttonPane);
                buttonPane.setLayout(null);
                
                JButton btnOk = new JButton("OK");
                btnOk.setBounds(251, 11, 89, 23);
                buttonPane.add(btnOk);
                btnOk.addActionListener(this);     
                
                Version();
                Author();
                Sponsor();
                Disclaimer();
                Logo();                  
 
	}
    private void Version()
        {        
                panel = new JPanel();
                panel.setBorder(new TitledBorder(null, "Version: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                panel.setBounds(10, 193, 234, 171);
                getContentPane().add(panel);
                panel.setLayout(null);
                label = new JLabel("Cichlid Behavior Simulator v1.0");
                label.setBounds(15, 34, 200, 14);
                panel.add(label); 
                label = new JLabel("jMonkeyEngine 3.0.0 RC2");
                label.setBounds(15, 59, 200, 14);
                panel.add(label); 
                label = new JLabel("Java 1.7.0_40");
                label.setBounds(15, 109, 200, 14);
                panel.add(label); 
                label = new JLabel("OpenGL 4.2.12422");
                label.setBounds(15, 84, 200, 14);
                panel.add(label); 
                label = new JLabel("JSON v1.1.10");
                label.setBounds(15, 134, 200, 14);
                panel.add(label);               
                
        }   		
    private void Author()
        {
                panel = new JPanel();
                panel.setBorder(new TitledBorder(null, "Author: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                panel.setBounds(254, 11, 354, 171);
                getContentPane().add(panel);
                panel.setLayout(null);

                label = new JLabel("TeamSeven\n\n");
                label.setBounds(35, 34, 200, 14);
                panel.add(label);

                label = new JLabel("Members:\n");
                label.setBounds(35, 54, 200, 14);
                panel.add(label);

                label = new JLabel("Pros Heng\n");
                label.setBounds(35, 69, 200, 14);
                panel.add(label);

                label = new JLabel("Wesley Perry\n");
                label.setBounds(35, 84, 200, 14);
                panel.add(label);

                label = new JLabel("Jerod Ewert\n");
                label.setBounds(35, 99, 200, 14);
                panel.add(label);

                label = new JLabel("Tyler Thomas\n");
                label.setBounds(35, 114, 200, 14);
                panel.add(label);

        }
    private void Sponsor()
       {
                panel = new JPanel();
                panel.setBorder(new TitledBorder(null, "Sponsor: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                panel.setBounds(254, 194, 354, 171);
                getContentPane().add(panel);
                panel.setLayout(null);

                JLabel label = new JLabel("Name:");
                label.setBounds(10, 31, 68, 14);
                panel.add(label);

                label = new JLabel("Title:");
                label.setBounds(10, 56, 68, 14);
                panel.add(label);

                label = new JLabel("Contact: ");
                label.setBounds(10, 81, 68, 14);
                panel.add(label);

                label = new JLabel("Email:");
                label.setBounds(10, 106, 68, 14);
                panel.add(label);

                label = new JLabel("Dr. Ronald Coleman");
                label.setBounds(99, 31, 184, 14);
                panel.add(label);

                label = new JLabel("Associate Professor");
                label.setBounds(99, 56, 184, 14);
                panel.add(label);

                label = new JLabel("916 - 705 - 2606");
                label.setBounds(99, 81, 184, 14);
                panel.add(label);

                email = new JLabel("<html> <a href=\"\"> rcoleman@csus.edu</a></html>");
                email.setCursor(new Cursor(Cursor.HAND_CURSOR));
                email.addMouseListener(this);
                email.setBounds(99, 106, 184, 14);
                panel.add(email);

                label = new JLabel("Website: ");
                label.setBounds(10, 131, 68, 14);
                panel.add(label);

                website = new JLabel("<html> <a href=\"\"> http://www.csus.edu/indiv/c/colemanr/</a></html>");
                website.addMouseListener(this);
                website.setCursor(new Cursor(Cursor.HAND_CURSOR));
                website.setBounds(99, 131, 245, 14);
                panel.add(website);
        }
    private void Disclaimer()
      {        
                panel = new JPanel();
                panel.setBackground(UIManager.getColor("Button.background"));
                panel.setForeground(Color.WHITE);
                panel.setBorder(new TitledBorder(UIManager.getBorder("CheckBoxMenuItem.border"), "Disclaimer", TitledBorder.CENTER, TitledBorder.TOP, null, null));
                panel.setBounds(10, 377, 598, 202);
                getContentPane().add(panel);
                panel.setLayout(new BorderLayout(0, 0));
                
                JScrollPane scrollPane = new JScrollPane();
                panel.add(scrollPane, BorderLayout.CENTER);

                JTextPane txtpnTexst = new JTextPane();
                txtpnTexst.setEditable(false);
                txtpnTexst.setText("This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 1 of the License, or (at your option) any later version.\n\n"
                                    + "This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.\n\n"
                                    + "You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/ \n");
                scrollPane.setViewportView(txtpnTexst);

      }	    
    private void Logo()
        {   
                panel = new JPanel();                
                panel.setBounds(10, 11, 234, 171);                
                getContentPane().add(panel);
                panel.setLayout(new BorderLayout(0, 0));           
                panel.setBackground(Color.black);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("OK".equals(e.getActionCommand()))
        {
            this.setVisible(false);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
     
}

    @Override
    public void mousePressed(MouseEvent e) {
         try {
            try {
                if(e.getSource().equals(website)){
                    Desktop.getDesktop().browse(new URI("http://www.csus.edu/indiv/c/colemanr/"));
                }
                else if(e.getSource().equals(email))
                {
                    Desktop.getDesktop().mail(new URI("mailto:rcoleman@csus.edu"));
                }             

            } catch (IOException ex) {
                Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
            }
    } catch (URISyntaxException ex) {
            
    }
       
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
