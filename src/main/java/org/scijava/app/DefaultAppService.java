/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2013 Board of Regents of the University of
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

package org.scijava.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.scijava.Priority;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;
import org.scijava.plugin.PluginService;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;

/**
 * Default service for application-level functionality.
 * 
 * @author Curtis Rueden
 */
@Plugin(type = Service.class, priority = Priority.LOW_PRIORITY)
public class DefaultAppService extends AbstractService implements AppService {

	@Parameter
	private LogService log;

	@Parameter
	private PluginService pluginService;

	/** Read-only table of SciJava applications. */
	private Map<String, App> apps;

	// -- AppService methods --

	@Override
	public App getApp(final String name) {
		return apps.get(name);
	}

	@Override
	public Map<String, App> getApps() {
		return apps;
	}

	// -- Service methods --

	@Override
	public void initialize() {
		apps = Collections.unmodifiableMap(discoverApps());
		log.info("Found " + apps.size() + " applications.");
	}

	// -- Helper methods --

	/** Discovers applications. */
	private HashMap<String, App> discoverApps() {
		final HashMap<String, App> map = new HashMap<String, App>();

		final List<PluginInfo<App>> infos =
			pluginService.getPluginsOfType(App.class);
		for (final PluginInfo<App> info : infos) {
			final App app = pluginService.createInstance(info);
			if (app == null) continue;
			final String name = info.getName();
			if (!map.containsKey(name)) {
				// no (higher-priority) app with the same name exists
				map.put(name, app);
			}
		}
		return map;
	}

}
