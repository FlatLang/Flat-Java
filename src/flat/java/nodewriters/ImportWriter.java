package flat.java.nodewriters;

import flat.tree.ClassDeclaration;
import flat.tree.Import;

import static flat.java.engines.JavaCodeGeneratorEngine.isTestFile;

public abstract class ImportWriter extends NodeWriter
{
	public abstract Import node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		if (node().getClassDeclaration() != null &&
			node().getClassDeclaration().getFileDeclaration() != null &&
			!isTestFile(node().getFileDeclaration().file) &&
			isTestFile(node().getClassDeclaration().getFileDeclaration().file)) {
			return builder;
		}
		if (node().isExternal()) {
			return builder;
		}
		if (!node().isPackageImport() && node().getClassDeclaration() == null) {
			return builder;
		}
		if (!node().isPackageImport() && node().getClassDeclaration().getFileDeclaration() == node().getFileDeclaration()) {
			return builder;
		}
		
		return super.write(builder);
	}
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		builder.append("import ");

		if (node().isStatic) {
			builder.append("static ");
		}

		if (node().isPackageImport()) {
			return builder.append(node().location.replace('/', '.')).append(".*");
		}

		String components = String.join(".", node().location.substring(0, Math.max(0, node().location.lastIndexOf('/'))).split("[/]"));
		builder.append(components);

		if (components.length() > 0) {
			builder.append('.');
		}

		ClassDeclaration c = node().getClassDeclaration();
		ClassDeclaration encapsulating = c.encapsulatingClass;

		StringBuilder prefix = new StringBuilder();

		while (encapsulating != null) {
			prefix.insert(0, getWriter(encapsulating).writeName().append("."));

			encapsulating = encapsulating.encapsulatingClass;
		}

		builder.append(prefix);

		getWriter(c).writeName(builder);

		if (node().isStatic) {
			builder.append(".*");
		}

		return builder;
	}
}