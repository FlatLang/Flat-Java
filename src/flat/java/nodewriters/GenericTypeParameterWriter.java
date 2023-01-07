package flat.java.nodewriters;

import flat.tree.MethodCall;
import flat.tree.generics.GenericTypeArgument;
import flat.tree.generics.GenericTypeParameter;

public abstract class GenericTypeParameterWriter extends ValueWriter
{
	public abstract GenericTypeParameter node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		return builder.append(node().getName());
	}
}
