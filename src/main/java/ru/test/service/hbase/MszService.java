package ru.test.service.hbase;

import ru.test.mock.Constants;
import ru.test.mock.bz.BzMsz;
import ru.test.mock.hbase.Msz;

import java.util.ArrayList;
import java.util.Optional;

public class MszService {
  //todo: mock. replace with REST
  private final ArrayList<Msz> mock = new ArrayList<>();

  public MszService() {
    Msz msz;

    msz = new Msz();
    msz.setId(Constants.MSZ_ID_1);
    msz.setPersonId(Constants.PERSON_ID_1);
    msz.setBzMszId(Constants.BZ_MSZ_ID_1);
    mock.add(msz);

    msz = new Msz();
    msz.setId(Constants.MSZ_ID_2);
    msz.setPersonId(Constants.PERSON_ID_2);
    msz.setBzMszId(Constants.BZ_MSZ_ID_2);
    mock.add(msz);

    msz = new Msz();
    msz.setId(Constants.MSZ_ID_3);
    msz.setPersonId(Constants.PERSON_ID_3);
    msz.setBzMszId(Constants.BZ_MSZ_ID_3);
    mock.add(msz);
  }

  public Optional<Msz> findByMszAndPerson(final BzMsz bzMsz,
                                          final String personId) {
    final String id = bzMsz.getId();

    for (Msz msz : mock) {
      if (msz.getPersonId().equals(personId) && msz.getBzMszId().equals(id)) {
        return Optional.of(msz);
      }
    }

    return Optional.empty();
  }
}
