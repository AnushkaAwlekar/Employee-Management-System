import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
class EmployeeManagerUpdateTestCase{
public static void main(String gg[]){
try{
String employeeId="A10000002";
String name="Anushka Gupta";
DesignationInterface designation=new Designation();
designation.setCode(3);
Date dateOfBirth=new Date();
boolean isIndian=false;
BigDecimal basicSalary=new BigDecimal("988698");
String panNumber="A87049397";
String aadharCardNumber="JHV77K8O9Y";
EmployeeInterface employee;
employee =new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
employee.setGender(GENDER.MALE);
employee.setBasicSalary(basicSalary);
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.printf("Employee updated");
}catch(BLException ble){
if(ble.hasGenericException())System.out.println(ble.getGenericException());
List<String> properties =ble.getProperties();
for(String property: properties ){
System.out.println(ble.getException(property));
}
}
}
}