package ru.test2.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import ru.pfr.Tables;
import ru.pfr.tables.records.MszRecord;
import ru.test2.mock.hbase.Msz;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class MszRepository {
  private final DSLContext dslContext;

  public List<Msz> findAllByHumanId(String humanId) {
    return dslContext.selectFrom(Tables.MSZ)
                     .where(Tables.MSZ.PERSONID.eq(humanId))
                     .fetch()
                     .stream()
                     .map(this::map)
                     .collect(Collectors.toList());
  }

  public void save(Msz msz) {
    log.info("SAVING Msz " + msz);

    dslContext.insertInto(Tables.MSZ)
              .set(map(msz))
              .execute();

    log.info("SAVING Msz complete" + msz);

  }

  @NotNull
  private MszRecord map(Msz msz) {
    final MszRecord mszRecord = new MszRecord();
    mszRecord.setId(msz.getId());
    mszRecord.setPersonid(msz.getPersonId());
    mszRecord.setBzmszid(msz.getBzMszId());
    return mszRecord;
  }

  @NotNull
  private Msz map(MszRecord mszRecord) {
    final Msz msz = new Msz();
    msz.setId(mszRecord.getId());
    msz.setPersonId(mszRecord.getPersonid());
    msz.setBzMszId(mszRecord.getBzmszid());
    return msz;
  }
}
