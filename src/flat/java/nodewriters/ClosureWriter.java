package flat.java.nodewriters;

import flat.tree.*;

public abstract class ClosureWriter extends VariableWriter
{
	public abstract Closure node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		FlatMethodDeclaration method = node().getMethodDeclaration();

		builder.append("(");

		FlatParameterList params = method.getParameterList();

		if (params.getNumVisibleChildren() > 0) {
			for (int i = 0; i < params.getNumVisibleChildren(); i++) {
				if (i > 0) builder.append(", ");

				getWriter(params.getVisibleChild(i)).writeName(builder);
			}
		}

		builder.append(") -> ");

		return getWriter(method.getScope()).write(builder);

//		getWriter(node().getParentClass()).writeName(builder).append("::");
//
//		return super.writeExpression(builder);
	}
}