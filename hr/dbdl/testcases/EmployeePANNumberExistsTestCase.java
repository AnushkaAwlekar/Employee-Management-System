import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
public class EmployeePANNumberExistsTestCase{
public static void main(String gg[]){
String panNumber=gg[0];
try{
System.out.println("PAN Number : "+panNumber+" exists: "+new EmployeeDAO().panNumberExists(panNumber));
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}