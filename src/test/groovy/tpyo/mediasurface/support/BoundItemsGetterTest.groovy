package tpyo.mediasurface.support

import com.mediasurface.general.LinkSortOrder
import org.junit.Test

import static org.junit.Assert.*

final class BoundItemsGetterTest
{
    @Test
    public void allDefaults( )
    {
        def getter = BoundItemsGetter.Builder.allChildrenWithDefaults()

        assertNotNull 'allChildrenWithDefaults() must never return null.', getter
        assertEquals( LinkSortOrder.LINKSORT_ORDERING, getter.sortField )
        assertFalse getter.sortDescending
        assertTrue getter.sortAscending
        assertNull getter.viewName
        assertNull getter.resultCounter
        assertNull getter.itemFilter
    }
}
