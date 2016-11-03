import java.awt.*; 
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class TryWindow
{
  // The window object
  static JFrame aWindow = new JFrame("Window 2015");
  DataModel dm = new DataModel();

  public static void main(String[] args)
  {
    Toolkit theKit = aWindow.getToolkit();       // Get the window toolkit
    Dimension wndSize = theKit.getScreenSize();  // Get screen size

    // Set the position to screen center & size to half screen size
    aWindow.setBounds(wndSize.width/4, wndSize.height/8,   // Position
                      wndSize.width/2, wndSize.height*3/4);  // Size
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Box top = Box.createHorizontalBox();
    top.add(new JLabel("地址："));
    top.add(new JTextField());
    top.add(new JButton("Go"));

	TextArea txt = new TextArea();
//  Border edge = BorderFactory.createRaisedBevelBorder();  // Button border   
//	txt.setBorder(edge);

    Container content = aWindow.getContentPane();      // Get content pane
    content.setLayout(new BorderLayout());             // Set border layout manager
    content.add(top, BorderLayout.NORTH);
    content.add(new JScrollPane(txt), BorderLayout.CENTER);
    content.add(new JTextField(), BorderLayout.SOUTH);

    aWindow.setVisible(true);                          // Display the window
  }

  public void 
}
