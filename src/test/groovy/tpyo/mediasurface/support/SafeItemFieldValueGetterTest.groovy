package tpyo.mediasurface.support

import com.mediasurface.client.IItem
import com.mediasurface.general.SelfLoggingException
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull
import static org.mockito.Mockito.*

final class SafeItemFieldValueGetterTest
{
    @Test
    public void getStringFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'title', true ) ).thenReturn( 'Sitemap' )
        SafeItemFieldValueGetter sitemapFieldValues = new SafeItemFieldValueGetter( item )

        assertEquals 'Sitemap', sitemapFieldValues.getStringFieldValue( 'title' )

        verify( item ).getStringFieldValue( 'title', true )
    }

    @Test
    public void getStringFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'foshiz', true ) ).thenThrow( SelfLoggingException )
        SafeItemFieldValueGetter sitemapFieldValues = new SafeItemFieldValueGetter( item )

        assertNull sitemapFieldValues.getStringFieldValue( 'foshiz' )

        verify( item ).getStringFieldValue( 'foshiz', true )
    }

    @Test
    public void getStringFieldValue_WithFieldThatDoesNotExistWithDefault( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'foshiz', true ) ).thenThrow( SelfLoggingException )
        SafeItemFieldValueGetter sitemapFieldValues = new SafeItemFieldValueGetter( item )

        def defaultValue = 'Hurrah'
        assertEquals defaultValue, sitemapFieldValues.getStringFieldValue( 'foshiz', defaultValue )

        verify( item ).getStringFieldValue( 'foshiz', true )
    }

    @Test
    public void getDateFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        def date = new Date()
        when( item.getDateFieldValue( 'some_date' ) ).thenReturn( date )
        SafeItemFieldValueGetter sitemapFieldValues = new SafeItemFieldValueGetter( item )

        assertEquals date, sitemapFieldValues.getDateFieldValue( 'some_date' )

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void getDateFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getDateFieldValue( 'some_date' ) ).thenThrow( SelfLoggingException )
        SafeItemFieldValueGetter sitemapFieldValues = new SafeItemFieldValueGetter( item )

        assertNull sitemapFieldValues.getDateFieldValue( 'some_date' )

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void getDateFieldValue_WithFieldThatDoesNotExistWithDefault( )
    {
        IItem item = mock( IItem )
        when( item.getDateFieldValue( 'some_date' ) ).thenThrow( SelfLoggingException )
        SafeItemFieldValueGetter sitemapFieldValues = new SafeItemFieldValueGetter( item )

        def defaultValue = new Date()
        assertEquals defaultValue, sitemapFieldValues.getDateFieldValue( 'some_date', defaultValue )

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void safeStringFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'title', true ) ).thenReturn( 'Sitemap' )

        assertEquals 'Sitemap', SafeItemFieldValueGetter.safeStringFieldValue( item, 'title' )

        verify( item ).getStringFieldValue( 'title', true )
    }

    @Test
    public void safeStringFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getStringFieldValue( 'foshiz', true ) ).thenThrow( SelfLoggingException )

        assertNull SafeItemFieldValueGetter.safeStringFieldValue( item, 'foshiz' )

        verify( item ).getStringFieldValue( 'foshiz', true )
    }

    @Test
    public void safeDateFieldValue_WithFieldThatExists( )
    {
        IItem item = mock( IItem )
        def date = new Date()
        when( item.getDateFieldValue( 'some_date' ) ).thenReturn( date )

        assertEquals date, SafeItemFieldValueGetter.safeDateFieldValue( item, 'some_date' )

        verify( item ).getDateFieldValue( 'some_date' )
    }

    @Test
    public void safeDateFieldValue_WithFieldThatDoesNotExist( )
    {
        IItem item = mock( IItem )
        when( item.getDateFieldValue( 'some_date' ) ).thenThrow( SelfLoggingException )

        assertNull SafeItemFieldValueGetter.safeDateFieldValue( item, 'some_date' )

        verify( item ).getDateFieldValue( 'some_date' )
    }
}
