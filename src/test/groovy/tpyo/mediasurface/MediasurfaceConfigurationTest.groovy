package tpyo.mediasurface

import org.junit.Test

import static org.junit.Assert.*

final class MediasurfaceConfigurationTest
{
    private static final String SERVER_LOCATION = '//01.23.45.678:2170'
    private static final String SITE_GROUP = 'Sales'
    private static final String HOST = 'sales.foo.org'
    private static final String USERNAME = 'banjob'
    private static final String PASSWORD = 'zoink'

    @Test
    public void toStringIsWellFormed( ) throws Exception
    {
        def config = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .host( HOST )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        def toString = config.toString()

        assertNotNull( toString )
        assertTrue( toString.contains( SERVER_LOCATION ) )
        assertTrue( toString.contains( SITE_GROUP ) )
        assertTrue( toString.contains( HOST ) )
        assertFalse( 'The username must not be part of the toString() output for security reasons.', toString.contains( USERNAME ) )
        assertFalse( 'The password must not be part of the toString() output for security reasons.', toString.contains( PASSWORD ) )
    }

    @Test
    public void builderSunnyDay( ) throws Exception
    {
        def config = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        assertNotNull( config )
        assertEquals( SERVER_LOCATION, config.serverLocation )
        assertEquals( SITE_GROUP, config.siteGroup )
        assertNull( config.host )
        assertEquals( USERNAME, config.username )
        assertEquals( PASSWORD, config.password )
        assertEquals( 'The label must default to the site group if not explicitly provided.', SITE_GROUP, config.label )
        assertFalse( 'Admin mode must not be in effect by default.', config.adminMode )
    }

    @Test
    public void builderWithExplicitLabel( ) throws Exception
    {
        def config = new MediasurfaceConfiguration.Builder()
              .label( 'PROD Editorial' )
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        println config

        assertNotNull( config )
        assertEquals( SERVER_LOCATION, config.serverLocation )
        assertEquals( SITE_GROUP, config.siteGroup )
        assertEquals( USERNAME, config.username )
        assertEquals( PASSWORD, config.password )
        assertEquals( 'PROD Editorial', config.label )
        assertFalse( 'Admin mode must not be in effect by default.', config.adminMode )
    }

    @Test
    public void builderSunnyDayInExplicitAdminMode( ) throws Exception
    {
        def config = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .adminMode()
              .build()

        assertNotNull( config )
        assertEquals( SERVER_LOCATION, config.serverLocation )
        assertEquals( SITE_GROUP, config.siteGroup )
        assertEquals( USERNAME, config.username )
        assertEquals( PASSWORD, config.password )
        assertTrue( 'When explicitly configured, admin mode must be switched on.', config.adminMode )
    }

    @Test
    public void builderSunnyDayUsingDefaultAdminCredentials( ) throws Exception
    {
        def config = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .usingDefaultAdminCredentials()
              .build()

        assertNotNull( config )
        assertEquals( SERVER_LOCATION, config.serverLocation )
        assertEquals( SITE_GROUP, config.siteGroup )
        assertEquals( MediasurfaceConfiguration.DEFAULT_ADMIN_USERNAME, config.username )
        assertEquals( MediasurfaceConfiguration.DEFAULT_ADMIN_PASSWORD, config.password )
        assertTrue( 'When using the default admin credentials method, admin mode must be switched on.', config.adminMode )
    }

    @Test
    public void equalsWithEqualInstances( ) throws Exception
    {
        def firstConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        def secondConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        assertEquals( firstConfig, secondConfig )
    }

    @Test
    public void equalsWithInstanceBuiltUsingBasedOn( ) throws Exception
    {
        def firstConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .adminMode()
              .build()

        def secondConfig = new MediasurfaceConfiguration.Builder()
              .basedOn( firstConfig )
              .build()

        assertEquals( firstConfig, secondConfig )
    }

    @Test
    public void equalsWithNotMediasurfaceConfigurationInstance( ) throws Exception
    {
        def firstConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        assertFalse( firstConfig == new Object() )
    }

    @Test
    public void equalsWithNotEqualInstances( ) throws Exception
    {
        def firstConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( 'fork' )
              .siteGroup( 'banjo' )
              .username( 'rope' )
              .password( 'conservatory' )
              .build()

        def secondConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        assertFalse( firstConfig == secondConfig )
    }

    @Test
    public void hashCodeWithEqualInstances( ) throws Exception
    {
        def firstConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        def secondConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .build()

        assertEquals( firstConfig.hashCode(), secondConfig.hashCode() )
    }

    @Test
    public void hashCodeWithInstanceBuiltUsingBasedOn( ) throws Exception
    {
        def firstConfig = new MediasurfaceConfiguration.Builder()
              .serverLocation( SERVER_LOCATION )
              .siteGroup( SITE_GROUP )
              .username( USERNAME )
              .password( PASSWORD )
              .adminMode()
              .build()

        def secondConfig = new MediasurfaceConfiguration.Builder()
              .basedOn( firstConfig )
              .build()

        assertEquals( firstConfig.hashCode(), secondConfig.hashCode() )
    }

    @Test(expected = NullPointerException)
    public void buildNoStateSupplied( ) throws Exception
    {
        new MediasurfaceConfiguration.Builder().build()
    }
}
