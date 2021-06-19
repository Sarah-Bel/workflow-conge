package com.workflow.dto;

import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProcessInstanceResponse {
	
	@OneToOne
  String processId;
  boolean isEnded;
}