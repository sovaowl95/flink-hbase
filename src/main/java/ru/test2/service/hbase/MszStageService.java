package ru.test2.service.hbase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.test2.mock.bz.BzMszTransition;
import ru.test2.mock.hbase.Msz;
import ru.test2.mock.hbase.MszStage;
import ru.test2.repository.MszStageRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class MszStageService {
  private final MszStageRepository mszStageRepository;

  public Optional<MszStage> findByMszOptional(final Optional<Msz> mszOptional) {
    if (mszOptional.isEmpty()) {
      return Optional.empty();
    }
    return findByMszId(mszOptional.get().getBzMszId());
  }

  public Optional<MszStage> findByMszId(String mszId) {
    return mszStageRepository.findByMszId(mszId);
  }

  public MszStage createMszStage(final BzMszTransition bzMszTransition,
                                 final String bzMszStageId,
                                 final Msz msz) {
    final MszStage mszStage = new MszStage();
    mszStage.setId(UUID.randomUUID().toString());
    mszStage.setMszId(msz.getId());
    mszStage.setBzMszTransactionStagesId(bzMszTransition.getId());
    mszStage.setBzMszStageId(bzMszStageId);
    return mszStage;
  }

  public void save(MszStage mszStage) {
    mszStageRepository.save(mszStage);
  }
}
