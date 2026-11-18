package com.auca.department;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;

public class Department implements Identifiable, Registrable {

    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(String departmentId, String departmentName,
                      String departmentCode, String officeLocation,
                      String contactEmail, Teacher headTeacher) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    @Override
    public String getId() { return departmentId; }

    @Override
    public String getDisplayName() { return departmentName; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (departmentId == null || departmentId.isBlank())
            throw new ValidationException("Department ID is required.");
        if (departmentName == null || departmentName.length() < 2 || departmentName.length() > 60)
            throw new ValidationException("Department name must contain 2-60 characters.");
        if (departmentCode == null || departmentCode.isBlank())
            throw new ValidationException("Department code is required.");
        if (officeLocation == null || officeLocation.isBlank())
            throw new ValidationException("Office location is required.");
        if (contactEmail == null || !contactEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new ValidationException("Invalid contact email.");
    }

    @Override
    public void printDetails() { System.out.println(this); }

    @Override
    public String getRegistrationSummary() {
        return "Department: " + departmentName + " [" + departmentId + "]";
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public Teacher getHeadTeacher() { return headTeacher; }
    public void setHeadTeacher(Teacher headTeacher) { this.headTeacher = headTeacher; }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", officeLocation='" + officeLocation + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", headTeacher=" + (headTeacher == null ? "Not assigned" : headTeacher.getDisplayName()) +
                '}';
    }
}