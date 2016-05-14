package com.artem.students.controllers;

import com.artem.students.entities.Group;
import com.artem.students.entities.Student;
import com.artem.students.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by denis on 13.03.2016.
 */
@RestController()
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private StudentService service;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Group> getAllGroups() {
        return service.getAllGroups();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Boolean addGroup(@RequestBody Group group) {
        return service.addGroup(group);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Group getGroupById(@PathVariable("id") Long id) {
        return service.getOneGroup(id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public void deleteGroup(@PathVariable("id") Long id) {
        service.deleteGroup(id);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public Boolean updateGroup(@RequestBody Group group) {
        return service.updateGroup(group);
    }


    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    public List<Student> getStudents(@PathVariable("id") Long id) {
        return service.getGroupStudents(id);
    }

    @RequestMapping(value = "/{id}/students", method = RequestMethod.POST)
    public Boolean addStudents(@PathVariable("id") Long id,
                                     @RequestBody Long student) {
        return service.addStudent(id, student);
    }
}
