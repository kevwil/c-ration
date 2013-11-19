package core;

import java.io.File;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

public class DynaTreeJsonBuilder
{
    private final Path rootPath;
    
    public DynaTreeJsonBuilder( final Path root )
    {
        rootPath = root;
    }
    
    public static ObjectNode walkAsJson( final Path rootPath )
    {
        return new DynaTreeJsonBuilder( rootPath ).walk();
    }
    
    public ObjectNode walk()
    {
        ObjectNode root = Json.newObject();
        ArrayNode childNodes = root.arrayNode();
        root.put( "children", childNodes );
        root.put( "title", "|-root-|" );
        root.put( "expand", true );
        root.put( "noLink", true );
        stepInto( root, rootPath.toFile() );
        return root;
    }

    private void stepInto( ObjectNode parent, final File dir )
    {
        File[] children = dir.listFiles();
        if( children != null )
        {
            for( int i = 0; i < children.length; i++ )
            {
                File f = children[i];
                if( f.isDirectory() )
                {
                    ObjectNode dirNode = parent.objectNode();
                    ArrayNode childNodes = dirNode.arrayNode();
                    dirNode.put( "children", childNodes );
                    dirNode.put( "title", f.getName() );
                    dirNode.put( "expand", true );
                    dirNode.put( "isFolder", true );
                    dirNode.put( "noLink", true );
                    ( (ArrayNode) parent.get( "children" ) ).add( dirNode );
                    stepInto( dirNode, f );
                }
                else
                {
                    if( ! f.getName().contains( "shared.conf" ) )
                    {
                        ObjectNode fileNode = parent.objectNode();
                        fileNode.put( "title", f.getName() );
                        fileNode.put( "href", buildHref( f ) );
                        ( (ArrayNode) parent.get( "children" ) ).add( fileNode );
                    }
                }
            }
        }
    }

    public String buildHref( final File file )
    {
        Path path = rootPath.relativize( file.toPath().toAbsolutePath() );
        return "/config/" + path.toString();
    }

}
