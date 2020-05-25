package com.example.demo.repositories;

import com.example.demo.models.Student;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements IStudentRepository {
    private Connection conn;

    public StudentRepositoryImpl(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public boolean create(Student student) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Students " +
                    "VALUES ('"+
                    student.firstName + "','" +
                    student.lastName + "','" +
                    ((int) student.enrollmentDate.getYear()+1900) + "-" + (student.enrollmentDate.getMonth()+1) + "-" + student.enrollmentDate.getDate() + "','" +
                    student.cpr +
                    "');");
            ps.executeUpdate();
            return true;
        } catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public Student read(String cprno) {
        Student studentToReturn = new Student();
        try {
            PreparedStatement getSingleStudent = conn.prepareStatement("SELECT * FROM students WHERE cpr='" + cprno + "'");
            ResultSet rs = getSingleStudent.executeQuery();
            while(rs.next()){
                studentToReturn = new Student();
                studentToReturn.setFirstName(rs.getString(1));
                studentToReturn.setLastName(rs.getString(2));
                studentToReturn.setEnrollmentDate(rs.getDate(3));
                studentToReturn.setCpr(rs.getString(4));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return studentToReturn;
    }

    @Override
    public List<Student> readAll() {
        List<Student> allStudents = new ArrayList<Student>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Students");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Student tempStudent = new Student();
                tempStudent.setFirstName(rs.getString(1));
                tempStudent.setLastName(rs.getString(2));
                tempStudent.setEnrollmentDate(rs.getDate(3));
                tempStudent.setCpr(rs.getString(4));
                allStudents.add(tempStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allStudents;
    }

    @Override
    public boolean update(Student student) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Students SET Fornavn = '"+ student.firstName + "'" + ", Efternavn='" + student.lastName + "'" + ", Opstartsdato='" + ((int) student.enrollmentDate.getYear()+1900) + "-" + (student.enrollmentDate.getMonth()+1) + "-" + student.enrollmentDate.getDate() + "'"+ " WHERE CPR=" + "'"+ student.cpr + "';");
            ps.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean delete(String cprno) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Students WHERE cpr='" + cprno + "';");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
