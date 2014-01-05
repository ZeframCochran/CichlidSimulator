package cichlid_sim.gui;
import cichlid_sim.engine.logger.Logger;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class LogReport extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField textSize = new JTextField();
	private JTextField textTemp = new JTextField();
	private JLabel myLabel;
	private JPanel centerPanel, topPanel;
	private static JScrollPane scrollPane= new JScrollPane();
        private static JTextPane Log = new JTextPane();
        private JFileChooser fc = new JFileChooser();
        JCheckBox chckbxBites = new JCheckBox("Bites");
        JCheckBox chckbxChase = new JCheckBox("Chases");
        JCheckBox chckbxShile = new JCheckBox("Shelters");
        JCheckBox chckbxError = new JCheckBox("System");
	/**
	 * Launch the application.
	 */
	public void init() {
            LogReport dialog = new LogReport();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public LogReport() {
            setModal(true);
            setResizable(false);
            setTitle("Log Report");
            setBounds(100, 100, 616, 565);
            setLocationRelativeTo(null);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(new BorderLayout(0, 0));		
            topPanel();
            centerPanel();	
            bottomPanel();			
            }
	
	private void topPanel()
	{
            topPanel = new JPanel();
            topPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tank: ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
            contentPanel.add(topPanel, BorderLayout.NORTH);

            myLabel = new JLabel("Size:");
            topPanel.add(myLabel);
            topPanel.add(textSize);
            textSize.setColumns(2);
            textSize.setText("M");
            textSize.setFocusable(false);

            myLabel = new JLabel("Temp:");
            topPanel.add(myLabel);
            topPanel.add(textTemp);
            textTemp.setColumns(3);
            textTemp.setText("54");
            textTemp.setFocusable(false);
                
	}
	
	private void centerPanel()
	{
            centerPanel = new JPanel();
            centerPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            contentPanel.add(centerPanel, BorderLayout.CENTER);
            centerPanel.setLayout(null);

            myLabel = new JLabel("Time Stamp");
            myLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            myLabel.setBounds(20, 60, 127, 26);
            centerPanel.add(myLabel);

            myLabel = new JLabel("Activities");
            myLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            myLabel.setBounds(242, 66, 134, 14);
            centerPanel.add(myLabel);

            JSeparator separator = new JSeparator();
            separator.setBounds(20, 84, 560, 2);
            centerPanel.add(separator);

            scrollPane.setBounds(20, 97, 560, 322);
            centerPanel.add(scrollPane);		

            chckbxBites.setBounds(55, 30, 97, 23);
            centerPanel.add(chckbxBites);		

            chckbxChase.setBounds(166, 30, 97, 23);
            centerPanel.add(chckbxChase);		

            chckbxShile.setBounds(281, 30, 97, 23);
            centerPanel.add(chckbxShile);	

            chckbxError.setBounds(385, 30, 150, 23);
            centerPanel.add(chckbxError);

            Log.setEditable(false);
		          
		
	}
	
	private void bottomPanel()
	{
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);		
            JButton btnPrint = new JButton("Save");
            buttonPane.add(btnPrint);
            btnPrint.addActionListener(this);
            JButton closeButton = new JButton("Close");
            closeButton.setActionCommand("Close");
            closeButton.addActionListener(this);
            buttonPane.add(closeButton);
            getRootPane().setDefaultButton(closeButton);
	}

    public static void logReports(Logger.Type type, String output)
    {       
        Log.setText(MainGUI.getDay().getText()+":"+MainGUI.getHour().getText()+":"+MainGUI.getMinute().getText()+":"+MainGUI.getSecond().getText()
                    +"\t\t\t"+type+" : "+output+"\n"+
                    Log.getText()); 
        /*NullPointerException may occur*/
        scrollPane.setViewportView(Log);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand())
        {
            case "Close":
                setVisible(false);
                break;
            case "Save":
                System.out.println("save");
                int returnVal = fc.showSaveDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();}
                break;
            default:
                    break;
        }

    }
}

