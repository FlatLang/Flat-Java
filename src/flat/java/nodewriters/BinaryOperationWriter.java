package flat.java.nodewriters;

import flat.tree.*;

public abstract class BinaryOperationWriter extends IValueWriter
{
	public abstract BinaryOperation node();
	
	@Override
	public StringBuilder writeExpression(StringBuilder builder)
	{
		getWriter(node().getLeftOperand()).writeExpression(builder);
		
		builder.append(' ').append(getWriter(node().getOperator()).write()).append(' ');
		
		builder.append(getWriter(node().getRightOperand()).writeExpression());
		
		return builder;
	}
}