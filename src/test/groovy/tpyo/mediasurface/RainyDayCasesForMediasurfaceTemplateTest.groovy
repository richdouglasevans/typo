package tpyo.mediasurface

import com.mediasurface.client.ConnectionException
import com.mediasurface.general.AuthenticationException
import com.mediasurface.general.ResourceException
import org.junit.BeforeClass
import org.junit.Test
import tpyo.mediasurface.support.NewSessionEveryTimeMediasurfaceSessionFactory

import static org.junit.Assert.*
import static tpyo.mediasurface.MediasurfaceForTesting.destination
import static tpyo.mediasurface.MediasurfaceForTesting.sessionFactory
import org.junit.Ignore

/**
 * These tests test the "rainy day, bad stuff happens, exceptions are thrown" code paths for the
 * {@link MediasurfaceTemplate} class.
 *
 * They're bundled together here because they take a wee while to run and it's annoying to run
 * tests repeatedly that take a while to complete. The "sunny day, fast, good path" tests for the
 * {@code MediasurfaceTemplate} class are bundled in the {@link MediasurfaceTemplateTest} fixture.
 */
@Ignore( 'Requires actual Mediasurface connection.' )
final class RainyDayCasesForMediasurfaceTemplateTest
{
    private static MediasurfaceConfiguration cfg
    private static MediasurfaceOperations template

    @BeforeClass
    public static void beforeAnyTestsAreRun( ) throws Exception
    {
        cfg = destination()
        def sessionFactory = sessionFactory( cfg )
        template = MediasurfaceForTesting.template( sessionFactory )
    }

    @Test
    public void badCredentials( ) throws Exception
    {
        def badCredentials = new MediasurfaceConfiguration.Builder()
              .basedOn( cfg )
              .username( 'rubbish' )
              .password( 'rubbish' )
              .build()

        def badMediasurfaceSessionFactory = new NewSessionEveryTimeMediasurfaceSessionFactory( badCredentials )
        def badTemplate = new MediasurfaceTemplate( badMediasurfaceSessionFactory )

        try {
            badTemplate.execute( DoNothingMediasurfaceCallback.doNothing() )
            fail( 'Expected an AuthenticationException because bad credentials were supplied.' )
        }
        catch (Throwable ex) {
            assertNotNull( ex.cause )
            assertEquals( AuthenticationException.class, ex.cause.getClass() )
        }
    }

    @Test
    public void badHost( ) throws Exception
    {
        def badHost = new MediasurfaceConfiguration.Builder()
              .basedOn( cfg )
              .serverLocation( 'rubbish' )
              .build()

        def badMediasurfaceSessionFactory = new NewSessionEveryTimeMediasurfaceSessionFactory( badHost )
        def badTemplate = new MediasurfaceTemplate( badMediasurfaceSessionFactory )

        try {
            badTemplate.execute( DoNothingMediasurfaceCallback.doNothing() )
            fail( 'Expected a ConnectionException because bad serverLocation was supplied.' )
        }
        catch (Throwable ex) {
            assertNotNull( ex.cause )
            assertEquals( ConnectionException.class, ex.cause.getClass() )
        }
    }

    @Test
    public void badSite( ) throws Exception
    {
        def badSite = new MediasurfaceConfiguration.Builder()
              .basedOn( cfg )
              .host( 'rubbish' )
              .build()

        def badMediasurfaceSessionFactory = new NewSessionEveryTimeMediasurfaceSessionFactory( badSite )
        def badTemplate = new MediasurfaceTemplate( badMediasurfaceSessionFactory )

        try {
            badTemplate.execute( DoNothingMediasurfaceCallback.doNothing() )
            fail( 'Expected a ResourceException because bad serverLocation was supplied.' )
        }
        catch (Throwable ex) {
            assertNotNull( ex.cause )
            assertEquals( ResourceException.class, ex.cause.getClass() )
        }
    }

    @Test
    public void getItemAtPathWithPathThatDoesNotExist( ) throws Exception
    {
        try {
            template.getItemAtPath( 'nonexistentpathrubbish' )
            fail( 'Expected a ResourceException because a non-existent path was supplied.' )
        }
        catch (Throwable ex) {
            assertNotNull( ex.cause )
            assertEquals( ResourceException.class, ex.cause.getClass() )
        }
    }
}
