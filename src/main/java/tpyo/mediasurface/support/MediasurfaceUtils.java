package tpyo.mediasurface.support;

import com.mediasurface.client.IHost;
import com.mediasurface.client.ISite;
import com.mediasurface.general.AuthorizationException;
import com.mediasurface.general.ResourceException;

import static com.google.common.base.Preconditions.checkNotNull;

/** Mediasurface-related utility methods. */
public final class MediasurfaceUtils
{
    /**
     * Get the named serverLocation within the supplied {@code siteGroup}.
     *
     * @param siteGroup the target host group; must not be {@code null}.
     * @param hostName  the desired serverLocation name; must not be {@code null}.
     * @return the named {@link IHost IHost}; never {@code null}.
     * @throws NoSuchHostException    if the named serverLocation cannot be found in the supplied {@code siteGroup}.
     * @throws AuthorizationException if another Mediasurface API-related error occurs.
     * @throws ResourceException      if another Mediasurface API-related error occurs.
     */
    public static IHost getNamedHost( ISite siteGroup, String hostName )
          throws NoSuchHostException, AuthorizationException, ResourceException
    {
        checkNotNull( siteGroup );
        checkNotNull( hostName );

        IHost namedHost = null;
        for ( IHost host : siteGroup.getHosts() )
        {
            if ( hostName.equals( host.getName() ) )
            {
                namedHost = host;
                break;
            }
        }
        if ( namedHost == null )
        {
            throw new NoSuchHostException(
                  String.format( "Cannot find the '%s' serverLocation in the '%s' host group.",
                        siteGroup.getName(), hostName ) );
        }
        return namedHost;
    }
}
