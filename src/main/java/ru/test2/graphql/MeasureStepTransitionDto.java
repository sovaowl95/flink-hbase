package ru.test2.graphql;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasureStepTransitionDto {
	private Integer id;
	private Integer fromStepId;
	private Integer toStepId;
	private Integer decisionId;
	private Integer measureId;
}
