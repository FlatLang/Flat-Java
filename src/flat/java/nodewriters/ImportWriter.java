package flat.java.nodewriters;

import flat.tree.*;

public abstract class ImportWriter extends NodeWriter
{
	public abstract Import node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
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

		if (node().isPackageImport()) {
			return builder.append(node().location.replace('/', '.')).append(".*");
		}

		String components = String.join(".", node().location.substring(0, Math.max(0, node().location.lastIndexOf('/'))).split("[/]"));
		builder.append(components);

		if (components.length() > 0) {
			builder.append('.');
		}

		return getWriter(node().getClassDeclaration()).writeName(builder);
	}
}