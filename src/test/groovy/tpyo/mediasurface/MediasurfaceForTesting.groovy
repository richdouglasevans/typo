package tpyo.mediasurface

import tpyo.mediasurface.support.SingletonInstanceMediasurfaceSessionFactory

import static com.google.common.base.Preconditions.checkNotNull

public final class MediasurfaceForTesting
{
    public static MediasurfaceConfiguration destination( ) throws Exception
    {
        adminModeConfiguration(
              configuration.getProperty( 'destination.serverLocation' ),
              configuration.getProperty( 'destination.siteGroup' ),
              configuration.getProperty( 'destination.host' ),
              'Destination' )
    }

    public static MediasurfaceConfiguration source( ) throws Exception
    {
        adminModeConfiguration(
              configuration.getProperty( 'source.serverLocation' ),
              configuration.getProperty( 'source.siteGroup' ),
              configuration.getProperty( 'source.host' ),
              'Source' )
    }

    public static MediasurfaceSessionFactory sessionFactory( MediasurfaceConfiguration config ) throws Exception
    {
        new SingletonInstanceMediasurfaceSessionFactory( checkNotNull( config ) )
    }

    public static MediasurfaceTemplate template( MediasurfaceSessionFactory sessionFactory ) throws Exception
    {
        new MediasurfaceTemplate( checkNotNull( sessionFactory ) )
    }

    public static MediasurfaceTemplate templateForSource( ) throws Exception
    {
        template( sessionFactory( source() ) )
    }

    public static MediasurfaceTemplate templateForDestination( ) throws Exception
    {
        template( sessionFactory( destination() ) )
    }

    private static MediasurfaceConfiguration adminModeConfiguration(
          String serverLocation, String siteGroup, String host, String label )
    {
        new MediasurfaceConfiguration.Builder()
              .serverLocation( serverLocation )
              .siteGroup( siteGroup )
              .host( host )
              .label( label )
              .usingDefaultAdminCredentials()
              .build()
    }

    private static final Properties configuration = new Properties();

    static {
        configuration.load( MediasurfaceForTesting.class.getResourceAsStream( 'mediasurface.properties' ) )
    }
}
