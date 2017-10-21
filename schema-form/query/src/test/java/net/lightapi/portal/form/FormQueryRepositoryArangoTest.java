package net.lightapi.portal.form;

import com.arangodb.ArangoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.config.Config;
import com.networknt.service.SingletonServiceFactory;
import net.lightapi.portal.form.FormRepository;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FormQueryRepositoryArangoTest {
    static ArangoDB arangoDB;
    static FormRepository formQueryRepository = SingletonServiceFactory.getBean(FormRepository.class);
    ObjectMapper mapper = Config.getInstance().getMapper();
    @BeforeClass
    public static void setUp() {
        arangoDB = (ArangoDB)formQueryRepository.getDataSource();
        // add testing data
    }

    @AfterClass
    public static void tearDown() {
        // remove testing data
    }

    @Test
    public void testForm() throws Exception {
        // clean up all vertexes if exist.

        formQueryRepository.removeForm("e1");

        String site1 = "{\"id\":\"2233-55\",\"description\":\"example site\"}";
        formQueryRepository.createForm("e1", site1);

        String result = formQueryRepository.getFormByEntityId("e1");
        System.out.println("result = " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetForm() {
        String result = formQueryRepository.getForm();
        System.out.println("result = " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testCreateAnotherForm() {
        formQueryRepository.removeForm("e2");
        String site2 = "{\"id\":\"2233-555\",\"description\":\"example site\"}";
        formQueryRepository.createForm("e2", site2);
        String result = formQueryRepository.getFormByEntityId("e2");
        Assert.assertNotNull(result);
        String result2 = formQueryRepository.getFormByEntityId("e20");
        Assert.assertNull(result2);
    }

    @Test
    public void testUpdateAndGetMenu() {
        formQueryRepository.removeForm("e2");
        String site2 = "{\"id\":\"2233-555\",\"description\":\"example site\"}";
        formQueryRepository.createForm("e2", site2);
        String site2_new = "{\"id\":\"2233-555\",\"description\":\"real site\"}";
        formQueryRepository.updateForm("e2", site2_new);
        System.out.println(formQueryRepository.getForm());
    }

    @Test
    public void testRemove() {
        formQueryRepository.removeForm("e2");
        formQueryRepository.removeForm("e1");
    }
}
