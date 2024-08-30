import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
import com.thinking.machines.enums.*;
public class EmployeeGetByAadharCardNumberTestCase{
public static void main(String gg[]){
String aadharCardNumber=gg[0];
try{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
EmployeeDTOInterface employeeDTO;
employeeDTO=employeeDAO.getByAadharCardNumber(aadharCardNumber);
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
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
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}