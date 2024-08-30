import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerDesignationCodeExistsTestCase{
public static void main(String gg[]){
int code=Integer.parseInt(gg[0]);
try{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
System.out.printf(code + " exists: "+designationManager.designationCodeExists(code));
}catch(BLException ble){
List<String> properties=ble.getProperties();
for(String property: properties){
System.out.println(ble.getException(property));
}
}
}
}
