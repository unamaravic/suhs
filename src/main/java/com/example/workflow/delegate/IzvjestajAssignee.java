package com.example.workflow.delegate;

import org.camunda.bpm.engine.delegate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IzvjestajAssignee implements ExecutionListener {

    Logger logger = LoggerFactory.getLogger(IzvjestajAssignee.class);

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        logger.info("EVO MEEEEEEEEEEEEEEEEEEEE");
        System.out.println("pa dobro je");
    }
}
