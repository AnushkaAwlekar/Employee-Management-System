import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
import com.thinking.machines.enums.*;
public class EmployeeDeleteTestCase{
public static void main(String gg[]){
String employeeId =gg[0];
try{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
System.out.println("Employee is deleted");
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}