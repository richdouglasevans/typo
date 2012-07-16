package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import com.mediasurface.client.IView;
import com.mediasurface.datatypes.ViewKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.Editor;
import tpyo.mediasurface.MediasurfaceSession;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Bind an {@link IItem} to another {@code IItem}.
 * <p>
 * The {@code IItem} <strong>to</strong> which other items will be bound is supplied in the
 * {@link #BindToItem constructor}, so this editor can be invoked repeatedly to bind multiple items to that same item.
 *
 * @see IItem#bindToItem(com.mediasurface.datatypes.ItemKey, boolean, com.mediasurface.datatypes.ViewKey)
 * @see Builder
 */
public final class BindToItem implements Editor<IItem>
{
    /**
     * Bind the {@code item} to the item supplied in the {@link #BindToItem constructor}.
     *
     * @see IItem#bindToItem(com.mediasurface.datatypes.ItemKey, boolean, com.mediasurface.datatypes.ViewKey)
     */
    public void edit( MediasurfaceSession session, IItem item ) throws Exception
    {
        if ( LOGGER.isDebugEnabled() )
        {
            LOGGER.debug( "Binding '{}' to '{}' on site '{}'.",
                  new Object[]{item.getSimpleName(), bindItem.getPath(), session.getSite().getName()} );
        }
        item.bindToItem( bindItem.getKey(), isPrimaryAttachPoint, viewKey );
    }

    /**
     * A convenience method to create a {@link BindToItem} instance.
     *
     * @param item the {@link IItem} to which other items will be bound; must not be {@code null}.
     */
    public static BindToItem bindTo( IItem item )
    {
        return new Builder().bindingTo( item ).build();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( BindToItem.class );

    private final IItem bindItem;
    private final boolean isPrimaryAttachPoint;
    private final ViewKey viewKey;

    /**
     * Create an instance of the {@link BindToItem} class.
     *
     * @param bindItem the {@link IItem} to which other items will be bound; must not be {@code null}.
     * @see #bindTo(IItem)
     */
    private BindToItem( IItem bindItem, boolean isPrimaryAttachPoint, ViewKey viewKey )
    {
        this.bindItem = checkNotNull( bindItem );
        this.isPrimaryAttachPoint = isPrimaryAttachPoint;
        this.viewKey = viewKey;
    }

    @Override
    public String toString()
    {
        return toStringHelper( getClass() ).add( "bindItem", bindItem ).toString();
    }

    /** Create {@link BindToItem} instances. */
    public static final class Builder
    {
        private IItem bindItem;
        private boolean isPrimaryAttachPoint = true;
        private ViewKey viewKey;

        public BindToItem build()
        {
            return new BindToItem( bindItem, isPrimaryAttachPoint, viewKey );
        }

        public Builder bindingTo( IItem bindItem )
        {
            this.bindItem = bindItem;
            return this;
        }

        public Builder forView( ViewKey viewKey )
        {
            this.viewKey = viewKey;
            return this;
        }

        public Builder forView( IView view )
        {
            this.viewKey = checkNotNull( view ).getKey();
            return this;
        }

        public Builder primaryAttachPoint()
        {
            this.isPrimaryAttachPoint = true;
            return this;
        }

        public Builder secondaryAttachPoint()
        {
            this.isPrimaryAttachPoint = false;
            return this;
        }
    }
}
