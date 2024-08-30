import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
public class EmployeeGetCountByDesignationTestCase{
public static void main(String gg[]){
int designationCode=Integer.parseInt(gg[0]);
try{
System.out.println("Number of employees whose designation Code: "+designationCode+" is: "+new EmployeeDAO().getCountByDesignation(designationCode));
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}