import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
public class EmployeeGetAllTestCase{
public static void main(String gg[]){
try{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getAll();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeDTOInterface employeeDTO:employees){
System.out.println("EmployeeId: "+employeeDTO.getEmployeeId());
System.out.println("Name: "+employeeDTO.getName());
System.out.println("Designation Code: "+employeeDTO.getDesignationCode());
System.out.println("Date of Birth: "+simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender: "+employeeDTO.getGender());
System.out.println("IsIndian: "+employeeDTO.getIsIndian());
System.out.println("BasicSalary: "+employeeDTO.getBasicSalary().toPlainString());
System.out.println("PAN Number: "+employeeDTO.getPANNumber());
System.out.println("AadharCardNumber: "+employeeDTO.getAadharCardNumber());
System.out.println("*****************************************************************");
}
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}