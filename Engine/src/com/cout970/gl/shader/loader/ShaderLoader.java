package com.cout970.gl.shader.loader;

import com.cout970.gl.resource.IResource;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by cout970 on 05/05/2016.
 */
public class ShaderLoader {

    public static ShaderCode loadShader(IResource res) throws IOException {
        Scanner scanner = new Scanner(res.getInputStream());
        ShaderCode code = new ShaderCode(res.getName());
//        DEBUG
//        int i = 0;
//        Log.debug(res.getName());
        while(scanner.hasNext()){
            String line = scanner.nextLine();
//            Log.debug(i+" : "+line);
//            i++;
            if (line.startsWith("#require ") && line.replace("#require ", "").length() > 0){
                code.addRequirement(line.replace("#require ", ""));
            }else{
                code.appendLine(line+"\n");
            }
        }
        return code;
    }
}
