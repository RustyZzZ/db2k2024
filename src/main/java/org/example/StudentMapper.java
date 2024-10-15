package org.example;

import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class StudentMapper {

  private static final ModelMapper modelMapper = new ModelMapper();

  {
    modelMapper.addMappings(new PropertyMap<Document, Student>() {

      @Override
      protected void configure() {
        destination.setFirstName(source.getString("firstName"));
        destination.setStudentId(source.getString("studentId"));
        destination.setGroupId(source.getString("group_id"));
        destination.setLastName(source.getString("lastName"));
      }
    });
  }


  public static Student getStudentDocument(Document document) {
    return modelMapper.map(document, Student.class);
  }

  public static Document getDocumentFromStudent(Student student) {
    return new Document()
        .append("firstName", student.getFirstName())
        .append("lastName", student.getLastName())
        .append("studentId", student.getStudentId())
        .append("group_id", student.getGroupId())
        .append("_id", student.get_id());
  }


}
