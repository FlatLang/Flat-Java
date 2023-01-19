package flat.java.nodewriters;

import flat.tree.*;

public abstract class BodyMethodDeclarationWriter extends FlatMethodDeclarationWriter
{
	public abstract BodyMethodDeclaration node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeStaticMethodInstanceOverload(builder);

		return writeSignature(builder).append(' ').append(getWriter(node().getScope()).write());
	}

	public StringBuilder writeStaticMethodInstanceOverload(StringBuilder builder) {
		if (node().isStatic()) {
			writeSignature(builder, null, null, false).append(" {\n");

			if (node().getType() != null) builder.append("return ");

			getWriter(node().getParentClass()).writeName(builder).append(".");
			writeName(builder).append("(");

			for (int i = 0; i < node().getParameterList().getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				Parameter param = node().getParameterList().getVisibleChild(i);

				getWriter(param).writeInitialName(builder);
			}

			builder.append(");\n}\n\n");
		}

		return builder;
	}

	@Override
	public StringBuilder writeSignature(StringBuilder builder, Value context, String name) {
		return writeSignature(builder, context, name, true);
	}

	public StringBuilder writeSignature(StringBuilder builder, Value context, String name, boolean writeStatic)
	{
		if (node().getParentClass() instanceof Trait) {
			if (!writeStatic || !node().isStatic()) {
				builder.append("default ");
			}
		} else {
			writeVisibility(builder);
		}
		if (writeStatic) writeStatic(builder);
		writeGenericTypeParameters(builder);
		writeType(builder, true, true, false, null);
		writeName(builder, name, writeStatic);
		writeParameters(builder, context);

		return builder;
	}
}