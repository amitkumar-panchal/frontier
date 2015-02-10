/*******************************************************************************
 * Copyright (c) 2014 Imperial College London
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Raul Castro Fernandez - initial API and implementation
 ******************************************************************************/
package uk.ac.imperial.lsds.seep.acita15.operators;

import java.util.List;

import uk.ac.imperial.lsds.seep.GLOBALS;
import uk.ac.imperial.lsds.seep.comm.serialization.DataTuple;
import uk.ac.imperial.lsds.seep.operator.StatelessOperator;

public class Sink implements StatelessOperator {
	private static final long serialVersionUID = 1L;
	private long numTuples;
	private long tuplesReceived = 0;
	
	public void setUp() {
		numTuples = Long.parseLong(GLOBALS.valueFor("numTuples"));
		System.out.println("SINK expecting "+numTuples+" tuples.");
	}
	
	public void processData(DataTuple dt) {
		
		if (tuplesReceived == 0)
		{
			System.out.println("SNK: Received initial tuple at t="+System.currentTimeMillis());
		}
		
		tuplesReceived++;
		recordTuple(dt);
		long tupleId = dt.getLong("tupleId");
		if (tupleId != tuplesReceived -1)
		{
			System.out.println("SNK: Received tuple " + tuplesReceived + " out of order, id="+tupleId);
		}
		
		if (tuplesReceived >= numTuples)
		{
			System.out.println("SNK: FINISHED with total tuples="+tuplesReceived+",t="+System.currentTimeMillis());
			System.exit(0);
		}
	}
	
	private void recordTuple(DataTuple dt)
	{
		System.out.println("SNK: Received tuple with cnt="+tuplesReceived 
				+",id="+dt.getLong("tupleId")
				+",txts="+dt.getPayload().timestamp
				+",rxts="+System.currentTimeMillis());
	}
	
	public void processData(List<DataTuple> arg0) {
	}
}
