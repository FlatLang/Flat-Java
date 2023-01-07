package flat.java.nodewriters;

import flat.tree.*;

public abstract class MethodCallWriter extends VariableWriter
{
	public abstract MethodCall node();
	
	@Override
	public StringBuilder writeName(StringBuilder builder)
	{
		if (node().getCallableDeclaration() instanceof MethodDeclaration)
		{
			return getWriter((MethodDeclaration)node().getCallableDeclaration()).writeName(builder);
		}
		
		return super.writeName(builder);
	}
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		writeName(builder);
		
		if (node().getCallableDeclaration() instanceof ClosureDeclaration)
		{
			builder.append(".call");
		}
		
		return getWriter(node().getArgumentList()).write(builder);
	}
}