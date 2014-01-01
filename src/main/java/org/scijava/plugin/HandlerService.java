/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2014 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package org.scijava.plugin;

import java.util.List;

/**
 * A service for managing {@link HandlerPlugin}s of a particular type.
 * <p>
 * For any given data object, the service is capable of determining the most
 * appropriate handler by sequentially querying each handler plugin on its list
 * (via {@link HandlerPlugin#supports}).
 * </p>
 * 
 * @author Curtis Rueden
 * @param <DT> Base data type handled by the handlers.
 * @param <PT> Plugin type of the handlers.
 * @see HandlerPlugin
 */
public interface HandlerService<DT, PT extends HandlerPlugin<DT>> extends
	SingletonService<PT>, TypedService<DT, PT>
{

	/**
	 * Gets the most appropriate handler for the given data object, or null if no
	 * handler supports it.
	 */
	PT getHandler(DT data);

	// NB: Javadoc overrides.

	// -- SingletonService methods --

	/**
	 * Gets the list of handlers. There will be one singleton instance for each
	 * available handler plugin.
	 */
	@Override
	List<PT> getInstances();

	// -- Typed methods --

	/** Gets whether the given data object is supported. */
	@Override
	boolean supports(DT data);

}
