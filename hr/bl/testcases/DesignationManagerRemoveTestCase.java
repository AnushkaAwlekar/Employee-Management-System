import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerRemoveTestCase{
public static void main(String gg[]){
int code=Integer.parseInt(gg[0]);
try{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
System.out.println("Designation removed\n");
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
