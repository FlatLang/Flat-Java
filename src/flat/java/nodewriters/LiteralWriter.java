package flat.java.nodewriters;

import flat.tree.*;

import java.util.Objects;

public abstract class LiteralWriter extends IValueWriter implements AccessibleWriter
{
	public abstract Literal node();
	
	@Override
	public StringBuilder writeUseExpression(StringBuilder builder)
	{
		if (node().isStringInstantiation())
		{
			getWriter(node().getStringInstantiation()).writeExpression(builder);
		} else {
			builder.append(node().value);

			if (isFloat()) {
				builder.append("f");
			}
		}
		
		return writeArrayAccess(builder);
	}
	
	@Override
	public StringBuilder writeExpression(final StringBuilder builder)
	{
		return writeUseExpression(builder).append(writeAccessedExpression());
	}

	public boolean isFloat() {
		if (!isDouble()) return false;

		try {
			Float.valueOf(node().value);

			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isDouble() {
		if (!node().value.contains(".")) return false;

		try {
			Double.valueOf(node().value);

			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}