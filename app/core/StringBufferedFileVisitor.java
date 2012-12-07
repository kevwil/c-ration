package core;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

class StringBufferedFileVisitor
extends SimpleFileVisitor<Path>
{
    private static final String SHARED = "shared";
    private StringBuffer _buffer;
    private Path _root;
    
    public StringBufferedFileVisitor( final Path rootPath )
    {
        _root = rootPath;
        _buffer = new StringBuffer();
    }

    @Override
    public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
            throws IOException
    {
        if( file.getFileName().toString().startsWith( SHARED ) )
        {
            // read file contents
            List<String> contents = PathUtil.readFileAsList( file );
            // build config-path prefix as relative diff path between root and file's parent
            String prefix = PathUtil.tokenizeRelativePath( _root, file.getParent() );
            for( String line : contents )
            {
                // prefix the line so that it reflects directory heirarchy
                _buffer.append( prefix ).append( line ).append( "\n" );
            }
        }

        return FileVisitResult.CONTINUE;
    }
    
    @Override
    public String toString()
    {
        return _buffer.toString();
    }
}
