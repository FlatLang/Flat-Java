package flat.java.nodewriters;

import flat.tree.*;

public abstract class ClosureWriter extends VariableWriter
{
	public abstract Closure node();

	@Override
	public StringBuilder writeExpression(StringBuilder builder) {
		getWriter(node().getParentClass()).writeName(builder).append("::");

		return super.writeExpression(builder);
	}
}