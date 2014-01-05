package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONObject;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class StockTank extends JDialog implements ActionListener, MouseListener{

	private JPanel contentPanel = new JPanel();
	private JTextField textField_1; //Name
	private JTextField textField_2; //Length
	private JTextField textField_3; //weight
	private JTextField textField_4; //color
	private JTextField textField_5; //aggr
	private JTextField textField_6; //health
	private JTextField textField_7; //breed
	private JTextField textField_8; //mate
	private JButton removeButton, okButton, importButton;
	private JLabel myLabel;
	private int marginY = 40;
	private int marginX =5;
	private int theMargin;
        private StockTankDisplay Canvas = new StockTankDisplay();  
	private JSONObject importObject = new JSONObject();  
        private Timer timer = new Timer(20,this);
     
	/**
	 * Launch the application.
	 */
        
	public void init() {
		try {
			StockTank dialog = new StockTank();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public StockTank() {
		
		setModal(true);
		setResizable(false);
		setTitle("Stock Tank");
		setSize(800, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);               
            
                Canvas.addMouseListener(this);              
                contentPanel.add(Canvas);                
               
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(new TitledBorder(null, "Attribute:  ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		statsPanel.setBounds(585, 10, 200, 500);
		contentPanel.add(statsPanel);
		statsPanel.setLayout(null);		
		myLabel(statsPanel);
		myTextField(statsPanel);
		myButton();
                timer.start();
		}

	public void myLabel(JPanel statsPanel)
	{
		myLabel = new JLabel("Name:");
		myLabel.setBounds(10, 30, 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY);
		myLabel = new JLabel("Length:");
		myLabel.setBounds(10, marginY+getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("Weight:");
		myLabel.setBounds(10, marginY+getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("Color:");
		myLabel.setBounds(10, marginY+getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("Aggr:");
		myLabel.setBounds(10, marginY+getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("Health:");
		myLabel.setBounds(10, marginY+getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("Breeding Status:");
		myLabel.setBounds(10, marginY+getTheMargin(), 110, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("Time Last Mate:");
		myLabel.setBounds(10, marginY+getTheMargin(), 110, 14);
		statsPanel.add(myLabel);
		
		//Units
		setTheMargin(marginY+marginY);
		myLabel = new JLabel("cm");
		myLabel.setBounds(marginX+106, getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("gram");
		myLabel.setBounds(marginX+106, getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+marginY+getTheMargin());
		myLabel = new JLabel("%");
		myLabel.setBounds(marginX+106, getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
		setTheMargin(marginY+getTheMargin());
		myLabel = new JLabel("%");
		myLabel.setBounds(marginX+106, getTheMargin(), 46, 14);
		statsPanel.add(myLabel);
		
	}
	
	public void myTextField(JPanel statsPanel)
	{
		textField_1 = new JTextField();
		textField_1.setBounds(marginX+66, 25, 86, 20);
                textField_1.setFocusable(false);
		statsPanel.add(textField_1);
		textField_1.setColumns(10);
		
		setTheMargin(marginY+marginY-5);
		textField_2 = new JTextField();
		textField_2.setBounds(marginX+66, getTheMargin(), 35, 20);
		textField_2.setFocusable(false);
                statsPanel.add(textField_2);
		textField_2.setColumns(10);
		
		setTheMargin(marginY+getTheMargin());
		textField_3 = new JTextField();
		textField_3.setBounds(marginX+66, getTheMargin(), 35, 20);
		textField_3.setFocusable(false);
                statsPanel.add(textField_3);
		textField_3.setColumns(10);
		
		setTheMargin(marginY+getTheMargin());
		textField_4 = new JTextField();
		textField_4.setBounds(marginX+66, getTheMargin(), 35, 20);
		textField_4.setFocusable(false);
                statsPanel.add(textField_4);
		textField_4.setColumns(10);
		
		setTheMargin(marginY+getTheMargin());
		textField_5 = new JTextField();
		textField_5.setBounds(marginX+66, getTheMargin(), 35, 20);
		textField_5.setFocusable(false);
                statsPanel.add(textField_5);
		textField_5.setColumns(10);
		
		setTheMargin(marginY+getTheMargin());
		textField_6 = new JTextField();
		textField_6.setBounds(marginX+66,getTheMargin(), 35, 20);
		textField_6.setFocusable(false);
                statsPanel.add(textField_6);
		textField_6.setColumns(10);
		
		setTheMargin(marginY+getTheMargin());
		textField_7 = new JTextField();
		textField_7.setBounds(marginX+120,getTheMargin(), 35, 20);
		textField_7.setFocusable(false);
                statsPanel.add(textField_7);
		textField_7.setColumns(10);
		
		setTheMargin(marginY+getTheMargin());
		textField_8 = new JTextField();
		textField_8.setBounds(marginX+120,getTheMargin(), 35, 20);
		textField_8.setFocusable(false);
                statsPanel.add(textField_8);
		textField_8.setColumns(10);
	}
	public void myButton()
	{
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);		
               
                removeButton = new JButton("Remove");
                removeButton.addActionListener(this);
                buttonPane.add(removeButton);
                
                importButton = new JButton("Import");
                importButton.setActionCommand("IMPORT");
                importButton.addActionListener(this);
                buttonPane.add(importButton);
                okButton = new JButton("OK");
                okButton.setActionCommand("OK");                 
                okButton.addActionListener(this);
                buttonPane.add(okButton);              
                getRootPane().setDefaultButton(okButton);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub          
            if(e.getSource().equals(okButton))
            {      
                    StockTankDisplay stock= new StockTankDisplay();
                    stock.getJSONArray().clear();
                    this.setVisible(false);
            }
            else if(e.getSource().equals(importButton))
            {
                    Canvas.importArena(importObject);                                             
            }
            else if(e.getSource().equals(removeButton))
            {
                    Canvas.RemoveFishObject(importObject);
            }
              
	}
       
	public int getTheMargin() {
		return theMargin;
	}

	public void setTheMargin(int theMargin) {
		this.theMargin = theMargin;
	}

    @Override
    public void mouseClicked(MouseEvent e) {
      
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
            StockTankDisplay stockFish = new StockTankDisplay();          
            for(int x=0;x<stockFish.getJSONArray().length();x++)
            {
               JSONObject obj = (JSONObject)stockFish.getJSONArray().get(x);
               int px = e.getX();
               int py = e.getY();          
               int xloc=(int)obj.get("posX");
               int yloc=(int)obj.get("posY");
               if((px>=xloc)&&(px<=xloc+(int)obj.get("SizeX"))&&(py>=yloc)&&(py<=yloc+(int)obj.get("SizeY")))
               {               
                     textField_1.setText((String)obj.get("Name")); //Name
                     textField_2.setText(Float.toString((float) obj.getDouble("Size"))); //Length
//                     textField_3.setText(Float.toString((float) obj.get("Weight"))); //weight
                     textField_7.setText(Integer.toString(obj.getInt("Breeding Status"))); //Breed
                     textField_5.setText(Integer.toString(obj.getInt("Aggression"))); //aggr
                     textField_6.setText(Integer.toString(obj.getInt("Health"))); //health                   
                     importObject=obj;
                     Canvas.setSelected(true);
                     Canvas.setImportFish(importObject);             
               }
            
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