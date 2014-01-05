package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONArray;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.game.PipeFromGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class MainGUI extends JFrame implements ActionListener, WindowListener, KeyListener {

    /**
     * Launch the application.
     */
    private StockTank myStockTank = new StockTank();
    private About myAbout = new About();
    private LogReport myReport = new LogReport();
    private KeyCommands myKeys = new KeyCommands();
    private AddObjectMenu myOjectMenu = new AddObjectMenu();
    private JPanel tankPanel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JButton stockTankButton, addObjectButton, pauseButton, logReportButton, startSimulationButton,
            fastTimeButton, slowTimeButton, quitButton;
    private JMenuItem myMenuItem;
    private JMenu myMenu;
    private JLabel size, temp, time;
    private static JTextField textTemp, textTime, textHour, textMinute, textSecond, textSpeed, textDay;
    private JRadioButton smallTank, mediumTank, largeTank;
    private JFileChooser fc = new JFileChooser();
    private Timer timer = new Timer(20, this);
    private static float[] tankSize = {0, 0, 0};
    private IsolationTankDisplayLeft isoTank1 = new IsolationTankDisplayLeft();
    private IsolationTankDisplayRight isoTank2 = new IsolationTankDisplayRight();
    public JButton getStockTank() {
        return stockTankButton;
    }

    public void setStockTank(JButton stockTankButton) {
        this.stockTankButton = stockTankButton;
    }

    /*  public static void main(String[] args)  {
     EventQueue.invokeLater(new Runnable() {
     public void run() {
     try {
     MainGUI frame = new MainGUI();
     frame.setVisible(true);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }
     });
     }   

     /**
     * Create the frame.
     */
    public MainGUI() {
        //  addWindowListener(this);
        setTitle("Cichlid Behavior Research Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(0, 0));
        leftPanel();
        centerPanel(0, 0);
        bottomPanel();
        menuItem();
        topPanel();
        setVisible(true);
        timer.start();
        //BufferUtils.setTrackDirectMemoryEnabled(true);

    }

    public JPanel getTankPanel() {
        return this.tankPanel;
    }

    private void topPanel() {

        getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        time = new JLabel("Clock: ");
        topPanel.add(time);
        Font font = new Font("Verdana", Font.BOLD, 24);

        time = new JLabel("D");
        textDay = new JTextField(2);
        textDay.setText("0");
        textDay.setPreferredSize(new Dimension(20, 30));
        textDay.setFont(font);
        textDay.setHorizontalAlignment(JTextField.CENTER);
        textDay.setFocusable(false);
        topPanel.add(textDay);
        topPanel.add(time);

        time = new JLabel("H");
        textHour = new JTextField(2);
        textHour.setText("0");
        textHour.setPreferredSize(new Dimension(20, 30));
        textHour.setFont(font);
        textHour.setFocusable(false);
        textHour.setHorizontalAlignment(JTextField.CENTER);
        topPanel.add(textHour);
        topPanel.add(time);

        time = new JLabel("M");
        textMinute = new JTextField(2);
        textMinute.setText("0");
        textMinute.setPreferredSize(new Dimension(20, 30));
        textMinute.setFont(font);
        textMinute.setFocusable(false);
        textMinute.setHorizontalAlignment(JTextField.CENTER);
        topPanel.add(textMinute);
        topPanel.add(time);

        time = new JLabel("S              ");
        textSecond = new JTextField(2);
        textSecond.setPreferredSize(new Dimension(20, 30));
        textSecond.setFont(font);
        textSecond.setFocusable(false);
        textSecond.setHorizontalAlignment(JTextField.CENTER);
        topPanel.add(textSecond);
        topPanel.add(time);

        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(100, 40));
        pauseButton.setEnabled(true);
        pauseButton.addActionListener(this);
        pauseButton.addKeyListener(this);
        topPanel.add(pauseButton);

        fastTimeButton = new JButton("Forward");
        fastTimeButton.setPreferredSize(new Dimension(100, 40));
        fastTimeButton.addActionListener(this);
        fastTimeButton.addKeyListener(this);
        topPanel.add(fastTimeButton);

        time = new JLabel("  Speed:");   
        topPanel.add(time);  
        textSpeed = new JTextField(3); 
        textSpeed.setEditable(false);
        topPanel.add(textSpeed);
        
    }

    private void leftPanel() {

        leftPanel.setPreferredSize(new Dimension(150, 0));
        JPanel commandPanel = new JPanel();
        JPanel optionPanel = new JPanel();

        commandPanel.setBorder(new TitledBorder("Commands: "));
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));

        addObjectButton = new JButton("Add Object");
        addObjectButton.addActionListener(this);
        addObjectButton.addKeyListener(this);
        commandPanel.add(addObjectButton);

        stockTankButton = new JButton("Stock Tank");
        stockTankButton.addActionListener(this);
        stockTankButton.addKeyListener(this);

        logReportButton = new JButton("Log Report");
        logReportButton.addKeyListener(this);
        logReportButton.addActionListener(this);

        commandPanel.add(addObjectButton);
        commandPanel.add(stockTankButton);
        commandPanel.add(logReportButton);

        optionPanel.setBorder(new TitledBorder("Tank Options: "));
        //optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        temp = new JLabel("Temp: ");
        optionPanel.add(temp);
        textTemp = new JTextField(3);

        optionPanel.add(textTemp);
        temp = new JLabel("°C ");
        optionPanel.add(temp);

        size = new JLabel("Tank Size:        ");
        optionPanel.add(size);

        ButtonGroup group = new ButtonGroup();
        smallTank = new JRadioButton("Small");        
        optionPanel.add(smallTank);
        smallTank.addActionListener(this);
        group.add(smallTank);

        mediumTank = new JRadioButton("Medium   ");
        optionPanel.add(mediumTank);
        mediumTank.addActionListener(this);
        group.add(mediumTank);

        largeTank = new JRadioButton("Large");
        optionPanel.add(largeTank);
        largeTank.addActionListener(this);
        group.add(largeTank);

        startSimulationButton = new JButton("Update Tank");
        startSimulationButton.addActionListener(this);
        optionPanel.add(startSimulationButton);

        leftPanel.setLayout(new BorderLayout(0, 0));
        leftPanel.add(commandPanel, BorderLayout.NORTH);
        leftPanel.add(optionPanel, BorderLayout.CENTER);

        leftPanel.addKeyListener(this);
        this.getContentPane().add(leftPanel, BorderLayout.WEST);


    }

    private void centerPanel(int x, int y) {

        centerPanel.setBorder(new TitledBorder(null, "Arena Tank: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        centerPanel.repaint();
        tankPanel = new JPanel(new java.awt.BorderLayout(), true);
        tankPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        tankPanel.setBackground(Color.LIGHT_GRAY);
        tankPanel.addKeyListener(this);
        if (x == 0 && y == 0) {
            tankPanel.setPreferredSize(new Dimension(1000, 500));
            centerPanel.add(tankPanel);
            getContentPane().add(centerPanel, BorderLayout.CENTER);
        } else {
            tankPanel.setPreferredSize(new Dimension(x, y));
            centerPanel.add(tankPanel);
            getContentPane().add(centerPanel, BorderLayout.CENTER);
        }

    }

    private void bottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tanks: ", TitledBorder.CENTER, TitledBorder.TOP, null, null));
        isoTank1.setPreferredSize(new Dimension(200, 100));
        bottomPanel.add(isoTank1);
        isoTank2.setPreferredSize(new Dimension(200, 100));
        bottomPanel.add(isoTank2);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void menuItem() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        myMenu = new JMenu("File");
        menuBar.add(myMenu);

        myMenuItem = new JMenuItem("New");
        myMenuItem.addActionListener(this);
        myMenu.add(myMenuItem);

        myMenuItem = new JMenuItem("Open File");
        myMenuItem.addActionListener(this);
        myMenu.add(myMenuItem);


        myMenuItem = new JMenuItem("Save File");
        myMenuItem.addActionListener(this);
        myMenu.add(myMenuItem);

        JSeparator separator = new JSeparator();
        myMenu.add(separator);

        myMenuItem = new JMenuItem("Quit");
        myMenuItem.addActionListener(this);
        myMenu.add(myMenuItem);

        myMenu = new JMenu("Commands");
        menuBar.add(myMenu);

        myMenuItem = new JMenuItem("Add Object");
        myMenu.add(myMenuItem);

        myMenuItem = new JMenuItem("Remove Object");
        myMenu.add(myMenuItem);

        myMenu = new JMenu("Help");
        menuBar.add(myMenu);

        myMenuItem = new JMenuItem("Key Commands");
        myMenuItem.addActionListener(this);
        myMenu.add(myMenuItem);

        myMenuItem = new JMenuItem("About");
        myMenuItem.addActionListener(this);
        myMenu.add(myMenuItem);
    }

    public static void setClock(double timeInSeconds) {
        int rawSeconds = (int) timeInSeconds;
        int rawMinutes = (int) (timeInSeconds / 60);
        int rawHours = (int) rawMinutes / 60;
        int rawDays = (int) rawHours / 24;

        int seconds = rawSeconds - (60 * rawMinutes);
        int minutes = rawMinutes - (60 * rawHours);
        int hours = rawHours - (24 * rawDays);
        int days = rawDays;

        textSecond.setText(Integer.toString(seconds));
        textMinute.setText(Integer.toString(minutes));
        textHour.setText(Integer.toString(hours));
        textDay.setText(Integer.toString(days));

    }
    public static void speedDisplay(float speed)
    {        
        textSpeed.setText(Float.toString(speed));
    }
    public void updateTank()
    {       
        try{
        if((float)Float.parseFloat(textTemp.getText())>=16&&(float)Float.parseFloat(textTemp.getText())<=34)
        {
            JSONObject object = new JSONObject();
            object.put("TankX", tankSize[0]);
            object.put("TankY", tankSize[1]);
            object.put("TankZ", tankSize[2]);
            object.put("Temperature", (float)Float.parseFloat(textTemp.getText()));
            PipeFromGUI.updateTankSize(object);
            
        }
        else
        {
            JOptionPane.showMessageDialog(this,
            "Invalid Input, Temperature should be range between 16c to 34c","Warning",
            JOptionPane.WARNING_MESSAGE); 
        }}catch(java.lang.NumberFormatException nfe){
         JOptionPane.showMessageDialog(null, "Invalid Entry "+nfe);}
          
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            switch (e.getActionCommand()) {
                case "Add Object":
                    myOjectMenu.init();
                    break;
                case "Log Report":
                    myReport.init();
                    break;
                case "Stock Tank":                  
                    try{StockTankDisplay.setJSONArray((JSONArray)PipeFromGUI.getStockTankObjects().get("FISH"));}
                    catch(cichlid_sim.engine.json.JSONException nfe)
                    {Logger.outputToGUI(Logger.Type.ERROR, "No Current Fish inside the stock tank " +nfe);}
                    myStockTank.init();
                    break;
                case "Key Commands":
                    myKeys.init();
                    break;
                case "About":
                    myAbout.init();
                    break;
                case "Save File":
                    int returnVal = fc.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        PipeFromGUI.saveWorldToFile(file.getPath());
                    }
                    break;
                case "Open File":
                    int returnVal2 = fc.showOpenDialog(this);
                    if (returnVal2 == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        PipeFromGUI.loadWorldFromFile(file.getPath());
                    }
                    break;
                case "Pause":
                    pauseButton.setText("Resume");
                    PipeFromGUI.requestPauseAction(true);
                    timer.stop();
                    break;
                case "Resume":
                    pauseButton.setText("Pause");
                    PipeFromGUI.requestPauseAction(false);
                    timer.start();
                    break;
                case "Forward":
                    Forward forward = new Forward();
                    forward.init();
                    break;
                case "Small":                   
                    tankSize = new float[]{61.60f,32.38f,31.75f};
                    break;
                case "Medium    ":
                    tankSize = new float[]{61.60f, 16.75f, 31.75f};
                    break;
                case "Large":
                    tankSize = new float[]{61.60f, 50.80f, 31.75f};
                    break;
                case "Update Tank":                    
                    updateTank();
                    break;
                case "New":
                    int newSim = JOptionPane.showConfirmDialog(this, "Are you sure?", "New", JOptionPane.YES_NO_OPTION);
                    if (newSim == JOptionPane.YES_OPTION) {
                        PipeFromGUI.destroyWorld();
                    }
                    break;
                case "Quit":
                    int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    break;
            }

        } catch (java.lang.NullPointerException exp) {
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        this.requestFocus();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (result == 0) {
            System.exit(0);
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_O) {
            myOjectMenu.init();
        } else if (e.getKeyCode() == KeyEvent.VK_K) {
            StockTankDisplay.setJSONArray((JSONArray)PipeFromGUI.getStockTankObjects().get("FISH"));
            myStockTank.init();
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
          //  EditMenu.init(null);
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            myReport.init();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public static JTextField getHour(){    
        return textHour; }
    public static JTextField getMinute(){
       return textMinute; }
    public static JTextField getDay(){
      return textDay;}
    public static JTextField getSecond() {
      return textSecond;}
}
