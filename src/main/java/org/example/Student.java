package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
  private String firstName;
  private String lastName;
  private String studentId;
  private LocalDate dateOfBirth;
  @BsonProperty("group_id")
  private String groupId;
  @BsonProperty("_id")
  private ObjectId id;
  @BsonIgnore
  private Group group;
}