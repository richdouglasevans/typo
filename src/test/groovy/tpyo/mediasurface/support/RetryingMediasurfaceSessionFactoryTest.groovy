package tpyo.mediasurface.support

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tpyo.mediasurface.MediasurfaceException
import tpyo.mediasurface.MediasurfaceSessionFactory

import static org.mockito.Mockito.*
import static tpyo.mediasurface.support.RetryingMediasurfaceSessionFactory.Builder.DEFAULT_NUMBER_OF_RETRY_ATTEMPTS

@SuppressWarnings("GroovyUnusedCatchParameter")
final class RetryingMediasurfaceSessionFactoryTest
{
    @Test
    public void sunnyDayUsingDefaultNumberOfRetries( )
    {
        when( delegate.newSession() ).thenThrow( new MediasurfaceException() )

        def factory = new RetryingMediasurfaceSessionFactory.Builder( delegate ).build()
        try {
            factory.newSession()
            Assert.fail 'An exception must have been thrown...'
        }
        catch (MediasurfaceException expected) {}

        verify( delegate, times( DEFAULT_NUMBER_OF_RETRY_ATTEMPTS ) ).newSession()
    }

    @Test
    public void sunnyDayUsingExplicitlyConfiguredNumberOfRetries( )
    {
        when( delegate.newSession() ).thenThrow( new MediasurfaceException() )

        def factory = new RetryingMediasurfaceSessionFactory.Builder( delegate )
              .retryAttempts( 2 )
              .delayBetweenRetriesInMilliseconds( 500 )
              .build()
        try {
            factory.newSession()
            Assert.fail 'An exception must have been thrown...'
        }
        catch (MediasurfaceException expected) {}

        verify( delegate, times( 2 ) ).newSession()
    }

    @Test(expected = IllegalArgumentException)
    public void chokesOnBeingConfiguredWithLessThanTwoRetryAttempts( )
    {
        new RetryingMediasurfaceSessionFactory.Builder( delegate )
              .retryAttempts( 1 )
              .build()
    }

    @Test(expected = IllegalArgumentException)
    public void chokesOnBeingConfiguredWithDelayThatIsNotPositive( )
    {
        new RetryingMediasurfaceSessionFactory.Builder( delegate )
              .delayBetweenRetriesInSeconds( 0 )
              .build()
    }

    private MediasurfaceSessionFactory delegate

    @Before
    public void beforeEachTestIsRun( )
    {
        delegate = mock( MediasurfaceSessionFactory )
    }
}
