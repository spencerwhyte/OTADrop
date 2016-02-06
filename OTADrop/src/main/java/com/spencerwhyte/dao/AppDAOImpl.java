package com.spencerwhyte.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.spencerwhyte.configuration.OtaDropApplication;
import com.spencerwhyte.model.App;

/*
	create table apps(
	  uuid varchar(255),
	  name varchar (255),
	  bundleid varchar (255),
	  bundleversion varchar (255),
	  buildNumber varchar (255),
	  PRIMARY KEY (uuid)
	);
*/


public class AppDAOImpl implements AppDAO {
	private JdbcTemplate jdbcTemplate;
	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());
	
    public AppDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public void add(App app) {
		String sql = "INSERT INTO apps (uuid, name, bundleid, bundleversion, buildNumber) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, app.getUuid(), app.getName(), app.getBundleId(), app.getBundleVersion(), app.getBuildNumber());
	}

	@Override
	public App get(String uuid) {
		String sql = "SELECT * FROM apps WHERE uuid='"+uuid+"'";
		log.debug("Querying for app with id: " + uuid);
		return jdbcTemplate.query(sql, new ResultSetExtractor<App>() {
			
			@Override
	        public App extractData(ResultSet rs) throws SQLException,
	                DataAccessException {
				log.debug("Selected from apps");
	            if (rs.next()) {
	            	log.debug("Selected from apps, actually found something");
	            	String uuid = rs.getString("uuid");
	            	String name = rs.getString("name");
	            	String bundleid = rs.getString("bundleid");
	            	String bundleversion = rs.getString("bundleversion");
	            	String buildNumber = rs.getString("buildNumber");
	                App app = new App(uuid, name, bundleid, bundleversion, buildNumber);
	                return app;
	            }
	 
	            return null;
	        }
	 
	    });
	}
 

 
}
