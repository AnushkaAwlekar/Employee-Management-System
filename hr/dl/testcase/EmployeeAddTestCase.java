import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
import com.thinking.machines.enums.*;
public class EmployeeAddTestCase{
public static void main(String gg[]){
String name=gg[0];
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
int designationCode=Integer.parseInt(gg[1]);
Date dateOfBirth=null;
try{
dateOfBirth=sdf.parse(gg[2]);
}catch(ParseException pe){
System.out.println(pe.getMessage());
return;
}
char gender=gg[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary=new BigDecimal(gg[5]);
String panNumber=gg[6];
String aadharCardNumber=gg[7];
try{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M'){
employeeDTO.setGender(GENDER.MALE);
}
if(gender=='F'){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee added with employeeId as: "+employeeDTO.getEmployeeId());
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}