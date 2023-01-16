package flat.java.nodewriters;

import flat.tree.*;

public abstract class FlatMethodDeclarationWriter extends MethodDeclarationWriter
{
	public abstract FlatMethodDeclaration node();

	public StringBuilder writeParameters(StringBuilder builder) {
		return getWriter(node().getParameterList()).write(builder);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder) {
		super.writeName(builder);

		if (node().isOverloaded()) {
			builder.append('_').append(node().getOverloadID());
		}

		return builder;
	}
}