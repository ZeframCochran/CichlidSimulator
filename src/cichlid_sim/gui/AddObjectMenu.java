package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONArray;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.game.objects.Tank;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;





public class AddObjectMenu extends JDialog implements ActionListener, ChangeListener{

	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	private JPanel panel_1 = new JPanel();
	private JRadioButton radioButton;
        private JButton button;        	
	private JTextField textFieldLength=new JTextField();
	private JTextField textFieldHeight=new JTextField();
	private JTextField textFieldWeight=new JTextField();
	private JTextField textFieldName=new JTextField();
	private JTextField textFieldBreed=new JTextField();
        private JTextField textFieldLastMate=new JTextField();
        private JTextField textFieldHealth=new JTextField();
        private JTextField textFieldAggr=new JTextField();
        private ArrayList<JTextField> textFieldObj = new ArrayList<>();
        private static boolean genAttribute=false;
        private static boolean isConfirm = false;
        private static JSONArray objectAttrib = new JSONArray();
        private ButtonGroup genderGroup = new ButtonGroup();
        private ButtonGroup colorGroup = new ButtonGroup();
        private ButtonGroup tankGroup = new ButtonGroup();
        private ButtonGroup objectGroup = new ButtonGroup();       
	private JLabel myLabel;   
        private JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,50); //aggression
        private JSlider slider2 = new JSlider(JSlider.HORIZONTAL,0,100,60); //Health
	private GenerateRandomObject randObject = new GenerateRandomObject(); 
        private String type;     
        private float fishSize[]={15.24f,2.54f};    //in cm
        private float fishWeight[]={1.0f,30.0f}; //in gram
	/**
	 * Launch the application.
	 */
	public void init(){
			AddObjectMenu dialog = new AddObjectMenu();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public AddObjectMenu() {
          
		setTitle("Add Object");
		setResizable(false);
		setModal(true);
		setSize(610, 400);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		//contentPanel.setLayout(null);
		contentPanel.setLayout(new BorderLayout(0,0));		
		myButton();
		myLabel();
		myTextField();		
		myObjectRadio();
		myGenderRadio();
		myAddRadio();
		myColorRadio();
		panel_1.setBounds(10, 35, 297, 255);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);              
		
	}
	private void myTextField()
	{
		//textField for Name
		textFieldName.setBounds(66, 47, 86, 20);
		panel_1.add(textFieldName);
		textFieldName.setColumns(10);
		textFieldName.setEnabled(false);
                
		//textField for Length
		textFieldLength.setBounds(66, 78, 46, 20);
		panel_1.add(textFieldLength);
		textFieldLength.setColumns(10);
		textFieldLength.setEnabled(false);
                
		//textField for Height
		textFieldHeight.setBounds(66, 108, 46, 20);
		panel_1.add(textFieldHeight);
		textFieldHeight.setColumns(10);
		textFieldHeight.setEnabled(false);
                
		//textField for Weight
		textFieldWeight.setBounds(66, 139, 46, 20);
		panel_1.add(textFieldWeight);
		textFieldWeight.setColumns(10);
		textFieldWeight.setEnabled(false);
                
		//textField for Breeding Status
//		textFieldBreed.setBounds(130, 204, 86, 20);
//		panel_1.add(textFieldBreed);
//		textFieldBreed.setColumns(10);
//		textFieldBreed.setEnabled(false);
                
		//textField for Time Last Mate
//		textFieldLastMate.setBounds(130, 234, 86, 20);
//		panel_1.add(textFieldLastMate);
//		textFieldLastMate.setColumns(10);
//		textFieldLastMate.setEnabled(false);
                
                textFieldHealth.setBounds(230,267,30,20);
                panel_1.add(textFieldHealth);
                textFieldHealth.setColumns(10);
                textFieldHealth.setEnabled(false);
                textFieldHealth.setEditable(false);
                String str=Integer.toString(slider2.getValue());
                textFieldHealth.setText(str);
           
                textFieldAggr.setBounds(230,204,30,20);
                panel_1.add(textFieldAggr);
                textFieldAggr.setColumns(10);
                textFieldAggr.setEnabled(false);
                textFieldAggr.setEditable(false);
                String str2=Integer.toString(slider.getValue());
                textFieldAggr.setText(str2);
                
                //Slider for aggression
		slider.setBounds(66, 204, 150, 23);
                slider.addChangeListener(this);
                slider2.setName("aggr");
		panel_1.add(slider);
                slider.setEnabled(false);
                
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 194, 287, 2);
		panel_1.add(separator);
              		
                //Slider for health
                slider2.setBounds(70,267,150,23);
                slider2.addChangeListener(this);
                slider2.setName("health");
                panel_1.add(slider2);
                slider2.setEnabled(false);
                
                textFieldObj.add(textFieldLength);
                textFieldObj.add(textFieldName);
                textFieldObj.add(textFieldHeight);
                textFieldObj.add(textFieldWeight);
                textFieldObj.add(textFieldHealth);
                textFieldObj.add(textFieldAggr);
	}
	private void myLabel()
	{
		
		//myLabel = new JLabel("ID ");
		//myLabel.setBounds(210, 10, 14, 14);
		//contentPanel.add(myLabel);
		
		myLabel = new JLabel("Name:");
		myLabel.setBounds(10, 50, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("Length:");
		myLabel.setBounds(10, 80, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("Height:");
		myLabel.setBounds(10, 110, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("Weight:");
		myLabel.setBounds(10, 140, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("Aggr:");
		myLabel.setBounds(10, 207, 46, 14);
		panel_1.add(myLabel);

//		myLabel = new JLabel("Breeding Status:");
//		myLabel.setBounds(10, 207, 110, 14);
//		panel_1.add(myLabel);
		
//		myLabel = new JLabel("Time Last Mate:");
//		myLabel.setBounds(10, 237, 110, 14);
//		panel_1.add(myLabel);
		
		myLabel = new JLabel("Health:");
		myLabel.setBounds(10, 267, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("cm");
		myLabel.setBounds(122, 80, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("cm");
		myLabel.setBounds(122, 110, 46, 14);
		panel_1.add(myLabel);
		
		myLabel = new JLabel("gram");
		myLabel.setBounds(122, 140, 46, 14);
		panel_1.add(myLabel);
                
                myLabel = new JLabel("%");
                myLabel.setBounds(270,267,30,20);
                panel_1.add(myLabel);
                
                myLabel = new JLabel("%");
                myLabel.setBounds(270,207,30,20);
                panel_1.add(myLabel);
	}
	
	private void myColorRadio()
	{
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Breeding Status:  ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(341, 234, 240, 56);
		contentPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		radioButton = new JRadioButton("0");
		panel.add(radioButton);
		colorGroup.add(radioButton);
		radioButton = new JRadioButton("1");
		panel.add(radioButton);
		colorGroup.add(radioButton);
		radioButton = new JRadioButton("2");
		panel.add(radioButton);
		colorGroup.add(radioButton);
		radioButton = new JRadioButton("3");
		panel.add(radioButton);
		colorGroup.add(radioButton);
		radioButton = new JRadioButton("4");
		panel.add(radioButton);
		colorGroup.add(radioButton);
		radioButton = new JRadioButton("5");
		panel.add(radioButton);
		colorGroup.add(radioButton);
		
	}
	private void myGenderRadio()
	{
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Gender:  ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(341, 102, 201, 56);
		contentPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		radioButton = new JRadioButton("Female");
		panel.add(radioButton);
		genderGroup.add(radioButton);
		radioButton = new JRadioButton("Male");
		panel.add(radioButton);
                genderGroup.add(radioButton);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(318, 35, 2, 255);
		contentPanel.add(separator_1);
	}
	
	private void myAddRadio()
	{
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add Object To:  ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(341, 169, 201, 56);
		contentPanel.add(panel);
		
                radioButton = new JRadioButton("Arena Tank");
		//radioButton.setSelected(true);
                
                radioButton.addActionListener(this);
		panel.add(radioButton);
		tankGroup.add(radioButton);
                
		radioButton = new JRadioButton("Stock Tank");
               // radioButton.setSelected(true);
                radioButton.addActionListener(this);
		panel.add(radioButton);
		tankGroup.add(radioButton);
                
		
	}
	private void myObjectRadio()
	{
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Select Object Type:  ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(341, 35, 201, 56);
		contentPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		radioButton = new JRadioButton("Fish");
		panel.add(radioButton);
		radioButton.addActionListener(this);
		objectGroup.add(radioButton);
		
		radioButton = new JRadioButton("Plant");
		panel.add(radioButton);
		radioButton.addActionListener(this);
		objectGroup.add(radioButton);
		
		radioButton = new JRadioButton("Pot");
		panel.add(radioButton);
		radioButton.addActionListener(this);
		objectGroup.add(radioButton);
		
	}
	private void myButton()
	{
		JPanel OkPanel = new JPanel();
                JPanel AutomatePanel = new JPanel();
		JPanel buttonPane = new JPanel();
                
                contentPanel.add(buttonPane, BorderLayout.SOUTH);
                buttonPane.setLayout(new BorderLayout(0,0));
                buttonPane.add(AutomatePanel, BorderLayout.WEST);
                button = new JButton("Automate");
                button.addActionListener(this);
                AutomatePanel.add(button);
                
                buttonPane.add(OkPanel, BorderLayout.EAST);        
                button = new JButton("Generate");
                button.addActionListener(this);
		OkPanel.add(button);

		button = new JButton("Close");
		button.setActionCommand("Close");
		button.addActionListener(this);
		OkPanel.add(button);
	}
	
	/*The Generate methods will first check for errors 
         *then it will call makeObjectAttrib if no errors occur*/
	private void Generate()
	{      
                /*check to see the number of radiobuttons selected and return the value*/
		int selection = 0;
		ArrayList<ButtonGroup> group = new ArrayList<>();
		group.add(tankGroup);
		group.add(genderGroup);
		group.add(colorGroup);
		Iterator<ButtonGroup> itGroup=group.iterator();
		while(itGroup.hasNext())
		{
			Enumeration<AbstractButton> elements = itGroup.next().getElements();
			while(elements.hasMoreElements())
			{
				AbstractButton radio = (AbstractButton)elements.nextElement();
				if(radio.isEnabled())
				{
					if(radio.isSelected())
					{					 
                                            selection++;
					}
				}
				else {
					radio.setEnabled(false);					
				}
			}			
		}
		
		/*Verify that user has selected the required radiobutton if the object is type Fish, else return a message dialog*/
		Enumeration<AbstractButton> fishRadio = objectGroup.getElements();
		while(fishRadio.hasMoreElements())
		{
			AbstractButton radio =(AbstractButton)fishRadio.nextElement();
			if(radio.isSelected())
			{
                        switch (radio.getLabel()) {
                            case "Fish":
                                if(selection!=3)
                                {
                                    JOptionPane.showMessageDialog(null, "Please fill in all require radio buttons ");
                                    setGenAttrib(false);
                                }
                                break;
                            case "Pot":
                                selection=3;
                                break;
                            case "Plant":
                                selection=3;
                                break;
                        }
			}
		}
		
		/*Verify that user has enter all require fields else return message dialog
		 *textFields have been added to an arrayList and will iterate each one to check for input 
                 *if there is no input values, display message diaglog */
		Iterator<JTextField> it = textFieldObj.iterator();
		while(it.hasNext())
		{
			JTextField tf = it.next();
			if(tf instanceof JTextField)
			{
				if(tf.isEnabled())
				{
				  if(tf.getText().isEmpty())
                                    {
                                        tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));           
                                    }                                                              
				  else
                                  {
					 tf.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray)); 
				  }
                                 if(selection==3)
				  {
					 setGenAttrib(true);
				  }
				 
				}

			  }        
		   }
		
		/*If there is not errors and all parameter passed then generate object attribute*/
		if(genAttribute)
		{
			makeObjectAttrib();
		}		
	}
	private void Plant()
	{
            ArrayList<ButtonGroup> al = new ArrayList<>();
            al.add(colorGroup);
            al.add(tankGroup);
            al.add(genderGroup);

            Iterator<ButtonGroup> colorIt = al.iterator();
            while(colorIt.hasNext())
            {
            Enumeration<AbstractButton> color = colorIt.next().getElements();

                while(color.hasMoreElements())
                     {
                        AbstractButton radioButton = (AbstractButton)color.nextElement();
                        if(radioButton.isEnabled())
                        {
                            radioButton.setEnabled(false);
                            textFieldLength.setEnabled(true);
                            textFieldHeight.setEnabled(false);
                            textFieldWeight.setEnabled(false);
                            textFieldWeight.setText("");
                            textFieldName.setEnabled(true);
                            textFieldName.setText("");
                            textFieldBreed.setEnabled(false);
                            textFieldBreed.setText("");
                            textFieldLastMate.setEnabled(false);
                            textFieldLastMate.setText("");
                            textFieldAggr.setEnabled(false);
                            textFieldAggr.setText("");
                            textFieldHealth.setEnabled(false);
                            textFieldHealth.setText("");
                            colorGroup.clearSelection();
                            tankGroup.clearSelection();
                            genderGroup.clearSelection();
                            slider.setEnabled(false);
                            slider2.setEnabled(false);
                        }

                     }
            }     
            type="Plant";
	}
	private void Pot()
	{				
	 ArrayList<ButtonGroup> al = new ArrayList<>();
            al.add(colorGroup);
            al.add(tankGroup);
            al.add(genderGroup);

            Iterator<ButtonGroup> colorIt = al.iterator();
            while(colorIt.hasNext())
            {
            Enumeration<AbstractButton> color = colorIt.next().getElements();

            while(color.hasMoreElements())
                 {
                    AbstractButton radioButton = (AbstractButton)color.nextElement();
                    if(radioButton.isEnabled())
                    {
                        radioButton.setEnabled(false);
                        textFieldLength.setEnabled(true);
                        textFieldHeight.setEnabled(false);
                        textFieldWeight.setEnabled(false);
                        textFieldWeight.setText("");
                        textFieldName.setEnabled(true);
                        textFieldName.setText("");
                        textFieldBreed.setEnabled(false);
                        textFieldBreed.setText("");
                        textFieldLastMate.setEnabled(false);
                        textFieldLastMate.setText("");
                        textFieldAggr.setEnabled(false);
                        textFieldAggr.setText("");
                        textFieldHealth.setEnabled(false);
                        textFieldHealth.setText("");
                        colorGroup.clearSelection();
                        tankGroup.clearSelection();
                        genderGroup.clearSelection();
                    }
                 }
            }
										   
	slider.setEnabled(false);
	slider2.setEnabled(false);
        type="Pot";
	
	}
	
	
	private void Fish()
	{	
		ArrayList<ButtonGroup> group = new ArrayList<>();
		group.add(tankGroup);
		group.add(genderGroup);
		group.add(colorGroup);
		Iterator<ButtonGroup> itGroup=group.iterator();
		while(itGroup.hasNext())
		{
			Enumeration<AbstractButton> elements = itGroup.next().getElements();
			while(elements.hasMoreElements())
			{
				AbstractButton radio = (AbstractButton)elements.nextElement();
				if(radio.isEnabled()==false)
				{
					radio.setEnabled(true);
				}
			}
			
		}
	  
		textFieldLength.setEnabled(true);
		textFieldHeight.setEnabled(false);
		textFieldWeight.setEnabled(true);
		textFieldName.setEnabled(true);
		textFieldBreed.setEnabled(true);
		textFieldLastMate.setEnabled(true);
		textFieldHealth.setEnabled(true);
		textFieldAggr.setEnabled(true);             
		slider.setEnabled(true);
		slider2.setEnabled(true);		
                type="Fish";
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
          
            switch (e.getActionCommand()) {
                    case "Automate":
                            randObject.init();
                    case "Fish":
                            Fish();
                            break;
                    case "Pot":
                            Pot();
                            break;
                    case "Plant":
                            Plant();
                            break;
                    case "Generate":
                            Generate();
                            break;
                    case "Close":
                            objectAttrib.clear();
                            this.setVisible(false);
                            break;
            }
        }
        
        /*Creates the object attribute base on object type and put object's attributes inside JSON Object*/       
	private void makeObjectAttrib()
        { 
        switch (type) {
            case "Fish":
                try{
                  /*Enumerates through each ButtonGroup that holds JRadioButton variables
                    to get user's input*/
                   StockTankDisplay canvas = new StockTankDisplay();   
                  JSONObject obj = new JSONObject();
                  Enumeration<AbstractButton> gender = genderGroup.getElements();
                  while(gender.hasMoreElements())
                  {
                        AbstractButton radioButton = (AbstractButton)gender.nextElement();
                        if(radioButton.isSelected())
                        {
                            if(radioButton.getText().equals("Female"))
                                obj.put("Gender", new Boolean (false));
                            else
                                obj.put("Gender", new Boolean (true));
                        }
                  }
                  Enumeration<AbstractButton> color = colorGroup.getElements();
                  while(color.hasMoreElements())
                  {
                          AbstractButton radioButton = (AbstractButton)color.nextElement();
                          if(radioButton.isSelected())
                          {
                             obj.put("Breeding Status",Integer.parseInt(radioButton.getText()));
                          }
                  }
                  Enumeration<AbstractButton> tank = tankGroup.getElements();                  
                  while(tank.hasMoreElements())
                  {
                          AbstractButton radioButton = (AbstractButton)tank.nextElement();
                          if(radioButton.isSelected())
                          {                                  
                             obj.put("Tank", mapTankTextToTankType(radioButton.getText()));
                             if(obj.get("Tank").equals(Tank.Type.STOCK))
                             {
                                  if(canvas.getMax()>=1)
                                      {                                  
                                          int x = canvas.getMax();
                                          x--;
                                          canvas.setMax(x);                                                
                                      }                                        
                              }
                          }
                  }
                  
                  /*Check for upper bound and lower bound for fish size*/
                  if((float)Float.parseFloat(textFieldLength.getText())>=fishSize[1]&&(float)Float.parseFloat(textFieldLength.getText())<=fishSize[0])
                  {
                      if(!textFieldName.getText().isEmpty())
                      {
                          if((float)Float.parseFloat(textFieldWeight.getText())>=fishWeight[0]&&(float)Float.parseFloat(textFieldWeight.getText())<=fishWeight[1])
                          {
                            obj.put("Size", new Float(Float.parseFloat(textFieldLength.getText())));
                        //  obj.put("Height", new Float(Float.parseFloat(textFieldHeight.getText())));
                            obj.put("Weight", new Float(Float.parseFloat(textFieldWeight.getText())));
                            obj.put("Aggression", new Integer(Integer.parseInt(textFieldAggr.getText())));
                            obj.put("Health", Integer.parseInt(textFieldHealth.getText()));                     
                            obj.put("Name",textFieldName.getText());             
                            obj.put("Type", "FISH");
                            setGenAttrib(true); 
                          }
                          else
                          {
                              JOptionPane.showMessageDialog(this,
                           "Fish's weight should be between 1.0g to 30.0g ","Warning",
                           JOptionPane.WARNING_MESSAGE);                       
                           setGenAttrib(false); 
                          }
                      
                      }
                      else
                      {
                           JOptionPane.showMessageDialog(this,
                           "Name Field is Empty","Warning",
                           JOptionPane.WARNING_MESSAGE);                       
                           setGenAttrib(false); 
                      }
                  }
                  else
                  {                      
                      JOptionPane.showMessageDialog(this,
                      "Fish should be between 2.54cm to 15.24cm ","Warning",
                      JOptionPane.WARNING_MESSAGE);                       
                      setGenAttrib(false); 
                  }
                 
                  /*Check if the stock tank is full*/
                  if(obj.get("Tank").equals(Tank.Type.STOCK)&&canvas.getMax()==0)
                  {                           
                      obj.clear();                         
                      JOptionPane.showMessageDialog(this,
                      "Your Stock Tank is Full.",
                      "Warning",
                      JOptionPane.WARNING_MESSAGE);                                       
                      int x = canvas.getMax();
                      x++;
                      canvas.setMax(x);                     
                  }
                  /*adds the object to JSONArray and calls the PipeInit()*/
                  else if(isGenAttrib())
                  {PipeInit.AttribsToPipe(obj);                 
                  }                  
           }
           catch(NumberFormatException nfe){
                  JOptionPane.showMessageDialog(null, "Invalid Entry ");
                  objectGroup.clearSelection();
                  setGenAttrib(false);}
                break;
                
            case "Plant":
                try{
                JSONObject obj = new JSONObject();
                obj.put("Name", textFieldName.getText());
                obj.put("Size", new Integer(Integer.parseInt(textFieldLength.getText())));
               // obj.put("Height", new Integer(Integer.parseInt(textFieldHeight.getText())));
                objectAttrib.put(obj);
                obj.put("Type","PLANT");               
                PipeInit.AttribsToPipe(obj);

                }catch(NumberFormatException nfe)
                {
                        JOptionPane.showMessageDialog(null, "Invalid Entry ");
                        objectGroup.clearSelection();
                        setGenAttrib(false);                         
                }
                break;
            case "Pot":
                try{
                JSONObject obj = new JSONObject();
                obj.put("Name", textFieldName.getText());
                obj.put("Size", new Integer(Integer.parseInt(textFieldLength.getText())));
               // obj.put("Height", new Integer(Integer.parseInt(textFieldHeight.getText())));
                objectAttrib.put(obj);
                obj.put("Type","POT");               
                PipeInit.AttribsToPipe(obj);
                }catch(NumberFormatException nfe)
                {
                        JOptionPane.showMessageDialog(null, "Invalid Entry ");
                        objectGroup.clearSelection();
                        setGenAttrib(false);                   
                }
                break;
        }
	}
        
        @Override
        public void stateChanged(ChangeEvent e) {        
            Object source=e.getSource();
            if(source instanceof JSlider)
            {
                JSlider thisSlider =(JSlider)source;
                String name=thisSlider.getName();
                if("health".equals(name))
                {
                    String str=Integer.toString(slider2.getValue());
                    textFieldHealth.setText(str);
                }
                else
                {
                    String str=Integer.toString(slider.getValue());
                    textFieldAggr.setText(str);
                }
            }
        }
        /*Setters and Getters*/
        public JSONArray getJSONObjectAttrib()
        {
                return objectAttrib;
        }
        public Boolean isGenAttrib()
        {
                return genAttribute;
        }
        public Boolean setGenAttrib(Boolean b)
        {
                genAttribute = b;
                return genAttribute;
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
        
        /**
         * This method converts tank type from human-text to the game-type required
         * by the game world.
         * 
         * @author Wesley Perry
         * 
         * @param tankText The text as it appears on the GUI.
         * @return The Tank.Type corresponding to the provided text.
         */
        private Tank.Type mapTankTextToTankType(String tankText) {
            switch(tankText) {
                case "Arena Tank" : return Tank.Type.ARENA;
                case "Stock Tank" : return Tank.Type.STOCK;
                case "Isolation Tank" : return Tank.Type.ISOLATION;
                default : throw new RuntimeException("Unsupported Tank Type: " + tankText);
            }
        }

}

                       
	