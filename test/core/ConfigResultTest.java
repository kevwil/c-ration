package core;

import java.nio.file.Paths;

import org.junit.*;

import static org.junit.Assert.*;


public class ConfigResultTest
{
    private ConfigResult result;
    
    @Before
    public void setUp()
    {
        result = new ConfigResult( Paths.get( "/etc/hosts" ) );
    }
    
    @Test
    public void shouldSetFileExists()
    {
        assertFalse( result.notFound() );
    }

    @Test
    public void shouldMarkNotFoundOnBadFile()
    {
        assertTrue( new ConfigResult(Paths.get( "/foo/bar" ) ).notFound() );
    }
    
    @Test
    public void shouldMarkHasError()
    {
        result.setErrorMessage( "oops!" );
        assertTrue( result.hasError() );
    }
    
    @Test
    public void shouldMarkHasContentType()
    {
        result.setContentType( "text/html" );
        assertTrue( result.hasContentType() );
    }
}
