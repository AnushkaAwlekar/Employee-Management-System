import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerGetDesignationsTestCase{
public static void main(String gg[]){
try{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
Set<DesignationInterface> designations;
designations=designationManager.getDesignations();
designations.forEach((designation)->{
System.out.printf("Code %d ,Title %s\n",designation.getCode(),designation.getTitle());
});
}catch(BLException ble){
List<String> properties=ble.getProperties();
for(String property: properties){
System.out.println(ble.getException(property));
}
}
}
}
