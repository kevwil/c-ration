package core;

import java.nio.file.*;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class SharedConfigTreeWalker
{
    private final Path rootPath;
    private Config rootConfig;
    
    public SharedConfigTreeWalker( final String root )
    {
        rootPath = Paths.get( root );
        rootConfig = ConfigFactory.empty("shared config file tree rooted in "+rootPath.toAbsolutePath());
    }

    public SharedConfigTreeWalker walk()
    {
        try
        {
            StringBufferedFileVisitor visitor = new StringBufferedFileVisitor( rootPath );
            Files.walkFileTree( rootPath, visitor );
            Config combined = ConfigFactory.parseString( visitor.toString() ).resolve();
            rootConfig = combined.withFallback( rootConfig );
            
        }
        catch( Exception e )
        {
            // TODO: handle errors better
            e.printStackTrace();
        }
        return this;
    }
    
    public Config getConfig()
    {
        return rootConfig;
    }
}
