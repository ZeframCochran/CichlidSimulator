package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.game.PipeFromGUI;
import cichlid_sim.game.PipeToGUI;
import cichlid_sim.game.objects.Tank;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.AbstractButton;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditMenu extends JDialog implements ActionListener, ChangeListener {
        private static JButton  Apply;
	private final JPanel contentPanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();        
	private ButtonGroup group1 = new ButtonGroup();
	private static ButtonGroup group2 = new ButtonGroup();
	private static JTextField lengthField = new JTextField();
	private static JTextField weightField = new JTextField();
	private static JTextField healthField = new JTextField();
	private static JTextField aggrField = new JTextField();
        private static JTextField nameField = new JTextField();
	private static JRadioButton radioButton1 = new JRadioButton("0");
	private static JRadioButton radioButton2 = new JRadioButton("2");
	private static JRadioButton radioButton3 = new JRadioButton("3");
	private static JRadioButton radioButton4 = new JRadioButton("4");
	private static JRadioButton radioButton5 = new JRadioButton("5");
	private static JRadioButton radioButton6 = new JRadioButton("6");
        private static JRadioButton toStockTank = new JRadioButton("To Stock Tank");
        private static JRadioButton toIsolationTank = new JRadioButton("To Isolation Tank");
        private static JRadioButton fromTheSystem = new JRadioButton("From the System");
        private static JRadioButton toArenaTank = new JRadioButton("To Arena Tank");
	private JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        private static JSONObject object;        
        private static int tankInvoker; //specify which tank invoke EditMenu, Arena or Isolation tank.
        private IsolationTankDisplayLeft isoTank = new IsolationTankDisplayLeft();
        private IsolationTankDisplayRight isoTank2 = new IsolationTankDisplayRight();
        private float objectSize[]={2.54f,15.24f}; 
	/**
	 * Launch the application.
	 */
	public static void init(JSONObject obj, int i) {
		try {
                        tankInvoker=i;
			EditMenu dialog = new EditMenu(obj);
                        object=obj;
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
                        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
      
	public EditMenu(JSONObject obj) {
                
		setResizable(false);	
		setTitle("Edit Menu");
		setSize(545, 484);
                setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
	
		rightPanel();
		leftPanel();
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

                JButton cancelButton = new JButton("Close");
                cancelButton.setActionCommand("Close");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(this);
                
                displayAttribute(obj);
	}
	private void leftPanel()
	{
		leftPanel.setBorder(new TitledBorder(null, "Move Object: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leftPanel.setPreferredSize(new Dimension(200, 300));
		contentPanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(null);                
                toStockTank.setBounds(40, 47, 154, 23);
                leftPanel.add(toStockTank);
              
                toIsolationTank.setBounds(40, 91, 154, 23);
                leftPanel.add(toIsolationTank);
                
                toArenaTank.setBounds(40, 134, 154, 23);
                leftPanel.add(toArenaTank);
                
                fromTheSystem.setBounds(40, 177, 154, 23);
                leftPanel.add(fromTheSystem);
                
                Apply = new JButton("Apply");
                Apply.setBounds(40, 220, 89, 23);
                Apply.addActionListener(this);
                leftPanel.add(Apply);
                group1.add(fromTheSystem);
                group1.add(toIsolationTank);
                group1.add(toStockTank);
                group1.add(toArenaTank);
		}
	
	private void rightPanel()
	{
		
		rightPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Edit: ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		rightPanel.setPreferredSize(new Dimension(300, 400));
		contentPanel.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(null);
		
		slider.setEnabled(true);
		slider.setBounds(78, 216, 145, 26);
		rightPanel.add(slider);
                slider.addChangeListener(this);
		
		BreedingStatus();
		myTextField();
		myLabel();
                
                JButton Update = new JButton("Update");
		Update.setBounds(104, 368, 89, 23);
		rightPanel.add(Update);
                Update.addActionListener(this);
		
	}
	private void BreedingStatus()
	{
		JLabel Color = new JLabel("Breeding Status:");
		Color.setBounds(22, 272, 100, 14);
		rightPanel.add(Color);

		radioButton1.setEnabled(true);
		radioButton1.setBounds(32, 293, 36, 23);
		rightPanel.add(radioButton1);
		
		radioButton2.setEnabled(true);
		radioButton2.setBounds(70, 293, 36, 23);
		rightPanel.add(radioButton2);
		
		radioButton3.setEnabled(true);
		radioButton3.setBounds(116, 293, 36, 23);
		rightPanel.add(radioButton3);
		
		radioButton4.setEnabled(true);
		radioButton4.setBounds(154, 293, 39, 23);
		rightPanel.add(radioButton4);
		
		radioButton5.setEnabled(true);
		radioButton5.setBounds(195, 293, 36, 23);
		rightPanel.add(radioButton5);
		
		radioButton6.setEnabled(true);
		radioButton6.setBounds(233, 293, 42, 23);
		rightPanel.add(radioButton6);
		
		group2.add(radioButton1);
		group2.add(radioButton2);
		group2.add(radioButton3);
		group2.add(radioButton4);
		group2.add(radioButton5);
		group2.add(radioButton6);
		
	}
	private void myTextField()
	{
                //Name field            	
		nameField.setBounds(99, 30, 81, 20);
                nameField.setColumns(10);
		rightPanel.add(nameField);
                nameField.setEnabled(true);
                
		//textfield for Length
		lengthField.setBounds(81, 81, 46, 20);
		rightPanel.add(lengthField);
		lengthField.setColumns(10);
		lengthField.setEnabled(true);
		
		//textfield for height
		healthField.setColumns(10);
		healthField.setBounds(81, 126, 46, 20);
		rightPanel.add(healthField);
		healthField.setEnabled(true);
		
		//textfield for width
		weightField.setColumns(10);
		weightField.setBounds(81, 173, 46, 20);
		rightPanel.add(weightField);
		weightField.setEnabled(true);
		
		//textfield for aggr
		aggrField.setBounds(233, 216, 29, 20);
                aggrField.setEditable(false);
		rightPanel.add(aggrField);
		aggrField.setColumns(10);
		aggrField.setEnabled(true);
	}
	private void myLabel()
	{
		JLabel myLabel = new JLabel("Name:");
		myLabel.setBounds(22, 32, 69, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("Size:");
		myLabel.setBounds(22, 84, 69, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("Health:");
		myLabel.setBounds(22, 129, 69, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("Weight:");
		myLabel.setBounds(22, 176, 55, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("Aggr:");
		myLabel.setBounds(22, 216, 46, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("cm");
		myLabel.setBounds(134, 84, 46, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("%");
		myLabel.setBounds(137, 129, 46, 14);
		rightPanel.add(myLabel);
		
		myLabel = new JLabel("gram");
		myLabel.setBounds(137, 176, 46, 14);
		rightPanel.add(myLabel);
	
		myLabel = new JLabel("%");
		myLabel.setBounds(268, 216, 22, 14);
		rightPanel.add(myLabel);
	}

        private static void ClearAttribute()
        {
                healthField.setText("");
                weightField.setText("");
                aggrField.setText("");                        
        }
        private void MoveFishObject()
        {
            if(toStockTank.isSelected()){
                if(tankInvoker==1)
                {
                object.put("Tank", Tank.Type.STOCK);
                PipeFromGUI.updateGameWorldObject(object);
                }
                else if(tankInvoker==2){                   
                    isoTank.importTOGameWorld(object,"STOCK");
                    isoTank2.importTOGameWorld(object,"STOCK");
                }
            }                 
            if(toIsolationTank.isSelected()){                
                if(tankInvoker==1)
                {
                object.put("Tank", Tank.Type.ISOLATION);
                PipeFromGUI.updateGameWorldObject(object);               
                isoTank.displayFishInISO();
                isoTank2.displayFishInISO();                
                }
                else{}                             
            }
            if(toArenaTank.isSelected()){
                if(tankInvoker==1)
                {
                object.put("Tank", Tank.Type.ARENA);
                PipeFromGUI.updateGameWorldObject(object);
                }
                else if(tankInvoker==2)
                {  
                    isoTank.importTOGameWorld(object,"ARENA");
                    isoTank2.importTOGameWorld(object,"ARENA");}
            }
            if(fromTheSystem.isSelected()){
                if(tankInvoker==1)
                {
                if(object.has("ID")) 
                {                    
                    int result=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?","Confirm Deletion",JOptionPane.YES_NO_OPTION);
                    if(result==JOptionPane.YES_OPTION)                          
                    PipeFromGUI.removeObjectFromGameWorld(object.getInt("ID")); 
                }
                object.clear();}  
            else if(tankInvoker==2){ System.out.println("this delete");              
                isoTank.RemoveFishObject(object);
                isoTank2.RemoveFishObject(object);}
            }
        }
        
        private void UpdateObject()
        {
            if(object.get("Type").toString().equals("FISH"))
            {
                if(errorChecker("fish"))
                {
                object.put("Health", Float.parseFloat(healthField.getText()));
                object.put("Size", Float.parseFloat(lengthField.getText()));
                object.put("Weight", Float.parseFloat(weightField.getText()));
                object.put("Aggression", Float.parseFloat(aggrField.getText()));
                object.put("Name", nameField.getText());
                Enumeration<AbstractButton> breedStatus = group2.getElements();
                while(breedStatus.hasMoreElements())
		{
			AbstractButton radio =(AbstractButton)breedStatus.nextElement();
                        if(radio.isSelected())
                            object.put("Breeding Status", Float.parseFloat(radio.getText()));
                }               
                PipeFromGUI.updateGameWorldObject(object);
                }
            }
            else{
                if(errorChecker("pot"))
                {                    
                    object.put("Name", nameField.getText());
                    object.put("Size",Float.parseFloat(lengthField.getText()));               
                    PipeFromGUI.updateGameWorldObject(object);                                        
                }
            }
        }
        private static void displayAttribute(JSONObject object)
        {
            if(object.has("Type"))
            {
                if(object.get("Type").toString().equals("FISH"))
                {
                    Apply.setEnabled(true);
                    toStockTank.setEnabled(true);
                    toArenaTank.setEnabled(true);
                    toIsolationTank.setEnabled(true);
                    nameField.setText(object.getString("Name"));
                    healthField.setText(Float.toString((float) object.getInt("Health")));
                    lengthField.setText(Float.toString((float) object.getInt("Size")));
                    weightField.setText(Float.toString((float) object.getInt("Weight")));
                    aggrField.setText(Float.toString((float)object.getInt("Aggression")));
                    switch(object.getInt("Breeding Status"))
                    {
                        case 0:radioButton1.setSelected(true);
                            break;
                        case 1:radioButton2.setSelected(true);
                            break;
                        case 2:radioButton3.setSelected(true);
                            break;
                        case 3:radioButton4.setSelected(true);
                            break;
                        case 4:radioButton5.setSelected(true);
                            break;
                        case 5:radioButton6.setSelected(true);
                            break;                       
                    }
                    if(object.get("Tank").equals("ISOLATION"))
                    {
                        System.out.println("ISO");
                    }
                }
                else
                {
                    ClearAttribute();
                    toStockTank.setEnabled(false);
                    toArenaTank.setEnabled(false);
                    toIsolationTank.setEnabled(false);
                    Apply.setEnabled(true);               
                    nameField.setText(object.getString("Name"));
                    lengthField.setText(Float.toString((float) object.getInt("Size")));
                   // heightField.setText(Float.toString((float) object.getInt("Size")));                
                }
            }
            
        }
        
        
	@Override
	public void actionPerformed(ActionEvent e) {        
          
            switch(e.getActionCommand())
            {
                case "Update":
                    UpdateObject();
                    break;
                case "Close":
                    if(tankInvoker==2)
                    {isoTank.setSelected(false);
                     isoTank2.setSelected(false);}
                    ClearAttribute();
                    setVisible(false);
                    break;                        
                case "Apply":                    
                    MoveFishObject();
                    break;
                default:
                    break;                    
            }	
	}

    @Override
    public void stateChanged(ChangeEvent e) {
         Object source=e.getSource();
            if(source instanceof JSlider)
            {            
                String str=Integer.toString(slider.getValue());
                aggrField.setText(str);
             }
}
    
    private boolean errorChecker(String name)
    {
        try{
        if(name=="Fish")
        {
            if((float)Float.parseFloat(lengthField.getText())>objectSize[0]&&(float)Float.parseFloat(lengthField.getText())<objectSize[1])
            {
                 if((float)Float.parseFloat(healthField.getText())<=100)
                 {
                     if((float)Float.parseFloat(weightField.getText())<=30)
                     {
                         return true;
                     }
                     else
                     {
                         JOptionPane.showMessageDialog(this,
                         "Invalid Input, Please check your range ","Warning",
                         JOptionPane.WARNING_MESSAGE); 
                         return false;
                     }
                 }
            }
        }
        else
        {
            if((float)Float.parseFloat(lengthField.getText())>objectSize[0]&&(float)Float.parseFloat(lengthField.getText())<objectSize[1])
            {
                return true;
            }
            else
            {
               JOptionPane.showMessageDialog(this,
               "Invalid Input, Please check your range ","Warning",
               JOptionPane.WARNING_MESSAGE); 
               return false;
            }
        }
         }catch(java.lang.NumberFormatException nfe){
         JOptionPane.showMessageDialog(null, "Invalid Entry ");
         return false;}       
        return false;
    }
}