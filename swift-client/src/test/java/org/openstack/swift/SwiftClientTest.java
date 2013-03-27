package org.openstack.swift;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.openstack.keystone.utils.KeystoneUtils;
import org.openstack.swift.api.ListContainers;
import org.openstack.swift.api.ShowAccount;
import org.openstack.swift.model.Container;

public class SwiftClientTest {

	private final String token = "15e961c382c645dd9c070d594913b38c";

	
	@Test
	public void testSwiftClient() {
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
			    new javax.net.ssl.HostnameVerifier(){
			 
			        public boolean verify(String hostname,
			                javax.net.ssl.SSLSession sslSession) {
			            if (hostname.equals("localhost")) {
			                return true;
			            }
			            return false;
			        }
			    });
		try {
			SwiftClient client = new SwiftClient("https://localhost:8443/v1/AUTH_497e388381ac4f689d1bf9dd2ceddb2f",
					token);
			Response response = client.execute(new ShowAccount());
			System.out.println(response.getStatus());
			List<Container> containers = client.execute(new ListContainers());
			for (Container container : containers) {
				System.out.println(container);
			}
		} catch (ServerErrorException see) {
			System.out.println("HTTP Error : "
					+ see.getResponse().getStatusInfo().getStatusCode());
			throw see;
		}
	}

	
	
	
}
