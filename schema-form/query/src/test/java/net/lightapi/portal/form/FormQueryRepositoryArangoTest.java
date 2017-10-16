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

        String site1 = "{\"host\":\"example.com\",\"description\":\"example site\",\"contains\":[\"1\",\"2\",\"3\"]}";
        formQueryRepository.createMenu("e1", site1);

        String result = formQueryRepository.getMenuByHost("example.com");
        System.out.println("result = " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetMenuByHost() {
        String result = formQueryRepository.getMenuByHost("example.com");
        System.out.println("result = " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testCreateAnotherMenu() {
        formQueryRepository.removeMenu("e2");
        String site2 = "{\"host\":\"example.org\",\"description\":\"example site\",\"contains\":[\"1\",\"2\",\"3\"]}";
        formQueryRepository.createMenu("e2", site2);
    }

    @Test
    public void testUpdateAndGetMenu() {
        formQueryRepository.removeMenu("e2");
        String site2 = "{\"host\":\"example.org\",\"description\":\"example site\",\"contains\":[\"1\",\"2\",\"3\"]}";
        formQueryRepository.createMenu("e2", site2);
        String site2_new = "{\"host\":\"example.org\",\"description\":\"example site111\",\"contains\":[\"1\",\"5\",\"3\"]}";
        formQueryRepository.updateMenu("e2", site2_new);
        System.out.println(formQueryRepository.getMenu());
    }
}
