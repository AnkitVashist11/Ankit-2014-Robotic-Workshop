
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import gnu.io.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class Serial implements SerialPortEventListener
{
    //passed from main GUI
  
    //for containing the ports that will be found
    private Enumeration ports = null;
    //map the port names to CommPortIdentifiers
    private HashMap portMap = new HashMap();

    //this is the object that contains the opened port
    private CommPortIdentifier selectedPortIdentifier = null;
    private SerialPort serialPort = null;

    //input and output streams for sending and receiving data
    private InputStream input = null;
    private OutputStream output = null;

    //just a boolean flag that i use for enabling
    //and disabling buttons depending on whether the program
    //is connected to a serial port or not
    private boolean bConnected = false;

    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;

    //a string for recording what goes on in the program
    //this string is written to the GUI
    String logText = "";

    private JTextArea txtBox=null;
   private boolean recFlag=false;
   private int bRate=0;
   private int recMode=0;
   
     
   public void setRecMode(int rcmode)
   {
       recMode=rcmode;
   }

    //search for all the serial ports
    //pre: none
    //post: adds all the found ports to a combo box on the GUI
     public  String[] searchForPorts(int maxPorts)
    {
        String stArr[]=new String[maxPorts];
       int index=0;
        ports = CommPortIdentifier.getPortIdentifiers();

       
        while (ports.hasMoreElements() && index<maxPorts )
        {
            CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();

            //get only serial ports
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
            {
                 stArr[index]=curPort.getName();   
                 portMap.put(curPort.getName(), curPort);
                 index++;       
            }
        }
    return(stArr);
    }
public void delay(int sec)
{
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
        }

}
     
    //connect to the selected port in the combo box
    //pre: ports are already found by using the searchForPorts method
    //post: the connected comm port is stored in commPort, otherwise,
    //an exception is generated
    public boolean connect(String selectedPort)
    {
        //String selectedPort = (String)window.cboxPorts.getSelectedItem();
        boolean status=false;
        selectedPortIdentifier = (CommPortIdentifier)portMap.get(selectedPort);

        CommPort commPort = null;

        try
        {
            //the method below returns an object of type CommPort
            
            commPort = selectedPortIdentifier.open("TigerControlPanel", TIMEOUT);
            //the CommPort object can be casted to a SerialPort object
            serialPort = (SerialPort)commPort;
            //serialPort.set
            
        //    if(serialPort.setBaudBase(bRate))
        //        System.out.println("Baud Rate is"+bRate+ " set");
            //for controlling GUI elements
            setConnected(true);

            //logging
            logText = selectedPort + " opened successfully.";
System.out.println(logText);
            //CODE ON SETTING BAUD RATE ETC OMITTED
            //XBEE PAIR ASSUMED TO HAVE SAME SETTINGS ALREADY

            //enables the controls on the GUI if a successful connection is made

            status=true;
        }
        catch (PortInUseException e)
        {
            logText = selectedPort + " is in use. (" + e.toString() + ")";
    System.err.println(logText);
        }
        catch (Exception e)
        {
            logText = "Failed to open " + selectedPort + "(" + e.toString() + ")";
        System.err.println(logText);
        }
        return(status);
    }

    //open the input and output streams
    //pre: an open port
    //post: initialized intput and output streams for use to communicate data
    public boolean initIOStream()
    {
        //return value for whather opening the streams is successful or not
        boolean successful = false;

        try {
            //
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();
            bRate=serialPort.getBaudRate();
  //          transData(0);
            successful = true;
            return successful;
        }
        catch (IOException e) {
            logText = "I/O Streams failed to open. (" + e.toString() + ")";
                   System.err.println(logText);
   return successful;
        }
    }

    //starts the event listener that knows whenever data is available to be read
    //pre: an open serial port
    //post: an event listener for the serial port that knows when data is recieved
    public void initListener()
    {
        try
        {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        }
        catch (TooManyListenersException e)
        {
            logText = "Too many listeners. (" + e.toString() + ")";
        System.err.println(logText);
        }
    }

    public void init_Serial(String selectedPort, int bRate)
    {
        recFlag=false;
        this.bRate=bRate;
       connect(selectedPort);
        if (getConnected() == true)
        {
            if (initIOStream() == true)
            {
         //       initListener();
            }
                try {
         serialPort.setSerialPortParams(
                    bRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
 
            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE);
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
     this.bRate=serialPort.getBaudRate();
                
        }
    }
    
    public int getBaudRate()
    {
        return(bRate);
    }
            

public void init_Serial(String selectedPort, int bRate, JTextArea recText)
    {
        recFlag=true;
        this.bRate=bRate;
        txtBox=recText;
      connect(selectedPort);
       
        if (getConnected() == true)
        {
            if (initIOStream() == true)
            {
                initListener();
            }
         try {
            serialPort.setSerialPortParams(
                    bRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
 
            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE);
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
     this.bRate=serialPort.getBaudRate();
        
        
        }
    }

    
    
    //disconnect the serial port
    //pre: an open serial port
    //post: clsoed serial port
    public void disconnect()
    {
        //close the serial port
        try
        {
         //   transData(0);

            
            serialPort.removeEventListener();
            serialPort.close();
            input.close();
            output.close();
            setConnected(false);
         
            logText = "Disconnected.";
        System.err.println(logText);
  }
        catch (Exception e)
        {
            logText = "Failed to close " + serialPort.getName() + "(" + e.toString() + ")";
        System.err.println(logText);
        }
    }

    final public boolean getConnected()
    {
        return bConnected;
    }

    public void setConnected(boolean bConnected)
    {
        this.bConnected = bConnected;
    }

    //what happens when data is received
    //pre: serial event is triggered
    //post: processing on the data it reads
    public void serialEvent(SerialPortEvent evt) {
        if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
               int sndata=input.read();
           //    System.out.println(sndata);
 
               if(recMode==0)
               {    
                byte singleData = (byte)sndata;

                   if (singleData != NEW_LINE_ASCII)
                {
                    logText = new String(new byte[] {singleData});
                    txtBox.append(logText);
                }
                else
                {
                    txtBox.append("\n");
                }
               }
               else
               {
                   txtBox.append(sndata+" ");
               
               }
            }
            catch (Exception e)
            {
                logText = "Failed to read data. (" + e.toString() + ")";
                txtBox.setForeground(Color.red);
                txtBox.append(logText + "\n");
            }
        }
    }

    //method that can be called to send data
    //pre: open serial port
    //post: data sent to the other device
    public void transData(int Value)
    {
       try
        {
            output.write(Value);
            output.flush();
            
        }
        catch (Exception e)
        {
            logText = "Failed to write data. (" + e.toString() + ")";
            System.err.println(logText);
        }
    }
}
