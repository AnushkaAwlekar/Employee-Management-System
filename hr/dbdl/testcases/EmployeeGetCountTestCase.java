import java.util.*;
import java.text.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
public class EmployeeGetCountTestCase{
public static void main(String gg[]){
try{
System.out.println("Number of employees : "+new EmployeeDAO().getCount());
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}