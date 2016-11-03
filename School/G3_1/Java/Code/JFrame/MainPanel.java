import java.awt.*;
import javax.swing.*;
import java.util.*;
class MainPanel extends JPanel
	implements Observer{
	TextArea txt;
	DataModel dm;

	MainPanel(DataModel d){
		dm = d;
		add(txt = new TextArea());
	}

	public void update(Observerable thing, Object o){
		txt.setText(dm.getData());
		System.out.println("Observerable");
	}
}