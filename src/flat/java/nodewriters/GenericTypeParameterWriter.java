package flat.java.nodewriters;

import flat.tree.MethodCall;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;

public abstract class GenericTypeParameterWriter extends ValueWriter
{
	public abstract GenericTypeParameter node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		builder.append(node().getName()).append(" extends ");

		if (node().getDefaultType() != null && !node().getDefaultType().equals("Object")) {
			builder.append(node().getDefaultType());
		} else {
			builder.append("FlatObject");
		}

		return builder;
	}
}
