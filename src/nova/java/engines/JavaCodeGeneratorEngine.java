package nova.java.engines;

import net.fathomsoft.nova.CodeGeneratorEngine;
import net.fathomsoft.nova.Nova;
import net.fathomsoft.nova.error.SyntaxMessage;
import net.fathomsoft.nova.tree.*;
import nova.java.nodewriters.FileDeclarationWriter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static net.fathomsoft.nova.Nova.*;
import static nova.java.nodewriters.Writer.getWriter;

public class JavaCodeGeneratorEngine extends CodeGeneratorEngine
{
	public JavaCodeGeneratorEngine(Nova controller)
	{
		super(controller);
		
		
	}
	
	/**
	 * Generate the Java from the data contained within the syntax tree.
	 */
	public void generateOutput()
	{
		tree.getRoot().forEachVisibleListChild(file -> {
			try
			{
				File outputDir = getOutputDirectory(file);
				
				new File(outputDir, file.getPackage().getLocation()).mkdirs();
				
				writeFile(file.getPackage().getLocation() + "/" + getWriter(file).writeName(), outputDir, formatText(getWriter(file).write().toString()));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});

		StringBuilder builder = new StringBuilder();
		builder.append("public class NovaUtilities\n{\n");
		
		int count = 20;
		
		for (int i = 0; i < count + 1; i++)
		{
			builder.append("@FunctionalInterface\n");
			builder.append("public static interface Function").append(count - i).append("<");

			for (int n = i; n < count + 1; n++)
			{
				if (n > i)
				{
					builder.append(", ");
				}
				
				builder.append("T").append(n - i);
			}
			
			builder.append(">\n{\n");
			builder.append("T").append(count - i).append(" call(");
			
			for (int n = i; n < count; n++)
			{
				if (n > i)
				{
					builder.append(", ");
				}
				
				builder.append("T").append(n - i).append(" t").append(n - i);
			}
			
			builder.append(");");
			builder.append("\n}\n");
		}
		
		try
		{
			writeFile("NovaUtilities.java", controller.targetEngineWorkingDir, builder.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void formatOutput()
	{
		
	}
	
	public void writeFiles()
	{
//		Arrays.stream(tree.getFiles()).forEach(file -> {
//			File outputDir = getOutputDirectory(file);
//			
//			new File(outputDir, file.getPackage().getLocation()).mkdirs();
//			
//			FileDeclarationWriter writer = getWriter(file);
//			
//			System.out.println(file.getFile().getAbsolutePath());
//			
//			//System.out.println(writer.write());
//		});
	}
	
	/**
	 * Insert the main method into the correct file. Also set up the
	 * initialization for the program within the main method.
	 */
	public void insertMainMethod()
	{
		MethodDeclaration mainMethod = tree.getMainMethod(mainClass);
		
		if (mainMethod == null)
		{
			if (!controller.isFlagEnabled(LIBRARY))
			{
				if (mainClass != null)
				{
					SyntaxMessage.error("No main method found in class '" + mainClass + "'", controller);
				}
				else
				{
					SyntaxMessage.error("No main method found in program", controller);
				}
				
				controller.completed(true);
			}
			
			return;
		}
		
		StringBuilder staticBlockCalls  = generateStaticBlockCalls();
		
		FileDeclaration fileDeclaration = mainMethod.getFileDeclaration();
		
		if (mainMethod != null)
		{
			
		}
	}
	
	private StringBuilder generateStaticBlockCalls()
	{
		StringBuilder builder = new StringBuilder();
		
		Program root = tree.getRoot();
		
		for (int i = 0; i < root.getNumVisibleChildren(); i++)
		{
			FileDeclaration  file  = root.getVisibleChild(i);
			
			for (ClassDeclaration clazz : file.getClassDeclarations())
			{
				clazz.getStaticBlockList().forEachVisibleChild(block -> {
					// do something with block
				});
			}
		}
		
		return builder;
	}
}