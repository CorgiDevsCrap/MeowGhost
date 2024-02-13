package dev.corgi.utils;

import static org.lwjgl.opengl.GL20.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import org.lwjgl.opengl.GL20;
import net.minecraft.util.ResourceLocation;

public class ShaderUtil {

	private String kawaseUpBloom = "#version 120\n" +
            "\n" +
            "uniform sampler2D inTexture, textureToCheck;\n" +
            "uniform vec2 halfpixel, offset, iResolution;\n" +
            "uniform int check;\n" +
            "\n" +
            "void main() {\n" +
            "  //  if(check && texture2D(textureToCheck, gl_TexCoord[0].st).a > 0.0) discard;\n" +
            "    vec2 uv = vec2(gl_FragCoord.xy / iResolution);\n" +
            "\n" +
            "    vec4 sum = texture2D(inTexture, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset);\n" +
            "    sum.rgb *= sum.a;\n" +
            "    vec4 smpl1 =  texture2D(inTexture, uv + vec2(-halfpixel.x, halfpixel.y) * offset);\n" +
            "    smpl1.rgb *= smpl1.a;\n" +
            "    sum += smpl1 * 2.0;\n" +
            "    vec4 smp2 = texture2D(inTexture, uv + vec2(0.0, halfpixel.y * 2.0) * offset);\n" +
            "    smp2.rgb *= smp2.a;\n" +
            "    sum += smp2;\n" +
            "    vec4 smp3 = texture2D(inTexture, uv + vec2(halfpixel.x, halfpixel.y) * offset);\n" +
            "    smp3.rgb *= smp3.a;\n" +
            "    sum += smp3 * 2.0;\n" +
            "    vec4 smp4 = texture2D(inTexture, uv + vec2(halfpixel.x * 2.0, 0.0) * offset);\n" +
            "    smp4.rgb *= smp4.a;\n" +
            "    sum += smp4;\n" +
            "    vec4 smp5 = texture2D(inTexture, uv + vec2(halfpixel.x, -halfpixel.y) * offset);\n" +
            "    smp5.rgb *= smp5.a;\n" +
            "    sum += smp5 * 2.0;\n" +
            "    vec4 smp6 = texture2D(inTexture, uv + vec2(0.0, -halfpixel.y * 2.0) * offset);\n" +
            "    smp6.rgb *= smp6.a;\n" +
            "    sum += smp6;\n" +
            "    vec4 smp7 = texture2D(inTexture, uv + vec2(-halfpixel.x, -halfpixel.y) * offset);\n" +
            "    smp7.rgb *= smp7.a;\n" +
            "    sum += smp7 * 2.0;\n" +
            "    vec4 result = sum / 12.0;\n" +
            "    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a),check));\n" +
            "}";
	
	public static void uniformFB(int programId, String name, FloatBuffer floatBuffer) {
		GL20.glUniform1(getLocation(programId, name), floatBuffer);
	}
	  
	public static void uniform1i(int programId, String name, int i) {
		GL20.glUniform1i(getLocation(programId, name), i);
	}
	  
	public static void uniform2i(int programId, String name, int i, int j) {
		GL20.glUniform2i(getLocation(programId, name), i, j);
	}
	  
	public static void uniform1f(int programId, String name, float f) {
		GL20.glUniform1f(getLocation(programId, name), f);
	}
	  
	public static void uniform2f(int programId, String name, float f, float g) {
		GL20.glUniform2f(getLocation(programId, name), f, g);
	}
	  
	public static void uniform3f(int programId, String name, float f, float g, float h) {
		GL20.glUniform3f(getLocation(programId, name), f, g, h);
	}
	  
	public static void uniform4f(int programId, String name, float f, float g, float h, float i) {
		GL20.glUniform4f(getLocation(programId, name), f, g, h, i);
	}
	  
	private static int getLocation(int programId, String name) {
		return GL20.glGetUniformLocation(programId, name);
	}
	
	public static int createShader(String fragementShader, String vertexShader) {
		int program = glCreateProgram();

		try {
			glAttachShader(program, create(
	                MinecraftInstance.mc.getResourceManager().getResource(new ResourceLocation("assets/meowghost/shader/" + vertexShader)).getInputStream(), GL_VERTEX_SHADER));
	        glAttachShader(program, create(
	        		MinecraftInstance.mc.getResourceManager().getResource(new ResourceLocation("assets/meowghost/shader/" +
	        				fragementShader)).getInputStream(), GL_FRAGMENT_SHADER));

	        glLinkProgram(program);

	        int linked = glGetProgrami(program, GL_LINK_STATUS);

	        if (linked == 0) {
	            System.err.println(glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));

	            throw new IllegalStateException("Shader failed to link");
	        }
		} catch (Exception e) {
			System.out.println("Failed to load shader");
		}

        return program;
	}
	
	public static int create(InputStream inputStream, int shaderType) throws IOException {
        int shader = glCreateShader(shaderType);

        glShaderSource(shader, readStreamToString(inputStream));

        glCompileShader(shader);

        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);

        if (compiled == 0) {
            System.err.println(glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH)));

            throw new IllegalStateException("Failed to compile shader");
        }

        return shader;
    }
	
	private static String readStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte[512];

        int read;

        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, read);
        }

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }
	
}
