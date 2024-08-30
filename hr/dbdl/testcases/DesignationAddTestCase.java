import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class DesignationAddTestCase{
public static void main(String gg[]){
String title=gg[0];
try{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println("Designation: "+title+" added with the code as: "+designationDTO.getCode());

}catch(DAOException daoe){
System.out.println(daoe.getMessage());
}
}
}