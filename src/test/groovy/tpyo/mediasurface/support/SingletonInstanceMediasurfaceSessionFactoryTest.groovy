package tpyo.mediasurface.support

import org.junit.Test

import static org.junit.Assert.assertSame
import static tpyo.mediasurface.MediasurfaceForTesting.destination
import org.junit.Ignore

@Ignore( 'Requires actual Mediasurface connection.' )
final class SingletonInstanceMediasurfaceSessionFactoryTest
{
    @Test
    public void sameInstanceIsReturnedForEveryInvocation( ) throws Exception
    {
        def config = destination()
        def factory = new SingletonInstanceMediasurfaceSessionFactory( config )

        def firstSession = factory.newSession()
        def secondSession = factory.newSession()

        assertSame( firstSession, secondSession )
    }
}
