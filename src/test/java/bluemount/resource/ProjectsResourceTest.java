package bluemount.resource;

import bluemount.model.Project;
import bluemount.service.ProjectService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectsResourceTest {

    private ProjectsResource projectsResource;
    @Mock
    private ProjectService projectService;

    @Before
    public void setUp() throws Exception {
        projectsResource = new ProjectsResource(projectService);
    }

    @Test
    public void testList() throws Exception {
        List<Project> expected = Arrays.asList();
        when(projectService.list()).thenReturn(expected);

        List<Project> actual = projectsResource.list();

        Assert.assertThat(actual, Matchers.sameInstance(expected));
        verify(projectService, times(1)).list();
    }
}
