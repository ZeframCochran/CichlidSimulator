package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONArray;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.game.PipeToGUI;
import cichlid_sim.game.objects.Tank;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;


public class GenerateRandomObject extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;	
        private JCheckBox chckbxPot,chckbxPlant,chckbxFish;
	private static JSONArray objectAttrib = new JSONArray();
	private JButton button;
	private static boolean genAttribute=false;
        private static boolean isConfirm=false;
	private Random random = new Random();
        private NameGen objectName;
        private float fishSize[]={15.24f,2.54f};      
      
	/**
	 * Launch the application.
	 */
	public void init() {
		try {
			GenerateRandomObject dialog = new GenerateRandomObject();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
        
                
	public GenerateRandomObject() {
            setTitle("Object Generator");
            setResizable(false);
            setModal(true);
            setSize(346,367);
            setLocationRelativeTo(null);

            //setBounds(100, 100, 346, 367)
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);
            contentPanel.setLayout(new BorderLayout(0, 0));

            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "Selection: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            contentPanel.add(panel, BorderLayout.CENTER);
            panel.setLayout(null);

            JLabel myLabel = new JLabel("Object Type:");
            myLabel.setBounds(48, 53, 117, 14);
            panel.add(myLabel);

            myLabel = new JLabel("# of Objects:");
            myLabel.setBounds(190, 53, 86, 14);
            panel.add(myLabel);

            myButton();
            myCheckBox(panel);
            myTextField(panel);
	}
	
	private void myButton()
	{
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            button = new JButton("Generate");
            buttonPane.add(button);
            button.addActionListener(this);

            button = new JButton("Close");
            button.setActionCommand("Close");
            buttonPane.add(button);
            button.addActionListener(this);
	}
	private void myCheckBox(JPanel panel)
	{           
            chckbxFish = new JCheckBox("Fish");
            chckbxFish.setBounds(58, 74, 97, 23);
            chckbxFish.addActionListener(this);
            panel.add(chckbxFish);
            chckbxPot = new JCheckBox("Pot");
            chckbxPot.setBounds(58, 128, 97, 23);
            chckbxPot.addActionListener(this);
            panel.add(chckbxPot);
            chckbxPlant = new JCheckBox("Plant");
            chckbxPlant.setBounds(58, 179, 97, 23);
            chckbxPlant.addActionListener(this);
            panel.add(chckbxPlant);
	}
	
	private void myTextField(JPanel panel)
	{
            textField = new JTextField();
            textField.setEnabled(false);
            textField.setBounds(190, 75, 30, 20);
            panel.add(textField);
            textField.setColumns(10);

            textField_1 = new JTextField();
            textField_1.setEnabled(false);
            textField_1.setColumns(10);
            textField_1.setBounds(190, 129, 30, 20);
            panel.add(textField_1);

            textField_2 = new JTextField();
            textField_2.setEnabled(false);
            textField_2.setColumns(10);
            textField_2.setBounds(190, 180, 30, 20);
            panel.add(textField_2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
            switch(e.getActionCommand())
            {              
                case"Close":   
                    objectAttrib.clear();
                    this.setVisible(false);
                    break;
                case"Generate":
                    gButton();
                    break;
                case"Pot":
                     if(chckbxPot.isSelected())
                    textField_1.setEnabled(true);
                    else
                    textField_1.setEnabled(false);
                    break;
                case"Plant":
                    if(chckbxPlant.isSelected())
                    textField_2.setEnabled(true);
                    else
                    textField_2.setEnabled(false);
                    break;
                case"Fish":
                    if(chckbxFish.isSelected()){
                    textField.setEnabled(true);                    
                    }
                    else
                    textField.setEnabled(false);
                    break;
                default:
                    break;
            }
		
	}
        /*Takes user input to generate randomize attributes for each object selected
         *parse each input to check for validation, return dialog message for incorrect input type 
         */
        private void gButton()
        {
            if(textField.getText().isEmpty()&&textField.isEnabled())
            {
              setGenAttrib(false);                                
              JOptionPane.showMessageDialog(null, "Please fill in the field ");   
            }
            else if(chckbxFish.isSelected())
            {
                try{                                                            
                   for(int x=0;x<Integer.parseInt(textField.getText());x++)
                    {
                    generateFishAttrib();
                    }
                    setGenAttrib(true);                                
                }catch(NumberFormatException nfe)
                {
                    setGenAttrib(false);                                
                    JOptionPane.showMessageDialog(null, "Invalid entry: " + nfe);                           
                }
              }    
            if(textField_1.getText().isEmpty()&&textField_1.isEnabled())
            {
                setGenAttrib(false);                                
                JOptionPane.showMessageDialog(null, "Please fill in the field ");  
            }
            else if(chckbxPot.isSelected())
            {
                try{                                                            
                    for(int x=0;x<Integer.parseInt(textField_1.getText());x++)
                    {
                    generatePotAttrib();
                    }
                    setGenAttrib(true);
                }catch(NumberFormatException nfe)
                {
                    setGenAttrib(false);
                    JOptionPane.showMessageDialog(null, "Invalid entry: " + nfe);                           
                }
            }
            if(textField_2.getText().isEmpty()&&textField_2.isEnabled())
            {
               setGenAttrib(false);                                
               JOptionPane.showMessageDialog(null, "Please fill in the field ");   
            }
            else if(chckbxPlant.isSelected())
            {
                try{
                    for(int x=0;x<Integer.parseInt(textField_2.getText());x++)
                    {
                    generatePlantAttrib();
                    }
                    setGenAttrib(true);
                }catch(NumberFormatException nfe)
                {
                    setGenAttrib(false);
                    JOptionPane.showMessageDialog(null, "Invalid entry: " + nfe);                           
                }
            }
        }
        /*Generating Fish Attributes and adding them to JSONArray*/
	private void generateFishAttrib()
	{     
            objectName = new NameGen();
            JSONObject obj = new JSONObject();
            boolean gender[] = {false,true};  
            obj.put("Name", objectName.getName());
            obj.put("Type", "FISH");
            obj.put("Tank", Tank.Type.ARENA);
            obj.put("Size",new Float(random.nextFloat()*(fishSize[0]-fishSize[1])+fishSize[1]));
            obj.put("Weight",new Integer(random.nextInt(50 - 10) + 10));
            obj.put("Aggression",new Integer(random.nextInt(50 - 10) + 10));
            obj.put("Health",new Integer(random.nextInt(100 - 90) + 90));
            obj.put("Gender", gender[random.nextInt(2)]);           
            obj.put("Breeding Status", new Integer(random.nextInt(6)));        
            PipeInit.AttribsToPipe(obj);            
	}
        /*Generating Plant Attributes and adding them to JSONArray*/
	private void generatePlantAttrib()
	{           
            JSONObject obj = new JSONObject();          
            obj.put("Type", "PLANT");    
            obj.put("Name", "Plant"); //Testing
            obj.put("Length",new Integer(random.nextInt(50 - 10) + 10));
            obj.put("Height",new Integer(random.nextInt(50 - 10) + 10));          
            PipeInit.AttribsToPipe(obj);     

	}
        /*Generating Pot Attributes and adding them to JSONArray*/
	private void generatePotAttrib()
	{   
            JSONObject obj = new JSONObject();                   
            obj.put("Type", "POT");  
            obj.put("Name", "Pot"); //Testing
            obj.put("Length",new Integer(random.nextInt(50 - 10) + 10));
            obj.put("Height",new Integer(random.nextInt(50 - 10) + 10));              
            PipeInit.AttribsToPipe(obj);

	}
        /*Setters and Getters*/
	public Boolean isGenAttrib()
	{
		return genAttribute;
	}
	public Boolean setGenAttrib(Boolean b)
	{
		genAttribute = b;
		return genAttribute;
	}
        public JSONArray getJSONArrayAttrib()
	{
		return objectAttrib;
	}
        public boolean isConfirm()
        {
            return isConfirm;
        }
        public boolean setConfirm(boolean d)
        {
            isConfirm = d;
            return isConfirm;
        }
}


