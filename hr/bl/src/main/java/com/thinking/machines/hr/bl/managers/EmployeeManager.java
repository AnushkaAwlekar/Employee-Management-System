package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.text.*;
import java.math.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
public class EmployeeManager implements EmployeeManagerInterface{
private Map<String, EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String, EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String, EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManager employeeManager=null;
private EmployeeManager() throws BLException{
populateDataStructures();
}
private void populateDataStructures() throws BLException{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set <EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee: dlEmployees){
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M'){
employee.setGender(GENDER.MALE);
}
else{
employee.setGender(GENDER.FEMALE);
}
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setPANNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null){
ets=new TreeSet<>();
ets.add(employee);
designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else{
ets.add(employee);
}
}
}catch(DAOException daoe){
BLException blException=new BLException();
blException.setGenericException(daoe.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException{
if(employeeManager==null)employeeManager=new EmployeeManager();
return employeeManager;
}
 
public void addEmployee(EmployeeInterface employee)throws BLException
{
BLException blException =new BLException();
String employeeId=employee.getEmployeeId();
String name =employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
char gender=employee.getGender();

//validations;
if(employeeId!=null){
employeeId=employeeId.trim();
if(employeeId.length()>0){
blException.addException("employeeId","EmloyeeId should be nil/empty ");
}
}
if(name==null){
blException.addException("name","Name required");
}
else{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationManagerInterface designationManager;
designationManager =DesignationManager.getDesignationManager();
if(designation==null){
blException.addException("designation","Designation required");
}
else{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designation.getCode())==false){
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null){
blException.addException("dateOfBirth","DateOfBirth required");
}
if(gender==' '){
blException.addException("gender","Gender required");
}
if(basicSalary==null){
blException.addException("basicSalary","BasicSalary required");
}
else{
if(basicSalary.signum()==-1){
blException.addException("basicSalary","BasicSalary cannot be negative");
}
}
if(panNumber==null){
blException.addException("panNumber","PANNumber required");
}
else{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PANNumber required");
}
if(aadharCardNumber==null){
blException.addException("aadharCardNumber","AadharCardNumber required");
}
else{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadharCardNumber","AadharCardNumber required");
}
if(panNumber!=null && panNumber.length()>0){
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))blException.addException("panNumber","PANNumber "+panNumber+" exists.");
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0){
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))blException.addException("aadharCardNumber","AadharCardNumber "+aadharCardNumber+" exists.");
}

if(blException.hasExceptions()){
throw blException;
}
try{
EmployeeDAOInterface employeeDAO;
employeeDAO= new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee= new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
dlEmployee.setBasicSalary(basicSalary);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setDesignation(((DesignationManager) designationManager).getDesignationByCode(designation.getCode()));
dsEmployee.setName(employee.getName());
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setIsIndian(isIndian);
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
dsEmployee.setBasicSalary(basicSalary);
employeesSet.add(dsEmployee);
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null){
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else{
ets.add(dsEmployee);
}

}catch(DAOException daoe){
blException.setGenericException(daoe.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException{
BLException blException =new BLException();
String employeeId=employee.getEmployeeId();
String name =employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
char gender=employee.getGender();

//validations;
if(employeeId==null){
blException.addException("employeeId","EmloyeeId required");
}
else{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employeeId","Employee Id required");
else{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false){
blException.addException("employeeId","Invalid employeeId "+employeeId);
throw blException;
}
}
}
if(name==null){
blException.addException("name","Name required");
}
else{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationManagerInterface designationManager;
designationManager =DesignationManager.getDesignationManager();
if(designation==null){
blException.addException("designation","Designation required");
}
else{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designation.getCode())==false){
blException.addException("designation","Invalid designation");
}
}
if(dateOfBirth==null){
blException.addException("dateOfBirth","DateOfBirth required");
}
if(gender==' '){
blException.addException("gender","Gender required");
}
if(basicSalary==null){
blException.addException("basicSalary","BasicSalary required");
}
else{
if(basicSalary.signum()==-1){
blException.addException("basicSalary","BasicSalary cannot be negative");
}
}
if(panNumber==null){
blException.addException("panNumber","PANNumber required");
}
else{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PANNumber required");
}
if(aadharCardNumber==null){
blException.addException("aadharCardNumber","AadharCardNumber required");
}
else{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadharCardNumber","AadharCardNumber required");
}
if(panNumber!=null && panNumber.length()>0){
EmployeeInterface ee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false){
blException.addException("panNumber","PANNumber "+panNumber+" exists.");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0){
EmployeeInterface ee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false){
blException.addException("aadharCardNumber","AadharCardNumber "+aadharCardNumber+" exists.");
}
}
if(blException.hasExceptions()){
throw blException;
}
try{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();
EmployeeDAOInterface employeeDAO;
employeeDAO= new EmployeeDAO();
EmployeeDTOInterface dlEmployee;
dlEmployee= new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
dlEmployee.setBasicSalary(basicSalary);
employeeDAO.update(dlEmployee);
dsEmployee.setDesignation(((DesignationManager) designationManager).getDesignationByCode(designation.getCode()));
dsEmployee.setName(employee.getName());
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setIsIndian(isIndian);
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
dsEmployee.setBasicSalary(basicSalary);
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
if(oldDesignationCode!=dsEmployee.getDesignation().getCode()){
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null){
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else{
ets.add(dsEmployee);
}
}
}catch(DAOException daoe){
blException.setGenericException(daoe.getMessage());
throw blException;
}
}

