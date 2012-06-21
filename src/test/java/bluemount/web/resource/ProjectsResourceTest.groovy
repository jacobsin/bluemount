package bluemount.web.resource

import bluemount.core.service.ProjectService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner)
public class ProjectsResourceTest {

    ProjectsResource projectsResource
    @Mock ProjectService projectService

    @Before
    void setUp() throws Exception {
        projectsResource = new ProjectsResource(projectService)
    }

    @Test
    void list() throws Exception {
        def expected = []
        when(projectService.list()).thenReturn(expected)

        def actual = projectsResource.list()

        assert actual.is(expected)
        verify(projectService, times(1)).list()
    }
}
