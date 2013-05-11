package game;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

public class Game {
	private static boolean pause = false;
	
	public static void Pause()
	{
		pause = true;
	}
	
	public static void Resume()
	{
		pause = false;
	}
	
	public static void Save(String file)
	{
		//TODO Save the game
	}
	
	public static void Load(String file)
	{
		//TODO Load the game
	}
	
	public static boolean isPaused()
	{
		return pause;
	}
	
	public static void exit()
	{
		AL.destroy();
		glDeleteProgram(ResourceManager.shaderProgram);
		glDeleteShader(ResourceManager.vertexShader);
		glDeleteShader(ResourceManager.fragmentShader);
		Display.destroy();
		System.exit(0);
	}
}
