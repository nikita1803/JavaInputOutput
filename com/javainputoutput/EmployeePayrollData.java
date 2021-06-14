package com.javainputoutput;

public class EmployeePayrollData 
{
	    private int id;
	    private String name;
	    private double salary;

	    /**
	     * Parameterized Constructor of the class .
	     * @param id
	     * @param name
	     * @param salary
	     */
	    public EmployeePayrollData(int id, String name, double salary) 
	    {
	        this.id = id;
	        this.name = name;
	        this.salary = salary;
	    }
	    @Override
	    public String toString() {
	        return "EmployeePayrollData [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	    }


}
