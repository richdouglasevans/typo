package tpyo.mediasurface.support

import org.junit.Assert
import org.junit.Test

import static tpyo.mediasurface.MediasurfaceForTesting.destination
import org.junit.Ignore

@Ignore( 'Requires actual Mediasurface connection.' )
final class NewSessionEveryTimeMediasurfaceSessionFactoryTest
{
    @Test
    public void differentInstanceIsReturnedPerInvocation( ) throws Exception
    {
        def config = destination()
        def factory = new NewSessionEveryTimeMediasurfaceSessionFactory( config )

        def firstSession = factory.newSession()
        def secondSession = factory.newSession()

        Assert.assertNotSame( firstSession, secondSession )
    }
}
