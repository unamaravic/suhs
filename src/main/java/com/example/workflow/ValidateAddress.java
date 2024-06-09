package com.example.workflow;

import com.example.workflow.service.AddressService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("validateAddress")
public class ValidateAddress implements JavaDelegate {

    @Autowired
    private AddressService addressService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String naziv = (String) execution.getVariable("naziv");
        String mjesto = (String) execution.getVariable("mjesto");
        String pbr = (String) execution.getVariable("pbr");

        boolean isValid = addressService.isMjestoPbrValid(mjesto, pbr);

        // Postavljanje varijable 'isValid'
        execution.setVariable("rezultat", isValid);
    }
}
