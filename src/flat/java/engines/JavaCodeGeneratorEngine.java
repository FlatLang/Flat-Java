package flat.java.engines;

import flat.CodeGeneratorEngine;
import flat.Flat;
import flat.error.SyntaxMessage;
import flat.tree.*;
import flat.java.nodewriters.FileDeclarationWriter;
import flat.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static flat.Flat.*;
import static flat.java.nodewriters.Writer.getWriter;

public class JavaCodeGeneratorEngine extends CodeGeneratorEngine
{
	private static String POM_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
		"         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
		"         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
		"    <modelVersion>4.0.0</modelVersion>\n" +
		"\n" +
		"    <properties>\n" +
		"        <maven.compiler.source>1.8</maven.compiler.source>\n" +
		"        <maven.compiler.target>1.8</maven.compiler.target>\n" +
		"        <src.dir>src</src.dir>\n" +
		"    </properties>\n" +
		"\n" +
		"    <groupId>flat</groupId>\n" +
		"    <artifactId>flat-test</artifactId>\n" +
		"    <version>1.0-SNAPSHOT</version>\n" +
		"\n" +
		"    <build>\n" +
		"        <sourceDirectory>${src.dir}</sourceDirectory>\n" +
		"        <plugins>\n" +
		"            <plugin>\n" +
		"                <artifactId>maven-assembly-plugin</artifactId>\n" +
		"                <configuration>\n" +
		"                    <archive>\n" +
		"                        <manifest>\n" +
		"                        </manifest>\n" +
		"                    </archive>\n" +
		"                    <descriptorRefs>\n" +
		"                        <descriptorRef>jar-with-dependencies</descriptorRef>\n" +
		"                    </descriptorRefs>\n" +
		"                </configuration>\n" +
		"                <executions>\n" +
		"                    <execution>\n" +
		"                        <id>make-assembly</id> <!-- this is used for inheritance merges -->\n" +
		"                        <phase>package</phase> <!-- bind to the packaging phase -->\n" +
		"                        <goals>\n" +
		"                            <goal>single</goal>\n" +
		"                        </goals>\n" +
		"                        <configuration>\n" +
		"                            <finalName>flat-test</finalName>\n" +
		"                            <appendAssemblyId>false</appendAssemblyId>\n" +
		"                        </configuration>\n" +
		"                    </execution>\n" +
		"                </executions>\n" +
		"            </plugin>\n" +
		"        </plugins>\n" +
		"    </build>\n" +
		"</project>";

	public JavaCodeGeneratorEngine(Flat controller)
	{
		super(controller);
		
		
	}
	
	/**
	 * Generate the Java from the data contained within the syntax tree.
	 */
	public void generateOutput()
	{
		FileUtils.deleteDirectory(controller.outputDirectory);

		try {
			controller.log("Writing to directory " + controller.outputDirectory.getCanonicalPath(), true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		new File(controller.outputDirectory, "src/flat").mkdirs();

		writePom();
		writeFlatUtilities();

		tree.getRoot().forEachVisibleListChild(file -> {
			try
			{
				File outputDir = new File(getOutputDirectory(file), "src");

				File packageFile = new File(outputDir, file.getPackage().getLocation());
				packageFile.mkdirs();

				writeFile(file.getPackage().getLocation() + "/" + getWriter(file).writeName(), outputDir, formatText(getWriter(file).write().toString()));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}

	private void writePom() {
		try {
			writeFile("pom.xml", controller.outputDirectory, POM_XML);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeFlatUtilities() {
		StringBuilder builder = new StringBuilder();
		builder.append("package flat;\n\n");
		builder.append("public class FlatUtilities {\n");

		int count = 20;

		for (int i = 0; i < count + 1; i++)
		{
			builder.append("  @FunctionalInterface\n");
			builder.append("  public static interface Function").append(count - i).append("<");

			for (int n = i; n < count + 1; n++)
			{
				if (n > i)
				{
					builder.append(", ");
				}

				builder.append("T").append(n - i);
			}

			builder.append("> {\n");
			builder.append("    T").append(count - i).append(" call(");

			for (int n = i; n < count; n++) {
				if (n > i) {
					builder.append(", ");
				}

				builder.append("T").append(n - i).append(" t").append(n - i);
			}

			builder.append(");");
			builder.append("\n  }\n");
		}

		for (int i = 0; i < count + 1; i++)
		{
			builder.append("  @FunctionalInterface\n");
			builder.append("  public static interface Consumer").append(count - i);

			if (count - i > 0) {
				builder.append("<");

				for (int n = i; n < count; n++) {
					if (n > i) {
						builder.append(", ");
					}

					builder.append("T").append(n - i);
				}

				builder.append(">");
			}

			builder.append(" {\n");
			builder.append("    void ").append(" call(");

			for (int n = i; n < count; n++) {
				if (n > i) {
					builder.append(", ");
				}

				builder.append("T").append(n - i).append(" t").append(n - i);
			}

			builder.append(");");
			builder.append("\n  }\n");
		}

		builder.append("}\n");

		try
		{
			writeFile("FlatUtilities.java", new File(controller.outputDirectory, "src/flat"), builder.toString());
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