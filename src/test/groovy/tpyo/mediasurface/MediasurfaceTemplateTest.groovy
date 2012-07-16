package tpyo.mediasurface

import com.mediasurface.client.IType
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.*
import static tpyo.mediasurface.MediasurfaceForTesting.destination
import static tpyo.mediasurface.MediasurfaceForTesting.sessionFactory
import org.junit.Ignore

@Ignore( 'Requires actual Mediasurface connection.' )
final class MediasurfaceTemplateTest
{
    private static MediasurfaceTemplate template

    private static final String WELL_KNOWN_TYPE_NAME = 'ContentFolder'

    @BeforeClass
    public static void beforeAnyTestsAreRun( ) throws Exception
    {
        template = MediasurfaceForTesting.template( sessionFactory( destination() ) )
    }

    @Test
    public void smokeTestLoginWithGoodCredentials( ) throws Exception
    {
        def callback = { MediasurfaceSession session ->

            assertTrue session.connected
            assertEquals destination().host, session.host.name

        } as MediasurfaceCallback;

        template.execute( callback )
    }

    @Test
    public void getNamedType( ) throws Exception
    {
        IType type = template.getNamedType( WELL_KNOWN_TYPE_NAME )

        assertNotNull type
        assertEquals( WELL_KNOWN_TYPE_NAME, type.name )
    }

    @Test(expected = NullPointerException)
    public void editWithFetchCallbackThatReturnsNullMustThrowException( ) throws Exception
    {
        def fetchThatReturnsNull = { MediasurfaceSession session -> return null } as MediasurfaceCallback;
        def doNothing = {} as Editor;

        template.edit( fetchThatReturnsNull, doNothing )
    }

    @Test
    public void getBoundItemsForSiteRoot( ) throws Exception
    {
        def rootItem = template.getRootItem()
        def boundItems = template.getBoundItems( rootItem )

        assertNotNull boundItems
        assertFalse boundItems.empty
    }
}
