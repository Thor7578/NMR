package com.example.demo.repositories;

import com.example.demo.models.Student;

import java.util.List;

public interface IStudentRepository {
    // CRUD operations
    public boolean create(Student student);

    public Student read(String cprno);

    public List<Student> readAll();

    public boolean update(Student student);

    public boolean delete(String cprno);
}


