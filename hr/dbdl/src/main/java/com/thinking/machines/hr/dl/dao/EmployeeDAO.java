package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.enums.*;
import java.util.*;
import java.text.*;
import java.io.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.sql.*;
public class EmployeeDAO implements EmployeeDAOInterface{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null)throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length is null");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0)throw new DAOException("Invalid designation Code: "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ')
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to MALE/FEMALE");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is null");
}
if(basicSalary.signum()==-1)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PANNumber is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of panNumber is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("AadharCardNumber is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of AadharCardNumber is zero");
}
try{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_card_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

if(panNumberExists && aadharCardNumberExists){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PANNumber("+panNumber+") and AadharCardNumber("+aadharCardNumber+") exists");
}
if(panNumberExists){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PANNumber("+panNumber+") exists");
}
if(aadharCardNumberExists){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("AadharCardNumber("+aadharCardNumber+") exists");
}
preparedStatement=connection.prepareStatement("insert into employee(name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_card_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeId));
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO)throws DAOException{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null)throw new DAOException("EmployeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Length of EmployeeId is zero");
int actualEmployeeId;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception){
throw new DAOException("Invalid Employee Id");
}
String name=employeeDTO.getName();
if(name==null)throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0)throw new DAOException("Invalid designation Code: "+designationCode);
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender=employeeDTO.getGender();
if(gender==' ')
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender not set to MALE/FEMALE");
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is null");
}
if(basicSalary.signum()==-1)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic Salary is negative");
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PANNumber is null");
}
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of panNumber is zero");
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("AadharCardNumber is null");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of AadharCardNumber is zero");
}
try{
boolean panNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where pan_card_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
panNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

boolean aadharCardNumberExists;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=? and employe_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
aadharCardNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();

if(panNumberExists && aadharCardNumberExists){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PANNumber("+panNumber+") and AadharCardNumber("+aadharCardNumber+") exists");
}
if(panNumberExists){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PANNumber("+panNumber+") exists");
}
if(aadharCardNumberExists){
try{
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("AadharCardNumber("+aadharCardNumber+") exists");
}
preparedStatement=connection.prepareStatement("update employee set name=?,designation=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
}
public void delete(String employeeId) throws DAOException{
if(employeeId==null)throw new DAOException("EmployeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Length of EmployeeId is zero");
int actualEmployeeId;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception){
throw new DAOException("Invalid EmployeeId");
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
try{
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee id. "+employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from employee");
EmployeeDTOInterface employeeDTO;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next()){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(sqlDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")){
employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid deisgnationCode: "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next()){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(sqlDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")){
employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return employees;		
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException{
if(employeeId==null)throw new DAOException("Invalid employee id: "+employeeId); 
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Invalid employee id: employee is of zero length");
int actualEmployeeId=0;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception){
throw new DAOException("Invalid Employee id: "+employeeId);
}
EmployeeDTOInterface employeeDTO=null;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee id: "+employeeId);
}
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next()){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(sqlDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")){
employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException{
if(panNumber==null)throw new DAOException("Invalid PANNumber: "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("Invalid PanNumber: PANNumber is of zero length");
EmployeeDTOInterface employeeDTO=null;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where pan_card_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PANNumber: "+panNumber);
}
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next()){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(sqlDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")){
employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException{
if(aadharCardNumber==null)throw new DAOException("Invalid AadharCardNumber: "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("Invalid AadharCardNumber: AadharCardNumber is of zero length");
EmployeeDTOInterface employeeDTO=null;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid AadharCardNumber: "+aadharCardNumber);
}
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next()){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(1000000+resultSet.getInt("employee_id")));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(sqlDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")){
employeeDTO.setGender(GENDER.MALE);
}
if(gender.equals("F")){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public int getCount() throws DAOException{
int count=0;
try{
Connection connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select count(*) as cnt from employee");
resultSet.next();
count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return count;
}
public int getCountByDesignation(int designationCode)throws DAOException{
int count=0;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(*) as cnt from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return count;
}
public boolean employeeIdExists(String employeeId)throws DAOException{
if(employeeId==null)return false;
employeeId=employeeId.trim();
if(employeeId.length()==0)return false;
int actualEmployeeId=0;
try{
actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception){
throw new DAOException("Invalid Employee id: "+employeeId);
}
boolean exists=false;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return exists;
}
public boolean panNumberExists(String panNumber)throws DAOException{
if(panNumber==null)return false;
panNumber=panNumber.trim();
if(panNumber.length()==0)return false;
boolean exists=false;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where pan_card_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return exists;
}
public boolean aadharCardNumberExists(String aadharCardNumber)throws DAOException{
if(aadharCardNumber==null)return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)return false;
boolean exists=false;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
exists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return exists;
}
public boolean isDesignationAlloted(int designationCode)throws DAOException{
boolean designationExists=false;
try{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false){
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid designation Code: "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
designationExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException){
throw new DAOException(sqlException.getMessage());
}
return designationExists;
}
}

