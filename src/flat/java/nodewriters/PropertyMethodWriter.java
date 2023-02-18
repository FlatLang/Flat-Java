package flat.java.nodewriters;

import flat.tree.*;

public abstract class PropertyMethodWriter extends BodyMethodDeclarationWriter
{
	public abstract PropertyMethod node();

	@Override
	public StringBuilder writeStatic(StringBuilder builder) {
		if (node().getParentClass() instanceof ExtensionDeclaration) {
			return builder.append("static ");
		}

		return super.writeStatic(builder);
	}

	@Override
	public StringBuilder writeParameters(StringBuilder builder, Value context, String[] paramNames, boolean parenthesis, boolean useGivenNames, boolean box) {
		if (parenthesis) {
			builder.append('(');
		}

		if (node().getParentClass() instanceof ExtensionDeclaration) {
			writeExtensionReferenceParameter(builder);
		}

		super.writeParameters(builder, context, paramNames, false, useGivenNames, box);

		if (parenthesis) {
			builder.append(')');
		}

		return builder;
	}
}