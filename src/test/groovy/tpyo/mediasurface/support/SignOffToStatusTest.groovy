package tpyo.mediasurface.support

import com.mediasurface.client.IItem
import com.mediasurface.client.IStatus
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import tpyo.mediasurface.Editor
import tpyo.mediasurface.MediasurfaceForTesting
import tpyo.mediasurface.MediasurfaceSession
import tpyo.mediasurface.MediasurfaceTemplate

import static org.junit.Assert.assertEquals
import org.junit.Ignore

@Ignore( 'Requires actual Mediasurface connection.' )
final class SignOffToStatusTest
{
    @Test(expected = SignOffException)
    public void signOffSunnyDay( ) throws Exception
    {
        def publishedStatus = getPublishedStatus()
        def signOffToPublished = new SignOffToStatus( itemToBeSignedOff.key, publishedStatus )
        mediasurface.execute( signOffToPublished )

        IItem itemAfterSignOff = mediasurface.getItemAtPath( itemToBeSignedOff.path )
        assertEquals( 'The signed-off item must be in the target status', publishedStatus, itemAfterSignOff.status )
    }

    @Test(expected = SignOffException)
    public void signOffToStatusEarlierInThePublishingWorkflow( ) throws Exception
    {
        def publishedStatus = getPublishedStatus()
        def signOffToPublished = new SignOffToStatus( itemToBeSignedOff.key, publishedStatus )

        mediasurface.execute( signOffToPublished )
        IItem itemAfterSignOff = mediasurface.getItemAtPath( itemToBeSignedOff.path )

        assertEquals( 'The signed-off item must be in the target status', publishedStatus, itemAfterSignOff.status )

        // now attempt to sign-off the item back to the 'New' status
        def newStatus = mediasurface.getNamedStatus( WellKnownPublishingStatus.New.name() )
        def signOffToNew = new SignOffToStatus( itemToBeSignedOff.key, newStatus )

        // we expect this to fail with a SignOffException
        mediasurface.execute( signOffToNew )
    }

    private static MediasurfaceTemplate mediasurface

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
        def generalDocumentType = mediasurface.getNamedType( 'Blank Leaf' )
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
