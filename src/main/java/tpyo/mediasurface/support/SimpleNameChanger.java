package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.Editor;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Change an {@link IItem IItem's} {@link com.mediasurface.client.IItem#getSimpleName() simple name}.
 * <p>
 * <strong>Note: </strong> if another {@code IItem} with the target simple name already exists at the same bind point,
 * then the change will fail.
 *
 * @see IItem#setSimpleName(String)
 */
public final class SimpleNameChanger implements Editor<IItem>
{
    public void edit( MediasurfaceSession session, IItem item ) throws Exception
    {
        if ( LOGGER.isDebugEnabled() )
        {
            LOGGER.debug( "Changing simpleName of item at '{}' from '{}' to '{}'.",
                  new Object[]{item.getPath(), item.getSimpleName(), simpleName} );
        }
        item.setSimpleName( simpleName );
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( SimpleNameChanger.class );

    private final String simpleName;

    /**
     * Create an instance of the {@link SimpleNameChanger} class.
     *
     * @param simpleName the target simple name; must not be {@code null}.
     */
    public SimpleNameChanger( String simpleName )
    {
        checkNotNull( simpleName );
        checkArgument( !simpleName.trim().isEmpty(), "The simpleName must not be empty." );

        this.simpleName = simpleName;
    }
}