public void removeEmployee(String employeeId)throws BLException{
//validations;
if(employeeId==null){
BLException blException =new BLException();
blException.addException("employeeId","EmloyeeId required");
throw blException;
}
else{
employeeId=employeeId.trim();
if(employeeId.length()==0) {
BLException blException =new BLException();
blException.addException("employeeId","Employee Id required");
throw blException;
}
else{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false){
BLException blException =new BLException();
blException.addException("employeeId","Invalid employeeId "+employeeId);
throw blException;
}
}
}
try{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeDAOInterface employeeDAO;
employeeDAO= new EmployeeDAO();
employeeDAO.delete(dsEmployee.getEmployeeId());
employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}catch(DAOException daoe){
BLException blException=new BLException();
blException.setGenericException(daoe.getMessage());
throw blException;
}
}

public EmployeeInterface getEmployeeByEmployeeId(String employeeId)throws BLException{
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(dsEmployee==null){
BLException blException=new BLException();
blException.addException("employeeId","Invalid Employee Id "+employeeId);
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}

public EmployeeInterface getEmployeeByPANNumber(String panNumber)throws BLException{
EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(dsEmployee==null){
BLException blException=new BLException();
blException.addException("panNumber","Invalid PANNumber "+panNumber);
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}

public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber)throws BLException{
EmployeeInterface dsEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee==null){
BLException blException=new BLException();
blException.addException("aadharCardNumber","Invalid AadharCardNumber "+aadharCardNumber);
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}

public int getEmployeeCount(){
return employeesSet.size();
}
public boolean employeeIdExists(String employeeId){
return employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}

public boolean employeeAadharCardNumberExists(String aadharCardNumber){
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}

public boolean employeePANNumberExists(String panNumber){
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}

public Set<EmployeeInterface> getEmployees(){
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface dsDesignation;
DesignationInterface designation;
designation=new Designation();
for(EmployeeInterface dsEmployee:employeesSet){
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}

public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode)throws BLException{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false){
BLException blException=new BLException();
blException.setGenericException("Invalid designation Code "+designationCode);
}
Set<EmployeeInterface> employees=new TreeSet<>();
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return employees;
EmployeeInterface employee;
DesignationInterface dsDesignation;
DesignationInterface designation;
designation=new Designation();
for(EmployeeInterface dsEmployee:ets){
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}

public int getEmployeeCountByDesignationCount(int designationCode)throws BLException{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)return 0;
return ets.size();
}

public boolean designationAlloted (int designationCode)throws BLException{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}

}
