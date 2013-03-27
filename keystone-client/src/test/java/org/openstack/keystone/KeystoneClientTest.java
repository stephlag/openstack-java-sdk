package org.openstack.keystone;

import junit.framework.Assert;

import org.junit.Test;
import org.openstack.keystone.KeystoneClient;
import org.openstack.keystone.api.Authenticate;
import org.openstack.keystone.api.ListTenants;
import org.openstack.keystone.model.Access;
import org.openstack.keystone.model.Authentication;
import org.openstack.keystone.model.Authentication.PasswordCredentials;
import org.openstack.keystone.model.Authentication.Token;
import org.openstack.keystone.model.Tenant;
import org.openstack.keystone.model.Tenants;
import org.openstack.keystone.utils.KeystoneUtils;

public class KeystoneClientTest {

	@Test
	public void testKeystoneAuth() {
		KeystoneClient keystone = new KeystoneClient(
				"http://localhost:5000/v2.0/");
		Authentication authentication = new Authentication();
		PasswordCredentials passwordCredentials = new PasswordCredentials();
		passwordCredentials.setUsername("swift");
		passwordCredentials.setPassword("swift");
		authentication.setPasswordCredentials(passwordCredentials);

		// access with unscoped token
		Access access = keystone.execute(new Authenticate(authentication));

		String mainToken = access.getToken().getId();
		System.out.println("MainToken : " + mainToken + " until "
				+ access.getToken().getExpires().getTime());
		Assert.assertNotNull(mainToken);
		// use the token in the following requests
		keystone.setToken(mainToken);

		Tenants tenants = keystone.execute(new ListTenants());

		// try to exchange token using the first tenant
		for (Tenant tenant : tenants) {

			authentication = new Authentication();
			Token token = new Token();
			token.setId(mainToken);
			authentication.setToken(token);
			authentication.setTenantId(tenant.getId());

			access = keystone.execute(new Authenticate(authentication));
			System.out.println(tenant + " and his token "
					+ access.getToken().getId());

			String endpointURL = KeystoneUtils.findEndpointURL(access.getServiceCatalog(), "object-store",
					null, "public");
			System.out.println(endpointURL);
		}
	}
}
