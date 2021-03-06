/**
 * Candybean is a next generation automation and testing framework suite.
 * It is a collection of components that foster test automation, execution
 * configuration, data abstraction, results illustration, tag-based execution,
 * top-down and bottom-up batches, mobile variants, test translation across
 * languages, plain-language testing, and web service testing.
 * Copyright (C) 2013 SugarCRM, Inc. <candybean@sugarcrm.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.sugarcrm.candybean.examples.yelp;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import org.junit.Test;

import com.sugarcrm.candybean.CB;
import com.sugarcrm.candybean.automation.VInterface;
import com.sugarcrm.candybean.automation.Candybean;
import com.sugarcrm.candybean.configuration.Configuration;
import com.sugarcrm.candybean.examples.yelp.YelpUser.YelpUserBuilder;
import com.sugarcrm.candybean.utilities.Utils;

public class YelpTest {
	
	private static Candybean candybean;
	private static VInterface iface;
	private static Yelp yelp;
		
	@BeforeClass
	public static void first() throws Exception {
		String candybeanConfigStr = System.getProperty("candybean_config");
		if (candybeanConfigStr == null) {
			candybeanConfigStr = CB.CONFIG_DIR.getCanonicalPath() + File.separator + "candybean.config";
		}
		Configuration candybeanConfig = new Configuration(new File(Utils.adjustPath(candybeanConfigStr)));
		candybean = Candybean.getInstance(candybeanConfig);
		iface = candybean.getInterface();
		String yelpHooksStr = System.getProperty("yelp_hooks");
		if (yelpHooksStr == null) {
			yelpHooksStr = CB.CONFIG_DIR.getCanonicalPath() + File.separator + "yelp.hooks";
		}
		Properties yelpHooks = new Properties();
		yelpHooks.load(new FileInputStream(new File(yelpHooksStr)));
		YelpUser user = new YelpUserBuilder("Sugar", "Stevens", "95014", "cwarmbold@sugarcrm.com", "Sugar123!").build();
		yelp = new Yelp(iface, yelpHooks, user);
		iface.start();
		yelp.start();
	}

	@Ignore
	@Test
	public void yelpLoginLogoutTest() throws Exception {
		yelp.login();
		yelp.logout();
	}
	
//	@Ignore
	@Test
	public void yelpRandomTest() throws Exception {
		int timeout_in_minutes = 10;
		yelp.run(timeout_in_minutes);
	}
	
	@AfterClass
	public static void last() throws Exception {
		yelp.stop();
		iface.stop();
	}
}	
