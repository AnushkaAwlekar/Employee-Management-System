import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
public class EmployeeEmployeeIdExistsTestCase{
public static void main(String gg[]){
String employeeId=gg[0];
try{
System.out.println("Employee Id. : "+employeeId+" exists: "+new EmployeeDAO().employeeIdExists(employeeId));
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}