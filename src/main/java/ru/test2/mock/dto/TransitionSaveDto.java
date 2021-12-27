package ru.test2.mock.dto;

import lombok.Data;
import ru.test2.mock.hbase.Msz;
import ru.test2.mock.hbase.MszStage;
import ru.test2.mock.hbase.MszStageParam;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransitionSaveDto {
  private Msz msz;
  private MszStage mszStage;
  private List<MszStageParam> paramList = new ArrayList<>();
}
