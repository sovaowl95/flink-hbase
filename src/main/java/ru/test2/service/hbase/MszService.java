package ru.test2.service.hbase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.test2.mock.bz.BzMsz;
import ru.test2.mock.hbase.Msz;
import ru.test2.repository.MszRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MszService {
  private final MszRepository mszRepository;


  public Map<String, Msz> findAllKeyBzMszId(String humanId) {
    List<Msz> list = mszRepository.findAllByHumanId(humanId);

    final HashMap<String, Msz> objectObjectHashMap = new HashMap<>();
    for (final Msz msz : list) {
      if (msz.getPersonId().equals(humanId)) {
        objectObjectHashMap.put(msz.getBzMszId(), msz);
      }
    }

    return objectObjectHashMap;
  }


  public Map<BzMsz, Optional<Msz>> createMszMap(final String personId,
                                                final Collection<BzMsz> bzMszCollection) {
    final Map<String, Msz> currentMsz = findAllKeyBzMszId(personId);

    final HashMap<BzMsz, Optional<Msz>> map = new HashMap<>();
    for (BzMsz bzMsz : bzMszCollection) {
      map.put(bzMsz, Optional.of(currentMsz.getOrDefault(bzMsz.getId(), null)));
    }
    return map;
  }

  public void save(Msz msz) {
    mszRepository.save(msz);
  }
}
