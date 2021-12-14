package ru.test;

import ecp.zhs.Output;
import ecp.zhs.OutputVersion;
import ecp.zhs.WaveStateUpdate;
import ru.test.mock.EtapMSZ;
import ru.test.mock.ParametrEtapaMSZ;
import ru.test.mock.Perehod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SaveService {
  void save(WaveStateUpdate waveStateUpdate, String personId) {
    final OutputVersion allOutputs = waveStateUpdate.getAllOutputs();

    /**
     * обновляем все параметры в БД.
     */

    //todo: 1 привязан ли данный аутпут к чему-то?
    //
    List<ParametrEtapaMSZ> parametrEtapaMSZS = new ArrayList<>();
    for (Map.Entry<String, Output> entry : allOutputs.getOutputsMap().entrySet()) {
      //Ключ-идентификатор узла в дереве расчетов
      final Output value = entry.getValue();
      final String key = value.getOutputId();// todo: ключ лежит в Output.outputid = ParametrEtapaMSZ.id

      final ParametrEtapaMSZ parametrEtapaMSZ = ParametrEtapaMSZ.getByKey(key);
      parametrEtapaMSZ.setValue(value.getValue());
      parametrEtapaMSZ.save();

      parametrEtapaMSZS.add(parametrEtapaMSZ);
    }


    /**
     * Поиск переходов
     */
    var memo = new HashSet<EtapMSZ>();
    for (ParametrEtapaMSZ pEtapaMSZ : parametrEtapaMSZS) {
      final var etapMSZ = EtapMSZ.findByParametrEtapaMSZ(pEtapaMSZ);
      if (memo.contains(etapMSZ)) {
        continue;
      } else {
        memo.add(etapMSZ);
      }
    }


    /**
     * какие из них стали true?
     */
    for (EtapMSZ etapMSZ : memo) {
      //??
    }


    Perehod current = Perehod.findForUser();
    final Perehod perehodTo = current.getTo();
    perehodTo.checkAll

    //todo: запись в историю расчетов
    //todo: запись ЖС, прав на меру, итд
  }
}
