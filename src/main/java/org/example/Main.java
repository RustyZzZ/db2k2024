package org.example;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.DBRefCodecProvider;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
  public static void main(String[] args) {
    ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder()
        .automatic(true)
        .build());
    CodecRegistry codecRegistry = fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        pojoCodecRegistry);
    MongoClientSettings clientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .codecRegistry(codecRegistry)
        .build();

    try (MongoClient mongoClient = MongoClients.create(clientSettings)) {


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
      studentRepository.findDtoById("S1007").ifPresent(System.out::println);

      System.out.println("*************************NEW CODE");
      var newStudent = Student.builder()
          .firstName("Rostyslav")
          .lastName("Diachuk")
          .dateOfBirth(LocalDate.now())
          .groupId("312232")
          .id(new ObjectId())
          .studentId("S10009")
          .build();
      studentRepository.saveStudent(newStudent);

      studentRepository.findDtoById("S10009").ifPresent(System.out::println);
    }
  }


}