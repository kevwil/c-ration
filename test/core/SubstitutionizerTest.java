package core;

import static org.junit.Assert.*;

import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class SubstitutionizerTest
{
    @Test
    public void shouldPerformSubstitution() throws Exception
    {
        Config prelim = ConfigFactory.parseString( "database{host=\"127.0.0.1\"}" );
        Config config = prelim.resolve();
        String contents = "mongo=${database.host}";
        String expected = "mongo=127.0.0.1";
        String actual = Substitutionizer.substituteSharedValues( config, contents );
        assertEquals( expected, actual );
    }

}
