package bluemount.resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FirstServerResourceTest {
    private FirstServerResource firstServerResource;

    @Before
    public void setUp() throws Exception {
        firstServerResource = new FirstServerResource();
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("hello, world", firstServerResource.toString());
    }
}
