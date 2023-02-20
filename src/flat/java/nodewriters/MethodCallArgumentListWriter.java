package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.variables.Variable;

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

			Parameter parameter = method instanceof FlatMethodDeclaration ? ((FlatMethodDeclaration)method).getParameterList().getParameter(i) : null;
			
			boolean optional = parameter != null && parameter.isOptional();

			boolean propagateOptional = optional &&
				node().getParentMethod() instanceof Constructor &&
				method instanceof InitializationMethod;
			
			if (i < values.length)
			{
				if (propagateOptional && values[i] instanceof Variable && ((Variable) values[i]).declaration instanceof Parameter) {
					getWriter((Identifier) values[i]).writeOptionalName(builder);
				} else {
					if (optional) {
						if (values[i].isPrimitive()) {
							builder.append("Optional.of(");
						} else {
							builder.append("Optional.ofNullable(");
						}
					}

					String cast = null;

					if (parameter != null && parameter.isPrimitive()) {
						switch (parameter.getType()) {
							case "Short":
								cast = "(short)";
								break;
							case "Byte":
								cast = "(byte)";
								break;
						}
					}

					if (cast != null) {
						builder.append(cast).append('(');
					}

					getWriter(values[i]).writeExpression(builder);

					if (cast != null) {
						builder.append(')');
					}

					if (optional) {
						builder.append(')');
					}
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