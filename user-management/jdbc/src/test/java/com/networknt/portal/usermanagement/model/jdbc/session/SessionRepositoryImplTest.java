package com.networknt.portal.usermanagement.model.jdbc.session;


import com.networknt.portal.usermanagement.model.common.model.session.Session;
import com.networknt.portal.usermanagement.model.common.model.session.SessionRepository;
import com.networknt.portal.usermanagement.model.common.utils.IdentityGenerator;
import com.networknt.service.SingletonServiceFactory;
import org.h2.tools.RunScript;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Junit test class for SessionRepositoryImpl.
 * use H2 test database for data source
 */
public class SessionRepositoryImplTest {

    public static DataSource ds;

    static {
        ds = (DataSource) SingletonServiceFactory.getBean(DataSource.class);
        try (Connection connection = ds.getConnection()) {
            // Runscript doesn't work need to execute batch here.
            String schemaResourceName = "/user_management_ddl.sql";
            InputStream in = SessionRepositoryImplTest.class.getResourceAsStream(schemaResourceName);

            if (in == null) {
                throw new RuntimeException("Failed to load resource: " + schemaResourceName);
            }
            InputStreamReader reader = new InputStreamReader(in);
            RunScript.execute(connection, reader);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private SessionRepository sessionRepository = (SessionRepository)SingletonServiceFactory.getBean(SessionRepository.class);

    private static Session session;
    private static  Long  id;
    private static  Long  userId;
    @BeforeClass
    public static void setUp() {
        id = IdentityGenerator.generate();
        userId = IdentityGenerator.generate();
        session = new Session(id, userId, "aaaaaabbbbbbbccccccccc");
        session.setLastUsedAt(LocalDateTime.now());

    }

    @Test
    public void testSave() {
        Session result = sessionRepository.save(session);
        assertNotNull(result);
    }

    @Test
    public void testFindById() {
        Optional<Session> result = sessionRepository.findById(id);
        assertTrue(result.isPresent());
    }

    @Test
    public void findByUserId() {
        List<Session> result = sessionRepository.findByUserId(userId);
   //     assertTrue(result.size() > 0);
    }
}

