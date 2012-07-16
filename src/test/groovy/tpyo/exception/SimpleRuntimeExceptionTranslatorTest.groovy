package tpyo.exception

import org.junit.Test

import static org.junit.Assert.*

final class SimpleRuntimeExceptionTranslatorTest
{
    private final RuntimeExceptionTranslator translator = new SimpleRuntimeExceptionTranslator()

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
    public void exceptionThatIsNotRuntimeIsTranslatedIntoRuntime( ) throws Exception
    {
        def notRte = new Exception()
        def translatedException = translator.translate( notRte )

        assertNotNull translatedException
        assertTrue( translatedException instanceof RuntimeException )
        assertSame notRte, translatedException.cause
    }
}
