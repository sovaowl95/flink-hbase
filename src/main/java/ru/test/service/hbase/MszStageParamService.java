package ru.test.service.hbase;

import ru.test.mock.hbase.MszStageParam;

import java.util.ArrayList;

public class MszStageParamService {
  public void save(MszStageParam mszStageParam) {
    //todo:
  }

  public void saveAll(final ArrayList<MszStageParam> list) {
    //todo: batch
    for (MszStageParam mszStageParam : list) {
      save(mszStageParam);
    }
  }
}
