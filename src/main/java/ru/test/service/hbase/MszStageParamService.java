package ru.test.service.hbase;

import ru.test.mock.hbase.MszStageParam;

import java.util.ArrayList;

public class MszStageParamService {
  //todo: mock. replace with REST
  private final ArrayList<MszStageParam> mock = new ArrayList<>();

  public void saveAll(final ArrayList<MszStageParam> list) {
    for (MszStageParam mszStageParam : list) {
      save(mszStageParam);
    }
  }

  public void save(MszStageParam mszStageParam) {
    //todo:
  }
}
