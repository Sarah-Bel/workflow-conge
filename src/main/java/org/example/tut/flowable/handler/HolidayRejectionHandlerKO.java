package org.example.tut.flowable.handler;

import lombok.Data;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

@Data

public class HolidayRejectionHandlerKO implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {

        System.out.println("Conge KO");

    }
}

