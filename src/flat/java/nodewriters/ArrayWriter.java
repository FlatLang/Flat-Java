package flat.java.nodewriters;

import flat.tree.variables.Array;

public abstract class ArrayWriter extends VariableDeclarationWriter
{
	public abstract Array node();
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		return writeType(builder, false);
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		builder.append("new ");

		writeUseExpression(builder);

		return builder;
	}

	@Override
	public StringBuilder writeArrayDimensions(StringBuilder builder)
	{
		return getWriter(node().getDimensions()).writeExpression(builder);
	}
}