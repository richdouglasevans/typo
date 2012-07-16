package tpyo.util

import org.junit.Test

import static com.google.common.base.Predicates.alwaysFalse
import static com.google.common.base.Predicates.alwaysTrue
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull
import static tpyo.util.CollectionUtils.findFirst

final class CollectionUtilsTest
{
    @Test
    public void findFirstSunnyDay( )
    {
        def first = 'hi'
        assertEquals( first, findFirst( alwaysTrue(), first, 'ya' ) )
    }

    @Test
    public void findFirstWithEmptyList( )
    {
        assertNull( findFirst( alwaysTrue(), Collections.EMPTY_LIST ) )
    }

    @Test
    public void findFirstWithEmptyListPassedAsIterator( )
    {
        assertNull( findFirst( alwaysTrue(), Collections.EMPTY_LIST.iterator() ) )
    }

    @Test
    public void findFirstNothingSatisfiesPredicate( )
    {
        assertNull( findFirst( alwaysFalse(), 'hi', 'ya' ) )
    }
}