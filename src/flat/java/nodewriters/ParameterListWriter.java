package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.generics.GenericTypeArgument;

public abstract class ParameterListWriter extends TypeListWriter
{
	public abstract ParameterList node();
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return write(builder, true);
	}
	
	public StringBuilder write(StringBuilder builder, boolean parenthesis) {
		return write(builder, parenthesis, true, false);
	}

	public StringBuilder write(StringBuilder builder, boolean parenthesis, boolean names, boolean box)
	{
		if (parenthesis)
		{
			builder.append('(');
		}
		
		final int[] i = new int[] { 0 };
		
		node().forEachVisibleChild(param -> {
			if (i[0]++ > 0)
			{
				builder.append(", ");
			}

			if (!names && box && param instanceof Value && ((Value) param).isPrimitive()) {
				switch (((Value) param).getType()) {
					case "Byte": builder.append("java.lang.Byte"); break;
					case "Short": builder.append("java.lang.Short"); break;
					case "Int": builder.append("java.lang.Integer"); break;
					case "Long": builder.append("java.lang.Long"); break;
					case "Char": builder.append("java.lang.Character"); break;
					case "Bool": builder.append("java.lang.Boolean"); break;
					default: builder.append("???");
				}
			} else if (!names && param instanceof Parameter) {
				getWriter((Parameter) param).writeType(builder, false);
			} else {
				getWriter(param).writeExpression(builder);
			}
		});
		
		if (parenthesis)
		{
			builder.append(')');
		}
		
		return builder;
	}
}