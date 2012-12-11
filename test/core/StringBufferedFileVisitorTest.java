package core;

import static org.junit.Assert.*;

import java.nio.file.*;

import org.junit.*;

public class StringBufferedFileVisitorTest
{
    private StringBufferedFileVisitor visitor;
    private String rootPath;
    
    @Before
    public void setUp() throws Exception
    {
        rootPath = System.getProperty( "user.dir" );
        visitor = new StringBufferedFileVisitor( Paths.get( rootPath ) );
    }

    @Test
    public void shouldPrefixLineWithPath() throws Exception
    {
        Path file = Paths.get( rootPath, "/test/basic/shared.conf" );
        FileVisitResult result = visitor.visitFile( file, null );
        assertEquals( FileVisitResult.CONTINUE, result );
        assertTrue( visitor.toString().contains( "test.basic.key" ) );
    }

    @Test
    public void shouldNotPrefixIgnoreLine() throws Exception
    {
        Path file = Paths.get( rootPath, "/test/with_includes/shared.conf" );
        FileVisitResult result = visitor.visitFile( file, null );
        assertEquals( FileVisitResult.CONTINUE, result );
        assertTrue( visitor.toString().contains( "test.with_includes.bar" ) );
        assertFalse( visitor.toString().contains( "test.with_includes.include" ) );
    }
}
