package ru.test.service.hbase;

import lombok.extern.slf4j.Slf4j;
import ru.test.mock.bz.BzMszStageParam;
import ru.test.mock.hbase.MszStageParam;

import java.util.List;
import java.util.UUID;

@Slf4j
public class MszStageParamService {
  public List<MszStageParam> findAllByMszStageId(String mszStageId) {
    //todo: APPOLO!
    return List.of();
  }

  public MszStageParam create(final String mszStageId,
                              final BzMszStageParam bzMszStageParam,
                              final Object value) {
    final MszStageParam mszStageParam = new MszStageParam();
    mszStageParam.setId(UUID.randomUUID().toString());
    mszStageParam.setMszStageId(mszStageId);
    mszStageParam.setBzMszStageParamId(bzMszStageParam.getId());
    mszStageParam.setValue(value);
    return mszStageParam;
  }

  public void saveAll(final List<MszStageParam> list) {
    for (MszStageParam mszStageParam : list) {
      save(mszStageParam);
    }
  }

  public void save(MszStageParam mszStageParam) {
    log.info("SAVING MszStageParam " + mszStageParam);
  }
}
