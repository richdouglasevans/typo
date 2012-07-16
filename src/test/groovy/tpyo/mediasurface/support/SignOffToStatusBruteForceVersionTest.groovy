package tpyo.mediasurface.support

import com.mediasurface.client.IItem
import com.mediasurface.client.IStatus
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import tpyo.mediasurface.Editor
import tpyo.mediasurface.MediasurfaceForTesting
import tpyo.mediasurface.MediasurfaceOperations
import tpyo.mediasurface.MediasurfaceSession
import org.junit.Ignore

@Ignore( 'Requires actual Mediasurface connection.' )
final class SignOffToStatusBruteForceVersionTest
{
    @Test
    public void signOffItemThatDoesNotAllowTheSameUserToProgressTheWholeWayThroughTheWorkflow( ) throws Exception
    {
        def publishedStatus = getPublishedStatus()
        def signOffToPublished = new SignOffToStatusBruteForceVersion( itemToBeSignedOff.key, publishedStatus )

        mediasurface.execute( signOffToPublished )
    }

    private static MediasurfaceOperations mediasurface

    private IItem itemToBeSignedOff

    @BeforeClass
    public static void beforeAnyTestsAreRun( ) throws Exception
    {
        mediasurface = MediasurfaceForTesting.templateForDestination()
    }

    @Before
    public void beforeEachTestIsRun( ) throws Exception
    {
        itemToBeSignedOff = createTheItemToBeSignedOff()
    }

    @After
    public void afterEachTestIsRun( ) throws Exception
    {
        mediasurface.deleteItemPermanently( itemToBeSignedOff.key )
    }

    private static IStatus getPublishedStatus( ) throws Exception
    {
        mediasurface.getNamedStatus( WellKnownPublishingStatus.Published.name() )
    }

    private static IItem createTheItemToBeSignedOff( ) throws Exception
    {
        def generalDocumentType = mediasurface.getNamedType( 'Blank Branch' )
        def generalDocumentTypeKey = generalDocumentType.key

        def createItem = new CreateItem( generalDocumentTypeKey, 'Rick Test' )
        def enterContent = {
            MediasurfaceSession session, IItem item ->
            item.simpleName = 'rick_test'
            item
        } as Editor;

        mediasurface.edit( createItem, enterContent )
    }
}
