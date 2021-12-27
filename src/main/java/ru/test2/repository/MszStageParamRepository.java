package ru.test2.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import ru.pfr.Tables;
import ru.pfr.tables.records.MszstageparamRecord;
import ru.test2.mock.hbase.MszStageParam;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public class MszStageParamRepository {
  private final DSLContext dslContext;

  public void save(final MszStageParam mszStageParam) {
    log.info("SAVING MszstageparamRecord " + mszStageParam);

    final MszstageparamRecord mszstageparamRecord = new MszstageparamRecord();
    mszstageparamRecord.setId(mszStageParam.getId());
    mszstageparamRecord.setMszstageid(mszStageParam.getMszStageId());
    mszstageparamRecord.setBzmszstageparamid(mszStageParam.getBzMszStageParamId());
    //todo: очень плохой код
    final byte[] bytes = String.valueOf(mszStageParam.getValue())
                               .getBytes(StandardCharsets.UTF_8);
    mszstageparamRecord.setValue(bytes);

    dslContext.insertInto(Tables.MSZSTAGEPARAM)
              .set(mszstageparamRecord)
              .execute();

  }
}
