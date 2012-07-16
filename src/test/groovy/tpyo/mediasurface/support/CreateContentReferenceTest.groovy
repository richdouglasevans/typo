package tpyo.mediasurface.support

import com.mediasurface.client.IItem
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test
import tpyo.mediasurface.MediasurfaceForTesting
import tpyo.mediasurface.MediasurfaceTemplate

import static org.junit.Assert.*
import org.junit.Ignore

@Ignore( 'Requires actual Mediasurface connection.' )
final class CreateContentReferenceTest
{
    @Test
    public void createReferenceToKosherContent( ) throws Exception
    {
        def homepage = mediasurface.getRootItem()
        def contentReferenceType = mediasurface.getNamedType( 'foo ContentReference' )
        def createContentReference = new CreateContentReference( contentReferenceType.key, 'Home', homepage )
        createdContentReference = mediasurface.execute( createContentReference )

        assertNotNull createdContentReference
        assertEquals 'Home', createdContentReference.fullName

        assertTrue( 'A content reference link must have been added in the contained items hash.',
              createdContentReference.containedItems.containsKey( CreateContentReference.LINK_NAME_CONTENT_REFERENCE ) )
        IItem link = createdContentReference.containedItems.'ms:contentRef'
        assertNotNull 'The link in the contained items hash must not be null.', link
        assertEquals( 'The link must point to the content we wanted to reference.', homepage, link )
    }

    private static MediasurfaceTemplate mediasurface

    private IItem createdContentReference

    @BeforeClass
    public static void beforeAnyTestsAreRun( ) throws Exception
    {
        mediasurface = MediasurfaceForTesting.templateForDestination()
    }

    @After
    public void afterEachTestIsRun( ) throws Exception
    {
        if (createdContentReference?.key) {
            mediasurface.execute( new DeleteItemPermanentlyBruteForceVersion( createdContentReference.key ) )
        }
    }
}
