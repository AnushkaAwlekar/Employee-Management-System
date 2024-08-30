import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerGetDesignationCountTestCase{
public static void main(String gg[]){
try{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
System.out.printf("Number of Designations: "+designationManager.getDesignationCount());
}catch(BLException ble){
List<String> properties=ble.getProperties();
for(String property: properties){
System.out.println(ble.getException(property));
}
}
}
}
