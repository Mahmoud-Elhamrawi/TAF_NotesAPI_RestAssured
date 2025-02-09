package Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JasonDataUtils {


    public static <T> T readJsonData(String pathFile , Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper() ;
      return   mapper.readValue(new File(pathFile),clazz) ;

    }






}
