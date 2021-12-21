package ru.test.service.bz;

import ru.test.mock.Constants;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.bz.BzMszStage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BzMszService {
  //todo: mock. replace with REST
  private final ArrayList<BzMsz> mock = new ArrayList<>();

  public BzMszService() {
    BzMsz el;

    el = new BzMsz();
    el.setId(Constants.BZ_MSZ_ID_1);
    mock.add(el);

    el = new BzMsz();
    el.setId(Constants.BZ_MSZ_ID_2);
    mock.add(el);

    el = new BzMsz();
    el.setId(Constants.BZ_MSZ_ID_3);
    mock.add(el);
  }


  public Optional<BzMsz> findByBzMszStage(BzMszStage bzMszStage) {
    final String targetId = bzMszStage.getId();

    for (BzMsz bzMsz : mock) {
      if (bzMsz.getId().equals(targetId)) {
        return Optional.of(bzMsz);
      }
    }

    return Optional.empty();
  }

  public List<BzMsz> findAll() {
    return mock;
  }
}
