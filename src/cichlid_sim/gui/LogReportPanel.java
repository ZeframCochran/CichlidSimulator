package cichlid_sim.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class LogReportPanel extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField textSize = new JTextField();
	private JTextField textTemp = new JTextField();
	private JLabel myLabel;
	private JPanel centerPanel, topPanel;
	JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public void init() {
		try {
			LogReportPanel dialog = new LogReportPanel();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LogReportPanel() {
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
	
	public void topPanel()
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
	
	public void centerPanel()
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 97, 560, 322);
		centerPanel.add(scrollPane);
		
		JTextPane Log = new JTextPane();
                Log.setEditable(false);
		Log.setText("10:05:07 \t\t\t Fish A bites Fish B\n" +
                            "11:55:04 \t\t\t Fish A chases Fish C");
                
                
		scrollPane.setViewportView(Log);
	}
	
	public void bottomPanel()
	{
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnPrint = new JButton("Save");
		buttonPane.add(btnPrint);
		
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
                okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "OK") {
            this.setVisible(false);
        }
    }
	}

