import java.awt.*; 
import javax.swing.*;
import java.awt.event.*; 

public class TryWindow2 extends JFrame
	implements ActionListener
{
	JTextField	htm;
	TextArea	txt;

	TryWindow2(String title)
	{
		super(title);
		BorderLayout border = new BorderLayout();        // Create a layout manager
		Container content = getContentPane();
		content.setLayout(border);

		Box top = Box.createHorizontalBox();
		top.add(new JLabel("网址："));
		htm = new JTextField(
			"http://www.baidu.com/");
		htm.addActionListener(
			new ActionListener(){
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Hi!");
	}
			}
		);                // Add listener for button
		top.add(htm);

		JButton btn = new JButton("Go");
		btn.addActionListener(this);                // Add listener for button
		top.add(btn);
		
		content.add(top, BorderLayout.NORTH);

		txt = new TextArea();
/*		GridLayout grid = new GridLayout(4,4,5,5);
		JPanel main = new JPanel();
		main.setLayout(grid);

		for(int i = 1; i <= 16; i++)
			main.add(new JButton("JPress " + i));      // Add a Button to content pane
*/	
		content.add(txt, BorderLayout.CENTER);

		JTextField stu = new JTextField();
		content.add(stu, BorderLayout.SOUTH);

	}
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Hello!"+htm.getText());
		txt.setText(new ReadHTML().get(
			(String)htm.getText())); 
	}

	public static void main(String[] args)
	{
		TryWindow2 aWindow =
			new TryWindow2("Java 2014");
		Toolkit theKit = aWindow.getToolkit();       // Get the window toolkit
		Dimension wndSize = theKit.getScreenSize();  // Get screen size

		aWindow.setBounds(
			wndSize.width/4,
			wndSize.height/4,	// Position
			wndSize.width/2,
			wndSize.height/2);  // Size
		aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aWindow.setVisible(true);                              // Display the window
  }
}