package ru.test2.tasks.transition.dto;

import lombok.Data;
import ru.test.mock.hbase.Msz;
import ru.test.mock.hbase.MszStage;
import ru.test.mock.hbase.MszStageParam;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransitionSaveDto {
  private Msz msz;
  private MszStage mszStage;
  private List<MszStageParam> paramList = new ArrayList<>();
}
