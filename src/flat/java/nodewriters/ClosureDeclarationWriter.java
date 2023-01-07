package flat.java.nodewriters;

import flat.tree.*;

public abstract class ClosureDeclarationWriter extends ParameterWriter
{
	public abstract ClosureDeclaration node();

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive) {
		if (node().getType() == null)
		{
			builder.append("FlatUtilities.Consumer");
		}
		else
		{
			builder.append("FlatUtilities.Function");
		}

		builder.append(node().getParameterList().getNumParameters());

		if (node().getParameterList().getNumVisibleChildren() > 0) {
			builder.append("<");

			getWriter(node().getParameterList()).write(builder, false, false, true);

			if (node().getType() != null) {
				if (node().getParameterList().getNumParameters() > 0) {
					builder.append(", ");
				}

				builder.append(node().isPrimitive() ? node().getTypeClass().getName() : node().getType());
			}

			builder.append(">");
		}

		if (space) {
			builder.append(" ");
		}

		return builder;
	}

	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		if (node().isOptional()) {
			builder.append("Optional<");
		}

		writeType(builder, false);

		if (node().isOptional()) {
			builder.append("> ");
			writeOptionalName(builder);
		} else {
			builder.append(" ");
			getWriter(node()).writeName(builder);
		}

		return builder;
	}
}