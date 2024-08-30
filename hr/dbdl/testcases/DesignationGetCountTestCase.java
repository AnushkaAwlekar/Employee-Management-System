import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;

public class DesignationGetCountTestCase{
public static void main(String gg[]){
try{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
System.out.println("Designation count: "+designationDAO.getCount());
 
}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}