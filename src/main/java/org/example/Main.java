package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public class Main {
  public static void main(String[] args) {
    try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
      MongoDatabase db = mongoClient.getDatabase("test");

      StudentRepository studentRepository = new StudentRepository(db);
      List<Student> allStudents = studentRepository.getAllStudents();
      allStudents.forEach(System.out::println);
      System.out.println("*************************");
      Optional<Student> mbStudent = studentRepository.getStudentById("S1007");
      mbStudent.ifPresent(System.out::println);

      System.out.println("*************************");
      studentRepository.getStudentByFirstNameOrStudentId("asdas", "S1007")
          .ifPresent(System.out::println);
      System.out.println("*************************NEW CODE");
      studentRepository.findDtoById("S1007")
          .ifPresent(System.out::println);

      System.out.println("*************************NEW CODE");
      var newStudent = Student.builder()
          .firstName("Rostyslav")
          .lastName("Diachuk")
          .groupId("312232")
          ._id(new ObjectId())
          .studentId("S10009")
          .build();
      studentRepository.saveStudent(newStudent);

      studentRepository.findDtoById("S10009")
          .ifPresent(System.out::println);
    }
  }


}