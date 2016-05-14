package com.artem.students.services;

import com.artem.students.entities.Group;
import com.artem.students.entities.Student;
import com.artem.students.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by denis on 13.03.2016.
 */
@Service
@Transactional
public class StudentService {

    @Autowired
    StudentDao dao;

    public List<Group> getAllGroups() {
        return dao.getAllGroups();
    }

    public Group getOneGroup(Long id) {
        return dao.getOneGroup(id);
    }

    public List<Student> getGroupStudents(Long id) {
        Group group = dao.getOneGroup(id);
        if (group != null) {
            return dao.getGroupStudents(id);
        }
        return null;
    }

    public boolean addStudent(Long id, Long student) {
        dao.releaseStudent(student);
        return dao.addStudent(id, student);
    }

    public boolean addGroup(Group group) {
        return dao.createGroup(group);
    }

    public boolean deleteGroup(Long id) {
        if (dao.groupIsEmpty(id)) {
            dao.deleteOneGroup(id);
            return true;
        }
        return false;
    }

    public boolean updateGroup(Group group) {
        return dao.updateGroup(group);
    }

    public List<Student> getAllStudents() {
        return dao.getAllStudents();
    }

    public Student getOneStudent(Long id) {
        return dao.getOneStudent(id);
    }

    public int updateStudent(Student student) {
        return dao.update(student);
    }


    public Long createStudent(Student student) {
        return dao.createOneStudent(student);
    }

    public int delete(Long id) {
        return dao.deleteOneStudent(id);
    }

    public Group getStudentGroup(Long id) {
        return dao.getStudentGroup(id);
    }
}
