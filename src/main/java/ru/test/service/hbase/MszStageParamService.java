package ru.test.service.hbase;

import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;

import java.util.ArrayList;
import java.util.List;

public class MszStageParamService {
  //todo: mock. replace with REST
  private final ArrayList<MszStageParam> mock = new ArrayList<>();

  public void saveAll(final List<MszStageParam> list) {
    for (MszStageParam mszStageParam : list) {
      save(mszStageParam);
    }
  }

  public void save(MszStageParam mszStageParam) {
    //todo:
  }

  public List<MszStageParam> findAllByMszStage(MszStage mszStage) {
    //todo:
    return List.of();
  }

  public MszStageParam create(BzMszStageParam bzMszStageParam) {
    return null;
  }
}
