package tpyo.mediasurface.support;

import com.mediasurface.client.IItem;
import com.mediasurface.client.IType;
import com.mediasurface.datatypes.TypeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tpyo.mediasurface.Editor;
import tpyo.mediasurface.MediasurfaceCallback;
import tpyo.mediasurface.MediasurfaceSession;

/** Create a content reference to an existing {@link IItem}. */
public final class CreateContentReference implements MediasurfaceCallback<IItem>
{
    /**
     * The link name required to add a content reference.
     *
     * @see IItem#addContainedItem(String, IItem)
     */
    public static final String LINK_NAME_CONTENT_REFERENCE = "ms:contentRef";

    /**
     * {@inheritDoc}
     * <p/>
     * The created content reference item will be created at the root of the content store; if you want to bind the
     * content to another location, you'll have to do {@link BindToItem any such binding} yourself in a subsequent step.
     *
     * @return the created content reference {@link IItem}, pointing to the target {@code IItem}; never {@code null}.
     */
    public IItem execute( MediasurfaceSession session ) throws Exception
    {
        IItem emptyContentReference = createItem.execute( session );
        IItem contentReferenceWithLink = session.edit( emptyContentReference, new Editor<IItem>()
        {
            public void edit( MediasurfaceSession session, IItem item ) throws Exception
            {
                item.addContainedItem( LINK_NAME_CONTENT_REFERENCE, targetItem );
            }
        } );

        LOGGER.debug( "Created content reference at '{}' pointing to '{}'.",
              contentReferenceWithLink.getPath(), targetItem.getPath() );

        return contentReferenceWithLink;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger( CreateContentReference.class );

    private final IItem targetItem;
    private final CreateItem createItem;

    /**
     * Create an instance of the {@link CreateContentReference} class.
     *
     * @param key        the {@link IType#getKey() key} of the host's content reference {@link IType}.
     * @param fullName   the full name of the content reference item to be created.
     * @param targetItem the {@link IItem} to be referenced.
     * @throws NullPointerException if any of the arguments is {@code null}.
     */
    public CreateContentReference( TypeKey key, String fullName, IItem targetItem )
    {
        this.targetItem = targetItem;
        this.createItem = new CreateItem( key, fullName );
    }
}
