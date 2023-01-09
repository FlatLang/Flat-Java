package flat.java.nodewriters;

import flat.tree.*;
import flat.tree.lambda.LambdaMethodDeclaration;

public abstract class ClosureWriter extends VariableWriter
{
	public abstract Closure node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		FlatMethodDeclaration method = node().getMethodDeclaration();

		if (!node().isAccessed()) {
			writeLambdaParams(builder);
		}

		if (method instanceof LambdaMethodDeclaration) {
			return getWriter(method.getScope()).write(builder);
		}

		super.writeExpression(builder);

		builder.append("(");

		FlatParameterList params = method.getParameterList();

		if (params.getNumVisibleChildren() > 0) {
			for (int i = 0; i < params.getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				getWriter(params.getVisibleChild(i)).writeName(builder);
			}
		}

		return builder.append(")");
	}

	public StringBuilder writeLambdaParams(StringBuilder builder) {
		FlatMethodDeclaration method = node().getMethodDeclaration();

		ParameterList<Value> params = node().getClosureDeclaration().getParameterList();

		builder.append("(");

		FlatParameterList usedParams = method.getParameterList();

		if (params.getNumVisibleChildren() > 0) {
			for (int i = 0; i < params.getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				if (i < usedParams.getNumVisibleChildren()) {
					getWriter(usedParams.getVisibleChild(i)).writeName(builder);
				} else {
					builder.append("_").append(i);
				}
			}
		}

		return builder.append(") -> ");
	}
}