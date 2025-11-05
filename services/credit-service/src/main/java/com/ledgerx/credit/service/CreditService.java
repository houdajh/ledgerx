package com.ledgerx.credit.service;

import com.ledgerx.credit.web.dto.CreditSimulationRequest;
import com.ledgerx.credit.web.dto.SimulationResult;

public interface CreditService {


    SimulationResult simulateCredit(CreditSimulationRequest request);
}
