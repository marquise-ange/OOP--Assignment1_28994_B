package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public class Student extends AcademicMember {

    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus,
                   String institutionalEmail, String advisorName,
                   String studentNumber, String programName,
                   LocalDate admissionDate, int studyYear,
                   String guardianPhone, boolean scholarshipStatus) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth,
                memberNumber, department, joinDate, academicStatus,
                institutionalEmail, advisorName);

        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    @Override
    public String getRoleDescription() {
        return "Student";
    }

    @Override
    public String getAcademicCategory() {
        return "Student";
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (studentNumber == null || studentNumber.isBlank())
            throw new ValidationException("Student number is required.");
        if (programName == null || programName.isBlank())
            throw new ValidationException("Program name is required.");
        if (admissionDate == null || admissionDate.isAfter(LocalDate.now()))
            throw new ValidationException("Invalid admission date.");
        if (studyYear < 1 || studyYear > 6)
            throw new ValidationException("Study year must be between 1 and 6.");
        if (guardianPhone == null || !guardianPhone.matches("^\\+?\\d{10,15}$"))
            throw new ValidationException("Invalid guardian phone.");
    }

    @Override
    public String getId() {
        return getPersonId();
    }

    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(getAcademicStatus());
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    public int getStudyYear() { return studyYear; }
    public void setStudyYear(int studyYear) { this.studyYear = studyYear; }

    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }

    public boolean isScholarshipStatus() { return scholarshipStatus; }
    public void setScholarshipStatus(boolean scholarshipStatus) { this.scholarshipStatus = scholarshipStatus; }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", name='" + getDisplayName() + '\'' +
                ", programName='" + programName + '\'' +
                ", studyYear=" + studyYear +
                ", department=" + getDepartment().getDepartmentName() +
                '}';
    }
}