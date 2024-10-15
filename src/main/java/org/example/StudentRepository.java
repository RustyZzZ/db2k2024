package org.example;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
  private MongoDatabase db;

  public StudentRepository(MongoDatabase db) {
    this.db = db;
  }

  public List<Student> getAllStudents() {
    return db.getCollection("students").find()
        .map(StudentMapper::getStudentDocument).into(new ArrayList<>());
  }

  public Optional<Student> getStudentById(String id) {
    var query = eq("studentId", id);
    return findStudentByQuery(query);
  }


  public Optional<Student> getStudentByName(String firstName, String lastName) {
    Document query = new Document();
    query.put("firstName", firstName);
    query.put("lastName", lastName);
    return findStudentByQuery(query);
  }

  public Optional<StudentDto> findDtoById(String id) {
    return db.getCollection("students")
        .find(eq("studentId", id))
        .projection(Projections.include("firstName", "lastName"))
        .map(doc -> toDto(doc))
        .into(new ArrayList<>())
        .stream()
        .findAny();
  }


  public void saveStudent(Student student) {
    db.getCollection("students")
        .insertOne(StudentMapper.getDocumentFromStudent(student));
  }

  private static StudentDto toDto(Document doc) {
    return StudentDto.builder()
        .firstName(doc.getString("firstName"))
        .lastName(doc.getString("lastName"))
        .build();
  }

  public Optional<Student> getStudentByFirstNameOrStudentId(String firstName, String studentId) {
    return findStudentByQuery(or(
        eq("firstName", firstName),
        eq("studentId", studentId)));
  }

  private Optional<Student> findStudentByQuery(Bson query) {
    return db.getCollection("students")
        .find(query)
        .map(document -> mapStudentWithGroup(document))
        .into(new ArrayList<>())
        .stream()
        .findAny();
  }

  private Student mapStudentWithGroup(Document document) {
    Group group = findGroupById(document.getString("group.$id"));
    Student student = StudentMapper.getStudentDocument(document);
    student.setGroup(group);
    return student;
  }

  private Group findGroupById(String groupId) {
    return db.getCollection("groups")
        .find(eq("_id", groupId))
        .map(groupDoc -> new Group(
            groupDoc.getString("_id"),
            new StudentDto(
                groupDoc.getString("curator.firstName"),
                groupDoc.getString("curator.lastName")),
            groupDoc.getString("name")
        )).first();
  }

}
