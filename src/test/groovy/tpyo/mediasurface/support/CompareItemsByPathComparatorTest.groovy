package tpyo.mediasurface.support

import com.mediasurface.client.IItem
import com.mediasurface.general.ResourceException
import org.junit.Before
import org.junit.Test
import tpyo.mediasurface.MediasurfaceException

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.*

final class CompareItemsByPathComparatorTest
{
    @Test
    public void samePaths( ) throws Exception
    {
        paths( '/', '/' )
        assertEquals 0, comparator.compare( first, second )

        verifyThatGetPathIsInvokedOnBothItemsOnce()
    }

    @Test
    public void pathsDifferingOnlyByCase( ) throws Exception
    {
        paths( '/foo', '/FOO' )
        assertEquals 0, comparator.compare( first, second )

        verifyThatGetPathIsInvokedOnBothItemsOnce()
    }

    @Test
    public void differentPathFirstComesBeforeSecond( ) throws Exception
    {
        paths( '/bar', '/bar/foo' )
        assertTrue comparator.compare( first, second ) < 0

        verifyThatGetPathIsInvokedOnBothItemsOnce()
    }

    @Test
    public void differentPathFirstComesAfterSecond( ) throws Exception
    {
        paths( '/foo', '/bar' )
        assertTrue comparator.compare( first, second ) > 0

        verifyThatGetPathIsInvokedOnBothItemsOnce()
    }

    @Test(expected = MediasurfaceException)
    public void getPathThrowsException( ) throws Exception
    {
        when( first.path ).thenThrow( new ResourceException() )
        when( second.path ).thenReturn( 'whatever' )

        comparator.compare( first, second )

        verify( first ).path
        verify( second, never() ).path
    }

    private IItem first
    private IItem second

    private final CompareItemsByPathComparator comparator = new CompareItemsByPathComparator()

    private void paths( firstPath, secondPath )
    {
        when( first.path ).thenReturn( firstPath )
        when( second.path ).thenReturn( secondPath )
    }

    @Before
    public void beforeEachTestIsRun( )
    {
        first = mock( IItem )
        second = mock( IItem )
    }

    private void verifyThatGetPathIsInvokedOnBothItemsOnce( )
    {
        verify( first ).path
        verify( second ).path
    }
}
