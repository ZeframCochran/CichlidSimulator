/*A panel to allow user to forward time during simulation.*/

package cichlid_sim.gui;

import cichlid_sim.game.PipeFromGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Forward extends JDialog implements ActionListener{

	private final JPanel contentPanel = new JPanel();
	private JTextField Hours,Minutes,Day;	
	private JPanel panel = new JPanel();
	private JRadioButton hour1,hour2,hour3;
	private ButtonGroup group = new ButtonGroup();
	/**
	 * Launch the application.
	 */
	public void init() {
		try {
			Forward dialog = new Forward();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Forward() {
		setTitle("Forward");
		setBounds(100, 100, 400, 350);
		setResizable(false);
		setModal(true);		
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		panel.setBorder(new TitledBorder(null, "Skip Forward: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(163, 49, 2, 149);
		panel.add(separator);
		radioButton();
		textField();
		buttons();
		
	}
	
	private void radioButton()
	{
		hour1 = new JRadioButton("1 Hour");
		hour1.setBounds(47, 56, 70, 23);
		panel.add(hour1);
		group.add(hour1);
		hour1.addActionListener(this);
		
		hour2 = new JRadioButton("6 Hours");
		hour2.setBounds(47, 110, 83, 23);
		panel.add(hour2);
		group.add(hour2);
		hour2.addActionListener(this);
		
		hour3 = new JRadioButton("12 Hours");
		hour3.setBounds(47, 168, 83, 23);
		panel.add(hour3);
		group.add(hour3);
		hour3.addActionListener(this);
	}
	private void textField()
	{
		 
		JLabel label = new JLabel("Minutes:");
		label.setBounds(175, 60, 62, 14);
		panel.add(label);
		
		label = new JLabel("Hours");
		label.setBounds(175, 114, 62, 14);
		panel.add(label);
		
		label = new JLabel("Day");
		label.setBounds(175, 172, 62, 14);
		panel.add(label);
		
		Minutes = new JTextField();
		Minutes.setBounds(247, 56, 34, 23);
		panel.add(Minutes);
		Minutes.setColumns(10);
		Minutes.addActionListener(this);
		
		Hours = new JTextField();
		Hours.setColumns(10);
		Hours.setBounds(247, 110, 34, 23);
		panel.add(Hours);
		Hours.addActionListener(this);
		
		Day = new JTextField();
		Day.setColumns(10);
		Day.setBounds(247, 168, 34, 23);
		panel.add(Day);
		Day.addActionListener(this);
	}
	private void buttons()
	{		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton Apply = new JButton("Apply");
		buttonPane.add(Apply);
		Apply.addActionListener(this);
	
		JButton OK = new JButton("OK");
		buttonPane.add(OK);
		getRootPane().setDefaultButton(OK);	
		OK.addActionListener(this);
	
		JButton cancelButton = new JButton("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(this);
		
	}
	private void Select()
	{
		int second=0;                
                if(!Hours.getText().isEmpty())
                    second+=Float.parseFloat(Hours.getText())*60*60;
		if(!Minutes.getText().isEmpty())
                    second+=Float.parseFloat(Minutes.getText())*60;
                if(!Day.getText().isEmpty())
                    second+=Float.parseFloat(Day.getText())*60*60*24;
                if(hour1.isSelected())
                    second+=60*60;
                if(hour2.isSelected())
                    second+=60*60*6;
                if(hour3.isSelected())
                    second+=60*60*12;
                PipeFromGUI.skipForwardInTime(second);
                
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getActionCommand())
		{
		case "OK":
			this.setVisible(false);
			break;
		case "Apply":
			Select();
			break;
		case "Cancel":
			this.setVisible(false);
			break;		
		}		
	}
}
