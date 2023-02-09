package flat.java.nodewriters;

import flat.tree.Assignment;
import flat.tree.Value;
import flat.tree.variables.Variable;

public abstract class VariableWriter extends IdentifierWriter
{
	public abstract Variable node();

	public boolean requiresLambdaWrapperClass() {
		return getWriter(node().declaration).requiresLambdaWrapperClass();
	}

	@Override
	public StringBuilder writeUseExpression(StringBuilder builder) {
		if ((node().getRootNode() instanceof Assignment == false || ((Assignment)node().getRootNode()).getAssignedNode() != node()) &&
			requiresLambdaWrapperClass()) {
			return super.writeUseExpression(builder).append(".get()");
		}

		return super.writeUseExpression(builder);
	}

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context) {
		StringBuilder type = getWriter(node().getDeclaration()).writeType(new StringBuilder(), space, convertPrimitive, boxPrimitive, context);

		for (int i = 0; i < node().getArrayAccessDimensions(); i++) {
			type.delete(type.length() - 2, type.length());
		}

		return builder.append(type);
	}
}