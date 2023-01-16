package flat.java.nodewriters;

import flat.tree.*;

public abstract class MethodDeclarationWriter extends InstanceDeclarationWriter
{
	public abstract MethodDeclaration node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return builder;
	}

	@Override
	public StringBuilder writeName(StringBuilder builder) {
		if (node().getName().equals("toString") && node().getParameterList().getNumVisibleChildren() == 0) {
			builder.append("flat_");
			return super.writeName(builder);
		}

		return super.writeName(builder);
	}
}