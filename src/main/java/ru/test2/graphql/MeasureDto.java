package ru.test2.graphql;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasureDto {
	private Integer id;
	private String title;
}
