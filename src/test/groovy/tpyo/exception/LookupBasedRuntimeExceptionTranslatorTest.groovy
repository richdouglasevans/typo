package tpyo.exception

import org.junit.Test
import tpyo.mediasurface.MediasurfaceException

import static org.junit.Assert.*

final class LookupBasedRuntimeExceptionTranslatorTest
{
    @Test(expected = NullPointerException.class)
    public void ctorRejectsNullArgument( ) throws Exception
    {
        new LookupBasedRuntimeExceptionTranslator( null )
    }

    @Test
    public void directMatch( ) throws Exception
    {
        def mapping = [
              (NullPointerException.class): MediasurfaceException.class
        ]

        def translator = new LookupBasedRuntimeExceptionTranslator( mapping )
        def ex = translator.translate( new NullPointerException() )

        assertNotNull( ex )
        assertTrue( ex instanceof MediasurfaceException )
    }

    @Test
    public void noMatchJustWrapsExceptionInPlainRuntimeException( ) throws Exception
    {
        def mapping = [
              (UnsupportedOperationException.class): MediasurfaceException.class
        ]

        def translator = new LookupBasedRuntimeExceptionTranslator( mapping )
        def npe = new NullPointerException()
        def ex = translator.translate( npe )

        assertNotNull( ex )
        assertTrue( ex instanceof RuntimeException )
        assertNotNull( ex.cause )
        assertSame( npe, ex.cause )
    }

    @Test
    public void matchViaSuperClass( ) throws Exception
    {
        def mapping = [
              (RuntimeException.class): MediasurfaceException.class
        ]

        def translator = new LookupBasedRuntimeExceptionTranslator( mapping )
        def ex = translator.translate( new NullPointerException() )

        assertNotNull( ex )
        assertTrue( ex instanceof MediasurfaceException )
    }

    private static final class ExceptionWithNoCtorWithSingleThrowableArgument extends RuntimeException
    {}

    @Test(expected = NoSuchMethodException)
    public void matchWithBadCtor( ) throws Exception
    {
        def mapping = [
              (NullPointerException.class): ExceptionWithNoCtorWithSingleThrowableArgument.class
        ]

        new LookupBasedRuntimeExceptionTranslator( mapping )
    }
}
