package flat.java.nodewriters;

import flat.tree.variables.Array;

public abstract class ArrayWriter extends VariableDeclarationWriter
{
	public abstract Array node();
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		if (node().isGenericType()) {
			getWriter(node().getGenericTypeParameter()).writeDefaultType(builder);
			return writeArrayDimensions(builder);
		}

		return writeType(builder, false);
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		builder.append("new ");

		return writeUseExpression(builder);
	}

	@Override
	public StringBuilder writeArrayDimensions(StringBuilder builder)
	{
		return getWriter(node().getDimensions()).writeExpression(builder);
	}
}