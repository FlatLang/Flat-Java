package flat.java.nodewriters;

import flat.tree.*;

public abstract class MethodCallWriter extends VariableWriter
{
	public abstract MethodCall node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder, String name)
	{
		if (node().isSuperCall()) {
			return builder.append("super");
		}

		if (node().getCallableDeclaration() instanceof FlatMethodDeclaration) {
			boolean appendStatic = node().isWithinStaticContext() || node().isAccessedWithinStaticContext();

			return getWriter((FlatMethodDeclaration)node().getCallableDeclaration()).writeName(builder, name, appendStatic);
		} else if (node().getCallableDeclaration() instanceof MethodDeclaration) {
			return getWriter((MethodDeclaration)node().getCallableDeclaration()).writeName(builder, name);
		}
		
		return super.writeName(builder, name);
	}
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		if (isExtensionDeclaration()) {
			return writeExtensionUseExpression(builder, node());
		} else {
			writeExtensionReferenceAccess(builder);
		}

		writeName(builder);
		
		if (node().getCallableDeclaration() instanceof ClosureDeclaration)
		{
			builder.append(".call");
		}
		
		return getWriter(node().getArgumentList()).write(builder);
	}

	@Override
	public StringBuilder writeExtensionUseExpression(StringBuilder builder, Identifier start) {
		writeName(builder).append('(');

		if (node() == start) {
			builder.append("_this");
		} else {
			getWriter(start).writeExpression(builder, node());
		}

		StringBuilder args = getWriter(node().getArgumentList()).write(new StringBuilder(), false);

		if (args.length() > 0) {
			builder.append(", ").append(args);
		}

		builder.append(')');

		return writeAccessedExpression(builder);
	}
}