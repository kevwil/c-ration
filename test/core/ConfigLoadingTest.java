package core;

import java.io.*;
//import java.net.*;
//import java.util.*;

import com.typesafe.config.*;

public class ConfigLoadingTest
{
    public static void main( String[] args ) throws Exception
    {
//        withUrlClassLoader();
//        withClasspath();
//        withConfig();
        withCustomClassLoader();
    }
    
    private static void withCustomClassLoader() throws IOException
    {
        String path = new File("test/config_heirarchy").getAbsolutePath();
        ClassLoader loader = new MyCustomClassLoader( path );
        Config all = ConfigFactory.load( loader );
//        System.out.println( all.root().render( ConfigRenderOptions.concise().setFormatted( true ) ) );
        Config app1 = all.getConfig( "app1" );
        System.out.println( app1.root().render( ConfigRenderOptions.concise().setFormatted( true ) ) );
    }
    
//    private static void withClasspath() throws IOException
//    {
//        ClassLoader loader = ConfigLoadingTest.class.getClassLoader();
//        Enumeration<URL> resources = loader.getResources( "reference.conf" );
//        while( resources.hasMoreElements())
//        {
//            System.out.println( resources.nextElement() );
//        }
//        System.out.println( "classpath items ::>" );
//        String classpath = System.getProperty( "java.class.path" );
//        for( String element : classpath.split( ":" ))
//        {
//            System.out.println( element );
//        }
//    }

//    private static void withUrlClassLoader() throws MalformedURLException
//    {
//        String path = "test/config_heirarchy";
//        URL[] urls = {new File(path).toURI().toURL()};
//        URLClassLoader loader = null;
//        try
//        {
//            loader = new URLClassLoader( urls );//, Thread.currentThread().getContextClassLoader() );
//            Enumeration<URL> resources = loader.getResources( "reference.conf" );
//            while( resources.hasMoreElements())
//            {
//                System.out.println( resources.nextElement() );
//            }
//            URL[] classpath = loader.getURLs();
//            for( URL element : classpath )
//            {
//                System.out.println( element );
//            }
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace( System.err );
//        }
//        finally
//        {
//            if(loader != null)
//            {
//                try{ loader.close(); } catch(Exception ignored){}
//            }
//            System.out.println("\nfinished.");
//        }
//    }
    
//    private static void withConfig() throws MalformedURLException
//    {
//        String path = "test/config_heirarchy";
//        URL[] urls = {new File(path).toURI().toURL()};
//        URLClassLoader loader = null;
//        try
//        {
//            loader = new URLClassLoader( urls );
//            Config all = ConfigFactory.load( loader );
//            Config app1 = all.getConfig( "app1" );
//            System.out.println( app1.root().render( ConfigRenderOptions.concise().setFormatted( true ) ) );
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace( System.err );
//        }
//        finally
//        {
//            if(loader != null)
//            {
//                try{ loader.close(); } catch(Exception ignored){}
//            }
//            System.out.println("\nfinished.");
//        }
//    }
}
