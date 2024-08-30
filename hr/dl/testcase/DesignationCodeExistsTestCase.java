import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationCodeExistsTestCase{
public static void main(String gg[]){
int code=Integer.parseInt(gg[0]);
try{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
System.out.printf(code+ "exists: "+designationDAO.codeExists(code));

}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}