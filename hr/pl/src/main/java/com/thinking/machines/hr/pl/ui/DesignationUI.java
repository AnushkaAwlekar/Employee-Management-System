package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener{
private JLabel titleLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JButton clearSearchFieldButton;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JTable designationTable;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private Container container;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon deleteIcon;
private ImageIcon clearIcon;
public DesignationUI(){
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
}
public void initComponents(){
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
setIconImage(logoIcon.getImage());
addIcon=new ImageIcon(this.getClass().getResource("/icons/add.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save.png"));
clearIcon=new ImageIcon(this.getClass().getResource("/icons/clear.png"));
designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchErrorLabel=new JLabel("");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton(clearIcon);
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
}
private void setAppearance(){
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(40);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
designationPanel=new DesignationPanel();
container.setLayout(null);
int tm,lm;
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+100+10+400-75,tm+10+10+20,100,20);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+100+10+400,tm+10+10+40,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+10+300,565,200);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(searchErrorLabel);
container.add(scrollPane);
container.add(designationPanel);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int h,w;
h=660;
w=600;
setSize(w,h);
setLocation((d.width/2)-(w/2),(d.height/2)-(h/ 2));
}
public void addListeners(){
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
public void searchDesignation(){
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0)return;
int rowIndex;
try{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException bl){
searchErrorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}
public void changedUpdate(DocumentEvent de){
searchDesignation();
}
public void removeUpdate(DocumentEvent de){
searchDesignation();
}
public void insertUpdate(DocumentEvent de){
searchDesignation();
}
public void valueChanged(ListSelectionEvent ev){
int selectedRowIndex=designationTable.getSelectedRow();
try{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException ble){
designationPanel.clearDesignation();
}
}

private void setViewMode(){
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0){
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}

private void setAddMode(){
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setEditMode(){
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setDeleteMode(){
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

private void setExportToPDFMode(){
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}

//inner class starts
class DesignationPanel extends JPanel{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton addButton;
private JButton editButton;
private JButton exportToPDFButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton clearTitleTextFieldButton;
private JPanel buttonPanel;
private DesignationInterface designation;
public DesignationPanel(){
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
initComponenets();
setAppearance();
addListener();
}
public void setDesignation(DesignationInterface designation){
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation(){
this.designation=null;
titleLabel.setText("");
}
private void initComponenets(){
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
exportToPDFButton=new JButton(pdfIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
buttonPanel=new JPanel();
clearTitleTextFieldButton=new JButton("x");
}
private void setAppearance(){
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleTextField.setFont(dataFont);
titleLabel.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+110+10,tm+20,400,30);
titleTextField.setBounds(lm+110+10+5+5,tm+20,350,30);
clearTitleTextFieldButton.setBounds(lm+110+10+5+350+10,tm+20,30,30);
buttonPanel.setBounds(50,tm+20+30+30,465,75);
buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonPanel.setLayout(null);
buttonPanel.add(addButton);
buttonPanel.add(editButton);
buttonPanel.add(cancelButton);
buttonPanel.add(deleteButton);
buttonPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(buttonPanel);
add(clearTitleTextFieldButton);
}

private boolean addDesignation(){
String title=titleTextField.getText().trim();
if(title.length()==0){
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
try{
designationModel.add(d);
int rowIndex=0;
try{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException bl){
//do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException ble){
if(ble.hasGenericException()){
JOptionPane.showMessageDialog(this,ble.getGenericException());
}
else{
if(ble.hasException("title")){
JOptionPane.showMessageDialog(this,ble.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}

private boolean updateDesignation(){
String title=titleTextField.getText().trim();
if(title.length()==0){
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try{
designationModel.update(d);
int rowIndex=0;
try{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException bl){
//do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException ble){
if(ble.hasGenericException()){
JOptionPane.showMessageDialog(this,ble.getGenericException());
}
else{
if(ble.hasException("title")){
JOptionPane.showMessageDialog(this,ble.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}


private void removeDesignation(){
try{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION)return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted");
//this.clearDesignation();
}catch(BLException ble){
if(ble.hasGenericException()){
JOptionPane.showMessageDialog(this,ble.getGenericException());
}
else{
if(ble.hasException("title")){
JOptionPane.showMessageDialog(this,ble.getException("title"));
}
}
titleTextField.requestFocus();
}
}
private void addListener(){
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
if(mode==MODE.VIEW){
setAddMode();
}
else{
if(addDesignation()){
setViewMode();
}
}
}
});

this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
if(mode==MODE.VIEW){
setEditMode();
}
else{
if(updateDesignation()){
setViewMode();
}
}
}
});

this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
setViewMode();
}
});


this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
setDeleteMode();
}
});

this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev){
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION){
try{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith("."))pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false)pdfFile+=".pdf";
File file=new File(pdfFile);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false){
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path: "+file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to: "+file.getAbsolutePath());
}catch(BLException ble){
if(ble.hasGenericException()){
JOptionPane.showMessageDialog(DesignationUI.this,ble.getGenericException());
}
}
catch(Exception e){
System.out.println(e);
}
}
}
});
}

void setViewMode(){
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
DesignationUI.this.setViewMode();
this.addButton.setEnabled(true);
this.titleTextField.setVisible(false);
this.titleLabel.setVisible(true);
this.cancelButton.setEnabled(false);
this.clearTitleTextFieldButton.setVisible(false);
if(designationModel.getRowCount()<=0){
this.deleteButton.setEnabled(false);
this.editButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
else{
this.deleteButton.setEnabled(true);
this.editButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
}

void setAddMode(){
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleTextField.setVisible(true);
this.titleLabel.setVisible(false);
this.clearTitleTextFieldButton.setVisible(true);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
this.editButton.setEnabled(false);
addButton.setIcon(saveIcon);
}

void setEditMode(){
if(designationTable.getSelectedRow()<0||designationTable.getSelectedRow()>=designationModel.getRowCount()){
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
this.addButton.setEnabled(false);
editButton.setIcon(saveIcon);
}

void setDeleteMode(){
if(designationTable.getSelectedRow()<0||designationTable.getSelectedRow()>=designationModel.getRowCount()){
JOptionPane.showMessageDialog(this,"Select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
this.addButton.setEnabled(false);
removeDesignation();
DesignationUI.this.setViewMode();
this.setViewMode();
}

void setExportToPDFMode(){
DesignationUI.this.setExportToPDFMode();
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
this.addButton.setEnabled(false);
}
//inner class ends
}
}

