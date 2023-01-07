package flat.java.nodewriters;

import flat.tree.*;

public abstract class MethodCallArgumentListWriter extends ArgumentListWriter
{
	public abstract MethodCallArgumentList node();
	
	@Override
	public StringBuilder write(StringBuilder builder, boolean parenthesis)
	{
		MethodCall call = node().getMethodCall();
		
		CallableMethod method = call.getInferredDeclaration();
		
		Value[] values = method instanceof FlatMethodDeclaration ? node().getArgumentsInOrder((FlatMethodDeclaration)method) : node().getArgumentsInOrder();
		
		if (parenthesis)
		{
			builder.append('(');
		}
		
		for (int i = 0; i < method.getParameterList().getNumParameters(); i++)
		{
			if (i > 0)
			{
				builder.append(", ");
			}
			
			boolean optional = method instanceof FlatMethodDeclaration && ((FlatMethodDeclaration)method).getParameterList().getParameter(i).isOptional();
			
			if (i < values.length)
			{
				if (optional)
				{
					builder.append("Optional.ofNullable(");
				}
				
				getWriter(values[i]).writeExpression(builder);
				
				if (optional)
				{
					builder.append(')');
				}
			}
			else
			{
				builder.append("null");
			}
		}
		
		if (parenthesis)
		{
			builder.append(')');
		}
		
		return builder;
	}
}