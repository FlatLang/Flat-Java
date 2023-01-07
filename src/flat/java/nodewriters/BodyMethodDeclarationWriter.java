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
	public StringBuilder writeSignature(StringBuilder builder)
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
		writeType(builder);
		writeName(builder);

		return getWriter(node().getParameterList()).write(builder);
	}

	private StringBuilder writeGenericTypeParameters(StringBuilder builder) {
		if (node().getMethodGenericTypeParameterDeclaration().getNumVisibleChildren() == 0) return builder;

		builder.append("<");

		final int[] i = new int[]{0};

		node().getMethodGenericTypeParameterDeclaration().forEachVisibleChild(param -> {
			if (i[0]++ > 0) builder.append(", ");

			getWriter(param).writeExpression(builder);
		});

		return builder.append("> ");
	}
}