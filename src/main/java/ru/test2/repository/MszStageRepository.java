package ru.test2.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import ru.pfr.Tables;
import ru.pfr.tables.records.MszstageRecord;
import ru.test2.mock.hbase.MszStage;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class MszStageRepository {
  private final DSLContext dslContext;

  public Optional<MszStage> findByMszId(String mszId) {
    final Optional<MszstageRecord> optionalMszstageRecord = dslContext.selectFrom(Tables.MSZSTAGE)
                                                                      .where(Tables.MSZSTAGE.MSZID.eq(mszId))
                                                                      .fetchOptionalInto(MszstageRecord.class);

    if (optionalMszstageRecord.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(map(optionalMszstageRecord.get()));
  }


  public void save(final MszStage mszStage) {
    log.info("SAVING MszStage " + mszStage);

    final MszstageRecord mszstageRecord = map(mszStage);

    dslContext.insertInto(Tables.MSZSTAGE)
              .set(mszstageRecord)
              .execute();

    log.info("SAVING MszStage complete" + mszStage);
  }

  @NotNull
  private MszstageRecord map(MszStage mszStage) {
    final MszstageRecord mszstageRecord = new MszstageRecord();
    mszstageRecord.setId(mszStage.getId());
    mszstageRecord.setMszid(mszStage.getMszId());
    mszstageRecord.setBzmsztransactionstagesid(mszStage.getBzMszTransactionStagesId());
    mszstageRecord.setBzmszstageid(mszStage.getBzMszStageId());
    return mszstageRecord;
  }

  @NotNull
  private MszStage map(MszstageRecord mszstageRecord) {
    final MszStage mszStage = new MszStage();
    mszStage.setId(mszstageRecord.getId());
    mszStage.setMszId(mszstageRecord.getMszid());
    mszStage.setBzMszTransactionStagesId(mszstageRecord.getBzmsztransactionstagesid());
    mszStage.setBzMszStageId(mszstageRecord.getBzmszstageid());
    return mszStage;
  }
}
