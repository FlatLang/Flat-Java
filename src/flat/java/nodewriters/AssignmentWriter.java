package flat.java.nodewriters;

import flat.tree.*;

public abstract class AssignmentWriter extends ValueWriter
{
	public abstract Assignment node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		getWriter(node().getAssigneeNode()).writeExpression(builder).append(" = ");

		Value leftType = node().getAssigneeNode().getReturnedNode();
		Value rightType = node().getAssignmentNode().getReturnedNode();
		String leftTypeStr = getWriter(leftType).writeType(new StringBuilder(), false).toString();
		String rightTypeStr = getWriter(rightType).writeType(new StringBuilder(), false).toString();

		if (!leftTypeStr.equals(rightTypeStr)) {
			builder.append('(');
			builder.append(leftTypeStr);
			builder.append(')');
		}

		return builder.append(getWriter(node().getAssignmentNode()).writeExpression());
	}
}