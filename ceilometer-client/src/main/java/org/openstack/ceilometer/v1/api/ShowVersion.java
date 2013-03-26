package org.openstack.ceilometer.v1.api;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.openstack.ceilometer.CeilometerCommand;
import org.openstack.ceilometer.v1.model.Version;

public class ShowVersion implements CeilometerCommand<Version> {

	@Override
	public Version execute(WebTarget target) {
		return target.request(MediaType.APPLICATION_JSON).get(Version.class);
	}

}
