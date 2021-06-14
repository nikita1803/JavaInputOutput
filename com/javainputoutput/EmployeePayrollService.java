package com.javainputoutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService 
{
	public enum IOService {CONSOL_TO, FILE_IO, DB_TO, REST_TO}

    private List<EmployeePayrollData> employeePayrollList;

    public EmployeePayrollService() {
    	
    }

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) 
    {
    	this.employeePayrollList=employeePayrollList;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Employee PayRoll Service Program");
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData();
    }
    /* Read Employee Payroll data from console */
    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employree ID:");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employree Name");
        String name = consoleInputReader.next();
        System.out.println("Enter Employree Salary");
        double salary = consoleInputReader.nextDouble();
       
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    /*Write Employee Payroll data to console*/
    private void writeEmployeePayrollData() {
        System.out.println("\nWritting Employee Payroll Roaster to Console\n" + employeePayrollList);
    }
}
