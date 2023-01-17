package flat.java.nodewriters;

import flat.tree.*;

public abstract class FlatMethodDeclarationWriter extends MethodDeclarationWriter
{
	public abstract FlatMethodDeclaration node();

	public final StringBuilder writeParameters() {
		return writeParameters(new StringBuilder());
	}

	public final StringBuilder writeParameters(StringBuilder builder) {
		return writeParameters(builder, null);
	}

	public final StringBuilder writeParameters(StringBuilder builder, Value context) {
		return writeParameters(builder, context, null);
	}

	public StringBuilder writeParameters(StringBuilder builder, Value context, String[] paramNames) {
		if (node().doesOverride()) {
			String[] names = new String[node().getParameterList().getNumVisibleChildren()];

			for (int i = 0; i < names.length; i++) {
				names[i] = node().getParameterList().getVisibleChild(i).getName();
			}

			return getWriter(node().getRootDeclaration()).writeParameters(builder, node(), names);
		}

		return getWriter(node().getParameterList()).write(builder, true, true, false, context, paramNames);
	}

	@Override
	public StringBuilder writeName(StringBuilder builder, String name) {
		super.writeName(builder, name);

		if (node().isOverloaded()) {
			builder.append('_').append(node().getOverloadID());
		}

		return builder;
	}

	public StringBuilder writeGenericTypeParameters(StringBuilder builder) {
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