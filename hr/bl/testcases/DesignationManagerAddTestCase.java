import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerAddTestCase{
public static void main(String gg[]){
DesignationInterface designation=new Designation();
designation.setTitle("Baker");
try{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("Designation added with code as: "+designation.getCode());
}catch(BLException ble){
if(ble.hasGenericException()){
System.out.println(ble.getGenericException());
}
List<String> properties=ble.getProperties();
for(String property: properties){
System.out.println(ble.getException(property));
}
}
}
}
