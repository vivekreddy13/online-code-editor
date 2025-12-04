package com.example.onlinecodeeditor.service;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class CodeExecutionService {

    public Map<String, Object> executeJavaCode(String sourceCode, String input) {
        Map<String, Object> result = new HashMap<>();

        try{
            // 1. Write code to temp file
            File tempDir = new File(System.getProperty("java.io.tmpdir"), "codeexec");
            tempDir.mkdirs();
            File sourceFile = new File(tempDir, "Main.java");
            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(sourceCode);
            }

            // 2. Compile
            Process compileProcess = new ProcessBuilder("javac", sourceFile.getAbsolutePath()).directory(tempDir).start();
            compileProcess.waitFor();

            // Capture compile errors
            String compileErrors = new String(compileProcess.getErrorStream().readAllBytes());
            if(!compileErrors.isEmpty()){
                result.put("stdout", "");
                result.put("stderr", compileErrors);
                result.put("exitCode", 1);
                return result;
            }

            // 3. Run Program
            Process runProcess = new ProcessBuilder("java", "-cp", tempDir.getAbsolutePath(), "Main").directory(tempDir).start();

            if(!input.isEmpty()){
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                    writer.flush();
                }
            }

            // Read Output
            String stdout = new String(runProcess.getInputStream().readAllBytes());
            String stderr = new String(runProcess.getErrorStream().readAllBytes());
            int exitCode = runProcess.waitFor();

            result.put("stdout", stdout);
            result.put("stderr", stderr);
            result.put("exitCode", exitCode);
        }
        catch (Exception e){
            result.put("stdout", "");
            result.put("stderr", e.getMessage());
            result.put("exitCode", -1);
        }
        return result;
    }
}
