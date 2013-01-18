package core;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyCustomClassLoader extends ClassLoader
{
    private String root;
    
    public MyCustomClassLoader( final String root )
    {
        super();
        this.root = root;
    }

    public MyCustomClassLoader( final String root, ClassLoader parent )
    {
        super( parent );
        this.root = root;
    }

    @Override
    protected URL findResource( String name )
    {
        File r = new File( root + File.pathSeparator + name );
        if( r.exists() && r.canRead() )
        {
            try{ return r.toURI().toURL(); } catch( MalformedURLException ignored ){}
        }
        return super.findResource( name );
    }

    @Override
    protected Enumeration<URL> findResources( final String resourceName )
            throws IOException
    {
        File rootDir = new File( root );
        final String match = resourceName.trim().toLowerCase();
        final List<File> results = new ArrayList<File>();
        rootDir.listFiles(
                new FileFilter() {
                    @Override public boolean accept( File pathname ){
                        if( pathname.isDirectory() )
                            pathname.listFiles( this );
                        if( pathname.getName().toLowerCase().endsWith( match ) )
                        {
                            results.add( pathname );
                            return true;
                        }
                        return false;
                    } } );
        if( results.size() > 0 )
        {
            return filesAsUrls( results );
        }
        else
        {
            return super.findResources( resourceName );
        }
    }

    private Enumeration<URL> filesAsUrls( final List<File> files )
            throws MalformedURLException
    {
        Vector<URL> resources = new Vector<URL>();
        for( File f : files )
        {
            resources.add( f.toURI().toURL() );
        }
        return resources.elements();
    }
    
//    class ResourceFileFilter implements FileFilter
//    {
//        private String match;
//        
//        public ResourceFileFilter( final String fileName )
//        {
//            match = fileName.toLowerCase();
//        }
//        
//        @Override
//        public boolean accept( File pathname )
//        {
//            if( pathname.isDirectory() )
//                pathname.listFiles( this );
//            String candidate = pathname.getName().toLowerCase();
//            boolean isMatch = candidate.endsWith( match );
//            return isMatch;
//        }
//    }
}
