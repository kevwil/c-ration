package core;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * Only works with key=value syntax, won't do concatenation or lists :(
 */
class MappingFileVisitor
extends SimpleFileVisitor<Path>
{
    private static final String SHARED = "shared";
    private Map<String, String> _map;
    private Path _root;
    
    public MappingFileVisitor( Map<String, String> map, final Path rootPath )
    {
        _map = map;
        _root = rootPath;
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
                // prefix the key and put key & value into map
                String[] kv = (prefix + line).split( "=" );
                _map.put( kv[0], kv[1] );
            }
        }

        return FileVisitResult.CONTINUE;
    }
    
    // convert map to string because com.typesafe.config.ConfigFactory#parseMap
    // will not resolve smart values at all
    public String asString()
    {
        StringBuffer sb = new StringBuffer();
        for( Entry<String,String> entry : _map.entrySet() )
        {
            sb.append( entry.getKey() ).append( "=" ).append( entry.getValue() ).append( "\n" );
        }
        return sb.toString();
    }
}
