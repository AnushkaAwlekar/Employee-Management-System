package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exception.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface{
private final static String FILE_NAME="designation.data";
public void add(DesignationDTOInterface designationDTO)throws DAOException{
if(designationDTO==null)throw new DAOException("Designation is null");
String title=designationDTO.getTitle();
if(title==null)throw new DAOException("Designation is null");
title=title.trim();
if(title.length()==0)throw new DAOException("length of designation is zero");
try{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";
if(randomAccessFile.length()==0){
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10){
lastGeneratedCodeString+=" ";
}
recordCountString="0";
while(recordCountString.length()<10){
recordCountString+=" ";
}
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCount=Integer.parseInt(recordCountString);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title)){
randomAccessFile.close();
throw new DAOException("Designation: "+title+" exists.");
}
}
int code=lastGeneratedCode+1;
randomAccessFile.writeBytes(String.valueOf(code));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n"); 
designationDTO.setCode(code);
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10){
lastGeneratedCodeString+=" ";
}
recordCountString=String.valueOf(recordCount);
while(recordCountString.length()<10){
recordCountString+=" ";
}
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO)throws DAOException
{
if(designationDTO==null)throw new DAOException("Designation is null");
int code=designationDTO.getCode();
if(code<=0)throw new DAOException ("Invalid code: "+code);
String title=designationDTO.getTitle();
if(title==null)throw new DAOException("Designation is null");
title=title.trim();
if(title.length()==0)throw new DAOException("Length of designation is zero");
try{
File file=new File(FILE_NAME);
if(!file.exists())throw new DAOException("Invalid code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
int fCode;
String fTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode==code){
found=true;
break;
}
randomAccessFile.readLine();
}
if(found==false){
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode!=code && title.equalsIgnoreCase(fTitle)==true){
randomAccessFile.close();
throw new DAOException("Title: "+title+"exists. ");
}
}
File tmpFile=new File("tmp.data");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(code!=fCode){
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
else{
tmpRandomAccessFile.writeBytes(String.valueOf(code));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(title);
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length()){
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0)throw new DAOException ("Invalid code: "+code);
try{
String fTitle="";
File file=new File(FILE_NAME);
if(!file.exists())throw new DAOException("Invalid code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
int fCode;
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code){
found=true;
break;
}
}
if(found==false){
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
if(new EmployeeDAO().isDesignationAlloted(code)){
randomAccessFile.close();
throw new DAOException("Employee exists with designation: "+fTitle);
}
File tmpFile=new File("tmp.data");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(code!=fCode){
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
tmpRandomAccessFile.readLine();
String recordCountString=String.valueOf(recordCount-1);
while(recordCountString.length()<10)recordCountString+=" ";
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length()){
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations;
designations =new TreeSet<>();
try{
File file=new File(FILE_NAME);
if(file.exists()==false)return designations;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return designations;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(randomAccessFile.readLine()));
designationDTO.setTitle(randomAccessFile.readLine());
designations.add(designationDTO);
}
randomAccessFile.close();
return designations;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
try{
File file= new File(FILE_NAME);
if(file.exists()==false)throw new DAOException ("Invalid code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0){
randomAccessFile.close();
throw new DAOException ("Invalid code: "+code);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code){
randomAccessFile.close();
DesignationDTOInterface designationDTO;
designationDTO =new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
try{
File file= new File(FILE_NAME);
if(file.exists()==false)throw new DAOException ("Invalid title: "+title);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0){
randomAccessFile.close();
throw new DAOException ("Invalid title: "+title);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle)){
randomAccessFile.close();
DesignationDTOInterface designationDTO;
designationDTO =new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try{
File file= new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0){
randomAccessFile.close();
return false;
}
int fCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode==code){
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
public boolean titleExists(String title) throws DAOException
{
try{
File file= new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0){
randomAccessFile.close();
return false;
}
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length()){
fTitle=randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle)){
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
public int getCount() throws DAOException
{
try{
File file=new File(FILE_NAME);
if(file.exists()==false)return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0){
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount;
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioe){
throw new DAOException(ioe.getMessage());
}
}
}