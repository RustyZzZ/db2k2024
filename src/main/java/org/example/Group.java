package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {
  private String _id;
  private StudentDto curator;
  private String name;
}
