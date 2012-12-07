package core;

import play.Play;
import java.nio.file.*;
import com.typesafe.config.Config;

public abstract class SharedConfigManager
{
    private static Config SHARED;
    private static String rootDir;
    
    public static Config getSharedConfig()
    {
        if( SHARED == null ) loadSharedConfig();
        return SHARED;
    }
    
    public static String getRootDir()
    {
        if( rootDir == null )
        {
            rootDir = Play.application().configuration().getString( "configfiles.rootdir" );
        }
        return rootDir;
    }
    
    // for testing from 'Tester' console app
    public static void setRootDir( final String dir )
    {
        rootDir = dir;
    }
    
    public static Path getRootPath()
    {
        return Paths.get( getRootDir() );
    }
    
    private static void loadSharedConfig()
    {
        SharedConfigTreeWalker walker = new SharedConfigTreeWalker( getRootDir() );
        SHARED = walker.walk().getConfig();
    }
    
}
