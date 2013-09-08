/*******************************************************************************
 * Copyright (c) 2013 Imperial College London.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Raul Castro Fernandez - initial design and implementation
 ******************************************************************************/
package uk.ac.imperial.lsds.seep.reliable;

import java.util.ArrayList;
import java.util.Iterator;

import uk.ac.imperial.lsds.seep.operator.Streamable;

public class StreamStateManager {

	private static final int chunkSize = 10000;
	
	private Streamable state;
	private int totalNumberChunks;
	private Iterator stateIterator;
	
	public StreamStateManager(Streamable state){
		this.state = state;
		this.totalNumberChunks = state.getTotalNumberOfChunks(chunkSize);
		this.stateIterator = state.getIterator();
	}
	
	public int getTotalNumberChunks(){
		return totalNumberChunks;
	}
	
	public MemoryChunk getChunk(){
		MemoryChunk mc = null;
		if(stateIterator.hasNext()){
			ArrayList<Object> chunk = state.streamSplitState(chunkSize);
			if(chunk == null){
				return null;
			}
			mc = new MemoryChunk();
			mc.chunk = chunk;
		}
		return mc;
	}
}