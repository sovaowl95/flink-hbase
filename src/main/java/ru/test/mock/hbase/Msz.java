package ru.test.mock.hbase;

import lombok.Data;
import ru.test.mock.bz.BzMsz;

@Data
public class Msz {
  private String id;
  private String personId;
  private BzMsz bzMsz;
}
