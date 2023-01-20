package flat.java.nodewriters;

import flat.tree.Value;
import flat.tree.variables.Variable;

public abstract class VariableWriter extends IdentifierWriter
{
	public abstract Variable node();

	@Override
	public StringBuilder writeType(StringBuilder builder, boolean space, boolean convertPrimitive, boolean boxPrimitive, Value context) {
		StringBuilder type = getWriter(node().getDeclaration()).writeType(new StringBuilder(), space, convertPrimitive, boxPrimitive, context);

		for (int i = 0; i < node().getArrayAccessDimensions(); i++) {
			type.delete(type.length() - 2, type.length());
		}

		return builder.append(type);
	}
}