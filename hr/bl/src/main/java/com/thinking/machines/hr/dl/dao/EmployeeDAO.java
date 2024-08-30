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
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false)throw new DAOException("Invalid designation code: "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)throw new DAOException("Date of Birth is null");
char gender=employeeDTO.getGender();
if(gender==' ')throw new DAOException("Gender not set to MALE/FEMALE");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary.signum()==-1)throw new DAOException("Basic Salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)throw new DAOException("PANNumber is null");
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("Length of panNumber is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)throw new DAOException("AadharCardNumber is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("Length of AadharCardNumber is zero");
try{
int lastGeneratedEmployeeId=10000000;
String lastGeneratedEmployeeIdString="";
int recordCount=0;
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString + "\n");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(recordCountString + "\n");
}
else{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());

}
boolean panNumberExists,aadharCardNumberExists;
panNumberExists=false;
aadharCardNumberExists=false;
String fPANNumber;
String fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
for(x=1;x<=7;x++)randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fPANNumber.equalsIgnoreCase(panNumber)){
panNumberExists=true;
}
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)){
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists){
break;
}
}
if(panNumberExists && aadharCardNumberExists){
randomAccessFile.close();
throw new DAOException("PANNumber("+panNumber+") and AadharCardNumber("+aadharCardNumber+") exists");
}
if(panNumberExists){
randomAccessFile.close();
throw new DAOException("PANNumber("+panNumber+") exists");
}
if(aadharCardNumberExists){
randomAccessFile.close();
throw new DAOException("AadharCardNumber("+aadharCardNumber+") exists");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
recordCount++;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO)throws DAOException{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null)throw new DAOException("EmployeeId is null");
if(employeeId.length()==0)throw new DAOException("Length is null");
String name=employeeDTO.getName();
if(name==null)throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length is null");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0)throw new DAOException("Invalid designation Code: "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(isDesignationCodeValid==false)throw new DAOException("Invalid designation code: "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)throw new DAOException("Date of Birth is null");
char gender=employeeDTO.getGender();
if(gender==' ')throw new DAOException("Gender not set to MALE/FEMALE");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)throw new DAOException("Basic Salary is null");
if(basicSalary.signum()==-1)throw new DAOException("Basic Salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)throw new DAOException("PANNumber is null");
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("length of panNumber is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)throw new DAOException("AadharCardNumber is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("length of AadharCardNumber is zero");
try{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid EmployeeId: "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid EmployeeId: "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
int x;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
if(employeeIdFound==false)foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=6;x++)randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId)){
employeeIdFound=true;
}
if(panNumberFound==false && fPANNumber.equalsIgnoreCase(panNumber)){
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)){
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound)break;
} 
if(employeeIdFound==false){
randomAccessFile.close();
throw new DAOException("Invalid employee id: "+employeeId);
}
boolean panNumberExists=false;
if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false){
panNumberExists=true;
}
boolean aadharCardNumberExists=false;
if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false){
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists){
randomAccessFile.close();
throw new DAOException("PAN Number ("+panNumber+") and AadharCardNumber("+aadharCardNumber+") exists");
}
if(panNumberExists){
randomAccessFile.close();
throw new DAOException("PAN Number ("+panNumber+") exists.");
}
if(aadharCardNumberExists){
randomAccessFile.close();
throw new DAOException("AadharCardNumber ("+aadharCardNumber+") exists.");
}
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++)randomAccessFile.readLine();
File tmpFile =new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length()){
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public void delete(String employeeId) throws DAOException{
if(employeeId==null)throw new DAOException("EmployeeId is null");
if(employeeId.length()==0)throw new DAOException("Length of EmployeeId is zero");
try{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid EmployeeId: "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid EmployeeId: "+employeeId);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
String fEmployeeId;
int x;
boolean employeeIdFound=false;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=8;x++)randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)){
employeeIdFound=true;
break;
}
}
if(employeeIdFound==false){
randomAccessFile.readLine();
throw new DAOException("Invalid employeeId: "+employeeId);
}
File tmpFile =new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length()){
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try{
File file =new File(FILE_NAME);
if(file.exists()==false)return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException pe){
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M'){
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F'){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException{
if(new DesignationDAO().codeExists(designationCode)==false){
throw new DAOException("Invalid designation Code: "+designationCode);
}		
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try{
File file =new File(FILE_NAME);
if(file.exists()==false)return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fName;
int fDesignationCode;
int x;
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode!=designationCode){
for(x=1;x<=6;x++) randomAccessFile.readLine();
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException pe){
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M'){
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F'){
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException{
if(employeeId==null)throw new DAOException("Invalid employee id: "+employeeId); 
employeeId=employeeId.trim();
if(employeeId.length()==0)throw new DAOException("Invalid employee id: employee is of zero length");
try{
File file = new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid employee id: "+employeeId); 
RandomAccessFile randomAccessFile;
randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid employee id: "+employeeId); 
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException pe){
//do nothing
}
employeeDTO.setGender((randomAccessFile.readLine().charAt(0)=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(x=1;x<=8;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid employee id: "+employeeId); 
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException{
if(panNumber==null)throw new DAOException("Invalid PANNumber: "+panNumber);
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("Invalid PanNumber: PANNumber is of zero length");
try{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid PANNumber: "+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid PAN Number: "+panNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
int x;
fDateOfBirth=null;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException pe){
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber)){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid PAN Number: "+panNumber);
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException{
if(aadharCardNumber==null)throw new DAOException("Invalid AadharCardNumber: "+aadharCardNumber);
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("Invalid AadharCardNumber: AadharCardNumber is of zero length");
try{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid AadharCardNumber: "+aadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid AadharCardNumber: "+aadharCardNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
int x;
fDateOfBirth=null;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException pe){
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)){
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender((fGender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid AadharCardNumber: "+aadharCardNumber);
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public int getCount() throws DAOException{
try{
File file = new File(FILE_NAME);
if(file.exists()==false)return 0;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public int getCountByDesignation(int designationCode)throws DAOException{
try{
if(new DesignationDAO().codeExists(designationCode)==false)throw new DAOException("Invalid designation Code:  "+designationCode);
File file = new File(FILE_NAME);
if(file.exists()==false)return 0;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
int count=0;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)count++;
for(x=1;x<=6;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
return count;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}

}
public boolean employeeIdExists(String employeeId)throws DAOException{
if(employeeId==null)return false;
employeeId=employeeId.trim();
if(employeeId.length()==0)return false;
try{
File file = new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)){
randomAccessFile.close();
return true;
}
for(x=1;x<=8;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public boolean panNumberExists(String panNumber)throws DAOException{
if(panNumber==null)return false;
panNumber=panNumber.trim();
if(panNumber.length()==0)return false;
try{
File file = new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fPANNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
for(x=1;x<=7;x++)randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber)){
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber)throws DAOException{
if(aadharCardNumber==null)return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)return false;
try{
File file = new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile =new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
String fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
for(x=1;x<=8;x++)randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)){
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode)throws DAOException{
if(new DesignationDAO().codeExists(designationCode)==false){
throw new DAOException("Invalid designation code: "+designationCode);
}
try{
File file=new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode){
randomAccessFile.close();
return true;
}
for(int x=1;x<=6;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
}

