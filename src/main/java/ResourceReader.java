import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;

public class ResourceReader {
    public void fileReader(String filePath, Connection connection){
        ScriptRunner sr = new ScriptRunner(connection);
        InputStream inputStream = this.getClass().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        //Reader schema = new BufferedReader(new FileReader("src/main/resources/schema.sql"));
        sr.runScript(reader);
    }
}
