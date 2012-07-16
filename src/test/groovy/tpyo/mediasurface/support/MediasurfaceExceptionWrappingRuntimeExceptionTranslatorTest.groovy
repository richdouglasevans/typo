package tpyo.mediasurface.support

import org.junit.Test
import tpyo.exception.RuntimeExceptionTranslator
import tpyo.mediasurface.MediasurfaceException

import static org.junit.Assert.*

final class MediasurfaceExceptionWrappingRuntimeExceptionTranslatorTest
{
    private final RuntimeExceptionTranslator translator = new MediasurfaceExceptionWrappingRuntimeExceptionTranslator()

    @Test
    public void runtimeExceptionIsReturnedAsIs( ) throws Exception
    {
        def rte = new RuntimeException()
        def translatedException = translator.translate( rte )

        assertSame rte, translatedException
    }

    @Test
    public void nullExceptionJustReturnsNull( ) throws Exception
    {
        def translatedException = translator.translate( null )

        assertNull translatedException
    }

    @Test
    public void exceptionThatIsNotRuntimeAndIsNotMediasurfaceCheckedIsTranslatedIntoRuntime( ) throws Exception
    {
        def notRte = new Exception()
        def translatedException = translator.translate( notRte )

        assertNotNull translatedException
        assertTrue( translatedException instanceof RuntimeException )
        assertSame notRte, translatedException.cause
    }

    @Test
    public void exceptionThatIsNotRuntimeAndIsMediasurfaceCheckedIsTranslatedIntoRuntimeMediasurfaceException( ) throws Exception
    {
        def checkedMediasurfaceException = new com.mediasurface.general.MediasurfaceException()
        def translatedException = translator.translate( checkedMediasurfaceException )

        assertNotNull translatedException
        assertTrue( translatedException instanceof MediasurfaceException )
        assertSame checkedMediasurfaceException, translatedException.cause
    }
}
