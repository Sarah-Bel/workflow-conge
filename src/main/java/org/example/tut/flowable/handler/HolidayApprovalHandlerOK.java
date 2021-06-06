package org.example.tut.flowable.handler;

import org.flowable.engine.delegate.DelegateExecution;
import lombok.Data;
import org.flowable.engine.delegate.JavaDelegate;

@Data
public class HolidayApprovalHandlerOK implements JavaDelegate {

	 @Override
	    public void execute(DelegateExecution execution) {

	        System.out.println("ok");
	    }
}
