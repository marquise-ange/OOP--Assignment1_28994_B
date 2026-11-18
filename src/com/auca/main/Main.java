
package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import com.auca.administrator.Administrator;
import com.auca.department.Department;
import com.auca.course.Course;
import com.auca.classroom.Classroom;
import com.auca.enrollment.Enrollment;
import com.auca.assessment.Assessment;
import com.auca.feepayment.FeePayment;
import com.auca.librarybook.LibraryBook;
import com.auca.bookloan.BookLoan;
import com.auca.exceptions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> payments = new ArrayList<>();
    private static final List<LibraryBook> books = new ArrayList<>();
    private static final List<BookLoan> loans = new ArrayList<>();

    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> registerDepartment();
                    case 2 -> registerStudent();
                    case 3 -> registerTeacher();
                    case 4 -> registerAdministrator();
                    case 5 -> registerCourse();
                    case 6 -> registerClassroom();
                    case 7 -> enrollStudent();
                    case 8 -> recordAssessment();
                    case 9 -> recordPayment();
                    case 10 -> registerBook();
                    case 11 -> borrowBook();
                    case 12 -> returnBook();
                    case 13 -> viewAllRecords();
                    case 14 -> searchRecord();
                    case 15 -> updateRecord();
                    case 16 -> studentAcademicSummary();
                    case 17 -> studentPaymentHistory();
                    case 18 -> viewAvailableBooks();
                    case 19 -> demonstratePolymorphism();
                    case 0 -> System.out.println("Thank you for using AUCA School Management System.");
                    default -> System.out.println("Invalid menu option.");
                }
            } catch (ValidationException | DuplicateRecordException |
                     RecordNotFoundException | PrerequisiteMissingException |
                     BookUnavailableException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DateTimeParseException | NumberFormatException e) {
                System.out.println("Invalid input format. Please try again.");
            } catch (Exception e) {
                System.out.println("Operation could not be completed: " + e.getMessage());
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n==========================================");
        System.out.println("      AUCA SCHOOL MANAGEMENT SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. Register Department");
        System.out.println("2. Register Student");
        System.out.println("3. Register Teacher");
        System.out.println("4. Register Administrator");
        System.out.println("5. Register Course");
        System.out.println("6. Register Classroom");
        System.out.println("7. Enroll Student");
        System.out.println("8. Record Assessment");
        System.out.println("9. Record Fee Payment");
        System.out.println("10. Register Library Book");
        System.out.println("11. Borrow Library Book");
        System.out.println("12. Return Library Book");
        System.out.println("13. View All Records");
        System.out.println("14. Search Record by ID");
        System.out.println("15. Update Existing Record");
        System.out.println("16. Student Academic Summary");
        System.out.println("17. Student Payment History");
        System.out.println("18. View Available Books");
        System.out.println("19. Demonstrate Polymorphism");
        System.out.println("0. Exit");
        System.out.println("==========================================");
    }

    private static String read(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(read(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(read(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            try {
                return LocalDate.parse(read(prompt));
            } catch (DateTimeParseException e) {
                System.out.println("Use YYYY-MM-DD format.");
            }
        }
    }

    private static boolean readBoolean(String prompt) {
        while (true) {
            String value = read(prompt + " (yes/no): ");
            if (value.equalsIgnoreCase("yes")) return true;
            if (value.equalsIgnoreCase("no")) return false;
            System.out.println("Enter yes or no.");
        }
    }

    private static void checkUnique(String id, List<? extends Identifiable> records)
            throws DuplicateRecordException {
        for (Identifiable record : records) {
            if (record.getId().equalsIgnoreCase(id)) {
                throw new DuplicateRecordException("A record with this ID already exists.");
            }
        }
    }

    private static Department selectDepartment() throws RecordNotFoundException {
        if (departments.isEmpty()) {
            throw new RecordNotFoundException("No departments registered. Register a department first.");
        }

        System.out.println("\nAvailable Departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getDepartmentName()
                    + " [" + departments.get(i).getDepartmentId() + "]");
        }

        int choice = readInt("Select department number: ");
        if (choice < 1 || choice > departments.size()) {
            throw new RecordNotFoundException("Invalid department selection.");
        }
        return departments.get(choice - 1);
    }

    private static Student selectStudent() throws RecordNotFoundException {
        if (students.isEmpty()) {
            throw new RecordNotFoundException("No students registered.");
        }

        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getDisplayName()
                    + " [" + students.get(i).getStudentNumber() + "]");
        }

        int choice = readInt("Select student number: ");
        if (choice < 1 || choice > students.size()) {
            throw new RecordNotFoundException("Invalid student selection.");
        }
        return students.get(choice - 1);
    }

    private static Teacher selectTeacher() throws RecordNotFoundException {
        if (teachers.isEmpty()) {
            throw new RecordNotFoundException("No teachers registered.");
        }

        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ". " + teachers.get(i).getDisplayName()
                    + " [" + teachers.get(i).getEmployeeNumber() + "]");
        }

        int choice = readInt("Select teacher number: ");
        if (choice < 1 || choice > teachers.size()) {
            throw new RecordNotFoundException("Invalid teacher selection.");
        }
        return teachers.get(choice - 1);
    }

    private static Course selectCourse() throws RecordNotFoundException {
        if (courses.isEmpty()) {
            throw new RecordNotFoundException("No courses registered.");
        }

        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseTitle()
                    + " [" + courses.get(i).getCourseCode() + "]");
        }

        int choice = readInt("Select course number: ");
        if (choice < 1 || choice > courses.size()) {
            throw new RecordNotFoundException("Invalid course selection.");
        }
        return courses.get(choice - 1);
    }

    private static LibraryBook selectBook() throws RecordNotFoundException {
        if (books.isEmpty()) {
            throw new RecordNotFoundException("No books registered.");
        }

        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).getTitle()
                    + " (Available: " + books.get(i).getAvailableCopies() + ")");
        }

        int choice = readInt("Select book number: ");
        if (choice < 1 || choice > books.size()) {
            throw new RecordNotFoundException("Invalid book selection.");
        }
        return books.get(choice - 1);
    }

    private static void registerDepartment()
            throws ValidationException, DuplicateRecordException {

        System.out.println("\n--- REGISTER DEPARTMENT ---");

        String id = read("Department ID: ");
        checkUnique(id, departments);

        String name = read("Department name: ");
        String code = read("Department code: ");
        String office = read("Office location: ");
        String email = read("Contact email: ");

        Department department = new Department(id, name, code, office, email, null);
        department.validate();

        if (!teachers.isEmpty() && readBoolean("Assign a head teacher now?")) {
            department.setHeadTeacher(selectTeacher());
        }

        departments.add(department);
        System.out.println("Department registered successfully.");
    }

    private static void registerStudent()
            throws ValidationException, DuplicateRecordException, RecordNotFoundException {

        System.out.println("\n--- REGISTER STUDENT ---");

        String id = read("Person ID: ");
        checkUnique(id, students);

        String firstName = read("First name: ");
        String lastName = read("Last name: ");
        String email = read("Personal email: ");
        String phone = read("Phone number: ");
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");

        String memberNumber = read("Member number: ");
        Department department = selectDepartment();
        LocalDate joinDate = readDate("Join date (YYYY-MM-DD): ");
        String status = read("Academic status (ACTIVE/INACTIVE): ");
        String institutionalEmail = read("Institutional email: ");
        String advisor = read("Advisor name: ");

        String studentNumber = read("Student number: ");
        String program = read("Program name: ");
        LocalDate admissionDate = readDate("Admission date (YYYY-MM-DD): ");
        int year = readInt("Study year (1-6): ");
        String guardianPhone = read("Guardian phone: ");
        boolean scholarship = readBoolean("Scholarship status");

        Student student = new Student(
                id, firstName, lastName, email, phone, dob,
                memberNumber, department, joinDate, status,
                institutionalEmail, advisor, studentNumber, program,
                admissionDate, year, guardianPhone, scholarship
        );

        student.validate();

        for (Student existing : students) {
            if (existing.getStudentNumber().equalsIgnoreCase(studentNumber)) {
                throw new DuplicateRecordException("Student number already exists.");
            }
        }

        students.add(student);
        System.out.println("Student registered successfully.");
    }

    private static void registerTeacher()
            throws ValidationException, DuplicateRecordException, RecordNotFoundException {

        System.out.println("\n--- REGISTER TEACHER ---");

        String id = read("Person ID: ");
        checkUnique(id, teachers);

        String firstName = read("First name: ");
        String lastName = read("Last name: ");
        String email = read("Personal email: ");
        String phone = read("Phone number: ");
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");

        String memberNumber = read("Member number: ");
        Department department = selectDepartment();
        LocalDate joinDate = readDate("Join date (YYYY-MM-DD): ");
        String status = read("Academic status (ACTIVE/INACTIVE): ");
        String institutionalEmail = read("Institutional email: ");
        String advisor = read("Advisor name: ");

        String employeeNumber = read("Employee number: ");
        String specialization = read("Specialization: ");
        LocalDate hireDate = readDate("Hire date (YYYY-MM-DD): ");
        BigDecimal salary = new BigDecimal(read("Salary: "));
        String office = read("Office number: ");
        String employmentType = read("Employment type (FULL-TIME/PART-TIME): ");

        Teacher teacher = new Teacher(
                id, firstName, lastName, email, phone, dob,
                memberNumber, department, joinDate, status,
                institutionalEmail, advisor, employeeNumber,
                specialization, hireDate, salary, office, employmentType
        );

        teacher.validate();

        for (Teacher existing : teachers) {
            if (existing.getEmployeeNumber().equalsIgnoreCase(employeeNumber)) {
                throw new DuplicateRecordException("Employee number already exists.");
            }
        }

        teachers.add(teacher);
        System.out.println("Teacher registered successfully.");
    }

    private static void registerAdministrator()
            throws ValidationException, DuplicateRecordException {

        System.out.println("\n--- REGISTER ADMINISTRATOR ---");

        String id = read("Person ID: ");
        checkUnique(id, administrators);

        Administrator administrator = new Administrator(
                id,
                read("First name: "),
                read("Last name: "),
                read("Email: "),
                read("Phone number: "),
                readDate("Date of birth (YYYY-MM-DD): "),
                read("Staff number: "),
                read("Job title: "),
                read("Assigned office: "),
                readDate("Employment date (YYYY-MM-DD): "),
                readInt("Access level (1-5): "),
                readBoolean("Active")
        );

        administrator.validate();

        for (Administrator existing : administrators) {
            if (existing.getStaffNumber().equalsIgnoreCase(administrator.getStaffNumber())) {
                throw new DuplicateRecordException("Staff number already exists.");
            }
        }

        administrators.add(administrator);
        System.out.println("Administrator registered successfully.");
    }

    private static void registerCourse()
            throws ValidationException, DuplicateRecordException, RecordNotFoundException {

        System.out.println("\n--- REGISTER COURSE ---");

        String id = read("Course ID: ");
        checkUnique(id, courses);

        String code = read("Course code: ");
        for (Course existing : courses) {
            if (existing.getCourseCode().equalsIgnoreCase(code)) {
                throw new DuplicateRecordException("Course code already exists.");
            }
        }

        String title = read("Course title: ");
        int credits = readInt("Credit hours (1-6): ");

        System.out.println("Select course department:");
        Department department = selectDepartment();

        System.out.println("Select assigned teacher:");
        Teacher teacher = selectTeacher();

        Course course = new Course(id, code, title, credits, department, teacher);
        course.validate();

        courses.add(course);
        System.out.println("Course registered successfully.");
    }

    private static void registerClassroom()
            throws ValidationException, DuplicateRecordException {

        System.out.println("\n--- REGISTER CLASSROOM ---");

        String id = read("Classroom ID: ");
        checkUnique(id, classrooms);

        Classroom classroom = new Classroom(
                id,
                read("Building name: "),
                read("Room number: "),
                readInt("Capacity: "),
                readInt("Floor number: "),
                readBoolean("Available")
        );

        classroom.validate();
        classrooms.add(classroom);
        System.out.println("Classroom registered successfully.");
    }

    private static void enrollStudent()
            throws ValidationException, DuplicateRecordException, RecordNotFoundException {

        System.out.println("\n--- ENROLL STUDENT ---");

        String id = read("Enrollment ID: ");
        checkUnique(id, enrollments);

        Student student = selectStudent();
        Course course = selectCourse();

        String semester = read("Semester: ");

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student)
                    && enrollment.getCourse().equals(course)
                    && enrollment.getSemester().equalsIgnoreCase(semester)
                    && enrollment.isActive()) {
                throw new DuplicateRecordException("Student already has an active enrollment for this course and semester.");
            }
        }

        Enrollment enrollment = new Enrollment(
                id, student, course, LocalDate.now(), semester, "ACTIVE"
        );

        enrollment.validate();
        enrollments.add(enrollment);
        System.out.println("Student enrolled successfully.");
    }

    private static void recordAssessment()
            throws ValidationException, DuplicateRecordException, RecordNotFoundException {

        System.out.println("\n--- RECORD ASSESSMENT ---");

        if (enrollments.isEmpty()) {
            throw new RecordNotFoundException("Register an enrollment before recording an assessment.");
        }

        String id = read("Assessment ID: ");
        checkUnique(id, assessments);

        for (int i = 0; i < enrollments.size(); i++) {
            System.out.println((i + 1) + ". " + enrollments.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select enrollment: ");
        if (choice < 1 || choice > enrollments.size()) {
            throw new RecordNotFoundException("Invalid enrollment selection.");
        }

        Enrollment enrollment = enrollments.get(choice - 1);

        Assessment assessment = new Assessment(
                id,
                enrollment,
                read("Assessment type: "),
                readDouble("Maximum score: "),
                readDouble("Obtained score: "),
                readDate("Assessment date (YYYY-MM-DD): ")
        );

        assessment.validate();
        assessments.add(assessment);
        System.out.println("Assessment recorded successfully.");
    }

    private static void recordPayment()
            throws ValidationException, DuplicateRecordException, RecordNotFoundException {

        System.out.println("\n--- RECORD FEE PAYMENT ---");

        String id = read("Payment ID: ");
        checkUnique(id, payments);

        Student student = selectStudent();
        BigDecimal amount = new BigDecimal(read("Amount: "));
        LocalDate date = readDate("Payment date (YYYY-MM-DD): ");
        String method = read("Payment method (CASH/MOBILE MONEY/BANK): ");
        String receipt = read("Receipt number: ");

        for (FeePayment payment : payments) {
            if (payment.getReceiptNumber().equalsIgnoreCase(receipt)) {
                throw new DuplicateRecordException("Receipt number already exists.");
            }
        }

        FeePayment payment = new FeePayment(id, student, amount, date, method, receipt);
        payment.validate();

        payments.add(payment);
        System.out.println("Payment recorded successfully.");
    }

    private static void registerBook()
            throws ValidationException, DuplicateRecordException {

        System.out.println("\n--- REGISTER LIBRARY BOOK ---");

        String id = read("Book ID: ");
        checkUnique(id, books);

        LibraryBook book = new LibraryBook(
                id,
                read("ISBN: "),
                read("Book title: "),
                read("Author: "),
                readInt("Total copies: "),
                readInt("Available copies: ")
        );

        book.validate();
        books.add(book);
        System.out.println("Book registered successfully.");
    }

    private static void borrowBook()
            throws ValidationException, DuplicateRecordException,
            RecordNotFoundException, BookUnavailableException {

        System.out.println("\n--- BORROW LIBRARY BOOK ---");

        String id = read("Loan ID: ");
        checkUnique(id, loans);

        Student student = selectStudent();
        LibraryBook book = selectBook();

        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("No copies of this book are available.");
        }

        LocalDate borrowedDate = LocalDate.now();
        LocalDate dueDate = readDate("Due date (YYYY-MM-DD): ");

        BookLoan loan = new BookLoan(
                id, student, book, borrowedDate, dueDate, null
        );

        loan.validate();

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        loans.add(loan);

        System.out.println("Book borrowed successfully.");
    }

    private static void returnBook() throws RecordNotFoundException {
        System.out.println("\n--- RETURN LIBRARY BOOK ---");

        List<BookLoan> activeLoans = new ArrayList<>();

        for (BookLoan loan : loans) {
            if (loan.isActive()) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            throw new RecordNotFoundException("There are no active book loans.");
        }

        for (int i = 0; i < activeLoans.size(); i++) {
            System.out.println((i + 1) + ". " + activeLoans.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select loan to return: ");

        if (choice < 1 || choice > activeLoans.size()) {
            throw new RecordNotFoundException("Invalid loan selection.");
        }

        BookLoan loan = activeLoans.get(choice - 1);
        loan.setReturnedDate(LocalDate.now());

        LibraryBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        System.out.println("Book returned successfully.");
    }

    private static List<Registrable> allRecords() {
        List<Registrable> records = new ArrayList<>();

        records.addAll(departments);
        records.addAll(students);
        records.addAll(teachers);
        records.addAll(administrators);
        records.addAll(courses);
        records.addAll(classrooms);
        records.addAll(enrollments);
        records.addAll(assessments);
        records.addAll(payments);
        records.addAll(books);
        records.addAll(loans);

        return records;
    }

    private static void viewAllRecords() {
        System.out.println("\n--- ALL REGISTERED RECORDS ---");

        List<Registrable> records = allRecords();

        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Registrable record : records) {
            record.printDetails();
        }
    }

    private static void searchRecord() {
        System.out.println("\n--- SEARCH RECORD BY ID ---");

        String id = read("Enter record ID: ");

        for (Registrable record : allRecords()) {
            if (record instanceof Identifiable identifiable
                    && identifiable.getId().equalsIgnoreCase(id)) {
                record.printDetails();
                return;
            }
        }

        System.out.println("No record found with ID: " + id);
    }

    private static void updateRecord() {
        System.out.println("\n--- UPDATE EXISTING RECORD ---");

        String id = read("Enter record ID: ");

        for (Administrator administrator : administrators) {
            if (administrator.getId().equalsIgnoreCase(id)) {
                if (readBoolean("Change administrator active status?")) {
                    administrator.setActive(!administrator.isActive());
                    System.out.println("Administrator status updated.");
                }
                return;
            }
        }

        for (Classroom classroom : classrooms) {
            if (classroom.getId().equalsIgnoreCase(id)) {
                if (readBoolean("Change classroom availability?")) {
                    classroom.setAvailable(!classroom.isAvailable());
                    System.out.println("Classroom availability updated.");
                }
                return;
            }
        }

        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                student.setAcademicStatus(read("Enter new academic status: "));
                System.out.println("Student status updated.");
                return;
            }
        }

        System.out.println("Record not found or this record has no update option.");
    }

    private static void studentAcademicSummary() throws RecordNotFoundException {
        System.out.println("\n--- STUDENT ACADEMIC SUMMARY ---");

        Student student = selectStudent();

        System.out.println("Student: " + student.getDisplayName());
        System.out.println("Student Number: " + student.getStudentNumber());
        System.out.println("Program: " + student.getProgramName());
        System.out.println("Department: " + student.getDepartment().getDepartmentName());
        System.out.println("Study Year: " + student.getStudyYear());

        System.out.println("\nEnrollments:");

        boolean found = false;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student)) {
                System.out.println(enrollment.getRegistrationSummary()
                        + " | Semester: " + enrollment.getSemester()
                        + " | Status: " + enrollment.getEnrollmentStatus());
                found = true;
            }
        }

        if (!found) System.out.println("No enrollments found.");

        System.out.println("\nAssessments:");

        found = false;

        for (Assessment assessment : assessments) {
            if (assessment.getEnrollment().getStudent().equals(student)) {
                System.out.println(assessment.getAssessmentType()
                        + ": " + assessment.getObtainedScore()
                        + "/" + assessment.getMaximumScore());
                found = true;
            }
        }

        if (!found) System.out.println("No assessments found.");
    }

    private static void studentPaymentHistory() throws RecordNotFoundException {
        System.out.println("\n--- STUDENT PAYMENT HISTORY ---");

        Student student = selectStudent();

        BigDecimal total = BigDecimal.ZERO;
        boolean found = false;

        for (FeePayment payment : payments) {
            if (payment.getStudent().equals(student)) {
                payment.printDetails();
                total = total.add(payment.getAmount());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No payments found for this student.");
        } else {
            System.out.println("Total payments: " + total);
        }
    }

    private static void viewAvailableBooks() {
        System.out.println("\n--- AVAILABLE LIBRARY BOOKS ---");

        boolean found = false;

        for (LibraryBook book : books) {
            if (book.getAvailableCopies() > 0) {
                book.printDetails();
                found = true;
            }
        }

        if (!found) System.out.println("No books are currently available.");
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n--- POLYMORPHISM DEMONSTRATION ---");

        List<Person> people = new ArrayList<>();

        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);

        System.out.println("\nPerson references:");

        if (people.isEmpty()) {
            System.out.println("Register students, teachers, or administrators first.");
        }

        for (Person person : people) {
            System.out.println(person.getRoleDescription()
                    + " | " + person.getDisplayName()
                    + " | " + person.getRegistrationSummary()
                    + " | Active: " + person.isActive());
        }

        System.out.println("\nIdentifiable interface references:");

        List<Identifiable> identifiableRecords = new ArrayList<>();

        identifiableRecords.addAll(departments);
        identifiableRecords.addAll(courses);
        identifiableRecords.addAll(classrooms);
        identifiableRecords.addAll(enrollments);
        identifiableRecords.addAll(assessments);
        identifiableRecords.addAll(payments);
        identifiableRecords.addAll(books);
        identifiableRecords.addAll(loans);

        if (identifiableRecords.isEmpty()) {
            System.out.println("No other records registered.");
        }

        for (Identifiable record : identifiableRecords) {
            System.out.println("ID: " + record.getId()
                    + " | Name: " + record.getDisplayName()
                    + " | Active: " + record.isActive());
        }
    }
}
