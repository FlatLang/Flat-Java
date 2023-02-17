package flat.java.nodewriters;

import flat.tree.ExtensionMethodDeclaration;
import flat.tree.Value;

public abstract class ExtensionMethodDeclarationWriter extends BodyMethodDeclarationWriter
{
	public abstract ExtensionMethodDeclaration node();

	@Override
	public StringBuilder writeParameters(StringBuilder builder, Value context, String[] paramNames, boolean parenthesis, boolean useGivenNames, boolean box) {
		if (parenthesis) {
			builder.append('(');
		}

		getWriter(node().getParameterList().getReferenceParameter()).writeType(builder, true);

		builder.append("_this");

		if (node().getParameterList().getNumVisibleChildren() > 0) {
			builder.append(", ");
		}

		super.writeParameters(builder, context, paramNames, false, useGivenNames, box);

		if (parenthesis) {
			builder.append(')');
		}

		return builder;
	}
}