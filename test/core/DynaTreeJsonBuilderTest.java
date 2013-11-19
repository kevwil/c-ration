package core;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.*;

import play.libs.Json;

public class DynaTreeJsonBuilderTest
{
    private DynaTreeJsonBuilder builder;
    
    @Before
    public void setUp() throws Exception
    {
        builder = new DynaTreeJsonBuilder( Paths.get( System.getProperty( "user.dir" ), "/test" ) );
    }

    @Test
    public void test()
    {
        ObjectNode node = builder.walk();
        assertNotNull( node );
        System.out.println( Json.stringify( node ) );
    }

}
