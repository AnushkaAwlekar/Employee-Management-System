package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel{
private String ColumnTitle[];
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
public DesignationModel(){
this.populateDataStructure();
}
private void populateDataStructure(){
this.ColumnTitle=new String[2];
this.ColumnTitle[0]="S.No.";
this.ColumnTitle[1]="Designation";
try{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException ble){
// what to do...
}
Set<DesignationInterface> blDesignations=designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation: blDesignations){
this.designations.add(designation);
} 
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right){
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount(){
return designations.size();
}
public int getColumnCount(){
return this.ColumnTitle.length;
}
public String getColumnName(int columnIndex){
return ColumnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex){
if(columnIndex==0)return (rowIndex+1);
return this.designations.get(rowIndex).getTitle();
}
public boolean isCellEditable(int columnIndex,int rowIndex){
return false;
}
public Class getColumnClass(int columnIndex){
if(columnIndex==0)return Integer.class;
return String.class;
}

public void add(DesignationInterface designation)throws BLException{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right){
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public int indexOfDesignation(DesignationInterface designation)throws BLException{
DesignationInterface d;
int index=0;
Iterator<DesignationInterface> iterator=this.designations.iterator();
while(iterator.hasNext()){
d=iterator.next();
if(d.equals(designation)){
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Exception "+ designation);
throw blException;
}

public int indexOfTitle(String title,boolean partialLeftSearch)throws BLException{
DesignationInterface d;
int index=0;
Iterator<DesignationInterface> iterator=this.designations.iterator();
while(iterator.hasNext()){
d=iterator.next();
if(partialLeftSearch){
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())){
return index;
}
}
else{
if(d.getTitle().equalsIgnoreCase(title)){
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Title "+ title);
throw blException;
}

public void update(DesignationInterface designation)throws BLException{
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right){
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public void remove(int code)throws BLException{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext()){
if(iterator.next().getCode()==code)break;
index++;
}
if(index==this.designations.size()){
BLException blException=new BLException();
blException.setGenericException("Invalid code "+ code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index)throws BLException{
if(index<0||index>=this.designations.size()){
BLException blException=new BLException();
blException.setGenericException("Invalid index "+ index);
throw blException;
}
return this.designations.get(index);
}
public void exportToPDF(File file)throws BLException{
try{
if(file.exists())file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document doc=new Document(pdfDocument);
Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);
Paragraph companyNamePara=new Paragraph();
companyNamePara.add("MG Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);
Paragraph reportTitlePara=new Paragraph("List of Designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFontSize(14);
columnTitle1.setFont(columnTitleFont);
Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFontSize(14);
columnTitle2.setFont(columnTitleFont);
Paragraph pageNumberParagraph;
Paragraph dataParagraph;
float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};
int sno,x,pageSize;
pageSize=20;
DesignationInterface designation;
boolean newPage=true;
Table pageNumberTable;
Table topTable;
Table dataTable=null;
Cell cell;
int numberOfPages=this.designations.size()/pageSize;
if((this.designations.size()%pageSize)!=0)numberOfPages++;
sno=0;
x=0;
int pageNumber=0;
while(x<designations.size()){
if(newPage==true){
//create new page header 
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
doc.add(topTable);
pageNumberParagraph=new Paragraph("Page: "+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
doc.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}
designation=this.designations.get(x);
//add row to table
sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell=new Cell();
dataParagraph=new Paragraph(designation.getTitle());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
dataTable.addCell(cell);
x++;
if(sno%pageSize==0 || x==this.designations.size()){
//create footer
doc.add(dataTable);
doc.add(new Paragraph("Software by: Anushka Awlekar"));
if(x<this.designations.size()){
//add new page to document
doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}
doc.close();
}catch(Exception e){
BLException ble;
ble=new BLException();
ble.setGenericException(e.getMessage());
throw ble;
}
}
}