package flat.java.nodewriters;

import flat.tree.*;

public abstract class BodyMethodDeclarationWriter extends FlatMethodDeclarationWriter
{
	public abstract BodyMethodDeclaration node();

	@Override
	public StringBuilder write(StringBuilder builder)
	{
		return writeSignature(builder).append(' ').append(getWriter(node().getScope()).write());
	}

	@Override
	public StringBuilder writeSignature(StringBuilder builder, Value context, String name)
	{
		if (node().getParentClass() instanceof Trait) {
			if (!node().isStatic()) {
				builder.append("default ");
			}
		} else {
			writeVisibility(builder);
		}
		writeStatic(builder);
		writeGenericTypeParameters(builder);
		writeType(builder, true, true, false, null);
		writeName(builder, name);
		writeParameters(builder, context);

		return builder;
	}
}