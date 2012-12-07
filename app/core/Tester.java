package core;

import java.io.*;
//import java.util.Map;
//import com.typesafe.config.*;

public class Tester
{
    public static void main( String[] args ) throws IOException
    {
        String rootDir = System.getProperty( "user.dir" )+"/configfiles";
//        SharedConfigTreeWalker walker = new SharedConfigTreeWalker( rootDir );
//        Config config = walker.walk().getConfig();
//        System.out.println( config.root().render(ConfigRenderOptions.concise().setFormatted( true )) );
//        System.out.println( config.getConfig( "people.dev" ) );
        SharedConfigManager.setRootDir( rootDir );
        System.out.println( SubstituteFileReader.resolveFile( "persona/pqa/env.properties" ) );
    }
}
