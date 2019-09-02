package com.redhat.example.calc;

import javax.jws.HandlerChain;
import javax.jws.WebService;

@WebService(name="Calc", serviceName="CalcService")
@HandlerChain(file="handlers.xml")
public class CalcImpl {
	public int add(int i, int j) {
		return i + j;
	}

}
