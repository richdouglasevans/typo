package tpyo.mediasurface;

import com.google.common.base.Objects;
import com.mediasurface.client.IHost;
import com.mediasurface.client.ISite;
import com.mediasurface.client.Mediasurface;
import com.mediasurface.datatypes.SecurityContextHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.support.MediasurfaceUtils;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The immutable configuration state required to connect to an {@link Mediasurface Alterian serverLocation}.
 * <p>
 * Use the nested {@link Builder} class to create {@code MediasurfaceConfiguration} instances.
 */
public final class MediasurfaceConfiguration implements MediasurfaceSessionFactory
{
    public MediasurfaceSession newSession() throws Exception
    {
        Mediasurface ms = new Mediasurface();
        ms.init( serverLocation );

        SecurityContextHandle handle = ms.login( username, password );
        ms.setAdminMode( handle, adminMode );

        ISite theSite = ms.getSite( handle, siteGroup );

        IHost theHost = (host != null)
              ? MediasurfaceUtils.getNamedHost( theSite, host )
              : theSite.getDefaultHost();

        LOGGER.debug( "Creating new session for {}.", this );

        return new DefaultMediasurfaceSession( ms, handle, theSite, theHost, this );
    }

    /**
     * The default Mediasurface administrator username.
     * <p>
     * Hardcoding this value is questionable, but convenience trumps said concern.
     */
    public static final String DEFAULT_ADMIN_USERNAME = "msadmin";

    /**
     * The default Mediasurface administrator password.
     * <p>
     * Hardcoding this value is questionable, but convenience trumps said concern.
     */
    public static final String DEFAULT_ADMIN_PASSWORD = "mediasurface";

    private static final Logger LOGGER = LoggerFactory.getLogger( MediasurfaceConfiguration.class );

    private final String username;
    private final String password;
    private final String serverLocation;
    private final String siteGroup;
    private final String host;
    private final String label;

    private final boolean adminMode;

    private MediasurfaceConfiguration(
          String label, String serverLocation, String siteGroup, String host,
          String username, String password, boolean adminMode )
    {
        this.label = checkNotNull( label );
        this.serverLocation = checkNotNull( serverLocation );
        this.siteGroup = checkNotNull( siteGroup );
        this.host = host; // <- can be null, which means "use the default serverLocation"
        this.username = checkNotNull( username );
        this.password = checkNotNull( password );
        this.adminMode = adminMode;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getServerLocation()
    {
        return serverLocation;
    }

    public String getHost()
    {
        return host;
    }

    public String getSiteGroup()
    {
        return siteGroup;
    }

    public String getLabel()
    {
        return label;
    }

    public boolean isAdminMode()
    {
        return adminMode;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode( serverLocation, siteGroup, host, username, password, adminMode );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof MediasurfaceConfiguration )
        {
            MediasurfaceConfiguration other = (MediasurfaceConfiguration) obj;
            return equal( this.serverLocation, other.serverLocation )
                  && equal( this.siteGroup, other.siteGroup )
                  && equal( this.host, other.host )
                  && equal( this.username, other.username )
                  && equal( this.password, other.password )
                  && equal( this.adminMode, other.adminMode );
        }
        return false;
    }

    @Override
    public String toString()
    {
        return toStringHelper( this )
              .add( "label", label )
              .add( "serverLocation", serverLocation )
              .add( "siteGroup", siteGroup )
              .add( "host", host )
              .add( "adminMode", adminMode )
              .toString();
    }

    /** Build {@link MediasurfaceConfiguration} instances. */
    public static final class Builder
    {
        private String username;
        private String password;
        private String label;
        private String serverLocation;
        private String siteGroup;
        private String host;
        private boolean adminMode;

        /** Build a {@link MediasurfaceConfiguration} instance. */
        public MediasurfaceConfiguration build()
        {
            if ( label == null )
            {
                label = siteGroup;
            }
            if ( host != null && host.trim().length() == 0 )
            {
                host = null; // explicitly null if the empty or blank string
            }
            return new MediasurfaceConfiguration( label, serverLocation, siteGroup, host, username, password, adminMode );
        }

        /**
         * Configure this builder to use the default admin credentials.
         * <p>
         * Also switches on {@link #adminMode()}.
         *
         * @return this; never {@code null}.
         * @see #DEFAULT_ADMIN_USERNAME
         * @see #DEFAULT_ADMIN_PASSWORD
         */
        public Builder usingDefaultAdminCredentials()
        {
            return username( DEFAULT_ADMIN_USERNAME )
                  .password( DEFAULT_ADMIN_PASSWORD )
                  .adminMode();
        }

        /**
         * Base the configuration of this builder on the state of the {@code other}.
         *
         * @param other another {@link MediasurfaceConfiguration}; must not be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder basedOn( MediasurfaceConfiguration other )
        {
            checkNotNull( other );
            serverLocation( other.serverLocation ).siteGroup( other.siteGroup ).host( other.host )
                  .username( other.username ).password( other.password );
            this.adminMode = other.adminMode;
            return this;
        }

        /**
         * Set the server location.
         *
         * @param serverLocation the server location, forex {@code //10.123.4.123:2170/}; must not be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder serverLocation( String serverLocation )
        {
            this.serverLocation = checkNotNull( serverLocation );
            return this;
        }

        /**
         * Set a nice, descriptive label to aid to logging and debugging.
         *
         * @param label the label, forex {@code "PROD Editorial"}; must not be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder label( String label )
        {
            this.label = checkNotNull( label );
            return this;
        }

        /**
         * Set the host name.
         *
         * @param host the desired host, forex {@code com.foo.whatever}; can be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder host( String host )
        {
            this.host = host;
            return this;
        }

        /**
         * Set the site group.
         *
         * @param siteGroup the desired site group, forex {@code Sales}; must not be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder siteGroup( String siteGroup )
        {
            this.siteGroup = checkNotNull( siteGroup );
            return this;
        }

        /**
         * Set the username.
         *
         * @param username the username associated with the connection; must not be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder username( String username )
        {
            this.username = checkNotNull( username );
            return this;
        }

        /**
         * Set the password.
         *
         * @param password the password for the above {@code username}; must not be {@code null}.
         * @return this; never {@code null}.
         */
        public Builder password( String password )
        {
            this.password = checkNotNull( password );
            return this;
        }

        /**
         * Put any resulting {@link MediasurfaceSession} into {@link Mediasurface#setAdminMode admin mode}.
         *
         * @return this; never {@code null}.
         */
        public Builder adminMode()
        {
            this.adminMode = true;
            return this;
        }
    }
}
