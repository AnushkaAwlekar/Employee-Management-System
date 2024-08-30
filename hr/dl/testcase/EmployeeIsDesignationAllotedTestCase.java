import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
public class EmployeeIsDesignationAllotedTestCase{
public static void main(String gg[]){
int designationCode=Integer.parseInt(gg[0]);
try{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
System.out.println("Employee with Designation Code: "+designationCode+"exists. "+employeeDAO.isDesignationAlloted(designationCode));
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}