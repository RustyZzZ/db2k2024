package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
  private String firstName;
  private String lastName;
  private String studentId;
  private String groupId;
  private ObjectId _id;
  private Group group;
}
