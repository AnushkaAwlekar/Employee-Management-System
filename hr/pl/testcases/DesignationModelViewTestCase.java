import java.awt.*;
import javax.swing.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
class DesignationModelTestCase extends JFrame{
private Container container;
private JTable jb;
private JScrollPane jsp;
private DesignationModel designationModel;
DesignationModelTestCase(){
designationModel=new DesignationModel();
jb=new JTable(designationModel);
jsp=new JScrollPane(jb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setVisible(true);
setLocation(100,100);
setSize(500,600);
}
public static void main(String gg[]){
DesignationModelTestCase d=new DesignationModelTestCase();
}
}