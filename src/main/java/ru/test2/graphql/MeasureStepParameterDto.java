package ru.test2.graphql;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasureStepParameterDto {
	private Integer id;
	private String title;
	private Integer measureStepId;
	private Integer decisionId;	
	private String code;
}
