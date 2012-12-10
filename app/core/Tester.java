package core;

import java.io.*;

import com.typesafe.config.Config;

public class Tester
{
    public static void main( String[] args ) throws IOException
    {
//        String rootDir = System.getProperty( "user.dir" )+"/configfiles";
        
//        SharedConfigTreeWalker walker = new SharedConfigTreeWalker( rootDir );
//        Config config = walker.walk().getConfig();
//        System.out.println( config.root().render(ConfigRenderOptions.concise().setFormatted( true )) );
//        System.out.println( config.getConfig( "people.dev" ) );
        
//        SharedConfigManager.setRootDir( rootDir );
//        System.out.println( SubstituteFileReader.resolveFile( "persona/pqa/env.properties" ) );
        
        
        String root = "/Users/willkd9/dev/git/c-ration-sample/config";
        SharedConfigManager.setRootDir( root );
        ConfigResult r = ConfigMaster.getFile( "/jsvc/dev/log4j.xml" );
        System.out.println(r.getContentType());
        SharedConfigTreeWalker walker = new SharedConfigTreeWalker( root );
        Config config = walker.walk().getConfig();
        System.out.println(config.getString( "jsvc.dev.logfile" ));
    }
}
