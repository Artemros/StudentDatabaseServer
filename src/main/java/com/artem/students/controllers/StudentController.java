package com.artem.students.controllers;

import com.artem.students.entities.Group;
import com.artem.students.entities.Student;
import com.artem.students.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by denis on 12.03.2016.
 */

@RestController()
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Long createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudentById(@PathVariable("id") Long id) {
        return studentService.getOneStudent(id);
    }

    @RequestMapping(value = "/{id}/group", method = RequestMethod.GET)
    public Group getStudentGroup(@PathVariable("id") Long id) {
        return studentService.getStudentGroup(id);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public int updateStudent(@PathVariable("id") Long id,
                             @RequestBody Student student) {
        student.setId(id);
        return studentService.updateStudent(student);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public int deleteStudent(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }
}
