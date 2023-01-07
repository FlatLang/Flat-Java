package flat.java.nodewriters;

import flat.tree.*;

public abstract class IfStatementWriter extends ControlStatementWriter
{
	public abstract IfStatement node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		return builder.append("if (").append(getWriter(node().getCondition()).writeExpression()).append(')');
	}
	
	@Override
	public StringBuilder write(StringBuilder builder)
	{
		writeExpression(builder).append(' ');
		
		getWriter(node().getScope()).write(builder, true, false);
		
		return builder.append(' ');
	}
}