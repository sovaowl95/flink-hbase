package ru.test2.service.hbase;

import ecp.zhs.Output;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.test2.mock.bz.BzMszStageParam;
import ru.test2.mock.hbase.MszStageParam;
import ru.test2.repository.MszStageParamRepository;
import ru.test2.util.UnpackUtil;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class MszStageParamService {
  private final MszStageParamRepository mszStageParamRepository;

  public MszStageParam create(final String mszStageId,
                              final BzMszStageParam bzMszStageParam,
                              final Output value) {
    final Object val = UnpackUtil.unpack(value.getValue(), bzMszStageParam.getType());

    final MszStageParam mszStageParam = new MszStageParam();
    mszStageParam.setId(UUID.randomUUID().toString());
    mszStageParam.setMszStageId(mszStageId);
    mszStageParam.setBzMszStageParamId(bzMszStageParam.getId());
    mszStageParam.setValue(val);
    return mszStageParam;
  }

  public void saveAll(final List<MszStageParam> list) {
    for (MszStageParam mszStageParam : list) {
      save(mszStageParam);
    }
  }

  public void save(MszStageParam mszStageParam) {
    mszStageParamRepository.save(mszStageParam);
  }
}
