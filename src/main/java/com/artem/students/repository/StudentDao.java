package com.artem.students.repository;

import com.artem.students.entities.Group;
import com.artem.students.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by denis on 13.03.2016.
 */
@Repository
public class StudentDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RowMapper studentMapper = new RowMapper<Student>() {

        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setId(resultSet.getLong("student_id"));
            student.setName(resultSet.getString("student_name"));
            student.setLastName(resultSet.getString("student_second_name"));
            student.setThirdName(resultSet.getString("student_third_name"));
            return student;
        }
    };

    public RowMapper<Group> groupMapper = new RowMapper<Group>() {

        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            Group group = new Group();
            group.setId(resultSet.getLong("group_id"));
            group.setName(resultSet.getString("group_name"));
            return group;
        }
    };

    public Long createOneStudent(Student student) {
        jdbcTemplate.update("INSERT INTO students (student_name,student_second_name,student_third_name) VALUES (?,?,?)",new Object[]{student.getName(),student.getLastName(),student.getThirdName()});
        return jdbcTemplate.queryForObject( "select last_insert_id()",Long.class);
    }

    public List<Student> getAllStudents() {
        return jdbcTemplate.query("select * from students", studentMapper);
    }

    public Student getOneStudent(Long id) {
        return (Student) jdbcTemplate.queryForObject("select * from students where student_id = ?", new Object[]{id}, studentMapper);
    }

    public int update(Student student) {
        return jdbcTemplate.update("UPDATE students SET student_name=?,student_second_name=?,student_third_name=? WHERE student_id=?", new Object[]{student.getName(),student.getLastName(),student.getThirdName(),student.getId()});

    }

    public int deleteOneStudent(Long id) {
        return jdbcTemplate.update("DELETE FROM students WHERE student_id=?",new Object[]{id});
    }



    public Group getOneGroup(Long id) {
        return jdbcTemplate.queryForObject("select * from groups where group_id = ?", new Object[]{id}, groupMapper);
    }

    public List<Student> getGroupStudents(Long id) {
        return jdbcTemplate.query("select students.* from students, group_students\n" +
                "where students.student_id = group_students.student_id\n" +
                "and group_students.group_id = ?", studentMapper, new Object[]{id});
    }

    public Group getStudentGroup(Long id) {
        List<Group> groups =  jdbcTemplate.query("SELECT groups.* from groups, group_students\n" +
                "where groups.group_id = group_students.group_id\n" +
                "and group_students.student_id = ?", new Object[]{id}, groupMapper);
        if (!groups.isEmpty())
            return groups.get(0);
        return null;
    }
    public List<Group> getAllGroups() {
        return jdbcTemplate.query("SELECT * FROM groups", groupMapper, new Object[]{});
    }

    public boolean isStudentAvailable(Long student) {
        Long result = (Long) jdbcTemplate.queryForObject("SELECT count(*) FROM group_students\n" +
                "where student_id = ?", new Object[]{student}, Long.class);
        return result == 0;
    }

    public boolean addStudent(Long id, Long student) {
        return jdbcTemplate.update("INSERT into group_students (group_id, student_id) VALUES (?, ?) ", new Object[]{id, student}) > 0;
    }

    public boolean createGroup(Group group) {
        return jdbcTemplate.update("INSERT into groups (group_name) VALUES (?) ", new Object[]{group.getName()}) > 0;
    }

    public boolean groupIsEmpty(Long id) {
        Long result = (Long) jdbcTemplate.queryForObject("SELECT count(*) FROM group_students\n" +
                "where group_id = ?", new Object[]{id}, Long.class);
        return result == 0;
    }

    public boolean deleteOneGroup(Long id) {
        return jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?", new Object[]{id}) > 1;
    }

    public boolean updateGroup(Group group) {
        return jdbcTemplate.update("UPDATE TABLE groups set group_name = ? WHERE GROUP_ID = ?", new Object[]{group.getName(), group.getId()}) > 1;
    }

    public void releaseStudent(Long student) {
        jdbcTemplate.update("DELETE FROM group_students WHERE student_id = ?", new Object[]{student});
    }
}
